package com.example.user.licenta2.UI;


import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.Comparator;
import java.util.List;

public class ActivityCVList extends AppCompatActivity implements View.OnClickListener{

    private List<File> CVs;
    private ArrayList<String> CVsName;
    private Spinner spinnerOrderBy;
    private Button btnOrder;
    private File[] xmlFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cvlist);

        spinnerOrderBy = (Spinner) findViewById(R.id.sp_orderBy);
        btnOrder = (Button) findViewById(R.id.btnSort);
        btnOrder.setClickable(true);
        btnOrder.setOnClickListener(this);

        // Create items for spinner
        List<String> itemsInSpinner = new ArrayList<>();
        itemsInSpinner.add("Order by Name ASC");
        itemsInSpinner.add("Order by Name DESC");
        itemsInSpinner.add("Order by Date ASC");
        itemsInSpinner.add("Order by Date DESC");

        // set ArrayAdapter
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemsInSpinner);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        // set ArrayAdapter to Spinner
        spinnerOrderBy.setAdapter(spinnerAdapter);

        // get all files
        xmlFiles = getXMLs();
        updateCvlist();
    }

    private void updateCvlist() {
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

                            try {
                                // instantiate an pdfGenerator and create .pdf file in ExternalStorage
                                pdfGenerator updateCV = new pdfGenerator(getApplicationContext(), selectedString, getCVFromXml);
                                String url = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CVs2/" + selectedString + ".pdf";
                                // go to and get that .pdf file.
                                File customPrivateDir = ActivityCVList.this.getExternalFilesDir("CVs2");
                                File myPdfFile = new File(customPrivateDir, selectedString + ".pdf");

//                                Log.d("MyDebug", "ActivityCVList: " + myPdfFile.getPath());

                                if (!myPdfFile.exists())
                                    Log.d("MyDebug", "file path is incorrect.");

                                if (!myPdfFile.canRead())
                                    Log.d("MyDebug", "file can not be readed.");

                                if (myPdfFile.exists() && myPdfFile.canRead()) {
                                    //                                    // open .pdf file with pdfViewer
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(myPdfFile.getAbsolutePath()));

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSort:
                String spinnerItem = spinnerOrderBy.getSelectedItem().toString();
//                Log.d("MyDebug", "SpinnerSelectedItem: " + spinnerItem);
                if (spinnerItem.toUpperCase().compareTo("ORDER BY NAME ASC") == 0) {
                    xmlFiles = getXMLs("byNameAsc");
                    updateCvlist();
                }
                else if (spinnerItem.toUpperCase().compareTo("ORDER BY NAME DESC") == 0) {
                    xmlFiles = getXMLs("byNameDesc");
                    updateCvlist();
                }
                else if (spinnerItem.toUpperCase().compareTo("ORDER BY DATE ASC") == 0) {
                    xmlFiles = getXMLs("byDateAsc");
                    updateCvlist();
                }
                else if (spinnerItem.toUpperCase().compareTo("ORDER BY DATE DESC") == 0) {
                    xmlFiles = getXMLs("byDateDesc");
                    updateCvlist();
                }
                break;
            default:

        }
    }

    private File[] getXMLs() {
        try {
            File[] tempFile;
            File x = new File(getApplicationInfo().dataDir + "/XMLs/");
            tempFile = x.listFiles();

            return tempFile;
        }
        catch (Exception e) {

        }

        return null;
    }

    private File[] getXMLs(String orderBy) {
        try {

            File[] tempFile;
            File x = new File(getApplicationInfo().dataDir + "/XMLs/");
            tempFile = x.listFiles();



            if(orderBy.toUpperCase().equals("BYNAMEASC")) {
                Log.d("MyDebug", "ByNameAsc");
                Arrays.sort(tempFile, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        if(o1.getName().toUpperCase().compareToIgnoreCase(o2.getName().toUpperCase()) > 1) {
                            return 1;
                        }
                        else if (o1.getName().toUpperCase().compareToIgnoreCase(o2.getName().toUpperCase()) < 1) {
                            return -1;
                        }
                        else
                            return 0;

                    }
                });
            }
            else if(orderBy.toUpperCase().equals("BYNAMEDESC")) {
                Log.d("MyDebug", "ByNameDesc");
                Arrays.sort(tempFile, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        if(o1.getName().toUpperCase().compareToIgnoreCase(o2.getName().toUpperCase()) < 1) {
                            return 1;
                        }
                        else if (o1.getName().toUpperCase().compareToIgnoreCase(o2.getName().toUpperCase()) > 1) {
                            return -1;
                        }
                        else
                            return 0;

                    }
                });
            }
            else if(orderBy.toUpperCase().equals("BYDATEASC")) {
                Log.d("MyDebug", "ByNameDesc");
                Arrays.sort(tempFile, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        SimpleDateFormat date = new SimpleDateFormat("YYYY.MM.dd");
                        if(date.format(o1.lastModified()).compareTo(date.format(o2.lastModified())) > 1) {
                            return 1;
                        }
                        else if(date.format(o1.lastModified()).compareTo(date.format(o2.lastModified())) < 1) {
                            return -1;
                        }
                        else {
                            return 0;
                        }
                    }
                });
            }
            else if(orderBy.toUpperCase().equals("BYDATEDESC")) {
                Log.d("MyDebug", "ByDateDesc");
                Arrays.sort(tempFile, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        SimpleDateFormat date = new SimpleDateFormat("YYYY.MM.dd");
                        if(date.format(o1.lastModified()).compareTo(date.format(o2.lastModified())) < 1) {
                            return 1;
                        }
                        else if(date.format(o1.lastModified()).compareTo(date.format(o2.lastModified())) > 1) {
                            return -1;
                        }
                        else {
                            return 0;
                        }

                    }
                });
            }
            for(File f : tempFile) {
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY.MM.dd");
                Log.d("MyDebug", sdf.format(f.lastModified()).toString());
            }
            return tempFile;
        }
        catch (Exception e) {

        }

        return null;
    }

}
