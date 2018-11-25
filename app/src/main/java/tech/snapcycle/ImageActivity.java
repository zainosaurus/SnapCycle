package tech.snapcycle;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


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
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        openCameraForPicture();
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
            List<String> results = new ImageDetection().detectWebResults(mCurrentPhotoPath);
            TextView imageIdentifierTextView = findViewById(R.id.image_identifier_text);
            if (results.size() >= 3) {
                TextView topTextView = findViewById(R.id.image_top_info);
                TextView middleTextView = findViewById(R.id.image_middle_info);
                TextView bottomTextView = findViewById(R.id.image_bottom_info);
                imageIdentifierTextView.setText(RESULTS_FOUND);
                topTextView.setText(results.get(0));
                middleTextView.setText(results.get(1));
                bottomTextView.setText(results.get(2));
            } else {
                imageIdentifierTextView.setText(NO_RESULTS_FOUND);
            }
        }
    }
}
