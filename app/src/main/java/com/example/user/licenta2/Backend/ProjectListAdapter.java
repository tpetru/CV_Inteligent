/*
    Copyrights
    -   https://medium.com/mindorks/custom-array-adapters-made-easy-b6c4930560dd
    -   https://javatutorial.net/android-listview-with-listadapter-example
 */
package com.example.user.licenta2.Backend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.licenta2.MyClasses.Project;
import com.example.user.licenta2.R;

import java.util.ArrayList;

public class ProjectListAdapter extends ArrayAdapter<Project> {

    private Context context;
    private ArrayList<Project> projectsAdapter;

    public ProjectListAdapter(Context _context, ArrayList<Project> _projects) {
        super(_context, 0, _projects);
        this.context = _context;
        this.projectsAdapter = _projects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        // Get the view
        view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_projects, parent, false);
        }


        if (projectsAdapter.size() == 0) return view;

        // Get the Project Object from pos. position.
        Project project = projectsAdapter.get(position);

        // Get the TextView from layout
        TextView projectTitle = view.findViewById(R.id.tv_ProjectList_projectTitle);
        TextView projectResume = view.findViewById(R.id.tv_ProjectList_projectResume);

        // Set Project info
        projectTitle.setText(project.getName());
        projectResume.setText(project.getRezumat());

        return view;

    }
}
