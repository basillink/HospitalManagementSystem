package com.basillnks.hospitalmanagementsystem;

public class PatientModel {
    private int mID;
    private String mFirstName;
    private String mLastName;
    private String mPhoneNumber;
    private String mContactAddress;
    private String mDateOfBirth;
    private int mGender;
    private int mGenotype;
    private int mBloodGroup;
    private String mNextKin;
    private String mKinRelationship;
    private String mKinPhoneNumber;

    public PatientModel(String firstName, String lastName, String phoneNumber, String contactAddress,
                        String dateOfBirth, int gender, int genotype, int bloodGroup, String nextKin,
                        String kinRelationship, String kinPhoneNumber, int id) {
        this.mID = id;
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mPhoneNumber = phoneNumber;
        this.mContactAddress = contactAddress;
        this.mDateOfBirth = dateOfBirth;
        this.mGender = gender;
        this.mGenotype = genotype;
        this.mBloodGroup = bloodGroup;
        this.mNextKin = nextKin;
        this.mKinRelationship = kinRelationship;
        this.mKinPhoneNumber = kinPhoneNumber;
    }

    public int getID() {
        return mID;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getContactAddress() {
        return mContactAddress;
    }

    public String getDateOfBirth() {
        return mDateOfBirth;
    }

    public int getGender() {
        return mGender;
    }

    public int getGenotype() {
        return mGenotype;
    }

    public int getBloodGroup() {
        return mBloodGroup;
    }

    public String getNextOfKin() {
        return mNextKin;
    }

    public String getKinRelationship() {
        return mKinRelationship;
    }

    public String getKinPhoneNumber() {
        return mKinPhoneNumber;
    }

    public String getPatientName() {
        return mFirstName + " " + mLastName;
    }

    public String getGenderValue() {
        String gender;
        switch (getGender()) {
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

    public String getGenotypeValue() {
        String genotype;
        switch (getGenotype()) {
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

    public String getBloodGroupValue() {
        String bloodGroup;
        switch (getBloodGroup()) {
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
