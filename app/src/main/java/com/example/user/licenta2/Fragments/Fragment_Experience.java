package com.example.user.licenta2.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.licenta2.Backend.ExperienceListAdapter;
import com.example.user.licenta2.Backend.SpeechToText;
import com.example.user.licenta2.CV;
import com.example.user.licenta2.MainActivity;
import com.example.user.licenta2.MyClasses.Experience;
import com.example.user.licenta2.R;
import com.example.user.licenta2.UI.ActivityCreateCV;
import com.example.user.licenta2.UI.SpeechToTextActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;


public class Fragment_Experience extends Fragment implements View.OnClickListener {

    private CV cv;
    private ExperienceListAdapter adapterExperiences;
    private int start_y, start_m, start_d, end_y, end_m, end_d;
    private String text;
    private SpeechToText speechToText_experience;
    public Fragment_Experience() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_fragment__experience, container, false);


        // Add Experience by Text Button
        Button addExperienceByTextBtn = rootView.findViewById(R.id.btnAddExperienceByText);
        addExperienceByTextBtn.setOnClickListener(this);

        // Add Experience by Voice Button
        Button addExperienceByVoiceBtn = rootView.findViewById(R.id.btnAddExperienceByVoice);
        addExperienceByVoiceBtn.setOnClickListener(this);



        // Get CV from ActivityCreateCV
        Bundle data = getActivity().getIntent().getExtras();
        cv = data.getParcelable("newCV");
        final ArrayList<Experience> experiences = cv.getExperiences();

        if (!(null == adapterExperiences))
            adapterExperiences = null;

        adapterExperiences = new ExperienceListAdapter(getActivity().getApplicationContext(), experiences);
        speechToText_experience = new SpeechToText(ActivityCreateCV.getAppContext());

        rootView.findViewById(R.id.btnAddExperienceByVoice).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.d("MyDebug", "Listening...");
//                        speechToText_experience.startListening(
//                                speechToText_experience.getMySpeechRecognizer(),
//                                speechToText_experience.getmSpeechRecognizerIntent() );
//
//                        break;

                        case MotionEvent.ACTION_DOWN:
                            if(speechToText_experience.getIsSpeacking() == false) {
                                speechToText_experience.startListening(
                                        speechToText_experience.getMySpeechRecognizer(),
                                        speechToText_experience.getmSpeechRecognizerIntent() );

                            }
                            break;

                        case MotionEvent.ACTION_UP:
//                            if (speechToText_experience.getIsSpeacking() == true) {
                                String smth = speechToText_experience.stopListening(speechToText_experience.getMySpeechRecognizer());

                                Experience newExperience = new Experience(smth);
                                adapterExperiences.add(newExperience);
                                adapterExperiences.notifyDataSetChanged();

                                speechToText_experience.resetText();
