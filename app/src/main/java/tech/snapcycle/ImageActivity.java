package tech.snapcycle;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.AsyncTask;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ImageActivity extends Activity {
    private static final int IMAGE_CAPTURE_REQUEST_NUMBER = 2;
    private String mCurrentPhotoPath;
    private static final String RESULTS_FOUND = "Results were found for the image";
    private static final String NO_RESULTS_FOUND = "No results were found for the image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity);
        createListener(ImageActivity.this, MainActivity.class, R.id.image_return_button);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        openCameraForPicture();
    }

    private void createListener(final Context context, final Class<?> activity, int id) {
        Button barcode_button = findViewById(id);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, activity);
                startActivity(intent);
            }
        };
        barcode_button.setOnClickListener(listener);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void openCameraForPicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {// Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, IMAGE_CAPTURE_REQUEST_NUMBER);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CAPTURE_REQUEST_NUMBER && resultCode == RESULT_OK) {
            ImageView imageView = findViewById(R.id.image_recognition_picture_view);
            imageView.setImageURI(Uri.fromFile(new File(mCurrentPhotoPath)));
            // Process image here
            new JustDoIt().execute(mCurrentPhotoPath);
        }
    }

    class JustDoIt extends AsyncTask<String, Void, List<String>> {

        @Override
        protected List<String> doInBackground(String... filename) {
            List<String> results = new ImageDetection().detectLabels(mCurrentPhotoPath);
            return results;
        }

        protected void onPostExecute (List<String> results) {
            TextView imageIdentifierTextView = findViewById(R.id.image_identifier_text);
            TextView recyclableView = findViewById(R.id.recyclable_field);

            try {
                TextView topTextView = findViewById(R.id.image_top_info);
                TextView middleTextView = findViewById(R.id.image_middle_info);
                TextView bottomTextView = findViewById(R.id.image_bottom_info);
                imageIdentifierTextView.setText(RESULTS_FOUND);
                int index = 0;
                if (results.get(index++) == "product") {
                    index++;
                }
                topTextView.setText(results.get(index++));
                if (results.get(index) == "product") {
                    index++;
                }
                middleTextView.setText(results.get(index++));
                if (results.get(index) == "product") {
                    index++;
                }
                bottomTextView.setText(results.get(index));
            } catch (Exception e) {
                // do nothing
            }

            if (results.size() == 0) {
                imageIdentifierTextView.setText(NO_RESULTS_FOUND);
            }

            // Recycling status
            if (GarbageDeterminer.checkGarbage(results)) {
                recyclableView.setText("This is trash");
            } else {
                recyclableView.setText("This can be recycled");
            }
        }
    }
}
