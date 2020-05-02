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

import com.example.ungdungdatvexemphim.Activities.DetailBookActivity;
import com.example.ungdungdatvexemphim.Models.HistoryBook;
import com.example.ungdungdatvexemphim.R;

import java.util.List;

public class HistoryBookAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<HistoryBook> arrhistorybook;



    public HistoryBookAdapter(Context context, int layout, List<HistoryBook> arrhistorybook) {
        this.context = context;
        this.layout = layout;
        this.arrhistorybook = arrhistorybook;
    }

    @Override
    public int getCount() {
        return arrhistorybook.size();
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
        TextView txvTimeCreate;
        Button btnDetail;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null)
        {
            holder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            holder.txvTimeCreate=(TextView)convertView.findViewById(R.id.txvTimeCreate);
            holder.btnDetail=(Button)convertView.findViewById(R.id.btnShowDetail);
            convertView.setTag(holder);
        }
        else {
            holder= (ViewHolder) convertView.getTag();
        }

        final HistoryBook historyBook=arrhistorybook.get(position);
        holder.txvTimeCreate.setText(historyBook.getTimeCreate());




        final View finalConvertView = convertView;
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(finalConvertView.getContext(), DetailBookActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("IDBOOK",historyBook.getIDBooking());
                    bundle.putString("TIMECRE",historyBook.getTimeCreate());
                    intent.putExtra("DULIEUBOOK",bundle);
                    finalConvertView.getContext().startActivity(intent);

                }
            });







        return convertView;
    }
}
