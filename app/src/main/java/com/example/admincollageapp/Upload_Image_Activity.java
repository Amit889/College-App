package com.example.admincollageapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.admincollageapp.Model.ImageModel;
import com.example.admincollageapp.Model.NoticeModel;
import com.example.admincollageapp.databinding.ActivityUploadImageBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Upload_Image_Activity extends AppCompatActivity {

    ActivityUploadImageBinding  binding;
    String spinnerCategory;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private Uri imageUri;
    private ProgressDialog pd;
    private String downloadUrl;
    private ImageModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialising firebasedatabase and firebasestorage
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        //Initialising Progressdialog
        pd = new ProgressDialog(this);

        //setting values in spinner
        String[] items= new String[]{"Select Category","Convocation","Independence Day",
        "Annual Function","Other Event"};
        binding.uploadImageSpinner.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,items));

        //set spinner on item selection listoner
        binding.uploadImageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerCategory =   binding.uploadImageSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // loading Image from gallery
        // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK ) {
                            // There are no request codes

                            Intent intent = result.getData();
                            if(intent==null)  return;
                            imageUri =intent.getData();

                            binding.uploadImageFinalImage.setImageURI(imageUri);
                        }
                    }
                });
        binding.uploadImageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImage= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                someActivityResultLauncher.launch(pickImage);
            }
        });


        //add onClicklistoner on upload button
        binding.uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinnerCategory.isEmpty()||spinnerCategory.equals("Select Category")){
                    Toast.makeText(Upload_Image_Activity.this," please select category",
                            Toast.LENGTH_SHORT).show();
                }else if(imageUri==null){
                    Toast.makeText(Upload_Image_Activity.this, "Please Select Image",
                            Toast.LENGTH_SHORT).show();
                }else{
                    uploadImage();
                }
            }
        });
    }
    private void uploadImage(){
        pd.setMessage("Uploading.......");
        pd.show();
        //uploading Image on database
        String myId="mystringImageId"+Math.random();
        try {
            myId = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final StorageReference reference = storage.getReference().child("Gallery").child(myId);

        reference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(Upload_Image_Activity.this,"Image Uploaded in Storage",Toast.LENGTH_SHORT).show();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                downloadUrl =  String.valueOf(uri);
                                uploadData();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Upload_Image_Activity.this, "Not  Uploaded!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void uploadData() {
        String uniqueKey = database.getReference().child("Gallery").push().getKey();

        if(uniqueKey==null){
            pd.dismiss();
            Toast.makeText(this, "Error Unique key is null", Toast.LENGTH_SHORT).show();
            return;
        }
        model =new ImageModel(downloadUrl,spinnerCategory);
        database.getReference().child("Gallery").child(uniqueKey).setValue(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        pd.dismiss();
                        Toast.makeText(Upload_Image_Activity.this,"Image Uploaded Successfully!",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                pd.dismiss();
                Toast.makeText(Upload_Image_Activity.this,"Error in Uploading Image",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

}