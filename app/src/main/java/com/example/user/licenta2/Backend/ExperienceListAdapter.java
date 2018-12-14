package com.example.user.licenta2.Backend;

import android.content.Context;
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
        this.context = context;
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

        // Set Experience info
        company.setText(experience.getName());
        job.setText(experience.getPosition());
        startDate.setText(getCorrectDate(experience.getStart_date()));
        endDate.setText(getCorrectDate(experience.getEnd_date()));

        return view;
    }

    private String getCorrectDate(String str) {
        String strings[] = str.split(Pattern.quote("."));

        int month = Integer.valueOf(strings[1]);
        month += 1;

        return strings[0] + "." + Integer.valueOf(month).toString() + "." + strings[2];
    }
}