//                            }

                            break;
                }
                return false;
            }
        });

        final ListView listView = (ListView) rootView.findViewById(R.id.lv_currentExperiences);
        listView.setAdapter(adapterExperiences);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                // get Experience clicked
                final Experience selectedExperience = (Experience) listView.getItemAtPosition(position);

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog__new_experience, null);

                final EditText expCompany = (EditText) mView.findViewById(R.id.et_dialogAddExp_company);
                final EditText expJob = (EditText) mView.findViewById(R.id.et_dialogAddExp_job);
                final CalendarView expStartDate = (CalendarView) mView.findViewById(R.id.calendar_startDate);
                final CalendarView expEndDate = (CalendarView) mView.findViewById(R.id.calendar_endDate);

                expStartDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        start_d = dayOfMonth;
                        start_m = month + 1;
                        start_y = year + 1;
                    }
                });

                expEndDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        end_y = year;
                        end_m = month;
                        end_d = dayOfMonth;
                    }
                });

                expCompany.setText(selectedExperience.getName());
                expJob.setText(selectedExperience.getPosition());
                expStartDate.setDate(convertDate(selectedExperience.getStart_date()));
                expEndDate.setDate(convertDate(selectedExperience.getEnd_date()));

                mBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String str_companyName = expCompany.getText().toString();
                        String str_jobName = expJob.getText().toString();
                        String str_startDate = "" + start_d + "." + start_m + "." + start_y;
                        String str_endDate = "" + end_d + "." + end_m + "." + end_y;

                        if(!str_companyName.isEmpty() && !str_jobName.isEmpty()) {
                            adapterExperiences.getItem(position).setName(str_companyName);
                            adapterExperiences.getItem(position).setPosition(str_jobName);
                            adapterExperiences.getItem(position).setStart_date(str_startDate);
                            adapterExperiences.getItem(position).setEnd_date(str_endDate);
                            adapterExperiences.notifyDataSetChanged();
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

                        adapterExperiences.remove(selectedExperience);
                        adapterExperiences.notifyDataSetChanged();
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
            case R.id.btnAddExperienceByText:

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                @SuppressLint("InflateParams") View mView = getLayoutInflater().inflate(R.layout.dialog__new_experience, null);

                final EditText companyName = (EditText) mView.findViewById(R.id.et_dialogAddExp_company);
                final EditText jobName = (EditText) mView.findViewById(R.id.et_dialogAddExp_job);

                final CalendarView startDate = (CalendarView) mView.findViewById(R.id.calendar_startDate);

                startDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        start_d = dayOfMonth;
                        start_m = month;
                        start_y = year;
                    }
                });

                final CalendarView endDate = (CalendarView) mView.findViewById(R.id.calendar_endDate);
                endDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        end_y = year;
                        end_m = month;
                        end_d = dayOfMonth;
                    }
                });

                mBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String str_companyName = companyName.getText().toString();
                        String str_jobName = jobName.getText().toString();
                        String str_startDate = "" + start_d + "." + start_m + "." + start_y;
                        String str_endDate = "" + end_d + "." + end_m + "." + end_y;

                        if(!str_companyName.isEmpty() && !str_jobName.isEmpty()) {

                            Experience newExperience = new Experience(str_companyName, str_jobName, str_startDate, str_endDate);
                            adapterExperiences.add(newExperience);
                            adapterExperiences.notifyDataSetChanged();
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

//            case R.id.btnAddExperienceByVoice:
//                SpeechToText speechToText_experience = new SpeechToText(ActivityCreateCV.getAppContext());
//                String tempString;// = speechToText_experience.getText();
//
//                /* daca nu vorbeste si nu am text, incep sa ascult */
//                if (speechToText_experience.getIsSpeacking() == false) {
//                    speechToText_experience.startListening(
//                            speechToText_experience.getMySpeechRecognizer(),
//                            speechToText_experience.getmSpeechRecognizerIntent()
//
//                    );
//                }
//                else {
//                    speechToText_experience.stopListening(speechToText_experience.getMySpeechRecognizer());
//                    Log.d("MyDebug", "HAHA");
//                }
//
//                tempString = speechToText_experience.getText();
//                Log.d("MyDebug: ", "========" + tempString);
//
//                if (tempString.length() > 0) {
//                   Experience newExperience = new Experience(speechToText_experience.getText());
//                   Log.d("MyDebug", "MyNewExperience: " + newExperience.getName());
//                   adapterExperiences.add(newExperience);
//                   adapterExperiences.notifyDataSetChanged();
//                }
//
//                speechToText_experience.resetText();
//
//                break;

            default:
                break;
        }
    }

    public void saveText(String _text) {
        this.text = _text;
        Log.d("MyDebug", "Fragment.text: " + text);
    }

    private long convertDate(String str) {
        Calendar calendar = new GregorianCalendar();

        String[] parts = str.split(Pattern.quote("."));

        int day = Integer.valueOf(parts[0]);
        int month = Integer.valueOf(parts[1]);
        int year = Integer.valueOf(parts[2]);

        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        return calendar.getTimeInMillis();
    }

    public ExperienceListAdapter getAdapterExperiences() {
        return adapterExperiences;
    }

    @Override
    public void onPause() {
        super.onPause();
//        Log.d("MyDebug", "Fragment onPause - Experience");
    }
}
