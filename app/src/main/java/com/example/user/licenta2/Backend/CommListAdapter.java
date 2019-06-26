package com.example.user.licenta2.Backend;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user.licenta2.MyClasses.Communication;
import com.example.user.licenta2.MyClasses.Skill;
import com.example.user.licenta2.R;

import java.util.ArrayList;

public class CommListAdapter extends ArrayAdapter<Communication> {
    private Context context;
    private ArrayList<Communication> communicationAdapter;

    public CommListAdapter(Context context, ArrayList<Communication> _communications) {
        super(context, 0, _communications);
        this.context = context;
        this.communicationAdapter = _communications;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        // Get the view
        view = convertView;
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_communication, parent, false);
        }


        if(communicationAdapter.size() == 0) return view;

        // Get the Skill Object from position position.
        Communication comm = communicationAdapter.get(position);

        // Get the TextView from layout
        TextView language = view.findViewById(R.id.tv_CommList_language);
        TextView level = view.findViewById(R.id.tv_CommList_level);

        // Set TextView(title) with skill.getName();
        String temp_Language = comm.getName();
        if (temp_Language.length() > 33) {
            temp_Language = temp_Language.substring(0, 31) + "...";
        }
        language.setText(temp_Language);
        level.setText(comm.getLevel());

        return view;
    }
}
