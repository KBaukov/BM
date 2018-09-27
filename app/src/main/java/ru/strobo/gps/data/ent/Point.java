package ru.strobo.gps.data.ent;

import java.util.Date;

public class Point {

    private String dateTime;
    private String point;
    private Integer isSend;

    public Point(String dateTime, String point, Integer isSend) {
        this.dateTime = dateTime;
        this.point = point;
        this.isSend = isSend;
    }

    public Point() { }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }
}
