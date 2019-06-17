package com.example.user.licenta2.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

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


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
}
