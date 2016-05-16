package com.light.light;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    ToggleButton tButton;
    static Camera camera = null;
    Camera.Parameters para;
    boolean isFlash = false;
    boolean isOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tButton = (ToggleButton) findViewById(R.id.btnSwitch);

        // int permissionCheck = ContextCompat.checkSelfPermission(android.permission.CAMERA);

        try {
            //To check camera availability
            if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                camera = Camera.open();
                para = camera.getParameters();
                isFlash = true;
            }
            tButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    if (isFlash) {
                        //Turn flash on/off
                        if (!isOn) {
                            para.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                            camera.setParameters(para);
                            camera.startPreview();
                            isOn = true;
                        } else {
                            para.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                            camera.setParameters(para);
                            camera.stopPreview();
                            isOn = false;
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Error");
                        builder.setMessage("Flash not available");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Error", "" + e);
        }
    }

    protected void onStop() {
        super.onStop();
        if (camera != null) {
            camera.release();
            camera = null;
        }
        super.onPause();
    }


}


