package com.prodalang1.fragments;



import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.prodalang1.R;
import com.prodalang1.models.HomePost;
import com.prodalang1.models.UploadModel;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class you extends Fragment {

    // Unknown variable
    private static final int PICK_IMAGE_REQUEST = 1;

    // VARIABLES declaration
    private EditText editTextProductName;
    private EditText editTextProductPrice;
    private EditText editTextProductRating;
    private Button buttonChooseImage;
    private ImageView imageviewShowImage;
    private FloatingActionButton fabAddProduct;
    private ProgressBar mProgressBar;
    private Uri imageUri; // this variable is for storing the link to the image

    // Firebase Firestore initialization
    private CollectionReference mFirestoreRef;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask; // Storage task for uploading


    // Required empty public constructor
    public you() {}


    // Main method for creating View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_you, container, false);

        // Variable initialization
        // View
        editTextProductName = viewRoot.findViewById(R.id.edit_text_productName);
        editTextProductPrice = viewRoot.findViewById(R.id.edit_text_productPrice);
        editTextProductRating = viewRoot.findViewById(R.id.edit_text_productRating);
        buttonChooseImage = viewRoot.findViewById(R.id.button_choose_image);
        imageviewShowImage = viewRoot.findViewById(R.id.image_view);
        fabAddProduct = viewRoot.findViewById(R.id.fab_addProduct);

        // Firestore and Firestorage
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads1");
        mFirestoreRef = FirebaseFirestore.getInstance().collection("Notebook3");

        // Button listener for choosing image
        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        // Button Listener fab for uploading data
        fabAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();
            }
        });

        return viewRoot;
    }


    // Method for getting the file extentsion
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    // Method for choosing image from local storage
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // |___> this is the method for showing the result of choosing an image
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageviewShowImage);
        }
    }


    // Method for uploading the data
    private void uploadData() {
        // VARIABLES declarations
        final String inputProductName = editTextProductName.getText().toString();
        final float inputProductPrice = Float.parseFloat(editTextProductPrice.getText().toString());
        final float inputProductRating = Float.parseFloat(editTextProductRating.getText().toString());


        // CHECKING if the imageUri isn't empty
        if (imageUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));

            // START uploading the image file to the storage
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    // WAIT for 3 seconds to retrieve the image download URL, after successfully uploading the image,
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //mProgressBar.setProgress(0);
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String inputImageUrl = uri.toString();
                                    UploadModel upload = new UploadModel(inputProductName, inputProductPrice, inputProductRating, inputImageUrl);
                                    mFirestoreRef.add(upload);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(),"Failed!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_LONG).show();
                        }
                    }, 3000);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // SHOWING the error message to the user
                    Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    // CALCULATING for the progress bar
                }
            });
        } else {
            Toast.makeText(getContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

}
