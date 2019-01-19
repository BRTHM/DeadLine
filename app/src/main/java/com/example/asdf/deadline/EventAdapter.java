package com.example.asdf.deadline;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asdf.deadline.R;
import com.example.asdf.deadline.bean.Event;

import java.util.List;

public class EventAdapter extends BaseAdapter {
    //印章数据
    private List<Event> list;

    private LayoutInflater mInflater;



    public EventAdapter(Context context, List<Event> list) {
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.dead_time_item, null);
        }
        ViewHolder holder = getViewHolder(convertView);
        Event e = list.get(position);
        holder.content.setText(e.getContent());
        holder.time_end.setText(e.getTime_end());
        holder.time_remain.setText("剩余时间:"+String.valueOf(e.getRemainDay())+"天");
        holder.type.setText(e.getType());
        String level=e.getLevel();
//        if(level!=null&&level.equals(new String("普通")))
//        holder.myGrad.setColor(Color.GREEN);
        if(level!=null) {
            switch (e.getLevel()) {
                case "普通":
                    holder.myGrad.setColor(Color.GREEN);
                    break;
                case "紧急":
                    holder.myGrad.setColor(Color.YELLOW);
                    break;
                case "非常紧急":
                    holder.myGrad.setColor(Color.RED);
                    break;
                default:
            }
        }
        return convertView;
    }


    /**

     * 获得控件管理对象

     *

     * @param view

     * @return

     */

    private ViewHolder getViewHolder(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        return holder;

    }


    /**

     * 控件管理类

     */

    private class ViewHolder {
        private TextView content, time_end,time_remain,type;
        GradientDrawable myGrad;
        ViewHolder(View view) {
            content = (TextView) view.findViewById(R.id.content);
            time_end = (TextView) view.findViewById(R.id.time_end);
            time_remain = (TextView) view.findViewById(R.id.time_remain);
            type=(TextView)view.findViewById(R.id.type_);
            ImageView time_cycle=view.findViewById(R.id.time_cycle);
            myGrad = (GradientDrawable)time_cycle.getBackground();
        }
    }
}
