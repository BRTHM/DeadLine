package com.example.asdf.deadline.EventList;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.asdf.deadline.bean.Event;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static android.content.Context.MODE_PRIVATE;
import android.content.ContextWrapper.*;
import android.util.Log;

public class EventList {
    private ArrayList<Event> event_list;
    private HashSet<String> id_set;
    private SharedPreferences sharedPreferences;
    private Context context;
    private int next;

    private static final String FILE_NAME= "event_list";
    public EventList(Context context) {
        this.context=context;
        sharedPreferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        id_set= (HashSet<String>) sharedPreferences.getStringSet("id_set",new HashSet<String>());
        next=sharedPreferences.getInt("next",0);
        event_list=new ArrayList<Event>();
        for(String id:id_set){
            Event event=getEvent(id);
            event_list.add(event);
        }
        for(Event e:event_list)
        {
            if(e.getRemainDay()<0)
                removeEvent(e);
        }

    }

    public void sort()
    {
        Collections.sort(event_list);
    }

    public void addEvent(Event event)
    {
        event_list.add(event);
        id_set.add(String.valueOf(next));
        event.setId(next);
        saveEvent(event,next);
        next=(next+1)%Integer.MAX_VALUE;
        saveIdSet();
        saveNext();
    }

    public int indexOfID(int id)
    {
        for(Event e :event_list)
        {
            if(e.getId()==id)
            {
                return event_list.indexOf(e);
            }
        }
        return  -1;
    }

    public void updata(Event event)
    {
        saveEvent(event,event.getId());
    }

    private void saveNext() {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("next",next);
        editor.commit();
    }

    public void removeEvent(Event event)
    {
        String id_str=String.valueOf(event.getId());
        event_list.remove(event);
        id_set.remove(id_str);
        saveIdSet();
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(id_str);
        editor.commit();
    }

    public ArrayList<Event> getEvent_list() {
        return event_list;
    }

    private void saveIdSet() {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putStringSet("id_set",id_set);
        editor.commit();
    }


    private Event getEvent(String id_str)  {
        String event_str=sharedPreferences.getString(id_str,null);
        Event event = null;
        try {
            event = deSerialization(event_str);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return event;
    }

    private void saveEvent(Event event,int id){
        String event_str= null;
        try {
            event_str = serialize(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(String.valueOf(id),event_str);
        editor.commit();
    }


    private String serialize (Event event) throws IOException{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(event);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return serStr;
    }

    private Event deSerialization(String str) throws IOException,
            ClassNotFoundException {
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        Event event = (Event) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return event;
    }

}
