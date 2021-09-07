package com.student.studentinformation;

public class dbref {

    private String id,name,faculty,department,blood,gender,mobile,email,job,present,permanent,pass,lastdonate,imgurl,primarykey,Session;



    dbref(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String bobile) {
        this.mobile = bobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getPermanent() {
        return permanent;
    }

    public void setPermanent(String permanent) {
        this.permanent = permanent;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLastdonate() {
        return lastdonate;
    }

    public void setLastdonate(String lastdonate) {
        this.lastdonate = lastdonate;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getPrimarykey() {
        return primarykey;
    }

    public void setPrimarykey(String primarykey) {
        this.primarykey = primarykey;
    }

    public String getSession() {
        return Session;
    }

    public void setSession(String session) {
        Session = session;
    }

    public dbref(String id, String name, String faculty, String department, String blood, String gender, String mobile, String email, String job, String present, String permanent, String pass, String lastdonate, String imgurl, String primarykey, String session) {
        this.id = id;
        this.name = name;
        this.faculty = faculty;
        this.department = department;
        this.blood = blood;
        this.gender = gender;
        this.mobile = mobile;
        this.email = email;
        this.job = job;
        this.present = present;
        this.permanent = permanent;
        this.pass = pass;
        this.lastdonate = lastdonate;
        this.imgurl = imgurl;
        this.primarykey = primarykey;
        Session = session;
    }
}
