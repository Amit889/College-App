package com.example.admincollageapp.Faculty;

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

import com.example.admincollageapp.Model.TeacherModel;
import com.example.admincollageapp.R;
import com.example.admincollageapp.Upload_Image_Activity;
import com.example.admincollageapp.databinding.ActivityAddTeacherBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddTeacher extends AppCompatActivity {

    ActivityAddTeacherBinding binding;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private Uri imageUri;
    private String downloadUrl;
    private ProgressDialog pd;
    private String spinnerCategory;
    private TeacherModel model;
    private String name;
    private String email;
    private String post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Initialising FirebaseDatabase and FirebaseStorage
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        //Initialising Progress dialog
        pd = new ProgressDialog(this);

        //setting adapter on spinner
        String[]  item = new String[]{"Select category","Computer Science","Information Technology","Electrical Engineering"
                ,"Mechanical Engineering","Electrical and Electronics","Civil Engineering"};

        binding.addTeacherSpinner.setAdapter(new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,item));

        binding.addTeacherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerCategory = binding.addTeacherSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //uploading Image from phone
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
                            binding.addTeacherProfileimage.setImageURI(imageUri);

                        }
                    }
                });

        binding.addTeacherProfileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImage= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                someActivityResultLauncher.launch(pickImage);
            }
        });


        binding.addTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 name = binding.addTeacherName.getText().toString().trim();
                 email = binding.addTeacherEmail.getText().toString().trim();
                 post = binding.addTeacherPost.getText().toString().trim();
                if(name.isEmpty()||email.isEmpty()||post.isEmpty()||spinnerCategory.isEmpty()||imageUri==null
                ||spinnerCategory.equals("Select category")){
                    if(name.isEmpty())  binding.addTeacherName.setError("Please enter name");
                    else if(email.isEmpty()) binding.addTeacherEmail.setError("Please enter email");
                    else if(post.isEmpty()) binding.addTeacherPost.setError("please enter post");
                    else if(spinnerCategory.isEmpty()||spinnerCategory.equals("Select category")){
                        Toast.makeText(AddTeacher.this,"Please select Category",Toast.LENGTH_SHORT).show();
                    }else if(imageUri==null){
                        Toast.makeText(AddTeacher.this,"Please select Image",Toast.LENGTH_SHORT).show();
                    }

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

        final StorageReference reference = storage.getReference().child("Teacher").child(myId);


        reference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(AddTeacher.this,"Image Uploaded in Storage",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AddTeacher.this, "Not  Uploaded!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void uploadData() {
        String uniqueKey = database.getReference().child("Teacher").push().getKey();

        //saving all data in model
        model = new TeacherModel(name,email,post,spinnerCategory,downloadUrl,uniqueKey);

        if(uniqueKey==null){
            pd.dismiss();
            Toast.makeText(this, "Error Unique key is null", Toast.LENGTH_SHORT).show();
            return;
        }
        database.getReference().child("Teacher").child(uniqueKey).setValue(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        pd.dismiss();
                        Toast.makeText(AddTeacher.this,"Teacher data Uploaded Successfully!",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                pd.dismiss();
                Toast.makeText(AddTeacher.this,"Error in Uploading Data",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }
}