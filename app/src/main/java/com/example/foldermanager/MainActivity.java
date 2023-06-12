package com.example.foldermanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends Activity {

    RecyclerView recyclerView;
    TextView textView, permission;
    Button button;
    String pathExternal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.listFolder);
        textView = findViewById(R.id.textView);
        permission = findViewById(R.id.textPermission);
        button = findViewById(R.id.btnConfirm);
        pathExternal = getIntent().getStringExtra("path");
        if(checkPermission())
        {
            folderList(pathExternal);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    requestPermission();
            }
        });

    }
    private boolean checkPermission()
    {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else return false;
    }

    public void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 111)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                folderList(null);
            }
            else
            {
                openAppSettings();
            }
        }
    }

    public void folderList(String pathExternal)
    {
        String path;
        if (pathExternal == null)
        {
             path = Environment.getExternalStorageDirectory().getPath();
        }
        else
        {
            path = pathExternal;
        }
        File root = new File(path);
        File[] filesAndFolders= root.listFiles();
        if(filesAndFolders == null || filesAndFolders.length == 0)
        {
            textView.setVisibility(View.VISIBLE);
            permission.setVisibility(View.INVISIBLE);
            button.setVisibility(View.INVISIBLE);
            return;
        }
        textView.setVisibility(View.INVISIBLE);
        permission.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), filesAndFolders));

        Toast.makeText(MainActivity.this, "Permissao Confirmada", Toast.LENGTH_SHORT).show();
    }

    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

}

