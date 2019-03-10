package com.example.user.licenta2.Backend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.licenta2.MyClasses.Education;
import com.example.user.licenta2.R;

import java.util.ArrayList;

public class EducationListAdapter extends ArrayAdapter<Education> {
    private Context context;
    private ArrayList<Education> educationsAdapter;

    public EducationListAdapter(Context _context, ArrayList<Education> _educations) {
        super(_context, 0, _educations);
        this.context = _context;
        this.educationsAdapter = _educations;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        // Get the view
        view = convertView;
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_educations, parent, false);
        }


        if(educationsAdapter.size() == 0) return view;

        // Get the Skill Object from position position.
        Education education = educationsAdapter.get(position);

        // Get the TextView from layout
        TextView school = view.findViewById(R.id.tv_EducationList_school);
        TextView profile = view.findViewById(R.id.tv_EducationList_profile);
        TextView startYear = view.findViewById(R.id.tv_EducationList_startDate);
        TextView endYear = view.findViewById(R.id.tv_EducationList_endDate);

        school.setText(education.getName());
        profile.setText(education.getProfile());
        startYear.setText(education.getStartDates());
        endYear.setText(education.getEndDates());

        return view;
    }

}
