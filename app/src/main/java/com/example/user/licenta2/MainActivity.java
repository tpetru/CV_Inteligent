package com.example.user.licenta2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.licenta2.Backend.pdfGenerator;
import com.example.user.licenta2.Backend.xmlParser;
import com.example.user.licenta2.MyClasses.Communication;
import com.example.user.licenta2.MyClasses.Experience;
import com.example.user.licenta2.MyClasses.Project;
import com.example.user.licenta2.MyClasses.Skill;
import com.example.user.licenta2.MyClasses.Education;
import com.example.user.licenta2.UI.ActivityCVList;
import com.example.user.licenta2.UI.ActivityCreateCV;

import java.util.ArrayList;

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

//        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));

        btn_newCV = (Button) findViewById(R.id.btn_MainActivity_createCV);
        btn_newCV.setOnClickListener(this);

        btn_CVlist = (Button) findViewById(R.id.btn_MainActivity_CVList);
        btn_CVlist.setOnClickListener(this);




//        if(true == false) {
//
//            try {
//                CV cv = new CV();
//                cv.setCvName("cv_TanasaPetru");
//                cv.setFirstName("Petru");
//                cv.setMiddleName("");
//                cv.setLastName("Tanasa");
//                cv.setCity("Iasi");
//                cv.setCountry("Romania");
//                cv.setCod_postal("700673");
//                cv.setPhoneNumber("0758990801");
//                cv.setEmail("tanasapetrut@hotmail.com");
//
//                // Add Aptitudini to CV
//                ArrayList<Skill> aptitudini = new ArrayList<>();
//
//                Skill apt_c = new Skill("C");
//                Skill apt_cpp = new Skill("C++");
//                Skill apt_java = new Skill("Java");
//
//                aptitudini.add(apt_c);
//                aptitudini.add(apt_cpp);
//                aptitudini.add(apt_java);
//
//                cv.setSkills(aptitudini);
//
//
//                // Add Education to CV
//                ArrayList<Education> education = new ArrayList<>();
//
//                Education liceu = new Education("liceu", "Colegiul National de Informatica", "2011", "2015", "mate-info");
//                Education facultate = new Education("facultate", "Facultatea de Informatica", "2015", "2018", "informatica");
//
//                education.add(liceu);
//                education.add(facultate);
//
//                cv.setEducation(education);
//
//
//                // Add Experiente to CV
//                ArrayList<Experience> experiente = new ArrayList<>();
//
//                Experience conti = new Experience("Continental", "Software Testing Developer", "2011", "2015");
//                Experience centric = new Experience("Centric", "Software Developer", "2015");
//
//                experiente.add(conti);
//                experiente.add(centric);
//
//                cv.setExperiences(experiente);
//
//
//                // Add Proiecte to CV
//                ArrayList<Project> proiecte = new ArrayList<>();
//
//                Project ticTacToeGame = new Project("TicTacToe", "joc TicTacToe in C, consola.", "2 jucatori, bla bla bla, bla bla bla bla bla bla, bla blaaaaa bla bla bla bla, bla bla bla bla bla bla, bla bla bla bla bla bla, bla bla bla bla bla bla, bla bla bla bla bla bla, bla bla bla bla bla bla, bla bla bla bla bla bla, bla bla bla bla bla bla, bla bla bla");
//                Project horseBet = new Project("horseBet", "Site pariuri sportive", "2 jucatori, bla bla bla, bla bla bla bla bla bla, bla bla bla bla bla bla, bla bla bla bla bla bla, bla bla bla bla bla bla, bla bla bla bla bla bla, bla bla bla bla bla bla, bla bla bla bla bla bla, bla bla bla bla bla bla, bla bla bla bla bla bla, bla bla bla, bla bla bla, bla bla bla, bla bla bla, bla bla bla, bla bla bla, bla bla bla");
//
//                proiecte.add(ticTacToeGame);
//                proiecte.add(horseBet);
//
//                cv.setProjects(proiecte);
//
//
//                // Add Communication to CV
//                ArrayList<Communication> limbi_straine = new ArrayList<>();
//
//                Communication engleza = new Communication("Limba Engleza", "B1");
//                Communication germana = new Communication("Limba Germana", "C2");
//
//                limbi_straine.add(engleza);
//                limbi_straine.add(germana);
//
//                cv.setCommunications(limbi_straine);
//
//
//                xmlParser xmlToCreate = new xmlParser("FIRST.xml");
//                xmlToCreate.createXML(cv);
//
//                xmlParser xmlToParse = new xmlParser("xmlToParse.xml");
//                xmlToParse.createXML(cv);
//
//                //CV cv = new CV("cv_12", "FirstName", "SecondName", "MiddleName", "0758990801", "tanasapetrut@hotmail.com", "123456789012345678901234567890123456789012345678901234567890");
////            pdfGenerator first = new pdfGenerator(cv.getCvName(), cv);
////            pdfGenerator second = new pdfGenerator(cv2.cvName, cv2);
//
//
//                CV secondCV = xmlToParse.readXML("xmlToParse.xml", "secondCV");
//                pdfGenerator secondPDF = new pdfGenerator(path, "second.pdf", secondCV);
//            } catch (Exception e) {
//            }
//        }
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
}
