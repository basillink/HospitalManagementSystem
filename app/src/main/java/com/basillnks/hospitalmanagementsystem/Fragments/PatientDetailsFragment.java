package com.basillnks.hospitalmanagementsystem.Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.basillnks.hospitalmanagementsystem.Database.PatientDBHelper;
import com.basillnks.hospitalmanagementsystem.Database.PatientsContract.PatientEntry;
import com.basillnks.hospitalmanagementsystem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDetailsFragment extends Fragment {

    private int rowId, position, genderValue, genotypeValue, bloodGroupValue;
    private String firstName, lastName, phone, address, dob, gender, genotype, bloodGroup,
            nextKin, kinRelation, kinPhone;
    private TextView tvFirstName, tvLastName, tvPhone, tvAddress, tvDOB, tvGender, tvGenotype,
            tvBloodGroup, tvNextKin, tvKinRelation, tvKinPhone;

    public PatientDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_details, container, false);

        setHasOptionsMenu(true);
        Toolbar toolbar = view.findViewById(R.id.patient_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Patient Information");

        findWidgets(view);

        return view;
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link Fragment#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("ID");
            setData();
        }
    }

    /**
     * Initialize the contents of the Fragment host's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called {@link #setHasOptionsMenu}.  See
     * {@link Fragment#onCreateOptionsMenu(Menu, MenuInflater)}
     * for more information.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater
     * @see #setHasOptionsMenu
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.crud_action_menu, menu);
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                editPatient();
                break;
            case R.id.menu_delete:
                deletePatient();
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findWidgets(View view) {
        tvFirstName = view.findViewById(R.id.patient_first_name);
        tvLastName = view.findViewById(R.id.patient_last_name);
        tvPhone = view.findViewById(R.id.patient_phone);
        tvAddress = view.findViewById(R.id.patient_address);
        tvDOB = view.findViewById(R.id.patient_dob);
        tvGender = view.findViewById(R.id.patient_gender);
        tvGenotype = view.findViewById(R.id.patient_genotype);
        tvBloodGroup = view.findViewById(R.id.patient_blood_group);
        tvNextKin = view.findViewById(R.id.patient_next_kin);
        tvKinRelation = view.findViewById(R.id.patient_kin_relation);
        tvKinPhone = view.findViewById(R.id.patient_kin_phone);
    }

    private void setData() {
        PatientDBHelper dbHelper = new PatientDBHelper(getActivity());
        Cursor cursor = dbHelper.displaySinglePatient();
        cursor.moveToPosition(position);

        rowId = cursor.getInt(cursor.getColumnIndex(PatientEntry.PAT_ID));
        firstName = cursor.getString(cursor.getColumnIndex(PatientEntry.FIRST_NAME));
        lastName = cursor.getString(cursor.getColumnIndex(PatientEntry.LAST_NAME));
        phone = cursor.getString(cursor.getColumnIndex(PatientEntry.PHONE_NUMBER));
        address = cursor.getString(cursor.getColumnIndex(PatientEntry.CONTACT_ADDRESS));
        dob = cursor.getString(cursor.getColumnIndex(PatientEntry.DATE_OF_BIRTH));
        genderValue = cursor.getInt(cursor.getColumnIndex(PatientEntry.GENDER));
        genotypeValue = cursor.getInt(cursor.getColumnIndex(PatientEntry.GENOTYPE));
        bloodGroupValue = cursor.getInt(cursor.getColumnIndex(PatientEntry.BLOOD_GROUP));
        nextKin = cursor.getString(cursor.getColumnIndex(PatientEntry.NEXT_OF_KIN));
        kinRelation = cursor.getString(cursor.getColumnIndex(PatientEntry.KIN_RELATIONSHIP));
        kinPhone = cursor.getString(cursor.getColumnIndex(PatientEntry.KIN_PHONE_NUMBER));

        tvFirstName.setText(firstName);
        tvLastName.setText(lastName);
        tvPhone.setText(phone);
        tvAddress.setText(address);
        tvDOB.setText(dob);
        tvGender.setText(getGender());
        tvGenotype.setText(getGenotype());
        tvBloodGroup.setText(getBloodGroup());
        tvNextKin.setText(nextKin);
        tvKinRelation.setText(kinRelation);
        tvKinPhone.setText(kinPhone);
    }

    private void editPatient() {
        PatientEditorFragment patientEditorFragment = new PatientEditorFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("ID", position);
        arguments.putString("FIRST_NAME", firstName);
        arguments.putString("LAST_NAME", lastName);
        arguments.putString("PHONE", phone);
        arguments.putString("ADDRESS", address);
        arguments.putString("DOB", dob);
        arguments.putInt("GENDER_POSITION", genderValue);
        arguments.putInt("GENOTYPE_POSITION", genotypeValue);
        arguments.putInt("BLOOD_GROUP_POSITION", bloodGroupValue);
        arguments.putString("NEXT_KIN", nextKin);
        arguments.putString("KIN_RELATION", kinRelation);
        arguments.putString("KIN_PHONE", kinPhone);
        patientEditorFragment.setArguments(arguments);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, patientEditorFragment)
                .addToBackStack(null).commit();
    }

    private void deletePatient(){
        PatientDBHelper dbHelper = new PatientDBHelper(getActivity());
        int patient = dbHelper.deletePatient(rowId);
        Log.v("PatientEditorFragment", "Row edited with ID=" + patient);
        Toast.makeText(getActivity(), "Patient deleted with ID: " + patient, Toast.LENGTH_SHORT).show();
    }

    public String getGender() {
        String gender;
        switch (genderValue) {
            case 1:
                gender = "Male";
                break;
            case 2:
                gender = "Female";
                break;
            default:
                gender = "No Gender";
                break;
        }
        return gender;
    }

    public String getGenotype() {
        String genotype;
        switch (genotypeValue) {
            case 1:
                genotype = "AA";
                break;
            case 2:
                genotype = "AS";
                break;
            case 3:
                genotype = "SS";
                break;
            case 4:
                genotype = "AC";
                break;
            case 5:
                genotype = "CC";
                break;
            case 6:
                genotype = "SC";
                break;
            default:
                genotype = "None";
                break;
        }
        return genotype;
    }

    public String getBloodGroup() {
        String bloodGroup;
        switch (bloodGroupValue) {
            case 1:
                bloodGroup = "A +";
                break;
            case 2:
                bloodGroup = "A -";
                break;
            case 3:
                bloodGroup = "B +";
                break;
            case 4:
                bloodGroup = "B -";
                break;
            case 5:
                bloodGroup = "O +";
                break;
            case 6:
                bloodGroup = "O -";
                break;
            case 7:
                bloodGroup = "AB +";
                break;
            case 8:
                bloodGroup = "AB -";
                break;
            default:
                bloodGroup = "None";
                break;
        }
        return bloodGroup;
    }
}
