package com.example.toursandtravels.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.toursandtravels.user.PackagesDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FortAdapter extends RecyclerView.Adapter<FortAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Packages> packagesArrayList;
    Context context;

    public FortAdapter(Context ctx, ArrayList<Packages> packagesArrayList){

        inflater = LayoutInflater.from(ctx);
        this.packagesArrayList = packagesArrayList;
    }

    @Override
    public FortAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.packages, parent, false);
        FortAdapter.MyViewHolder holder = new FortAdapter.MyViewHolder(view);
        return holder;
    }


    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(FortAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.txtName.setText(packagesArrayList.get(position).name);

        String details = packagesArrayList.get(position).location + "\n" + packagesArrayList.get(position).price ;

        holder.txtDetails.setText(details);




        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.e("id", "onClick: " + packagesArrayList.get(position).id);
                    Intent intent = new Intent(context, PackagesDetailsActivity.class);
                    intent.putExtra("id", "" + packagesArrayList.get(position).id);
                    intent.putExtra("name", "" + packagesArrayList.get(position).name);
                    intent.putExtra("location", "" + packagesArrayList.get(position).location);
                    intent.putExtra("description", "" + packagesArrayList.get(position).description);
                    intent.putExtra("price", "" + packagesArrayList.get(position).price);
                    intent.putExtra("lattitude", "" + packagesArrayList.get(position).lattitude);
                    intent.putExtra("longitude", "" + packagesArrayList.get(position).longitude);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } catch (Exception ex) {
                    Log.e("Ex", "onClick: " + ex);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return packagesArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtDetails;
        LinearLayout layoutCard;
        ImageView imageView;



        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtDetails = (TextView) view.findViewById(R.id.txtDetails);
            layoutCard = (LinearLayout) view.findViewById(R.id.layoutCard);
            imageView = (ImageView) view.findViewById(R.id.imgView1);

        }
    }
}
