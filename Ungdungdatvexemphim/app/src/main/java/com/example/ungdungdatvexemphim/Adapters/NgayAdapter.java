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

import com.example.ungdungdatvexemphim.Activities.SelectTimeStartActivity;
import com.example.ungdungdatvexemphim.Models.Ngay;
import com.example.ungdungdatvexemphim.R;

import java.util.List;

public class NgayAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Ngay> arrNgay;

    public NgayAdapter(Context context, int layout, List<Ngay> arrNgay) {
        this.context = context;
        this.layout = layout;
        this.arrNgay = arrNgay;
    }

    @Override
    public int getCount() {
        return arrNgay.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder
    {
        TextView txvdate;
        Button btnselecdate;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if(view==null)
        {
            holder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout,null);
            holder.txvdate=(TextView)view.findViewById(R.id.txvDate);
            holder.btnselecdate=(Button)view.findViewById(R.id.btnSelectDate);
            view.setTag(holder);

        }
        else {
            holder=(ViewHolder) view.getTag();
        }


        final Ngay ngay=arrNgay.get(position);
        holder.txvdate.setText(ngay.getNgayChieu());


        final View finalView = view;
        holder.btnselecdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectTimeStartActivity selectTimeStartActivity=new SelectTimeStartActivity();
                Intent intent=new Intent(finalView.getContext(),selectTimeStartActivity.getClass());
                Bundle bundle=new Bundle();
                bundle.putString("IDngaychieu",ngay.getID());
                bundle.putString("IDphim",ngay.getIDphim());
                intent.putExtra("DULIEUDATE",bundle);
                finalView.getContext().startActivity(intent);
            }
        });

        return view;
    }
}
