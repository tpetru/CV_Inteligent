package com.example.user.licenta2.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.licenta2.Backend.ExperienceListAdapter;
import com.example.user.licenta2.CV;
import com.example.user.licenta2.MyClasses.Experience;
import com.example.user.licenta2.R;

import java.util.ArrayList;


public class Fragment_Experience extends Fragment implements View.OnClickListener {

    private CV cv;
    private ExperienceListAdapter adapter;
    private int start_y, start_m, start_d, end_y, end_m, end_d;

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


        View rootView = inflater.inflate(R.layout.fragment_fragment__experience, container, false);


        // Add Experience Button
        Button addExperienceBtn = rootView.findViewById(R.id.btnAddExperience);
        addExperienceBtn.setOnClickListener(this);


        // Get CV from ActivityCreateCV
        Bundle data = getActivity().getIntent().getExtras();
        cv = data.getParcelable("newCV");
        final ArrayList<Experience> experiences = cv.getExperiences();

        if(!(null == adapter))
            adapter = null;

        adapter = new ExperienceListAdapter(getActivity(), experiences);

        final ListView listView = (ListView) rootView.findViewById(R.id.lv_currentExperiences);
        listView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddExperience:

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog__new_experience, null);

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
                            adapter.add(newExperience);
                            adapter.notifyDataSetChanged();
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
