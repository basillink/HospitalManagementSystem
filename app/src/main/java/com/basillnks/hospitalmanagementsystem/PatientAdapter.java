package com.basillnks.hospitalmanagementsystem;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.basillnks.hospitalmanagementsystem.Fragments.PatientDetailsFragment;
import com.basillnks.hospitalmanagementsystem.Fragments.PatientsListFragment;

import java.util.ArrayList;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {
    private Context mContext;
    private ArrayList<PatientModel> mPatients;

    public PatientAdapter(Context context, ArrayList<PatientModel> patients) {
        this.mContext = context;
        this.mPatients = patients;
    }

    @NonNull

    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_info, parent, false);
        PatientViewHolder holder = new PatientViewHolder(view, mPatients);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        PatientModel currentPatient = mPatients.get(position);
        holder.setData(currentPatient);
    }

    @Override
    public int getItemCount() {
        return mPatients.size();
    }

    public class PatientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int counter = 0;
        private ArrayList<PatientModel> patientModels;
        private CardView cardView;
        private CheckBox selector;
        private TextView name, gender, dob, phone, genotype, bloodGroup;

        public PatientViewHolder(View itemView, ArrayList<PatientModel> patient) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_patient_name);
            gender = itemView.findViewById(R.id.tv_gender);
            dob = itemView.findViewById(R.id.tv_dob);
            phone = itemView.findViewById(R.id.tv_phone);
            genotype = itemView.findViewById(R.id.tv_genotype);
            bloodGroup = itemView.findViewById(R.id.tv_blood_group);
            selector = itemView.findViewById(R.id.select_checkbox);
            cardView = itemView.findViewById(R.id.card_view);
            cardView.setOnClickListener(this);
            this.patientModels = patient;
        }

        public void setData(PatientModel currentPatient) {
            if (!PatientsListFragment.is_in_action_mode) {
                selector.setVisibility(View.GONE);
            } else {
                selector.setVisibility(View.VISIBLE);
                selector.setChecked(false);
            }
            name.setText(currentPatient.getPatientName());
            dob.setText(currentPatient.getDateOfBirth());
            phone.setText(currentPatient.getPhoneNumber());
            genotype.setText(String.format("Genotype: %s", currentPatient.getGenotypeValue()));
            bloodGroup.setText(String.format("BloodGroup: %s", currentPatient.getBloodGroupValue()));
            gender.setBackgroundResource(
                    currentPatient.getGender() == 1 ? R.drawable.ic_male : R.drawable.ic_female);
        }

        @Override
        public void onClick(View view) {
            int position = patientModels.size() - getAdapterPosition() - 1;
            PatientModel patient = this.patientModels.get(position);
            PatientDetailsFragment fragment = new PatientDetailsFragment();
            Bundle arguments = new Bundle();
            arguments.putInt("ID", position);
            arguments.putString("FIRST_NAME", patient.getFirstName());
            arguments.putString("LAST_NAME", patient.getLastName());
            arguments.putString("PHONE", patient.getPhoneNumber());
            arguments.putString("ADDRESS", patient.getContactAddress());
            arguments.putString("DOB", patient.getDateOfBirth());
            arguments.putString("GENDER", patient.getGenderValue());
            arguments.putString("GENOTYPE", patient.getGenotypeValue());
            arguments.putString("BLOOD_GROUP", patient.getBloodGroupValue());
            arguments.putString("NEXT_KIN", patient.getNextOfKin());
            arguments.putString("KIN_RELATION", patient.getKinRelationship());
            arguments.putString("KIN_PHONE", patient.getKinPhoneNumber());
            arguments.putInt("GENDER_POSITION", patient.getGender());
            arguments.putInt("GENOTYPE_POSITION", patient.getGenotype());
            arguments.putInt("BLOOD_GROUP_POSITION", patient.getBloodGroup());
            fragment.setArguments(arguments);
            FragmentTransaction transaction = PatientActivity.fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        }
    }

    public void updateSearch(List<PatientModel> searchList) {
        mPatients = new ArrayList<>();
        mPatients.addAll(searchList);
        notifyDataSetChanged();
    }
}
