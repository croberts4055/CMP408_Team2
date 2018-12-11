package com.example.abrahamlaragranados.team3proj;

import android.os.Parcel;
import android.os.Parcelable;

public class Cases implements Parcelable {
    public String case_id;
    public String case_Lawyer;
    public String case_clerk;
    public String case_location;
    public String case_type;
    public double case_budget;
    public String case_judge;
    public String case_date;
    public String case_court;
    public String case_status;

    public Cases(){}
    public Cases(String case_id, String case_lawyer, String case_clerk,String case_location, String case_type,
                 double case_budget, String case_judge, String case_date, String case_court,String case_status){
        this.case_id =  case_id;
        this.case_Lawyer= case_lawyer;
        this.case_clerk = case_clerk;
        this.case_location = case_location;
        this.case_type = case_type;
        this.case_budget = case_budget;
        this.case_judge = case_judge;
        this.case_date =  case_date;
        this.case_court  = case_court;
        this.case_status = case_status;
    }

    protected Cases(Parcel in) {
        case_id = in.readString();
        case_Lawyer = in.readString();
        case_clerk = in.readString();
        case_location = in.readString();
        case_type = in.readString();
        case_budget = in.readDouble();
        case_judge = in.readString();
        case_date = in.readString();
        case_court = in.readString();
        case_status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(case_id);
        dest.writeString(case_Lawyer);
        dest.writeString(case_clerk);
        dest.writeString(case_location);
        dest.writeString(case_type);
        dest.writeDouble(case_budget);
        dest.writeString(case_judge);
        dest.writeString(case_date);
        dest.writeString(case_court);
        dest.writeString(case_status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Cases> CREATOR = new Creator<Cases>() {
        @Override
        public Cases createFromParcel(Parcel in) {
            return new Cases(in);
        }

        @Override
        public Cases[] newArray(int size) {
            return new Cases[size];
        }
    };

    //void setters
    public void setCase_id(String case_id)
    {this.case_id =  case_id;}
    public void setCase_Lawyer(String case_lawyer)
    {this.case_Lawyer = case_lawyer;}
    public void setCase_clerk(String case_clerk)
    {this.case_clerk  = case_clerk;}
    public void setCase_location(String case_location)
    {this.case_location =  case_location;}
    public void setCase_type(String case_type)
    {this.case_type  = case_type;}
    public void setCase_budget(double value)
    {this.case_budget =  value;}
    public void setCase_judge(String case_judge)
    {this.case_judge =  case_judge;}
    public void setCase_date(String case_date)
    {this.case_date =  case_date;}
    public void setCase_court(String case_location)
    {this.case_location =  case_location ;}
    public void setCase_status (String case_status)
    {this.case_status   = case_status;}
    //void getters

    public String getCase_id()
    {return case_id;}
    public String getCase_Lawyer()
    {return case_Lawyer;}
    public String getCase_clerk()
    {return this.case_clerk;}
    public String getCase_location()
    {return  this.case_location;}
    public String getCase_type()
    {return this.case_type;}
    public String getCase_judge()
    {return this.case_judge;}
    public String getCase_date()
    {return this.case_date;}
    public double getCase_budget()
    {return this.case_budget;}
    public String getCase_court()
    {return this.case_court;}
    public String getCase_status()
    {return this.case_status;}

}
