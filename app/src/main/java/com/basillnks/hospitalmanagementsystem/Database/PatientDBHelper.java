package com.basillnks.hospitalmanagementsystem.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.basillnks.hospitalmanagementsystem.Database.PatientsContract.PatientEntry;
import com.basillnks.hospitalmanagementsystem.PatientModel;

import java.util.ArrayList;

public class PatientDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hospital.db";
    private static final int DATABASE_VERSION = 1;

    public PatientDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + PatientEntry.TABLE_NAME + "("
                + PatientEntry.PAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PatientEntry.FIRST_NAME + " TEXT NOT NULL, "
                + PatientEntry.LAST_NAME + " TEXT NOT NULL, "
                + PatientEntry.PHONE_NUMBER + " TEXT NOT NULL, "
                + PatientEntry.CONTACT_ADDRESS + " TEXT NOT NULL, "
                + PatientEntry.DATE_OF_BIRTH + " TEXT NOT NULL, "
                + PatientEntry.GENDER + " TEXT NOT NULL, "
                + PatientEntry.GENOTYPE + " TEXT NOT NULL, "
                + PatientEntry.BLOOD_GROUP + " TEXT NOT NULL, "
                + PatientEntry.NEXT_OF_KIN + " TEXT NOT NULL, "
                + PatientEntry.KIN_RELATIONSHIP + " TEXT NOT NULL, "
                + PatientEntry.KIN_PHONE_NUMBER + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + PatientEntry.TABLE_NAME;
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public Long addNewPatient(String firstName, String lastName, String phoneNumber,
                              String contactAddress, String dateOfBirth, int gender,
                              int genotype, int bloodGroup, String nextOfKin,
                              String kinRelationship, String kinPhone) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PatientEntry.FIRST_NAME, firstName);
        values.put(PatientEntry.LAST_NAME, lastName);
        values.put(PatientEntry.PHONE_NUMBER, phoneNumber);
        values.put(PatientEntry.CONTACT_ADDRESS, contactAddress);
        values.put(PatientEntry.DATE_OF_BIRTH, dateOfBirth);
        values.put(PatientEntry.GENDER, gender);
        values.put(PatientEntry.GENOTYPE, genotype);
        values.put(PatientEntry.BLOOD_GROUP, bloodGroup);
        values.put(PatientEntry.NEXT_OF_KIN, nextOfKin);
        values.put(PatientEntry.KIN_RELATIONSHIP, kinRelationship);
        values.put(PatientEntry.KIN_PHONE_NUMBER, kinPhone);

        return db.insert(PatientEntry.TABLE_NAME, null, values);
    }

    public ArrayList<PatientModel> displayPatientList() {
        ArrayList<PatientModel> patient = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                PatientEntry.PAT_ID, PatientEntry.FIRST_NAME, PatientEntry.LAST_NAME,
                PatientEntry.PHONE_NUMBER, PatientEntry.CONTACT_ADDRESS, PatientEntry.DATE_OF_BIRTH,
                PatientEntry.GENDER, PatientEntry.GENOTYPE, PatientEntry.BLOOD_GROUP,
                PatientEntry.NEXT_OF_KIN, PatientEntry.KIN_RELATIONSHIP, PatientEntry.KIN_PHONE_NUMBER
        };
        String sortOrder = PatientEntry.PAT_ID + " DESC";

        Cursor cursor =
                db.query(PatientEntry.TABLE_NAME, projection, null, null, null, null, sortOrder);

        try {
            // Find all the index of each column
            int columnID = cursor.getColumnIndex(PatientEntry.PAT_ID);
            int columnFirstName = cursor.getColumnIndex(PatientEntry.FIRST_NAME);
            int columnLastName = cursor.getColumnIndex(PatientEntry.LAST_NAME);
            int columnPhone = cursor.getColumnIndex(PatientEntry.PHONE_NUMBER);
            int columnAddress = cursor.getColumnIndex(PatientEntry.CONTACT_ADDRESS);
            int columnDOB = cursor.getColumnIndex(PatientEntry.DATE_OF_BIRTH);
            int columnGender = cursor.getColumnIndex(PatientEntry.GENDER);
            int columnGenotype = cursor.getColumnIndex(PatientEntry.GENOTYPE);
            int columnBloodGroup = cursor.getColumnIndex(PatientEntry.BLOOD_GROUP);
            int columnNextKin = cursor.getColumnIndex(PatientEntry.NEXT_OF_KIN);
            int columnKinRelation = cursor.getColumnIndex(PatientEntry.KIN_RELATIONSHIP);
            int columnKinPhone = cursor.getColumnIndex(PatientEntry.KIN_PHONE_NUMBER);

            // Iterate iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(columnID);
                String currentFirstName = cursor.getString(columnFirstName);
                String currentLastName = cursor.getString(columnLastName);
                String currentPhone = cursor.getString(columnPhone);
                String currentAddress = cursor.getString(columnAddress);
                String currentDOB = cursor.getString(columnDOB);
                int currentGender = cursor.getInt(columnGender);
                int currentGenotype = cursor.getInt(columnGenotype);
                int currentBloodGroup = cursor.getInt(columnBloodGroup);
                String currentNextKin = cursor.getString(columnNextKin);
                String currentKinRelation = cursor.getString(columnKinRelation);
                String currentKinPhone = cursor.getString(columnKinPhone);

                patient.add(new PatientModel(currentFirstName, currentLastName, currentPhone,
                        currentAddress, currentDOB, currentGender, currentGenotype, currentBloodGroup,
                        currentNextKin, currentKinRelation, currentKinPhone, currentID));
            }
        } finally {
            cursor.close();
            db.close();
        }
        return patient;
    }

    public Cursor displaySinglePatient() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                PatientEntry.FIRST_NAME, PatientEntry.LAST_NAME, PatientEntry.PHONE_NUMBER,
                PatientEntry.CONTACT_ADDRESS, PatientEntry.DATE_OF_BIRTH, PatientEntry.GENDER,
                PatientEntry.GENOTYPE, PatientEntry.BLOOD_GROUP, PatientEntry.NEXT_OF_KIN,
                PatientEntry.KIN_RELATIONSHIP, PatientEntry.KIN_PHONE_NUMBER, PatientEntry.PAT_ID
        };

        return db.query(PatientEntry.TABLE_NAME, projection, null, null, null, null, null);
    }

    public int updatePatient(String firstName, String lastName, String phoneNumber,
                             String contactAddress, String dateOfBirth, int gender,
                             int genotype, int bloodGroup, String nextOfKin,
                             String kinRelationship, String kinPhone, int rowId) {

        // New value for one column
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PatientEntry.FIRST_NAME, firstName);
        values.put(PatientEntry.LAST_NAME, lastName);
        values.put(PatientEntry.PHONE_NUMBER, phoneNumber);
        values.put(PatientEntry.CONTACT_ADDRESS, contactAddress);
        values.put(PatientEntry.DATE_OF_BIRTH, dateOfBirth);
        values.put(PatientEntry.GENDER, gender);
        values.put(PatientEntry.GENOTYPE, genotype);
        values.put(PatientEntry.BLOOD_GROUP, bloodGroup);
        values.put(PatientEntry.NEXT_OF_KIN, nextOfKin);
        values.put(PatientEntry.KIN_RELATIONSHIP, kinRelationship);
        values.put(PatientEntry.KIN_PHONE_NUMBER, kinPhone);

        // Which row to update, based on the ID
        String selection = PatientEntry.PAT_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(rowId)};

        return db.update(PatientEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    public int deletePatient(int rowID){
        // New value for one column
        SQLiteDatabase db = getWritableDatabase();

        // Which row to update, based on the ID
        String selection = PatientEntry.PAT_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(rowID)};

        return db.delete(PatientEntry.TABLE_NAME, selection, selectionArgs);
    }
}
