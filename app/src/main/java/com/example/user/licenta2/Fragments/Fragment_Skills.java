package com.example.user.licenta2.Fragments;

import android.annotation.SuppressLint;
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

import com.example.user.licenta2.Backend.SkillListAdapter;
import com.example.user.licenta2.CV;
import com.example.user.licenta2.MyClasses.Skill;
import com.example.user.licenta2.R;

import java.util.ArrayList;
import java.util.Random;


public class Fragment_Skills extends Fragment implements View.OnClickListener {

    private SkillListAdapter adapterSkills;

    public Fragment_Skills() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fragment__skills, container, false);


        // AddSkill Button
        Button addSkillBtn = (Button) rootView.findViewById(R.id.btnAddSkill);
        addSkillBtn.setOnClickListener(this);



        // Get CV from ActivityCreateCV
        Bundle data = getActivity().getIntent().getExtras();
        CV cv = data.getParcelable("newCV");
        ArrayList<Skill> skills = cv.getSkills();

        if(!(null == adapterSkills))
            adapterSkills = null;

        adapterSkills = new SkillListAdapter(getActivity().getApplicationContext(), skills);

        final ListView listView = (ListView) rootView.findViewById(R.id.lv_currentSkills);
        listView.setAdapter(adapterSkills);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                // get Skill clicked.
                final Skill selectedSkill = (Skill) listView.getItemAtPosition(position);

                // build Custom Dialog
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog__new_skill, null);

                // get objects from UI
                final EditText skillTitle = (EditText) mView.findViewById(R.id.et_dialogAddSkill_skillTitle);
                final EditText skillDescription = (EditText) mView.findViewById(R.id.et_dialogAddSkill_skillDescription);

                // update UI with clicked skill info.
                skillTitle.setText(selectedSkill.getNume());
                skillDescription.setText(selectedSkill.getNume() + " === " + selectedSkill.getNume() + " === " + selectedSkill.getNume() + " === " + selectedSkill.getNume() + " === " + selectedSkill.getNume() + " === " + selectedSkill.getNume() + " === ");


                // if click Update, then update the skill info.
                mBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String newSkillTitle = skillTitle.getText().toString();

                        if(! newSkillTitle.isEmpty()) {
                            adapterSkills.getItem(position).setNume(newSkillTitle);

                            adapterSkills.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(getActivity(), "Please fill 'Skill Title'.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                // if click Remove, then remove the skill.
                mBuilder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String newSkillTitle = skillTitle.getText().toString();

                        if(! newSkillTitle.isEmpty()) {
                            adapterSkills.remove(selectedSkill);
                            adapterSkills.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(getActivity(), "Please fill 'Skill Title'.", Toast.LENGTH_LONG).show();
                        }
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
            case R.id.btnAddSkill:

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                @SuppressLint("InflateParams") View mView = getLayoutInflater().inflate(R.layout.dialog__new_skill, null);

                final EditText skillTitle = (EditText) mView.findViewById(R.id.et_dialogAddSkill_skillTitle);

                mBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String str_newSkillTitle = skillTitle.getText().toString();

                        if(! str_newSkillTitle.isEmpty()) {
                            Skill newSkill = new Skill(str_newSkillTitle);
                            adapterSkills.add(newSkill);
                            adapterSkills.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(getActivity(), "Please fill 'Skill Title'.", Toast.LENGTH_LONG).show();
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

    public SkillListAdapter getAdapterSkills() {
        return adapterSkills;
    }
}
