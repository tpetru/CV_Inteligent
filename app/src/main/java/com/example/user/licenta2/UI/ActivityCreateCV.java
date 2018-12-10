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
import android.widget.EditText;
import android.widget.ListView;

import com.example.user.licenta2.Backend.SkillListAdapter;
import com.example.user.licenta2.Backend.ViewPagerAdapter;
import com.example.user.licenta2.Backend.pdfGenerator;
import com.example.user.licenta2.CV;
import com.example.user.licenta2.Fragments.Fragment_Communication;
import com.example.user.licenta2.Fragments.Fragment_Contact;
import com.example.user.licenta2.Fragments.Fragment_Education;
import com.example.user.licenta2.Fragments.Fragment_Experience;
import com.example.user.licenta2.Fragments.Fragment_Skills;
import com.example.user.licenta2.MyClasses.Skill;
import com.example.user.licenta2.R;

import java.io.IOException;
import java.util.ArrayList;

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
                    // get Contact data
                    EditText et_cv_firstname = (EditText) findViewById(R.id.et_contact_firstname);
                    EditText et_cv_middlename = (EditText) findViewById(R.id.et_contact_middlename);
                    EditText et_cv_lastname = (EditText) findViewById(R.id.et_contact_lastname);
                    EditText et_cv_country = (EditText) findViewById(R.id.et_contact_country);
                    EditText et_cv_city = (EditText) findViewById(R.id.et_contact_city);
                    EditText et_cv_zip = (EditText) findViewById(R.id.et_contact_zip);
                    EditText et_cv_phone = (EditText) findViewById(R.id.et_contact_phoneNumber);
                    EditText et_cv_email = (EditText) findViewById(R.id.et_contact_email);

                    newCV.setFirstName(et_cv_firstname.getText().toString());
                    newCV.setMiddleName(et_cv_middlename.getText().toString());
                    newCV.setLastName(et_cv_lastname.getText().toString());
                    newCV.setCountry(et_cv_country.getText().toString());
                    newCV.setCity(et_cv_city.getText().toString());
                    newCV.setCod_postal(et_cv_zip.getText().toString());
                    newCV.setPhoneNumber(et_cv_phone.getText().toString());
                    newCV.setEmail(et_cv_email.getText().toString());


                    // get Skill data
                    ListView lv_cv_skilllist = (ListView) findViewById(R.id.lv_currentSkills);
                    SkillListAdapter tempAdapter = (SkillListAdapter) lv_cv_skilllist.getAdapter();

                    ArrayList<Skill> skills = new ArrayList<Skill>();
                    for(int i = 0; i<tempAdapter.getCount(); i++) {
                        skills.add(tempAdapter.getItem(i));
                    }

                    newCV.setSkills(skills);



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
        adapter.addFragment(new Fragment_Contact(), "Contact");
        adapter.addFragment(new Fragment_Skills(), "Skills");
        adapter.addFragment(new Fragment_Education(), "Education");
        adapter.addFragment(new Fragment_Experience(), "Experience");
        adapter.addFragment(new Fragment_Experience(), "Projects");
        adapter.addFragment(new Fragment_Communication(), "Communication");
        viewPager.setAdapter(adapter);
    }

}
