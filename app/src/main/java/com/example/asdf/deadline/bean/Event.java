package com.example.asdf.deadline.bean;


import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event implements Serializable ,Comparable<Event>{
    private String content;
    private String time_end;
    private String time_need;
    private String time_create;
    private String time_start;
    private String level;
    private String type;
    private int id;

    private static final long serialVersionUID = -7060210544600464481L;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");




    public Event() {
        this.content = "移动平台软件开发";
        this.time_end = "2019-1-20";
        this.time_need = "10";
        this.time_create = "2018-1-17";
        this.time_start = "2018-1-17";
        this.level = "普通";
        this.id=-1;
        this.type = "作业";
    }

    public Event(String content, String time_end, String time_need, String time_create, String time_start, String level, String type) {
        this.content = content;
        this.time_end = time_end;
        this.time_need = time_need;
        this.time_create = time_create;
        this.time_start = time_start;
        this.level = level;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public String getTime_end() {
        return time_end;
    }

    public String getTime_need() {
        return time_need;
    }

    public String getTime_create() {
        return time_create;
    }

    public String getTime_start() {
        return time_start;
    }

    public String getLevel() {
        return level;
    }

    public String getType() {
        return type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public void setTime_need(String time_need) {
        this.time_need = time_need;
    }

    public void setTime_create(String time_create) {
        this.time_create = time_create;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRemainDay()
    {
        Date now=new Date();
        Date endtime= null;
        try {
            endtime = format.parse(time_end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long remaintime=(endtime.getTime()-now.getTime());
        return (int)(remaintime/(1000*3600*24))+1;
    }
    @Override
    public int compareTo(@NonNull Event o) {
        try {
            return format.parse(time_end).compareTo(format.parse(o.getTime_end()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 1;
    }
}

