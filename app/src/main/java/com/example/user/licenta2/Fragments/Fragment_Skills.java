package com.example.user.licenta2.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.licenta2.Backend.SkillListAdapter;
import com.example.user.licenta2.CV;
import com.example.user.licenta2.MyClasses.Skill;
import com.example.user.licenta2.R;
import com.example.user.licenta2.UI.ActivityCreateCV;

import java.util.ArrayList;
import java.util.Random;


public class Fragment_Skills extends Fragment implements View.OnClickListener {
    private Button addSkillBtn;
    private CV cv;
//    private SkillListAdapter skillsAdapter;
    private SkillListAdapter adapter;

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
        addSkillBtn = (Button) rootView.findViewById(R.id.btnAddSkill);
        addSkillBtn.setOnClickListener(this);



        // Get CV from ActivityCreateCV
        Bundle data = getActivity().getIntent().getExtras();
        cv = data.getParcelable("newCV");
        final ArrayList<Skill> skills = cv.getSkills();

        if(!(null == adapter))
            adapter = null;

        adapter = new SkillListAdapter(getActivity(), skills);

        ListView listView = (ListView) rootView.findViewById(R.id.lv_currentSkills);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), ": " + skills.get(position).getNume() + " [ " + position + " ] " , Toast.LENGTH_SHORT).show();

            }
        });

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddSkill:
//                Skill newSkill = new Skill(random());
//                ArrayList<Skill> currentSkills = (ArrayList<Skill>) cv.getSkills();
//
//                currentSkills.add(newSkill);
//                cv.setSkills(currentSkills);
//
//                for(Skill sk : currentSkills) {
//                    Log.i("MyDebug", sk.toString());
//                }

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog__new_skill, null);

                final EditText skillTitle = (EditText) mView.findViewById(R.id.et_dialogAddSkill_skillTitle);

                mBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String newSkillTitle = skillTitle.getText().toString();

                        if(! newSkillTitle.isEmpty()) {
                            Skill newSkill = new Skill(newSkillTitle);
                            adapter.add(newSkill);
                            adapter.notifyDataSetChanged();
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
}
