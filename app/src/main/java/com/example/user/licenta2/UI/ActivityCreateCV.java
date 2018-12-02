package com.example.user.licenta2.UI;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableLayout;

import com.example.user.licenta2.Backend.ViewPagerAdapter;
import com.example.user.licenta2.Fragments.Fragment_Communication;
import com.example.user.licenta2.Fragments.Fragment_Contact;
import com.example.user.licenta2.Fragments.Fragment_Education;
import com.example.user.licenta2.Fragments.Fragment_Experience;
import com.example.user.licenta2.Fragments.Fragment_Skills;
import com.example.user.licenta2.R;

public class ActivityCreateCV extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cv);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
