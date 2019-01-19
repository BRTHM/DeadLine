package com.example.asdf.deadline.eventDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.asdf.deadline.bean.Event;
import java.util.ArrayList;
import java.util.List;

public class EventDB {
    public static final String CITY_DB_NAME = "event.db";
    private static final String CITY_TABLE_NAME = "events";
    private SQLiteDatabase db;

    public EventDB(Context context, String path) {
        db = context.openOrCreateDatabase(path, Context.MODE_PRIVATE, null);
    }

    public void CreateTable()
    {

    }
    //读出城市列表
    public List<Event> getAllCity() {
        List<Event> list = new ArrayList<Event>();
        Cursor c = db.rawQuery("SELECT * from " + CITY_TABLE_NAME, null);
        while (c.moveToNext()) {
            String province = c.getString(c.getColumnIndex("province"));
            String city = c.getString(c.getColumnIndex("city"));
            String number = c.getString(c.getColumnIndex("number"));
            String allPY = c.getString(c.getColumnIndex("allpy"));
            String allFirstPY = c.getString(c.getColumnIndex("allfirstpy"));
            String firstPY = c.getString(c.getColumnIndex("firstpy"));
            Event event = new Event();
            list.add(event);
        }
        return list;
    }
}
