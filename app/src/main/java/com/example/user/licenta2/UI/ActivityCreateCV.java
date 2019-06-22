package com.example.user.licenta2.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.licenta2.Backend.CommListAdapter;
import com.example.user.licenta2.Backend.EducationListAdapter;
import com.example.user.licenta2.Backend.ExperienceListAdapter;
import com.example.user.licenta2.Backend.ProjectListAdapter;
import com.example.user.licenta2.Backend.SkillListAdapter;
import com.example.user.licenta2.Backend.ViewPagerAdapter;
import com.example.user.licenta2.Backend.xmlParser;
import com.example.user.licenta2.CV;
import com.example.user.licenta2.Fragments.Fragment_Communication;
import com.example.user.licenta2.Fragments.Fragment_Contact;
import com.example.user.licenta2.Fragments.Fragment_Education;
import com.example.user.licenta2.Fragments.Fragment_Experience;
import com.example.user.licenta2.Fragments.Fragment_Projects;
import com.example.user.licenta2.Fragments.Fragment_Skills;
import com.example.user.licenta2.MyClasses.Communication;
import com.example.user.licenta2.MyClasses.Education;
import com.example.user.licenta2.MyClasses.Experience;
import com.example.user.licenta2.MyClasses.Project;
import com.example.user.licenta2.MyClasses.Skill;
import com.example.user.licenta2.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityCreateCV extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public CV newCV;
    private ViewPagerAdapter adapter;
    private Fragment_Contact fragContact;
    private Fragment_Skills fragSkill;
    private Fragment_Education fragEducation;
    private Fragment_Experience fragExperience;
    private Fragment_Projects fragProject;
    private Fragment_Communication fragCommunication;
    public FloatingActionButton fab;

    private static Context myContext;

    private TextToSpeech myTTS;
    private SpeechRecognizer mySpeechRecognizer;
    public Button btnForListening;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cv);
        ActivityCreateCV.myContext = getApplicationContext();

        Bundle data = getIntent().getExtras();
        String cvName = data.getString("cvName");

        newCV = new CV();
        newCV.setCvName(cvName);

        getIntent().putExtra("newCV", newCV);

        initializeTextToSpeech();
        initializeSpeechRecognizer();


//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setClickable(false);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    /* This part is OK:
                            .xml path: data/data/com.example.user.licenta2/XMLs/___.xml
                     */
                    String path = getApplicationInfo().dataDir;
                    xmlParser newXML = new xmlParser(path + "/XMLs/", newCV.getCvName() + ".xml");
                    newXML.createXML(newCV);

                    Snackbar.make(view, "Datele CV'ului au fost salvate.", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                } catch (IOException e) {
                    Snackbar.make(view, "Nu s-au putut salva datele CV'ului.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    e.printStackTrace();

                    Log.e("MyErr", e.toString());
                }

            }
        });

        btnForListening = (Button) findViewById(R.id.btnForListening);
        btnForListening.setClickable(true);
        btnForListening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tempIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                tempIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                tempIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

                mySpeechRecognizer.startListening(tempIntent);
            }
        });
    }

    private void initializeTextToSpeech() {
        myTTS  = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(myTTS.getEngines().size() == 0) {
                    Toast.makeText(ActivityCreateCV.this, "There is no TTS engine on your devine.", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    myTTS.setLanguage(Locale.US);
//                    speak("Hello, Hello Kitty.");
                }
            }

        });
    }

    private void initializeSpeechRecognizer() {
        if(SpeechRecognizer.isRecognitionAvailable(this)) {
            mySpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            mySpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle bundle) {
                    List<String> myResults = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    processResult(myResults.get(0));
                    Log.d("MyDebug", myResults.toString());

                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }

    private void processResult(String command) {
        command = command.toLowerCase();

        if ((command.contains("add")) || (command.contains("an"))) {
            if (command.contains("skill")) {
                // show Skill Fragment and open Dialog for addSkill
                speak("Please insert a new skill");
                viewPager.setCurrentItem(1);
                fragSkill.onClick(viewPager.findViewById(R.id.btnAddSkill));
            }

            else if (command.contains("educat")) {
                // show Education Fragment and open Dialog for addEducation
                speak("Please insert a new education");
                viewPager.setCurrentItem(2);
                fragEducation.onClick(viewPager.findViewById(R.id.btnAddEducation));
            }

            else if (command.contains("experi")) {
                // show Experience Fragment and open Dialog for addExperience
                speak("Please insert a new experience");
                viewPager.setCurrentItem(3);
                fragExperience.onClick(viewPager.findViewById(R.id.btnAddExperienceByText));
            }

            else if (command.contains("proje")) {
                // show Project Fragment and open Dialog for addProject
                speak("Please insert a new project");
                viewPager.setCurrentItem(4);
                fragProject.onClick(viewPager.findViewById(R.id.btnAddProject));
            }

            else if (command.contains("communic")) {
                // show Communication Fragment and open Dialog for addCommunication
                speak("Please insert a new communication");
                viewPager.setCurrentItem(5);
                fragCommunication.onClick(viewPager.findViewById(R.id.btnAddComm));
            }
        }

        else if ((command.contains("open")) || (command.contains("deschide"))) {
            if (command.contains("contact")) {
                viewPager.setCurrentItem(0);
            }
            else if ((command.contains("skill")) || (command.contains("abilit"))) {
                viewPager.setCurrentItem(1);
            }
            else if ((command.contains("education")) || (command.contains("edu"))) {
                viewPager.setCurrentItem(2);
            }
            else if ((command.contains("experience")) || (command.contains("experie"))){
                viewPager.setCurrentItem(3);
            }
            else if ((command.contains("project")) || (command.contains("proiec"))) {
                viewPager.setCurrentItem(4);
            }
            else if ((command.contains("communication")) || (command.contains("comunication")) || (command.contains("contains")) || (command.contains("comunica")))  {
                viewPager.setCurrentItem(5);
            }
        }

    }

    private void speak(String message) {
        if(Build.VERSION.SDK_INT >= 21) {
            myTTS.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else {
            myTTS.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        fragContact = new Fragment_Contact();
        fragSkill = new Fragment_Skills();
        fragEducation = new Fragment_Education();
        fragExperience = new Fragment_Experience();
        fragProject = new Fragment_Projects();
        fragCommunication = new Fragment_Communication();

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(fragContact, "Contact");
        adapter.addFragment(fragSkill, "Skills");
        adapter.addFragment(fragEducation, "Education");
        adapter.addFragment(fragExperience, "Experience");
        adapter.addFragment(fragProject, "Projects");
        adapter.addFragment(fragCommunication, "Communication");
        viewPager.setAdapter(adapter);
    }

    public static Context getAppContext() {
        return ActivityCreateCV.myContext;
    }

    @Override
    protected void onPause() {
        super.onPause();
        myTTS.shutdown();
    }

}
