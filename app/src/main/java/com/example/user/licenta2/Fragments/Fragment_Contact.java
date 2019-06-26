package com.example.user.licenta2.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.user.licenta2.CV;
import com.example.user.licenta2.R;


public class Fragment_Contact extends Fragment {

    private ArrayAdapter<String> adapterContact;
    private EditText et_cv_firstname, et_cv_middlename, et_cv_lastname, et_cv_country, et_cv_city, et_cv_zip, et_cv_phone, et_cv_email;
    private CV cv;

    @SuppressLint("ValidFragment")
    public Fragment_Contact() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fragment__contact, container, false);

        // Get CV from ActivityCreateCV
        Bundle data = getActivity().getIntent().getExtras();
        cv = data.getParcelable("newCV");

        if(cv == null) {
            return rootView;
        }

        et_cv_firstname = (EditText) rootView.findViewById(R.id.et_contact_firstname);
        et_cv_middlename = (EditText) rootView.findViewById(R.id.et_contact_middlename);
        et_cv_lastname = (EditText) rootView.findViewById(R.id.et_contact_lastname);
        et_cv_country = (EditText) rootView.findViewById(R.id.et_contact_country);
        et_cv_city = (EditText) rootView.findViewById(R.id.et_contact_city);
        et_cv_zip = (EditText) rootView.findViewById(R.id.et_contact_zip);
        et_cv_phone = (EditText) rootView.findViewById(R.id.et_contact_phoneNumber);
        et_cv_email = (EditText) rootView.findViewById(R.id.et_contact_email);


        adapterContact = new ArrayAdapter<String>(getActivity().getApplicationContext(), 0);
        return rootView;
    }

    public ArrayAdapter<String> getContactAdapter() {
        return adapterContact;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        cv.setFirstName(et_cv_firstname.getText().toString());
        cv.setMiddleName(et_cv_middlename.getText().toString());
        cv.setLastName(et_cv_lastname.getText().toString());

        cv.setCountry(et_cv_country.getText().toString());
        cv.setCity(et_cv_city.getText().toString());
        cv.setCod_postal(et_cv_zip.getText().toString());

        cv.setPhoneNumber(et_cv_phone.getText().toString());
        cv.setEmail(et_cv_email.getText().toString());
    }
}
