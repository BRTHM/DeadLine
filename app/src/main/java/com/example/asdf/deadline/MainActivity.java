package com.example.asdf.deadline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asdf.deadline.EventList.EventList;
import com.example.asdf.deadline.util.NetUtil;

import com.example.asdf.deadline.bean.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private int UserId;
    private ListView lv;
    private EventAdapter adapter;
    private List<Event> list;
    private EventList eventList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventFill(new Event());
            }
        });




        if(NetUtil.getNetworkState(this)!=NetUtil.NETWORN_NONE)
        {
            Log.d("myweather", "net connected");
            Toast.makeText(MainActivity.this,"net connected",Toast.LENGTH_LONG).show();
        }
        else
        {
            Log.d("myweather", "net unconnected");
            Toast.makeText(MainActivity.this,"net unconnected",Toast.LENGTH_LONG).show();
        }
        initView();

    }

    private void eventFill(Event event)
    {
        Intent intent=new Intent(this,EventFill.class);
        intent.putExtra("event",event);
        startActivityForResult(intent,1);
    }
    private void initView() {
        context=this;
        eventList=new EventList(this);
        eventList.sort();
        lv=findViewById(R.id.lv);
        list=eventList.getEvent_list();
        adapter=new EventAdapter(this,list);
        if(lv!=null) {
            lv.setAdapter(adapter);
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                eventFill(list.get(position));
            }
        });

        if(getUserId()==-1)
        {
            setUserId();
        }
    }

    private void setUserId() {
        int id =1;
        SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("user_id",id);
        editor.commit();
        UserId=id;
        Log.d("setUserId",String.valueOf(UserId));
    }

    private int getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        UserId = sharedPreferences.getInt("user_id", -1);
        Log.d("getUserId",String.valueOf(UserId));
        return UserId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Event event = (Event) intent.getSerializableExtra("event");

            int index=eventList.indexOfID(event.getId());
            if(index==-1) {
                eventList.addEvent(event);
            }
            else {
                list.set(index,event);
                eventList.updata(event);
            }
            eventList.sort();
            adapter.notifyDataSetChanged();
        }
    }
}












