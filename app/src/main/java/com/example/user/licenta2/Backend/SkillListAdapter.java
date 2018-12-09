package com.example.user.licenta2.Backend;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.licenta2.Fragments.Fragment_Skills;
import com.example.user.licenta2.MyClasses.Skill;
import com.example.user.licenta2.R;

import java.util.ArrayList;
import java.util.List;

public class SkillListAdapter extends ArrayAdapter<Skill> {
    private Activity context;
    private ArrayList<Skill> skills;

    public SkillListAdapter(Activity context, ArrayList<Skill> _skills) {
        super(context, 0, _skills);
        this.context = context;
        this.skills = _skills;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view;

        // Get the view
        view = convertView;
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.skills_list, parent, false);
        }


        if(skills.size() == 0) return view;

        // Get the Skill Object from position position.
//        Skill skill = getItem(position);
        Skill skill = skills.get(position);

        // Get the TextView from layout
        TextView line1 = view.findViewById(R.id.tv_SkillsList_line1);
        TextView line2 = view.findViewById(R.id.tv_SkillsList_line2);

        // Set TextView(title) with skill.getName();
        line1.setText(skill.getNume());

        // Get and set TextView(description) with skill.getDescription();
        String description = skill.getNume() + " - descriere skill " + skill.getNume() + " - in work :) ... ";

        if(description.length() > 30) {
            description = description.substring(0, 30) + "  ...";
        }

        line2.setText(description);


        return view;
    }
}
