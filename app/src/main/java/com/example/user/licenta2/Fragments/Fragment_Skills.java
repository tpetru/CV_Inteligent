package com.example.user.licenta2.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.licenta2.Backend.SkillListAdapter;
import com.example.user.licenta2.Backend.SpeechToText;
import com.example.user.licenta2.CV;
import com.example.user.licenta2.MyClasses.Skill;
import com.example.user.licenta2.R;
import com.example.user.licenta2.UI.ActivityCreateCV;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class Fragment_Skills extends Fragment implements View.OnClickListener {

    private SkillListAdapter adapterSkills;
    private CV cv;
    private SpeechToText speechToText_skill;

    public Fragment_Skills() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fragment__skills, container, false);


        // Add Skill by Text Button
        Button addSkillByTextBtn = (Button) rootView.findViewById(R.id.btnAddSkillByText);
        addSkillByTextBtn.setOnClickListener(this);

        // Add SKill by Voice Button
        Button addSkillByVoiceBtn = (Button) rootView.findViewById(R.id.btnAddSkillByVoice);
        addSkillByVoiceBtn.setOnClickListener(this);



        // Get CV from ActivityCreateCV
        Bundle data = Objects.requireNonNull(getActivity()).getIntent().getExtras();
        cv = data.getParcelable("newCV");

        final ArrayList<Skill> skills = cv.getSkills();

        if(!(null == adapterSkills))
            adapterSkills = null;

        adapterSkills = new SkillListAdapter(getActivity().getApplicationContext(), skills);
        speechToText_skill = new SpeechToText(ActivityCreateCV.getAppContext(), adapterSkills);

        addSkillByVoiceBtn.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if(!speechToText_skill.getIsSpeacking()) {
                            speechToText_skill.startListening(
                                    speechToText_skill.getMySpeechRecognizer(),
                                    speechToText_skill.getmSpeechRecognizerIntent()
                            );

                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        speechToText_skill.stopListening(speechToText_skill.getMySpeechRecognizer());
                        break;
                }
                return false;
            }
        });


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
                skillDescription.setText(selectedSkill.getDescription());


                // if click Update, then update the skill info.
                mBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String newSkillTitle = skillTitle.getText().toString();
                        String newSkillDescription = " ";
                        if (skillDescription.getText().toString() != null && skillDescription.getText().toString().length() > 3) {
                            newSkillDescription = skillDescription.getText().toString();
                        }

                        if(! newSkillTitle.isEmpty()) {
                            Objects.requireNonNull(adapterSkills.getItem(position)).setNume(newSkillTitle);
                            adapterSkills.getItem(position).setDescription(newSkillDescription);

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
                        adapterSkills.remove(selectedSkill);
                        adapterSkills.notifyDataSetChanged();
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
            case R.id.btnAddSkillByText:

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog__new_skill, null);

                final EditText skillTitle = (EditText) mView.findViewById(R.id.et_dialogAddSkill_skillTitle);
                final EditText skillDescription = (EditText) mView.findViewById(R.id.et_dialogAddSkill_skillDescription);

                mBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String str_newSkillTitle = skillTitle.getText().toString();
                        String str_newSkillDescription = " ";

                        if (skillDescription.getText().toString() != null && skillDescription.getText().toString().length() > 3) {
                            str_newSkillDescription = skillDescription.getText().toString();
                        }

                        if(! str_newSkillTitle.isEmpty()) {
                            Skill newSkill = new Skill(str_newSkillTitle);//, str_newSkillDescription);

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


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (adapterSkills != null) {
            ArrayList<Skill> skills = new ArrayList<Skill>();
            for (int i = 0; i < adapterSkills.getCount(); i++) {
                skills.add(adapterSkills.getItem(i));
            }

            cv.setSkills(skills);
        }
        else {
            cv.setSkills(null);
        }
    }
}
