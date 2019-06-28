package com.example.user.licenta2.Backend;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user.licenta2.MyClasses.Communication;
import com.example.user.licenta2.MyClasses.Skill;
import com.example.user.licenta2.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CVListAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> CVsName;

    public CVListAdapter(Context context, ArrayList<String> _strings) {
        super(context, 0, _strings);
        this.context = context;
        this.CVsName = _strings;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        // Get the view
        view = convertView;
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list__cv, parent, false);
        }


        if(CVsName.size() == 0) return view;

        // Get the Skill Object from position position.
        String cvName = CVsName.get(position);
        cvName = cvName.substring(0, cvName.length() - 4);

        if(cvName.length() > 30)
            cvName = cvName.substring(0, 30) + "...";


        // Get the TextView from layout
        TextView leftCVName = (TextView) view.findViewById(R.id.tv_CVList_leftCV);
        TextView cvDateModified = (TextView) view.findViewById(R.id.tv_CVList_date);
        ImageView img = (ImageView) view.findViewById(R.id.img_CV1);

        // Set TextView(title) with skill.getName();
        String str_name = cvName.substring(11);
        String str_date = "Last modified on " + cvName.substring(0, 10);

        leftCVName.setText(str_name);
        cvDateModified.setText(str_date);
        img.setImageResource(R.drawable.ic_insert_drive_file_black_24dp);


        return view;
    }
}
