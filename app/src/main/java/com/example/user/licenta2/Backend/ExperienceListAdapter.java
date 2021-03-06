package com.example.user.licenta2.Backend;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.licenta2.MyClasses.Experience;
import com.example.user.licenta2.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ExperienceListAdapter extends ArrayAdapter<Experience> {
    private Context context;
    private ArrayList<Experience> experiencesAdapter;

    public ExperienceListAdapter(Context _context, ArrayList<Experience> _experiences) {
        super(_context, 0, _experiences);
        this.context =_context;
        this.experiencesAdapter = _experiences;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        // Get the view
        view = convertView;
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_experiences, parent, false);
        }


        if(experiencesAdapter.size() == 0) return view;

        // Get the Experience Object from position position.
        Experience experience = experiencesAdapter.get(position);

        // Get the TextView from layout
        TextView company = view.findViewById(R.id.tv_ExperienceList_company);
        TextView job = view.findViewById(R.id.tv_ExperienceList_job);
        TextView startDate = view.findViewById(R.id.tv_ExperienceList_startDate);
        TextView endDate = view.findViewById(R.id.tv_ExperienceList_endDate);

        // temp vars for job, startDate, endDate
        String tempJob = "";
        String tempStartDate = "";
        String tempEndDate = "";

        // Check if data is filled
        if (experience.getPosition().length() > 3) {
            tempJob = experience.getPosition();
        }

        if (experience.getStart_date().length() > 3) {
            tempStartDate = experience.getStart_date();
        }


        if (experience.getEnd_date().length() > 3) {
            tempEndDate = experience.getEnd_date();
        }


        // Set Experience info //
        String temp_CompanyName = experience.getName();
        if (temp_CompanyName.length() > 30) {
            temp_CompanyName = temp_CompanyName.substring(0, 28) + "...";
        }
        company.setText(temp_CompanyName);

        if (tempJob.length() > 30) {
            tempJob = tempJob.substring(0, 28) + "...";
        }
        job.setText(tempJob);


        startDate.setText(tempStartDate);
        endDate.setText(tempEndDate);

        return view;
    }

    private String getCorrectDate(String str) {
        String strings[] = str.split(Pattern.quote("."));

        int month = Integer.valueOf(strings[1]);
        month += 1;

        return strings[0] + "." + Integer.valueOf(month).toString() + "." + strings[2];
    }

}
