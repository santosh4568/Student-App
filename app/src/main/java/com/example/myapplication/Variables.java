package com.example.myapplication;

import android.widget.ProgressBar;

public class Variables {
    private String subjectname,total,absent,percentage;
    private ProgressBar p1;

    public Variables(String subjectname, String total, String absent, String percentage, ProgressBar p1) {
        this.subjectname = subjectname;
        this.total = total;
        this.absent = absent;
        this.percentage = percentage;
        this.p1 = p1;
    }

    public ProgressBar getP1() {
        return p1;
    }

    public void setP1(ProgressBar p1) {
        this.p1 = p1;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
