package com.example.user.licenta2.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import com.example.user.licenta2.Backend.CommListAdapter;
import com.example.user.licenta2.Backend.SkillListAdapter;
import com.example.user.licenta2.CV;
import com.example.user.licenta2.MyClasses.Communication;
import com.example.user.licenta2.MyClasses.Skill;
import com.example.user.licenta2.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Communication extends Fragment implements View.OnClickListener {

    private CommListAdapter adapterCommunications;
    private CV cv;

    public Fragment_Communication() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment__communication, container, false);


        // AddSkill Button
        Button addCommunicationBtn = (Button) rootView.findViewById(R.id.btnAddComm);
        addCommunicationBtn.setOnClickListener(this);



        // Get CV from ActivityCreateCV
        Bundle data = getActivity().getIntent().getExtras();
        cv = data.getParcelable("newCV");
        final ArrayList<Communication> communications = cv.getCommunications();

        if(!(null == adapterCommunications))
            adapterCommunications = null;

        adapterCommunications = new CommListAdapter(getActivity().getApplicationContext(), communications);

        final ListView listView = (ListView) rootView.findViewById(R.id.lv_currentComm);
        listView.setAdapter(adapterCommunications);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                // get Skill clicked.
                final Communication selectedCommunication = (Communication) listView.getItemAtPosition(position);

                // build Custom Dialog
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog__new_communication, null);

                // get objects from UI
                final EditText commLanguage = (EditText) mView.findViewById(R.id.et_dialogAddComm_language);
                final Spinner commLevel = (Spinner) mView.findViewById(R.id.sp_dialogAddComm_level);



                // update UI with clicked communication info.
                commLanguage.setText(selectedCommunication.getName());
                List<String> levels = new ArrayList<String>();
                levels.add("A1");
                levels.add("A2");
                levels.add("B1");
                levels.add("B2");
                levels.add("C1");
                levels.add("C2");

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_spinner_dropdown_item, levels);
                spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                commLevel.setAdapter(spinnerAdapter);

                commLanguage.setText(selectedCommunication.getName());
                int nivel = -1;
                if(selectedCommunication.getLevel().toLowerCase().equals("a1"))
                    nivel = 0;
                else if(selectedCommunication.getLevel().toLowerCase().equals("a2"))
                    nivel = 1;
                else if(selectedCommunication.getLevel().toLowerCase().equals("b1"))
                    nivel = 2;
                else if(selectedCommunication.getLevel().toLowerCase().equals("b2"))
                    nivel = 3;
                else if(selectedCommunication.getLevel().toLowerCase().equals("c1"))
                    nivel = 4;
                else if(selectedCommunication.getLevel().toLowerCase().equals("c2"))
                    nivel = 5;

                commLevel.setSelection(nivel);


                // if click Update, then update the skill info.
                mBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String str_newComm_language = commLanguage.getText().toString();
                        String str_newComm_level = commLevel.getSelectedItem().toString();

                        if(! str_newComm_language.isEmpty()) {
                            adapterCommunications.getItem(position).setName(str_newComm_language);


                        }
                        else {
                            Toast.makeText(getActivity(), "Please fill all fields.", Toast.LENGTH_LONG).show();
                        }

                        adapterCommunications.getItem(position).setLevel(str_newComm_level);

                        adapterCommunications.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                // if click Remove, then remove the skill.
                mBuilder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String oldLanguage = selectedCommunication.getName();

                        adapterCommunications.remove(selectedCommunication);
                        adapterCommunications.notifyDataSetChanged();

                        Toast.makeText(getActivity(), "'" + oldLanguage + "' removed.", Toast.LENGTH_SHORT).show();

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
            case R.id.btnAddComm:
                // build Custom Dialog
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog__new_communication, null);

                // get objects from UI
                final EditText commLanguage = (EditText) mView.findViewById(R.id.et_dialogAddComm_language);
                final Spinner commLevel = (Spinner) mView.findViewById(R.id.sp_dialogAddComm_level);

                // update Spinner with levels.
                List<String> levels = new ArrayList<String>();
                levels.add("A1");
                levels.add("A2");
                levels.add("B1");
                levels.add("B2");
                levels.add("C1");
                levels.add("C2");

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_spinner_dropdown_item, levels);
                spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                commLevel.setAdapter(spinnerAdapter);


                // if click Add, then add new Communication
                mBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String str_newComm_language = commLanguage.getText().toString();
                        String str_newComm_level = commLevel.getSelectedItem().toString();

                        if(! str_newComm_language.isEmpty()) {
                            Communication newCommunication = new Communication(str_newComm_language, str_newComm_level);

                            adapterCommunications.add(newCommunication);
                            adapterCommunications.notifyDataSetChanged();
                            dialog.dismiss();

                            Toast.makeText(getActivity(), "Communication added.", Toast.LENGTH_SHORT).show();

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

    public CommListAdapter getAdapterCommunications() {
        return adapterCommunications;
    }

    @Override
    public void onPause() {
        super.onPause();
//        Log.d("MyDebug", "Fragment onPause - Communication");
    }

    @Override
    public void onStop() {
        super.onStop();

        if (adapterCommunications != null) {
            ArrayList<Communication> communications = new ArrayList<Communication>();
            for (int i = 0; i < adapterCommunications.getCount(); i++) {
                communications.add(adapterCommunications.getItem(i));
            }

            cv.setCommunications(communications);
        }
        else {
            cv.setCommunications(null);
        }
    }
}
