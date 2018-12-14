package com.example.user.licenta2.UI;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.user.licenta2.Backend.EducationListAdapter;
import com.example.user.licenta2.Backend.ExperienceListAdapter;
import com.example.user.licenta2.Backend.ProjectListAdapter;
import com.example.user.licenta2.Backend.SkillListAdapter;
import com.example.user.licenta2.Backend.ViewPagerAdapter;
import com.example.user.licenta2.Backend.pdfGenerator;
import com.example.user.licenta2.CV;
import com.example.user.licenta2.Fragments.Fragment_Communication;
import com.example.user.licenta2.Fragments.Fragment_Contact;
import com.example.user.licenta2.Fragments.Fragment_Education;
import com.example.user.licenta2.Fragments.Fragment_Experience;
import com.example.user.licenta2.Fragments.Fragment_Projects;
import com.example.user.licenta2.Fragments.Fragment_Skills;
import com.example.user.licenta2.MyClasses.Education;
import com.example.user.licenta2.MyClasses.Experience;
import com.example.user.licenta2.MyClasses.Project;
import com.example.user.licenta2.MyClasses.Skill;
import com.example.user.licenta2.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActivityCreateCV extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public CV newCV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cv);

        newCV = new CV();
        getIntent().putExtra("newCV", new CV());


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
//                    Thread.sleep(2000);
//                    // get Contact data
//                    EditText et_cv_firstname = (EditText) findViewById(R.id.et_contact_firstname);
//                    EditText et_cv_middlename = (EditText) findViewById(R.id.et_contact_middlename);
//                    EditText et_cv_lastname = (EditText) findViewById(R.id.et_contact_lastname);
//                    EditText et_cv_country = (EditText) findViewById(R.id.et_contact_country);
//                    EditText et_cv_city = (EditText) findViewById(R.id.et_contact_city);
//                    EditText et_cv_zip = (EditText) findViewById(R.id.et_contact_zip);
//                    EditText et_cv_phone = (EditText) findViewById(R.id.et_contact_phoneNumber);
//                    EditText et_cv_email = (EditText) findViewById(R.id.et_contact_email);

//                    Thread.sleep(2000);
//                    String str_cv_firstname = et_cv_firstname.getText().toString();
//                    Log.d("MyDebug", str_cv_firstname);
//                    newCV.setFirstName(str_cv_firstname);
//                    newCV.setMiddleName(et_cv_middlename.getText().toString());
//                    newCV.setLastName(et_cv_lastname.getText().toString());
//                    newCV.setCountry(et_cv_country.getText().toString());
//                    newCV.setCity(et_cv_city.getText().toString());
//                    newCV.setCod_postal(et_cv_zip.getText().toString());
//                    newCV.setPhoneNumber(et_cv_phone.getText().toString());
//                    newCV.setEmail(et_cv_email.getText().toString());

//                    // get Contact data
//                    ListView lv_cv_contactList = (ListView) findViewById(R.id.lv_currentContact);
//                    ArrayAdapter<String> contactData = (ArrayAdapter) lv_cv_contactList.getAdapter();
//
//                    newCV.setFirstName(contactData.getItem(0).toString());
//                    newCV.setMiddleName(contactData.getItem(1));
//                    newCV.setLastName(contactData.getItem(2));



                    // get Skill data
                    ListView lv_cv_skillList = (ListView) findViewById(R.id.lv_currentSkills);
                    SkillListAdapter tempAdapter_skill = (SkillListAdapter) lv_cv_skillList.getAdapter();

                    ArrayList<Skill> skills = new ArrayList<Skill>();
                    for(int i = 0; i<tempAdapter_skill.getCount(); i++) {
                        skills.add(tempAdapter_skill.getItem(i));
                    }

                    newCV.setSkills(skills);


                    // get Education data
                    ListView lv_cv_educationList = (ListView) findViewById(R.id.lv_currentEducations);
                    EducationListAdapter tempAdapter_education = (EducationListAdapter) lv_cv_educationList.getAdapter();

                    ArrayList<Education> educations = new ArrayList<Education>();
                    for(int i = 0; i<tempAdapter_education.getCount(); i++) {
                        educations.add(tempAdapter_education.getItem(i));
                    }

                    newCV.setEducation(educations);


                    // get Experiences data
                    ListView lv_cv_experienceList = (ListView) findViewById(R.id.lv_currentExperiences);
                    ExperienceListAdapter tempAdapter_exp = (ExperienceListAdapter) lv_cv_experienceList.getAdapter();

                    ArrayList<Experience> experiences = new ArrayList<Experience>();
                    for(int i = 0; i<tempAdapter_exp.getCount(); i++) {
                        experiences.add(tempAdapter_exp.getItem(i));
                    }

                    newCV.setExperiences(experiences);


                    // get Project data
                    ListView lv_cv_projectList = (ListView) findViewById(R.id.lv_currentProjects);
                    ProjectListAdapter tempAdapter_proj = (ProjectListAdapter) lv_cv_projectList.getAdapter();

                    ArrayList<Project> projects = new ArrayList<Project>();
                    for(int i = 0; i<tempAdapter_proj.getCount(); i++) {
                        projects.add(tempAdapter_proj.getItem(i));
                    }

                    newCV.setProjects(projects);



                    pdfGenerator new_pdf = new pdfGenerator("test.pdf", newCV);

                    Snackbar.make(view, "CV'ul a fost generat cu succes!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } catch (IOException e) {

                    Snackbar.make(view, "Toate campurile trebuie completate.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    e.printStackTrace();
                }

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new Fragment_Contact(), "Contact");
//        adapter.addFragment(new Fragment_Skills(), "Skills");
        adapter.addFragment(new Fragment_Education(), "Education");
        adapter.addFragment(new Fragment_Experience(), "Experience");
        adapter.addFragment(new Fragment_Projects(), "Projects");
        adapter.addFragment(new Fragment_Communication(), "Communication");
        viewPager.setAdapter(adapter);
    }
}
