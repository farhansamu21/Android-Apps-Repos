package com.prodalang1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.prodalang1.R;
import com.prodalang1.models.UploadModel;
import com.squareup.picasso.Picasso;

public class HomePostAdapter extends FirestoreRecyclerAdapter<UploadModel, HomePostAdapter.HomePostHolder> {

    // Variable for listener
    private OnItemClickListener listener;


    public HomePostAdapter(@NonNull FirestoreRecyclerOptions<UploadModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HomePostHolder homePostHolder, int position, @NonNull UploadModel home_post) {
        homePostHolder.textViewProductName.setText(home_post.getUploadProductName());
        homePostHolder.textViewProductPrice.setText("Rp" + String.valueOf(home_post.getUploadProductPrice()));
        Picasso.get().
                load(home_post.getUploadProductImage())
                .placeholder(R.drawable.ic_chart)
                .fit()
                .centerCrop()
                .into(homePostHolder.imageViewProductImage);
        homePostHolder.textViewProductRating.setRating(home_post.getUploadProductRating());
    }

    @NonNull
    @Override
    public HomePostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_view_1, parent, false);
        return new HomePostHolder(v);
    }


    // Recycler View holder
    class HomePostHolder extends RecyclerView.ViewHolder {

        // Variable init
        MaterialRippleLayout material;
        TextView textViewProductName;
        TextView textViewProductPrice;
        ImageView imageViewProductImage;
        AppCompatRatingBar textViewProductRating;

        public HomePostHolder(@NonNull View itemView) {
            super(itemView);
            material = itemView.findViewById(R.id.material);
            textViewProductName = itemView.findViewById(R.id.text_view_productName);
            textViewProductPrice = itemView.findViewById(R.id.text_view_productPrice);
            imageViewProductImage = itemView.findViewById(R.id.image_view_productImage);
            textViewProductRating = itemView.findViewById(R.id.rating_bar_productRating);

            // Click for the listener
            material.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
