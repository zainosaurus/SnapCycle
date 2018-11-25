package tech.snapcycle;

import android.app.Activity;
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
import com.google.api.services.vision.v1.model.WebDetection;
import com.google.api.services.vision.v1.model.WebEntity;
import com.google.api.services.vision.v1.model.WebLabel;
//import com.google.cloud.vision.v1.AnnotateImageRequest;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageDetection imgdetect = new ImageDetection();
        imgdetect.detectWebResults("~/dev/SnapCycle/app/main/res/raw/timscup.jpg");
//        visionTest();
    }

//    public void visionTest() {
//        // Create new thread
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                Vision.Builder visionBuilder = new Vision.Builder(
//                        new NetHttpTransport(),
//                        new AndroidJsonFactory(),
//                        null);
//
//                visionBuilder.setVisionRequestInitializer(
//                        new VisionRequestInitializer("AIzaSyDTvLj9OYUWA23v_TSQroUbisypXaJIfXU"));
//
//                Vision vision = visionBuilder.build();
//
//                // Convert photo to byte array
//                InputStream inputStream = Files.newInputStream(Paths.get(filePath));
//                byte[] photoData = new byte[0];
//                try {
//                    photoData = IOUtils.toByteArray(inputStream);
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                // More code here
//                Image inputImage = new Image();
//                inputImage.encodeContent(photoData);
//
//                Feature desiredFeature = new Feature();
//                desiredFeature.setType("WEB_DETECTION");
//
//                AnnotateImageRequest request = new AnnotateImageRequest();
//                request.setImage(inputImage);
//                request.setFeatures(Arrays.asList(desiredFeature));
//
//                BatchAnnotateImagesRequest batchRequest = new BatchAnnotateImagesRequest();
//
//                batchRequest.setRequests(Arrays.asList(request));
//
//                BatchAnnotateImagesResponse batchResponse = null;
//                try {
//                    batchResponse = vision.images().annotate(batchRequest).execute();
//                } catch (IOException e) {
//                    System.out.println("------ ERROR GETTING ANNOTATIONS ----");
//                    e.printStackTrace();
//                }
//
//                WebDetection webResponse = batchResponse.getResponses().get(0).getWebDetection();
//                List<WebLabel> labels = webResponse.getBestGuessLabels();
//                List<WebEntity> entities = webResponse.getWebEntities();
//
//                // Count labels
//                int numberOfLabels = labels.size();
//                int numEntities = entities.size();
//
//                //Get label strings
//                String labelStrings = "";
//                for(int i=0; i<numberOfLabels; i++) {
//                    labelStrings += "\n" + labels.get(i).getLabel();
//                }
//
//                // Get entity descriptions
//                String entityStrings = "";
//                for(int i=0; i<numEntities; i++) {
//                    entityStrings += "\n" + entities.get(i).getDescription();
//                }
//
//                // Concatenate everything
//                final String message = "This photo has " + numberOfLabels + " labels and " + numEntities + " entities:";
//                System.out.println(message);
//                System.out.println(labelStrings);
//                System.out.println(entityStrings);
                // Display toast on UI thread
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                message, Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        });


//    }
}
