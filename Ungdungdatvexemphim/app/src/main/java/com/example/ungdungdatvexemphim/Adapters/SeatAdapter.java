package com.example.ungdungdatvexemphim.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ungdungdatvexemphim.Models.Seat;
import com.example.ungdungdatvexemphim.R;

import java.util.List;

public class SeatAdapter extends BaseAdapter {
    // private Button btnXacNhanDat;
    private Context context;
    private int layout;
    private List<Seat> SeatList;

//    public boolean isSelected;
//    public boolean isBooked = false;

    public SeatAdapter(Context context, int layout, List<Seat> seatList) {
        this.context = context;
        this.layout = layout;
        SeatList = seatList;
    }

    @Override
    public int getCount() {
        return SeatList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        ImageButton imgbtnseat;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view==null)
        {
            holder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout,null);
            holder.imgbtnseat=(ImageButton)view.findViewById(R.id.btnSeat);
            view.setTag(holder);
        }
        else {
            holder= (ViewHolder) view.getTag();
        }
        final Seat seat=SeatList.get(i);
        holder.imgbtnseat.setImageResource(seat.getHinh());

        if(seat.isBooked==true)
        {
            seat.checkbooked();
            holder.imgbtnseat.setImageResource(seat.getHinh());
        }


        holder.imgbtnseat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(seat.isSelected==true)
                {
                    seat.isSelected=false;
                    seat.checkselect();
                    holder.imgbtnseat.setImageResource(seat.getHinh());
                    Toast.makeText(view.getContext(),seat.isSelected+"",Toast.LENGTH_SHORT).show();
                }
                else {
                    seat.isSelected=true;
                    seat.checkselect();
                    holder.imgbtnseat.setImageResource(seat.getHinh());
                    Toast.makeText(view.getContext(),seat.isSelected+"",Toast.LENGTH_SHORT).show();
                }

            }
        });



//            holder.imgbtnseat.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    seat.isSelected=false;
//                     seat.checkselect();
//                }
//            });

//        holder.imgbtnseat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                seat.isSelected=false;
//                seat.checkselect();
//            }
//        });

        //=========================================

        return view;
    }




}
