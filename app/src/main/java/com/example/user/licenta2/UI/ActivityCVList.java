package com.example.user.licenta2.UI;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.licenta2.Backend.CVListAdapter;
import com.example.user.licenta2.Backend.pdfGenerator;
import com.example.user.licenta2.Backend.xmlParser;
import com.example.user.licenta2.CV;
import com.example.user.licenta2.MainActivity;
import com.example.user.licenta2.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ActivityCVList extends AppCompatActivity {

    private List<File> CVs;
    private ArrayList<String> CVsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cvlist);

        // get all files
        File[] xmlFiles = new File[] {};
        xmlFiles = getXMLs();

        if (xmlFiles == null || xmlFiles.length == 0) {
            Intent mainActivity = new Intent(ActivityCVList.this, MainActivity.class);
            ActivityCVList.this.startActivity(mainActivity);
            Toast.makeText(this, "There are no CVs available.", Toast.LENGTH_LONG).show();
        }
        else {
            CVs = new ArrayList<>(Arrays.asList(xmlFiles));
            CVsName = new ArrayList<String>();

            // create Strings with CVname and data last modified: dd.MM.YYYY_cvName
            for (File f : CVs) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY");
                CVsName.add(sdf.format(f.lastModified()) + "_" + f.getName());
            }

            // sort Strings alphabetically
            Collections.sort(CVsName, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareToIgnoreCase(o2);
                }
            });

            CVListAdapter CVsAdapter = new CVListAdapter(getApplicationContext(), CVsName);


            final ListView listView = (ListView) findViewById(R.id.lv_CVList);
            listView.setAdapter(CVsAdapter);

            listView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {


                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                            String selectedString = (String) listView.getItemAtPosition(position).toString();
                            selectedString = selectedString.substring(11, selectedString.length() - 4); // get only CV name. Remove data and '.xml'

                            // instantiate an xmlParser
                            xmlParser parser = new xmlParser();

                            // Parse xml and return CV object.
                            CV getCVFromXml = parser.readXML(getApplicationInfo().dataDir, selectedString + ".xml", selectedString);
//                            Log.d("MyDebug", getCVFromXml.getSkills().get(0).getNume());

                            try {

                                // instantiate an pdfGenerator and create .pdf file in ExternalStorage
                                pdfGenerator updateCV = new pdfGenerator(getApplicationContext(), selectedString, getCVFromXml);
                                String url = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CVs3/" + selectedString + ".pdf";

                                // go to and get that .pdf file.
                                File customPrivateDir = ActivityCVList.this.getExternalFilesDir("CVs2");
                                File myPdfFile = new File(customPrivateDir, selectedString + ".pdf");

                                Log.d("MyDebug", "ActivityCVList: " + myPdfFile.getPath());

                                if (!myPdfFile.exists())
                                    Log.d("MyDebug", "file path is incorrect.");
                                if (!myPdfFile.canRead()) Log.d("MyDebug", "file cand be readed");

                                if (myPdfFile.exists() && myPdfFile.canRead()) {

//                                    // open .pdf file with pdfViewer
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(myPdfFile.getAbsolutePath()));
                                    Log.d("MyDebug", "mimeType: " + mimeType);
                                    intent.setDataAndType(Uri.fromFile(myPdfFile), mimeType);
                                    Intent intent1 = Intent.createChooser(intent, "Open CV with...");

                                    try {
                                        ActivityCVList.this.startActivity(intent1);
                                    } catch (Exception e) {
                                        Log.e("MyErr", e.toString());
                                    }
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                Log.e("MyErr", e.toString());
                            }
                        }
                    }
            );
        }
    }

    private File[] getXMLs() {
        try {
            File x = new File(getApplicationInfo().dataDir + "/XMLs/");
            return x.listFiles();
        }
        catch (Exception e) {

        }

        return null;
    }
}
