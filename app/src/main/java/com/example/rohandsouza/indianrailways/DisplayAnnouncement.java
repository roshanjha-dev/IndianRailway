package com.example.rohandsouza.indianrailways;

/**
 * Created by Rohan Dsouza on 24-Dec-17.
 */

public class DisplayAnnouncement {

    private String date, train_no, info;

    public DisplayAnnouncement(String date, String train_no, String info){
        this.setDate(date);
        this.setTrain_no(train_no);
        this.setInfo(info);

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrain_no() {
        return train_no;
    }

    public void setTrain_no(String train_no) {
        this.train_no = train_no;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
