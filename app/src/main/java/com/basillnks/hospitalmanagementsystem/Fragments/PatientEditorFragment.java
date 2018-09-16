package com.basillnks.hospitalmanagementsystem.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.basillnks.hospitalmanagementsystem.Database.PatientDBHelper;
import com.basillnks.hospitalmanagementsystem.Database.PatientsContract.PatientEntry;
import com.basillnks.hospitalmanagementsystem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientEditorFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Bundle bundle;
    private EditText etFirstName, etLastName, etPhone, etAddress, etDOB, etNextKin, etKinRelation, etKinPhone;
    private Spinner spinnerGender, spinnerGenotype, spinnerBloodGroup;
    private int rowId, gender, genotype, bloodGroup, unknown;
    private String firstName, lastName, phone, address, dob, nextKin, kinRelation, kinPhone;

    public PatientEditorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_editor, container, false);

        findWidgets(view);

        setHasOptionsMenu(true);
        Toolbar toolbar = view.findViewById(R.id.patient_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        bundle = getArguments();
        if (bundle == null) {
            actionBar.setTitle("Add New Patient");
        } else {
            actionBar.setTitle("Edit Patient Detail");
            setData();
        }

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        String selected = (String) adapterView.getItemAtPosition(pos);
        if (!TextUtils.isEmpty(selected)) {
            if (selected.equals(getString(R.string.male))) {
                gender = PatientEntry.GENDER_MALE;
            } else if (selected.equals(getString(R.string.female))) {
                gender = PatientEntry.GENDER_FEMALE;
            } else if (selected.equals(getString(R.string.genotype_AA))) {
                genotype = PatientEntry.GENOTYPE_AA;
            } else if (selected.equals(getString(R.string.genotype_AS))) {
                genotype = PatientEntry.GENOTYPE_AS;
            } else if (selected.equals(getString(R.string.genotype_SS))) {
                genotype = PatientEntry.GENOTYPE_SS;
            } else if (selected.equals(getString(R.string.genotype_AC))) {
                genotype = PatientEntry.GENOTYPE_AC;
            } else if (selected.equals(getString(R.string.genotype_CC))) {
                genotype = PatientEntry.GENOTYPE_CC;
            } else if (selected.equals(getString(R.string.genotype_SC))) {
                genotype = PatientEntry.GENOTYPE_SC;
            } else    if (selected.equals(getString(R.string.group_A_positive))) {
                bloodGroup = PatientEntry.BLOOD_GROUP_A_POSITIVE;
            } else if (selected.equals(getString(R.string.group_A_negative))) {
                bloodGroup = PatientEntry.BLOOD_GROUP_A_NEGATIVE;
            } else if (selected.equals(getString(R.string.group_B_positive))) {
                bloodGroup = PatientEntry.BLOOD_GROUP_B_POSITIVE;
            } else if (selected.equals(getString(R.string.group_B_negative))) {
                bloodGroup = PatientEntry.BLOOD_GROUP_B_NEGATIVE;
            } else if (selected.equals(getString(R.string.group_O_positive))) {
                bloodGroup = PatientEntry.BLOOD_GROUP_O_POSITIVE;
            } else if (selected.equals(getString(R.string.group_O_negative))) {
                bloodGroup = PatientEntry.BLOOD_GROUP_O_NEGATIVE;
            } else if (selected.equals(getString(R.string.group_AB_positive))) {
                bloodGroup = PatientEntry.BLOOD_GROUP_AB_POSITIVE;
            } else if (selected.equals(getString(R.string.group_AB_negative))) {
                bloodGroup = PatientEntry.BLOOD_GROUP_AB_NEGATIVE;
            } else {
                unknown = PatientEntry.UNKNOWN;
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        unknown = PatientEntry.UNKNOWN;
    }

    /**
     * Initialize the contents of the Fragment host's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called {@link #setHasOptionsMenu}.  See
     * {@link Fragment#onCreateOptionsMenu(Menu, MenuInflater)}
     * for more information.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater The view used to instantiate the menu xml file.
     * @see #setHasOptionsMenu
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.editor_menu, menu);
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
            case R.id.menu_save:
                if (bundle == null) {
                    addNewPatient();
                } else {
                    updatePatientInfo();
                }
                getActivity().onBackPressed();
                break;
            case R.id.menu_reset:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addNewPatient() {
        firstName = etFirstName.getText().toString().trim();
        lastName = etLastName.getText().toString().trim();
        phone = etPhone.getText().toString().trim();
        address = etAddress.getText().toString().trim();
        dob = etDOB.getText().toString().trim();
        nextKin = etNextKin.getText().toString().trim();
        kinRelation = etKinRelation.getText().toString().trim();
        kinPhone = etKinPhone.getText().toString().trim();

        PatientDBHelper patientDBHelper = new PatientDBHelper(getActivity());
        Long newRow = patientDBHelper.addNewPatient(firstName, lastName, phone, address,
                dob, gender, genotype, bloodGroup, nextKin, kinRelation, kinPhone);
        patientDBHelper.close();
        Log.v("PatientEditorFragment", "New row added with ID=" + newRow);
    }

    private void updatePatientInfo() {
        firstName = etFirstName.getText().toString();
        lastName = etLastName.getText().toString();
        phone = etPhone.getText().toString();
        address = etAddress.getText().toString();
        dob = etDOB.getText().toString();
        nextKin = etNextKin.getText().toString();
        kinRelation = etKinRelation.getText().toString();
        kinPhone = etKinPhone.getText().toString();

        PatientDBHelper patientDBHelper = new PatientDBHelper(getActivity());
        int editRow = patientDBHelper.updatePatient(firstName, lastName, phone, address,
                dob, gender, genotype, bloodGroup, nextKin, kinRelation, kinPhone, rowId);
        patientDBHelper.close();
    }

    private void findWidgets(View view) {
        etFirstName = view.findViewById(R.id.et_first_name);
        etLastName = view.findViewById(R.id.et_last_name);
        etPhone = view.findViewById(R.id.et_phone);
        etAddress = view.findViewById(R.id.et_address);
        etDOB = view.findViewById(R.id.et_dob);
        etNextKin = view.findViewById(R.id.et_next_kin);
        etKinRelation = view.findViewById(R.id.et_kin_relationship);
        etKinPhone = view.findViewById(R.id.et_kin_phone);

        spinnerGender = view.findViewById(R.id.spinner_gender);
        ArrayAdapter genderAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.gender_options, android.R.layout.simple_spinner_dropdown_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        spinnerGenotype = view.findViewById(R.id.spinner_genotype);
        ArrayAdapter genotypeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.genotype_options, android.R.layout.simple_spinner_dropdown_item);
        genotypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenotype.setAdapter(genotypeAdapter);

        spinnerBloodGroup = view.findViewById(R.id.spinner_blood_group);
        ArrayAdapter bloodGroupAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.blood_group, android.R.layout.simple_spinner_dropdown_item);
        bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodGroup.setAdapter(bloodGroupAdapter);

        spinnerGender.setOnItemSelectedListener(this);
        spinnerGenotype.setOnItemSelectedListener(this);
        spinnerBloodGroup.setOnItemSelectedListener(this);
    }

    public void setData() {
        int position = bundle.getInt("ID");
        PatientDBHelper dbHelper = new PatientDBHelper(getActivity());
        Cursor cursor = dbHelper.displaySinglePatient();
        cursor.moveToPosition(position);

        rowId = cursor.getInt(cursor.getColumnIndex(PatientEntry.PAT_ID));
        firstName = cursor.getString(cursor.getColumnIndex(PatientEntry.FIRST_NAME));
        lastName = cursor.getString(cursor.getColumnIndex(PatientEntry.LAST_NAME));
        phone = cursor.getString(cursor.getColumnIndex(PatientEntry.PHONE_NUMBER));
        address = cursor.getString(cursor.getColumnIndex(PatientEntry.CONTACT_ADDRESS));
        dob = cursor.getString(cursor.getColumnIndex(PatientEntry.DATE_OF_BIRTH));
        int genderValue = cursor.getInt(cursor.getColumnIndex(PatientEntry.GENDER));
        int genotypeValue = cursor.getInt(cursor.getColumnIndex(PatientEntry.GENOTYPE));
        int bloodGroupValue = cursor.getInt(cursor.getColumnIndex(PatientEntry.BLOOD_GROUP));
        nextKin = cursor.getString(cursor.getColumnIndex(PatientEntry.NEXT_OF_KIN));
        kinRelation = cursor.getString(cursor.getColumnIndex(PatientEntry.KIN_RELATIONSHIP));
        kinPhone = cursor.getString(cursor.getColumnIndex(PatientEntry.KIN_PHONE_NUMBER));

        etFirstName.setText(firstName);
        etLastName.setText(lastName);
        etPhone.setText(phone);
        etAddress.setText(address);
        etDOB.setText(dob);
        etNextKin.setText(nextKin);
        etKinRelation.setText(kinRelation);
        etKinPhone.setText(kinPhone);
        spinnerGender.setSelection(genderValue);
        spinnerGenotype.setSelection(genotypeValue);
        spinnerBloodGroup.setSelection(bloodGroupValue);
    }
}
