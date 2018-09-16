package com.basillnks.hospitalmanagementsystem.Database;

import android.provider.BaseColumns;

public final class PatientsContract {

    private PatientsContract() {
    }

    public static class PatientEntry {
        public static final String TABLE_NAME = "patients";
        public static final String PAT_ID = BaseColumns._ID;
        public static final String FIRST_NAME = "pat_first_name";
        public static final String LAST_NAME = "pat_last_name";
        public static final String PHONE_NUMBER = "pat_phone_number";
        public static final String CONTACT_ADDRESS = "pat_address";
        public static final String DATE_OF_BIRTH = "pat_dob";
        public static final String GENDER = "pat_gender";
        public static final String GENOTYPE = "pat_genotype";
        public static final String BLOOD_GROUP = "pat_blood_group";
        public static final String NEXT_OF_KIN = "pat_next_kin";
        public static final String KIN_RELATIONSHIP = "pat_kin_relationship";
        public static final String KIN_PHONE_NUMBER = "pat_kin_phone";

        public static final int UNKNOWN = 0;

        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        public static final int GENOTYPE_AA = 1;
        public static final int GENOTYPE_AS = 2;
        public static final int GENOTYPE_SS = 3;
        public static final int GENOTYPE_AC = 4;
        public static final int GENOTYPE_CC = 5;
        public static final int GENOTYPE_SC = 6;

        public static final int BLOOD_GROUP_A_POSITIVE = 1;
        public static final int BLOOD_GROUP_A_NEGATIVE = 2;
        public static final int BLOOD_GROUP_B_POSITIVE = 3;
        public static final int BLOOD_GROUP_B_NEGATIVE = 4;
        public static final int BLOOD_GROUP_O_POSITIVE = 5;
        public static final int BLOOD_GROUP_O_NEGATIVE = 6;
        public static final int BLOOD_GROUP_AB_POSITIVE = 7;
        public static final int BLOOD_GROUP_AB_NEGATIVE = 8;
    }
}
