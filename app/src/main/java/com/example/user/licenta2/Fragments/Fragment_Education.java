package com.example.user.licenta2.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.licenta2.Backend.EducationListAdapter;
import com.example.user.licenta2.CV;
import com.example.user.licenta2.MyClasses.Education;
import com.example.user.licenta2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Fragment_Education extends Fragment implements View.OnClickListener {

    private EducationListAdapter adapterEducations;
    private String _startYear, _endYear;


    public Fragment_Education() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment__education, container, false);


        // AddSkill Button
        Button addEducationBtn = (Button) rootView.findViewById(R.id.btnAddEducation);
        addEducationBtn.setOnClickListener(this);



        // Get CV from ActivityCreateCV
        Bundle data = getActivity().getIntent().getExtras();
        CV cv = data.getParcelable("newCV");
        ArrayList<Education> educations = cv.getEducation();

        if(!(null == adapterEducations))
            adapterEducations = null;

        adapterEducations = new EducationListAdapter(getActivity().getApplicationContext(), educations);

        final ListView listView = (ListView) rootView.findViewById(R.id.lv_currentEducations);
        listView.setAdapter(adapterEducations);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                // get Education clicked.
                final Education selectedEducation = (Education) listView.getItemAtPosition(position);

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog__new_education, null);

                final EditText educationSchool = (EditText) mView.findViewById(R.id.et_dialogAddEdu_school);
                final EditText educationProfile = (EditText) mView.findViewById(R.id.et_dialogAddEdu_spec);
                final Spinner educationStartDate = (Spinner) mView.findViewById(R.id.sp_dialogAddEdu_startYear);
                final Spinner educationEndDate = (Spinner) mView.findViewById(R.id.sp_dialogAddEdu_endYear);

                List<Integer> years = new ArrayList<Integer>();
                for(short i = 0; i<30; i++) {
                    years.add(1990 + i);
                }

                ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<Integer>(mView.getContext(), android.R.layout.simple_spinner_item, years);
                spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                educationStartDate.setAdapter(spinnerAdapter);
                educationEndDate.setAdapter(spinnerAdapter);

                educationSchool.setText(selectedEducation.getName());
                educationProfile.setText(selectedEducation.getProfile());

                int startDate_position = (1990 - Integer.parseInt(selectedEducation.getStartDates())) * -1;
                educationStartDate.setSelection(startDate_position);

                int endDate_position = (1990 - Integer.parseInt(selectedEducation.getEndDates())) * -1;
                educationEndDate.setSelection(endDate_position);


                mBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String str_newEdu_school = educationSchool.getText().toString();
                        String str_newEdu_profile = educationProfile.getText().toString();
                        String str_newEdu_startDate = educationStartDate.getSelectedItem().toString();
                        String str_newEdu_endDate = educationEndDate.getSelectedItem().toString();

                        if(!str_newEdu_school.isEmpty() && !str_newEdu_profile.isEmpty()) {

                            adapterEducations.getItem(position).setName(str_newEdu_school);
                            adapterEducations.getItem(position).setProfile(str_newEdu_profile);
                            adapterEducations.getItem(position).setStartDates(str_newEdu_startDate);
                            adapterEducations.getItem(position).setEndDates(str_newEdu_endDate);

                            adapterEducations.notifyDataSetChanged();
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

                        String oldEducation = selectedEducation.getName();

                        adapterEducations.remove(selectedEducation);
                        adapterEducations.notifyDataSetChanged();

                        Toast.makeText(getActivity(), "'" + oldEducation + "' removed.", Toast.LENGTH_SHORT).show();

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

        switch (v.getId()) {
            case R.id.btnAddEducation:

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog__new_education, null);

                final EditText school = (EditText) mView.findViewById(R.id.et_dialogAddEdu_school);
                final EditText profile = (EditText) mView.findViewById(R.id.et_dialogAddEdu_spec);
                final Spinner startDate = (Spinner) mView.findViewById(R.id.sp_dialogAddEdu_startYear);
                final Spinner endDate = (Spinner) mView.findViewById(R.id.sp_dialogAddEdu_endYear);

                List<Integer> years = new ArrayList<Integer>();
                for(short i = 0; i<30; i++) {
                    years.add(1990 + i);
                }

                ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<Integer>(mView.getContext(), android.R.layout.simple_spinner_item, years);
                spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                startDate.setAdapter(spinnerAdapter);
                endDate.setAdapter(spinnerAdapter);


                mBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String str_newEdu_school = school.getText().toString();
                        String str_newEdu_profile = profile.getText().toString();

                        if(!str_newEdu_school.isEmpty() && !str_newEdu_profile.isEmpty()) {
                            Education newEducation = new Education("type", str_newEdu_school, str_newEdu_profile, startDate.getSelectedItem().toString(), endDate.getSelectedItem().toString());
                            adapterEducations.add(newEducation);
                            adapterEducations.notifyDataSetChanged();
                            dialog.dismiss();

                            Toast.makeText(getActivity(), "Education added.", Toast.LENGTH_SHORT).show();
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

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(5) + 10;
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(90) + 65);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public EducationListAdapter getAdapterEducations() {
        return adapterEducations;
    }
}
