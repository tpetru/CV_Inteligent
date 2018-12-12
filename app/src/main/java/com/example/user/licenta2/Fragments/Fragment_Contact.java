package com.example.user.licenta2.Fragments;

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


public class Fragment_Contact extends Fragment implements View.OnClickListener {

    private ArrayAdapter<String> adapterContact;
    private EditText et_cv_firstname, et_cv_middlename, et_cv_lastname, et_cv_country, et_cv_city, et_cv_zip, et_cv_phone, et_cv_email;
    private Button updateContactBtn;

    public Fragment_Contact() {
        // Required empty public constructor
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

        // AddSkill Button
        updateContactBtn = (Button) rootView.findViewById(R.id.btnUpdateContact);
        updateContactBtn.setOnClickListener(this);

        // Get CV from ActivityCreateCV
        Bundle data = getActivity().getIntent().getExtras();
        CV cv = data.getParcelable("newCV");

        final ListView listView = (ListView) rootView.findViewById(R.id.lv_currentContact);
        listView.setAdapter(adapterContact);



        et_cv_firstname = (EditText) rootView.findViewById(R.id.et_contact_firstname);
        et_cv_middlename = (EditText) rootView.findViewById(R.id.et_contact_middlename);
        et_cv_lastname = (EditText) rootView.findViewById(R.id.et_contact_lastname);
        et_cv_country = (EditText) rootView.findViewById(R.id.et_contact_country);
        et_cv_city = (EditText) rootView.findViewById(R.id.et_contact_city);
        et_cv_zip = (EditText) rootView.findViewById(R.id.et_contact_zip);
        et_cv_phone = (EditText) rootView.findViewById(R.id.et_contact_phoneNumber);
        et_cv_email = (EditText) rootView.findViewById(R.id.et_contact_email);

        et_cv_firstname.setText(cv.getFirstName());
        et_cv_middlename.setText(cv.getMiddleName());
        et_cv_lastname.setText(cv.getLastName());
        et_cv_country.setText(cv.getCountry());
        et_cv_city.setText(cv.getCity());
        et_cv_zip.setText(cv.getCod_postal());
        et_cv_phone.setText(cv.getPhoneNumber());
        et_cv_email.setText(cv.getEmail());

        adapterContact = new ArrayAdapter<String>(getActivity().getApplicationContext(), 0);
        adapterContact.add(et_cv_firstname.getText().toString());
        adapterContact.add(et_cv_middlename.getText().toString());
        adapterContact.add(et_cv_lastname.getText().toString());
        adapterContact.add(et_cv_country.getText().toString());
        adapterContact.add(et_cv_city.getText().toString());
        adapterContact.add(et_cv_zip.getText().toString());
        adapterContact.add(et_cv_phone.getText().toString());
        adapterContact.add(et_cv_email.getText().toString());

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdateContact:
                adapterContact.clear();

                if(et_cv_firstname.getText().toString().length() != 0)
                    adapterContact.add(et_cv_firstname.getText().toString());
                else
                    adapterContact.add(null);


                if(et_cv_middlename.getText().toString().length() != 0)
                    adapterContact.add(et_cv_middlename.getText().toString());
                else
                    adapterContact.add(null);


                if(et_cv_lastname.getText().toString().length() != 0)
                    adapterContact.add(et_cv_lastname.getText().toString());
                else
                    adapterContact.add(null);


                if(et_cv_country.getText().toString().length() != 0)
                    adapterContact.add(et_cv_country.getText().toString());
                else
                    adapterContact.add(null);


                if(et_cv_city.getText().toString().length() != 0)
                    adapterContact.add(et_cv_city.getText().toString());
                else
                    adapterContact.add(null);


                if(et_cv_zip.getText().toString().length() != 0)
                    adapterContact.add(et_cv_zip.getText().toString());
                else
                    adapterContact.add(null);


                if(et_cv_phone.getText().toString().length() != 0)
                    adapterContact.add(et_cv_phone.getText().toString());
                else
                    adapterContact.add(null);


                if(et_cv_email.getText().toString().length() != 0)
                    adapterContact.add(et_cv_email.getText().toString());
                else
                    adapterContact.add(null);


                adapterContact.notifyDataSetChanged();

                break;

                default:
                    break;
        }
    }

    public ArrayAdapter<String> getAdapterContact() {
        return this.adapterContact;
    }
}
