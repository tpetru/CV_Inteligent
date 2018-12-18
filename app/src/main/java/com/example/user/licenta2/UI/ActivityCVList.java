package com.example.user.licenta2.UI;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.licenta2.Backend.CVListAdapter;
import com.example.user.licenta2.Backend.pdfGenerator;
import com.example.user.licenta2.Backend.xmlParser;
import com.example.user.licenta2.CV;
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

        File x = new File(getApplicationInfo().dataDir + "/XMLs/");
        File[] fileNames = x.listFiles();

        CVs = new ArrayList<>(Arrays.asList(fileNames));

        CVsName = new ArrayList<String>();


        for (File f : CVs) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY");
            CVsName.add(sdf.format(f.lastModified()) + "_" + f.getName());
        }

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
                        selectedString = selectedString.substring(11);

//                        Log.d("MyDebug", selectedString);

                        selectedString = selectedString.substring(0, selectedString.length() - 4);

                        xmlParser parser = new xmlParser();
                        CV getCVFromXml = parser.readXML(getApplicationInfo().dataDir, selectedString + ".xml", selectedString);

                        String xmlFilePath = getApplicationInfo().dataDir + "/pdfs/" + selectedString + ".pdf";
                        try {
                            pdfGenerator updateCV = new pdfGenerator(getApplicationContext(), selectedString, getCVFromXml);

                            File customPrivateDir = getExternalFilesDir("CVs");
                            File myPdfFile = new File(customPrivateDir, selectedString + ".pdf");

//                            if(!myPdfFile.exists()) Log.d("MyDebug", "file path is incorrect.");
//                            if(!myPdfFile.canRead()) Log.d("MyDebug", "file cand be readed");

                            if(myPdfFile.exists() && myPdfFile.canRead()) {
                                Intent target = new Intent(Intent.ACTION_VIEW);
                                target.setDataAndType(Uri.fromFile(myPdfFile), "application/pdf");
                                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                                Intent intent = Intent.createChooser(target, "Open File");
                                try {
                                    startActivity(intent);
                                } catch (ActivityNotFoundException e) {
                                    // Instruct the user to install a PDF reader here, or something
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
