package com.example.permissionhandling;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.namespace.R;
import com.google.android.material.button.MaterialButton;


import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private MaterialButton singlePermissionBtn;
    private MaterialButton multiplePermissionBtn;
    private TextView resultTv;

    private static final String TAG = "PERMISSION_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        singlePermissionBtn = findViewById(R.id.singlePermissionBtn);
        multiplePermissionBtn = findViewById(R.id.multiplePermissionBtn);
        resultTv = findViewById(R.id.resultTv);

        singlePermissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String permission = Manifest.permission.ACCESS_BACKGROUND_LOCATION;
                permissionLauncherSingle.launch(permission);
            }
        });
        multiplePermissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.CALL_PHONE};

                permissionLauncherMutliple.launch(permissions);

            }
        });
    }

    private ActivityResultLauncher<String> permissionLauncherSingle = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean isGranted) {
                    Log.d(TAG, "onActivityResult:isGranted:"+isGranted);

                    if (isGranted) {
                        singlePermissionGranted();

                    } else {
                        Log.d(TAG, "onActivityResult:All or some Permission denied .... ");
                        Toast.makeText(MainActivity.this, "All or some Permission denied ....",
                                Toast.LENGTH_SHORT).show();

                    }
                }

            });


    private  ActivityResultLauncher<String[]> permissionLauncherMutliple = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            new ActivityResultCallback<Map<String,Boolean>>() {
                @Override
                public void onActivityResult(Map<String,Boolean> result) {
                    Log.d(TAG, "onActivityResult:isGranted:");
                    boolean allAreGranted=true;
                    for (boolean isGranted:result.values()){
                        Log.d(TAG, "onActivityResult:isGranted:"+isGranted);
                        allAreGranted=allAreGranted && isGranted;
                    }
                    if(allAreGranted){
                        MultiplePermissionGranted();
                    }else {
                        Log.d(TAG, "onActivityResult:All or some Permission denied .... ");
                        Toast.makeText(MainActivity.this, "All or some Permission denied ....",
                                Toast.LENGTH_SHORT).show();
                    }
                    }
            });

    private void singlePermissionGranted(){
        resultTv.setText("Single Permission granted .You cann do your tasks .... ");
    }
    private void MultiplePermissionGranted(){
        resultTv.setText("All or  Permissions granted .You cann do your tasks .... ");
    }
}






