package com.example.user.licenta2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.licenta2.UI.ActivityCVList;
import com.example.user.licenta2.UI.ActivityCreateCV;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_newCV, btn_CVlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Check whether this app has write external storage permission or not.
        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // If do not grant write external storage permission.
        if(writeExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
        {
            // Request user to grant write external storage permission.
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    1);

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                    1);
        }

        checkPermissionForMic();

//        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));

        btn_newCV = (Button) findViewById(R.id.btn_MainActivity_createCV);
        btn_newCV.setOnClickListener(this);

        btn_CVlist = (Button) findViewById(R.id.btn_MainActivity_CVList);
        btn_CVlist.setOnClickListener(this);
    }


    public String getPath() {
        return getApplicationInfo().dataDir;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_MainActivity_createCV:
                Intent createCV = new Intent (MainActivity.this, ActivityCreateCV.class);
                EditText et_cv = (EditText) findViewById(R.id.et_CVName);

                String cvName;

                if(et_cv.getText().toString().length() == 0)
                    cvName = "default";
                else
                    cvName = et_cv.getText().toString();

                createCV.putExtra("cvName", cvName);
                MainActivity.this.startActivity(createCV);
                break;

            case R.id.btn_MainActivity_CVList:
                Intent CVLists = new Intent(MainActivity.this, ActivityCVList.class);
                MainActivity.this.startActivity(CVLists);
                break;

            default:
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            int grantResultsLength = grantResults.length;
            if (grantResultsLength > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "You grant write/read external storage permission.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "You denied write/read external storage permission.", Toast.LENGTH_LONG).show();
            }
        }
    }

    /* check permission for MIC */
    private void checkPermissionForMic() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {

                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Aplicatia are nevoie de acces la microfon.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

}
