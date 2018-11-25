package tech.snapcycle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);
        createListener(InfoActivity.this, MainActivity.class, R.id.about_return_button);
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
}
