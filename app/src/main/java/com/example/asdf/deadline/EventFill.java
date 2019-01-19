package com.example.asdf.deadline;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asdf.deadline.bean.Event;

import java.util.Calendar;

public class EventFill extends Activity implements View.OnClickListener {
    private EditText title;
    private TextView time_end;
    private EditText time_need;
    private Spinner type;
    private Spinner level;
    private TextView cancel;
    private TextView confirm ;
    private Event event;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_fill);
        Intent intent = this.getIntent();
        event= (Event) intent.getSerializableExtra("event");
        init();
    }

    public void init()
    {
        title=(EditText)findViewById(R.id.title);
        time_end=(TextView)findViewById(R.id.time_end);
        time_need=(EditText)findViewById(R.id.time_need);
        type=(Spinner)findViewById(R.id.type);
        level=(Spinner)findViewById(R.id.level);
        cancel=(TextView)findViewById(R.id.cancel);
        confirm=(TextView)findViewById(R.id.confirm);
        title.setText(event.getContent());
        time_end.setText(event.getTime_end());
        time_end.setOnClickListener(this);
        time_need.setText(event.getTime_need());
        type.setPrompt(event.getType());
        level.setPrompt(event.getLevel());
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);

        SpinnerAdapter apsAdapter= type.getAdapter();
        int k=apsAdapter.getCount();
        String type_=event.getType();
        for(int i=0;i<k;i++)
        {
            if(type_.equals(apsAdapter.getItem(i).toString()))
            {
                type.setSelection(i);
                break;
            }
        }

        apsAdapter= level.getAdapter();
        k=apsAdapter.getCount();
        String level_=event.getLevel();
        for(int i=0;i<k;i++)
        {
            if(level_.equals(apsAdapter.getItem(i).toString()))
            {
                level.setSelection(i);
                break;
            }
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.confirm)
        {
            event.setContent(title.getText().toString());
            event.setTime_end(time_end.getText().toString());
            event.setTime_need(time_need.getText().toString());
            event.setType((String)type.getSelectedItem());
            event.setLevel((String)level.getSelectedItem());
            Toast.makeText(this,String.valueOf(event.getLevel()),Toast.LENGTH_LONG).show();
            Intent i = new Intent();
            i.putExtra("event",event);
            setResult(RESULT_OK, i);
            finish();
        }
        if(v.getId()==R.id.cancel)
        {
            Intent i = new Intent();
            setResult(10,i);
            finish();
        }
        if(v.getId()==R.id.time_end)
        {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            System.out.println("年-->" + year + "月-->"
                                    + monthOfYear + "日-->" + dayOfMonth);
                            time_end.setText(year + "-" + (monthOfYear+1) + "-"
                                    + dayOfMonth);
                        }
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }
    }
}
