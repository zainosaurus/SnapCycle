package tech.snapcycle;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.FaceAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //visionTest();
    }

    public void visionTest() {

        // Create new thread
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Vision.Builder visionBuilder = new Vision.Builder(
                        new NetHttpTransport(),
                        new AndroidJsonFactory(),
                        null);

                visionBuilder.setVisionRequestInitializer(
                        new VisionRequestInitializer("AIzaSyDTvLj9OYUWA23v_TSQroUbisypXaJIfXU"));

                Vision vision = visionBuilder.build();

                // Convert photo to byte array
                InputStream inputStream =
                        getResources().openRawResource(R.raw.apollo9_prime_crew);
                byte[] photoData = new byte[0];
                try {
                    photoData = IOUtils.toByteArray(inputStream);
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // More code here
                Image inputImage = new Image();
                inputImage.encodeContent(photoData);

                Feature desiredFeature = new Feature();
                desiredFeature.setType("FACE_DETECTION");

                AnnotateImageRequest request = new AnnotateImageRequest();
                request.setImage(inputImage);
                request.setFeatures(Arrays.asList(desiredFeature));

                BatchAnnotateImagesRequest batchRequest =
                        new BatchAnnotateImagesRequest();

                batchRequest.setRequests(Arrays.asList(request));

                BatchAnnotateImagesResponse batchResponse =
                        null;
                try {
                    batchResponse = vision.images().annotate(batchRequest).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                List<FaceAnnotation> faces = batchResponse.getResponses()
                        .get(0).getFaceAnnotations();

                // Count faces
                int numberOfFaces = faces.size();

                // Get joy likelihood for each face
                String likelihoods = "";
                for(int i=0; i<numberOfFaces; i++) {
                    likelihoods += "\n It is " +
                            faces.get(i).getJoyLikelihood() +
                            " that face " + i + " is happy";
                }

                // Concatenate everything
                final String message =
                        "This photo has " + numberOfFaces + " faces" + likelihoods;
                System.out.println(message);
                // Display toast on UI thread
                Toast.makeText(getApplicationContext(),
                        message, Toast.LENGTH_LONG).show();
            }
        });


    }
}
