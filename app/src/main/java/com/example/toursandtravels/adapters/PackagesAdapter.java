package com.example.toursandtravels.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.toursandtravels.DBClass;
import com.example.toursandtravels.R;
import com.example.toursandtravels.user.AddBookingsActivity;
import com.example.toursandtravels.user.PackagesDetailsActivity;

import java.util.ArrayList;

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Packages> packagesArrayList;
    Context context;

    public PackagesAdapter(Context ctx, ArrayList<Packages> packagesArrayList) {
        inflater = LayoutInflater.from(ctx);
        this.context = ctx;
        this.packagesArrayList = packagesArrayList;
    }

    public void updateList(ArrayList<Packages> newList) {
        this.packagesArrayList = newList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.packages, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Packages item = packagesArrayList.get(position);
        holder.txtName.setText(item.name);

        String details = item.location + "  •  " + item.description;
        holder.txtDetails.setText(details);
        holder.txtPrice.setText("₹" + item.price + " / person");

        String rating = (item.rating != null && !item.rating.isEmpty()) ? item.rating : "4.7";
        holder.txtRating.setText(rating);

        String cat = (item.category != null && !item.category.isEmpty()) ? item.category : "City Tour";
        String dur = (item.duration != null && !item.duration.isEmpty()) ? item.duration : "1 Day";
        holder.txtTag.setText(cat + " • " + dur);

        int imgRes = (item.imageResId != 0) ? item.imageResId : R.drawable.img_4;
        holder.imgThumb.setImageResource(imgRes);

        boolean isFav = DBClass.isFavorite(item.id);
        holder.imgFavorite.setImageResource(isFav ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);

        holder.imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean nowFav = DBClass.toggleFavorite(item.id);
                holder.imgFavorite.setImageResource(nowFav ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);
                Toast.makeText(context, nowFav ? "Saved to Wishlist" : "Removed from Wishlist", Toast.LENGTH_SHORT).show();
            }
        });

        holder.layoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PackagesDetailsActivity.class);
                intent.putExtra("id", item.id);
                intent.putExtra("name", item.name);
                intent.putExtra("location", item.location);
                intent.putExtra("description", item.description);
                intent.putExtra("price", item.price);
                intent.putExtra("lattitude", item.lattitude);
                intent.putExtra("longitude", item.longitude);
                intent.putExtra("imageResId", imgRes);
                intent.putExtra("rating", rating);
                intent.putExtra("category", cat);
                intent.putExtra("duration", dur);
                intent.putExtra("bestTime", item.bestTime);
                intent.putExtra("highlights", item.highlights);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.btnBooknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddBookingsActivity.class);
                intent.putExtra("id", item.id);
                intent.putExtra("name", item.name);
                intent.putExtra("location", item.location);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return packagesArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtDetails, txtPrice, txtRating, txtTag;
        LinearLayout layoutCard;
        ImageView imgThumb, imgFavorite;
        Button btnBooknow;

        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            txtName = view.findViewById(R.id.txtName);
            txtDetails = view.findViewById(R.id.txtDetails);
            txtPrice = view.findViewById(R.id.txtPrice);
            txtRating = view.findViewById(R.id.txtRating);
            txtTag = view.findViewById(R.id.txtTag);
            layoutCard = view.findViewById(R.id.layoutCard);
            imgThumb = view.findViewById(R.id.imgThumb);
            imgFavorite = view.findViewById(R.id.imgFavorite);
            btnBooknow = view.findViewById(R.id.btnBooknow);
        }
    }
}
