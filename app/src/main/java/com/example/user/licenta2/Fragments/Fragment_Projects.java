package com.example.user.licenta2.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.licenta2.Backend.ProjectListAdapter;
import com.example.user.licenta2.CV;
import com.example.user.licenta2.MyClasses.Project;
import com.example.user.licenta2.R;

import java.util.ArrayList;


public class Fragment_Projects extends Fragment implements View.OnClickListener {

    private ProjectListAdapter adapterProjects;
    private CV cv;

    public Fragment_Projects() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment__projects, container, false);


        // Add Project Button
        Button addProjectBtn = rootView.findViewById(R.id.btnAddProject);
        addProjectBtn.setOnClickListener(this);



        // Get CV from ActivityCreateCV
        Bundle data = getActivity().getIntent().getExtras();
        cv = data.getParcelable("newCV");
        final ArrayList<Project> projects = cv.getProjects();

        if (!(null == adapterProjects))
            adapterProjects = null;

        adapterProjects = new ProjectListAdapter(getActivity().getApplicationContext(), projects);

        final ListView listView = (ListView) rootView.findViewById(R.id.lv_currentProjects);
        listView.setAdapter(adapterProjects);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                // get Project clicked
                final Project selectedProject = (Project) listView.getItemAtPosition(position);

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog__new_projects, null);

                final EditText projTitle = (EditText) mView.findViewById(R.id.et_dialogAddProject_projectTitle);
                final EditText projResume = (EditText) mView.findViewById(R.id.et_dialogAddProject_projectResume);


                projTitle.setText(selectedProject.getName());
                projResume.setText(selectedProject.getRezumat());


                mBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String str_projTitle = projTitle.getText().toString();
                        String str_projResume = projResume.getText().toString();

                        if(!str_projTitle.isEmpty() && !str_projResume.isEmpty()) {
                            adapterProjects.getItem(position).setName(str_projTitle);
                            adapterProjects.getItem(position).setRezumat(str_projResume);

                            adapterProjects.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(getActivity(), "Please fill all fields.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                // if click Remove, then remove the skill.
                mBuilder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        adapterProjects.remove(selectedProject);
                        adapterProjects.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();


            }
        });

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnAddProject:

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog__new_projects, null);

                final EditText projTitle = (EditText) mView.findViewById(R.id.et_dialogAddProject_projectTitle);
                final EditText projResume = (EditText) mView.findViewById(R.id.et_dialogAddProject_projectResume);

                mBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String str_projTitle = projTitle.getText().toString();
                        String str_projResume = projResume.getText().toString();


                        if(!str_projTitle.isEmpty() && !str_projResume.isEmpty()) {

                            Project newProject = new Project(str_projTitle, str_projResume, " ");
                            adapterProjects.add(newProject);
                            adapterProjects.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(getActivity(), "Please fill all fields.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

                break;

            default:
                break;
        }
    }
}
