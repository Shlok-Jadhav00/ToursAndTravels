package com.example.toursandtravels.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.toursandtravels.DBClass;
import com.example.toursandtravels.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Booking> bookingArrayList;
    Context context;

    public BookingAdapter(Context ctx, ArrayList<Booking> bookingArrayList){

        inflater = LayoutInflater.from(ctx);
        this.bookingArrayList = bookingArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.booking, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.txtName.setText(bookingArrayList.get(position).username);

        String details = bookingArrayList.get(position).address + "\n" + bookingArrayList.get(position).mobileno+ "\n"  + bookingArrayList.get(position).fort;

        holder.txtDetails.setText(details);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = DBClass.deleteBooking(bookingArrayList.get(position).id);
                if (success) {
                    Toast.makeText(context,"Deleted Booking Successfully", Toast.LENGTH_SHORT).show();
                    bookingArrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, bookingArrayList.size());
                } else {
                    Toast.makeText(context,"Delete Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtDetails;
        LinearLayout layoutCard;
        ImageView imgDelete;


        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtDetails = (TextView) view.findViewById(R.id.txtDetails);

            imgDelete = (ImageView) view.findViewById(R.id.imgDelete);
            layoutCard = (LinearLayout) view.findViewById(R.id.layoutCard);

        }
    }
}
