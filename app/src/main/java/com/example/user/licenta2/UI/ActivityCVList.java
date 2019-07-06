package com.example.user.licenta2.UI;


import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
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

public class ActivityCVList extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private List<File> CVs;
    private ArrayList<String> CVsName;
    private Spinner spinnerOrderBy;
    private File[] xmlFiles;
    private ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cvlist);

        spinnerOrderBy = (Spinner) findViewById(R.id.sp_orderBy);
        spinnerOrderBy.setOnItemSelectedListener(this);

        // Create items for spinner
        List<String> itemsInSpinner = new ArrayList<>();
        itemsInSpinner.add("Sort by name in ascending order");
        itemsInSpinner.add("Sort by name in descending order");
        itemsInSpinner.add("Sort by date in ascending order");
        itemsInSpinner.add("Sort by date in descending order");

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
            registerForContextMenu(listView);
            myListView = listView;


//            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                @Override
//                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//                                               int pos, long id) {
//                    // TODO Auto-generated method stub
//
//                    registerForContextMenu(arg1);
//                    Log.d("MyDebug", "Loooong press " + pos);
//
//                    return true;
//                }
//            });
            listView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            String selectedString = (String) listView.getItemAtPosition(position).toString();
                            createPDF(selectedString, position, (short) 1);
                        }
                    }
            );
        }
    }

    private void createPDF(String selectedString, int position, short template) {
        selectedString = selectedString.substring(11, selectedString.length() - 4); // get only CV name. Remove data and '.xml'

        // instantiate an xmlParser
        xmlParser parser = new xmlParser();

        // Parse xml and return CV object.
        CV getCVFromXml = parser.readXML(getApplicationInfo().dataDir, selectedString + ".xml", selectedString);

        try {
            // instantiate an pdfGenerator and create .pdf file in ExternalStorage
            pdfGenerator updateCV = new pdfGenerator(getApplicationContext(), selectedString, getCVFromXml, template);
//            String url = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CVs2/myCV.pdf";

            // go to and get that .pdf file.
            File customPrivateDir = ActivityCVList.this.getExternalFilesDir("CVs2");
            File myPdfFile = new File(customPrivateDir, "myCV.pdf");
//            Log.d("MyDebug", ".getPath: " + myPdfFile.getPath());
//            Log.d("MyDebug", ".getAbsPath: " + myPdfFile.getAbsolutePath());


            if (!myPdfFile.exists() || !myPdfFile.canRead()) {
                Toast.makeText(getApplicationContext(), "There was a problem, please restart the app.", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Your CV was generated.", Toast.LENGTH_LONG).show();
            }

            if (!myPdfFile.exists()) {
                Log.d("MyDebug", "file path is incorrect.");
            }

            if (!myPdfFile.canRead()) {
                Log.d("MyDebug", "file can not be readed.");
            }

            if (myPdfFile.exists() && myPdfFile.canRead()) {
                // open .pdf file with pdfViewer
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(myPdfFile.getAbsolutePath()));
//
//                intent.setDataAndType(Uri.fromFile(myPdfFile), mimeType);
//                Intent intent1 = Intent.createChooser(intent, "Open CV with...");

                try {
                    Intent pdfIntentViewer = new Intent(Intent.ACTION_VIEW);
                    pdfIntentViewer.setDataAndType(Uri.fromFile(myPdfFile), "application/pdf");
                    pdfIntentViewer.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    Intent intent = Intent.createChooser(pdfIntentViewer, "Open PDF file");
                    ActivityCVList.this.startActivity(intent);

                } catch (Exception e) {
                    Log.d("MyDebug: ", e.toString());
                    Log.e("MyErr:", e.toString());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            Log.e("MyErr", e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btnSort:
//                String spinnerItem = spinnerOrderBy.getSelectedItem().toString();
//                if (spinnerItem.toUpperCase().compareTo("ORDER BY NAME ASC") == 0) {
//                    xmlFiles = getXMLs("byNameAsc");
//                    updateCvlist();
//                }
//                else if (spinnerItem.toUpperCase().compareTo("ORDER BY NAME DESC") == 0) {
//                    xmlFiles = getXMLs("byNameDesc");
//                    updateCvlist();
//                }
//                else if (spinnerItem.toUpperCase().compareTo("ORDER BY DATE ASC") == 0) {
//                    xmlFiles = getXMLs("byDateAsc");
//                    updateCvlist();
//                }
//                else if (spinnerItem.toUpperCase().compareTo("ORDER BY DATE DESC") == 0) {
//                    xmlFiles = getXMLs("byDateDesc");
//                    updateCvlist();
//                }
//                break;
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

    /* Get XMLS in specific order */
    private File[] getXMLs(String orderBy) {
        try {

            File[] tempFile;
            File x = new File(getApplicationInfo().dataDir + "/XMLs/");
            tempFile = x.listFiles();


//            Log.d("MyDebug", orderBy.toString());
            if(orderBy.toUpperCase().equals("BYNAMEASC")) {
//                Log.d("MyDebug", "ByNameAsc");
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
//                Log.d("MyDebug", "ByNameDesc");
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
//                Log.d("MyDebug", "ByNameDesc");
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
//                Log.d("MyDebug", "ByDateDesc");
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
//            for(File f : tempFile) {
//                SimpleDateFormat sdf = new SimpleDateFormat("YYYY.MM.dd");
//                Log.d("MyDebug", sdf.format(f.lastModified()).toString());
//            }
            return tempFile;
        }
        catch (Exception e) {

        }

        return null;
    }

    /* methods when user change the value from Spinner */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0) {
            xmlFiles = getXMLs("byNameAsc");
            updateCvlist();
        }
        else if(position == 1) {
            xmlFiles = getXMLs("byNameDesc");
            updateCvlist();
        }
        else if(position == 2) {
            xmlFiles = getXMLs("byDateAsc");
            updateCvlist();
        }
        else if(position == 3) {
            xmlFiles = getXMLs("byDateDesc");
            updateCvlist();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /* methods for when use long press an item from pdf */
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        Long myId = new Long(info.id);
        int myIntId = myId.intValue();

        switch (item.getItemId()) {
            case R.id.op_WithTemplate1:
                Log.d("MyDebug", "Open with template 1: " + myListView.getItemAtPosition(myIntId).toString());
                createPDF(myListView.getItemAtPosition(myIntId).toString(), myIntId, (short) 1);
                break;

            case R.id.op_WithTemplate2:
                Log.d("MyDebug", "Open with template 2: " + myListView.getItemAtPosition(myIntId).toString());
                createPDF(myListView.getItemAtPosition(myIntId).toString(), myIntId, (short) 2);
                break;

            case R.id.op_delete:
                String CVName = myListView.getItemAtPosition(myIntId).toString().substring(11);

                if(removeFile(CVName) == true) {
                    Toast.makeText(ActivityCVList.this, "CV '" + CVName.substring(0, CVName.length()-4) + "' was removed.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ActivityCVList.this, "CV '" + CVName.substring(0, CVName.length()-4) + "' was not removed.", Toast.LENGTH_SHORT).show();
                }

            default:

        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Choose your option: ");
        getMenuInflater().inflate(R.menu.settings, menu);
    }

    private boolean removeFile(String fileName) {
        File fileToRemove = new File(getApplicationInfo().dataDir + "/XMLs/" + fileName);

        try {
            fileToRemove.delete();

            int position = spinnerOrderBy.getSelectedItemPosition();
            if(position == 0) {
                xmlFiles = getXMLs("byNameAsc");
                updateCvlist();
            }
            else if(position == 1) {
                xmlFiles = getXMLs("byNameDesc");
                updateCvlist();
            }
            else if(position == 2) {
                xmlFiles = getXMLs("byDateAsc");
                updateCvlist();
            }
            else if(position == 3) {
                xmlFiles = getXMLs("byDateDesc");
                updateCvlist();
            }

            return true;
        }
        catch (Exception e) {
            Log.d("MyErr", e.toString());
        }

        return false;
    }
}
