package com.example.abrahamlaragranados.team3proj;

public class Notes {

    public String user_name;
    public String user_id;
    public String note_date;
    public String user_role;
    public String user_note;
    public String case_id;

    public Notes(){}

    public Notes(String id, String case_id, String user_name, String user_role, String user_note, String date){
        this.user_name =  user_name;
        this.user_id = id;
        this.note_date =  date;
        this.user_note =  user_note;
        this.user_role = user_role;
        this.case_id = case_id;

    }

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setNote_date(String note_date) {
        this.note_date = note_date;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public void setUser_note(String user_note) {
        this.user_note = user_note;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getNote_date() {
        return note_date;
    }

    public String getUser_role() {
        return user_role;
    }

    public String getUser_note() {
        return user_note;
    }





}
