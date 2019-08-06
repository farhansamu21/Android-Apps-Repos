package com.prodalang1;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.StorageTask;
import com.prodalang1.models.UploadModel;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class product_detail extends AppCompatActivity {

    // String Constant
    public static final String EXTRA_POST_KEY = "post_key";

    // Variable declaration
    // Layoutview
    private ImageView vImageProductDetail;
    private TextView vNameProductDetail;
    private AppCompatRatingBar vRatingProductDetail;
    private TextView vProductDescription;
    private Toolbar toolbarHeader;

    // Others variables
    private String mPostKey;
    private String valueProductImage;
    private String valueProductTitle;
    private float valueProductRating;
    private float valueProuductDesc;

    // Firebase
    private DocumentReference mDocumentReference;
    private StorageTask mStorageTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // GET post key from intent
        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        // |__ exception if there is any error with getting the EXTRA_POST_KEY
        if (mPostKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }

        // Variable initialization
        // |__ LayoutView
        vImageProductDetail = findViewById(R.id.product_detail_productImage);
        vNameProductDetail = findViewById(R.id.product_detail_productName);
        vRatingProductDetail = findViewById(R.id.product_detail_productRating);
        vProductDescription = findViewById(R.id.product_detail_productDescription);

        // |__ Firebase
        mDocumentReference = FirebaseFirestore.getInstance().collection("Notebook3").document(mPostKey);

        // Setup the toolbar
        toolbarHeader = findViewById(R.id.product_detail_toolbar);
        setSupportActionBar(toolbarHeader);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // START listening to the Firestore
        mDocumentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                // In case if there is an error in getting the data from firestore
                if ( e!= null){
                    Toast.makeText(product_detail.this,e.getMessage(), Toast.LENGTH_LONG).show();
                }
                else {
                    // GET the data from the Firestore
                    UploadModel getDataFirestore = documentSnapshot.toObject(UploadModel.class);
                    valueProductImage = getDataFirestore.getUploadProductImage();
                    valueProductTitle = getDataFirestore.getUploadProductName();
                    valueProductRating = getDataFirestore.getUploadProductRating();
                    valueProuductDesc = getDataFirestore.getUploadProductPrice();

                    // PLACE the value into the views
                    Picasso.get().
                            load(valueProductImage)
                            .placeholder(R.drawable.ic_chart)
                            .fit()
                            .centerCrop()
                            .into(vImageProductDetail);
                    vNameProductDetail.setText(valueProductTitle);
                    vRatingProductDetail.setRating(valueProductRating);
                    vProductDescription.setText("Rp "+String.valueOf(valueProuductDesc));
                }
            }
        });
    }
}
