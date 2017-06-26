package com.gregttn.snackbarsample;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onShowSnackbar(View view) {
        View rootLayout = findViewById(R.id.root_layout);
        final Snackbar snackbar = Snackbar.make(rootLayout, R.string.test_snackbar, Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction(R.string.dismiss_button, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });

        snackbar.show();
    }
}
