package com.example.user.licenta2.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.user.licenta2.Backend.EducationListAdapter;
import com.example.user.licenta2.CV;
import com.example.user.licenta2.MyClasses.Education;
import com.example.user.licenta2.R;

import java.util.ArrayList;
import java.util.Random;


public class Fragment_Education extends Fragment implements View.OnClickListener {

    private EducationListAdapter adapterEducations;


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

        return rootView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnAddEducation:

//                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
//                View mView = getLayoutInflater().inflate(R.layout.dialog__new_skill, null);
//
//                final EditText skillTitle = (EditText) mView.findViewById(R.id.et_dialogAddSkill_skillTitle);
//
//                mBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        String str_newSkillTitle = skillTitle.getText().toString();
//
//                        if(! str_newSkillTitle.isEmpty()) {
                            Education newEducation = new Education("type", "scoala", "specializare", "12.12.2018", "12.12.2018");
                            adapterEducations.add(newEducation);
                            adapterEducations.notifyDataSetChanged();
//                            dialog.dismiss();
//                        }
//                        else {
//                            Toast.makeText(getActivity(), "Please fill 'Skill Title'.", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//
//                mBuilder.setView(mView);
//                AlertDialog dialog = mBuilder.create();
//                dialog.show();

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
}
