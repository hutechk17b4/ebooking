package com.example.ungdungdatvexemphim.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.ungdungdatvexemphim.Activities.ChooseSeatActivity;
import com.example.ungdungdatvexemphim.Models.LichTrinh;
import com.example.ungdungdatvexemphim.R;

import java.util.List;

public class LichTrinhAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<LichTrinh> arrLichtrinh;

    public LichTrinhAdapter(Context context, int layout, List<LichTrinh> arrLichtrinh) {
        this.context = context;
        this.layout = layout;
        this.arrLichtrinh = arrLichtrinh;
    }

    @Override
    public int getCount() {
        return arrLichtrinh.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder
    {
        TextView txvstart,txvend;
        Button btnselecttime;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null)
        {
            holder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout,null);
            holder.txvstart=(TextView)view.findViewById(R.id.txvStartShow);
            holder.txvend=(TextView)view.findViewById(R.id.txvEndShow);
            holder.btnselecttime=(Button)view.findViewById(R.id.btnSelectTimeShow);
            view.setTag(holder);
        }
        else {
            holder= (ViewHolder) view.getTag();
        }

        final LichTrinh lichTrinh=arrLichtrinh.get(i);
        holder.txvstart.setText(lichTrinh.getStartTime());
        holder.txvend.setText(lichTrinh.getEndTime());


        holder.btnselecttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseSeatActivity chooseseatactivity= new ChooseSeatActivity();
                Intent intent=new Intent(view.getContext(),chooseseatactivity.getClass());
                Bundle bundle=new Bundle();
                bundle.putString("IDphim",lichTrinh.getIDphim());
                bundle.putString("IDrap",lichTrinh.getIDrap());
                intent.putExtra("DulieuPhimRap",bundle);
                view.getContext().startActivity(intent);
            }
        });


        return view;
    }
}
