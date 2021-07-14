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
import com.example.admincollageapp.databinding.ActivityUpdateTeacherBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;

public class UpdateTeacher extends AppCompatActivity {

    ActivityUpdateTeacherBinding binding;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private Uri imageUri;
    private String downloadUrl;
    private ProgressDialog pd;
    private TeacherModel model;
    private String name;
    private String email;
    private String post;
    private String uniqueKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Initialising FirebaseDatabase and FirebaseStorage
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        //Initialising Progress dialog
        pd = new ProgressDialog(this);


       binding.updateTeacherName.setText(getIntent().getStringExtra("name"));
       binding.updateTeacherPost.setText(getIntent().getStringExtra("post"));
       binding.updateTeacherEmail.setText(getIntent().getStringExtra("email"));
       uniqueKey = getIntent().getStringExtra("uniqueKey");
       downloadUrl = getIntent().getStringExtra("downloadUrl");
       Picasso.get().load(getIntent().getStringExtra("downloadUrl")).placeholder(R.drawable.avatar)
                .into(binding.updateTeacherProfileimage);


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
                            binding.updateTeacherProfileimage.setImageURI(imageUri);

                        }
                    }
                });

        binding.updateTeacherProfileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImage= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                someActivityResultLauncher.launch(pickImage);
            }
        });

        binding.updateTeacherUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = binding.updateTeacherName.getText().toString().trim();
                email = binding.updateTeacherEmail.getText().toString().trim();
                post = binding.updateTeacherPost.getText().toString().trim();
                if(name.isEmpty()||email.isEmpty()||post.isEmpty()){
                    if(name.isEmpty())  binding.updateTeacherName.setError("Please enter name");
                    else if(email.isEmpty()) binding.updateTeacherEmail.setError("Please enter email");
                    else if(post.isEmpty()) binding.updateTeacherPost.setError("please enter post");

                }else{
                   if(imageUri==null){
                       uploadData();
                   }else{
                        uploadImage();
                   }

                }
            }
        });
        binding.updateTeacherDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
    }

    private void deleteData() {
        // first deleting old image
        storage.getReferenceFromUrl(downloadUrl).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull  Task<Void> task) {
                Toast.makeText(UpdateTeacher.this,"Old image successfully deleted from firebase",
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                Toast.makeText(UpdateTeacher.this,"Old image deleting error",
                        Toast.LENGTH_SHORT).show();
            }
        });
        database.getReference().child("Teacher").child(uniqueKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(UpdateTeacher.this,"value deleted successfully!",
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                Toast.makeText(UpdateTeacher.this,"Error in deleting value",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage(){
        pd.setMessage("Uploading.......");
        pd.show();
        if(imageUri!=null) {
            // first deleting old image
            storage.getReferenceFromUrl(downloadUrl).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(UpdateTeacher.this, "Old image successfully deleted from firebase",
                            Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UpdateTeacher.this, "Old image deleting error",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
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
                        Toast.makeText(UpdateTeacher.this,"Image Uploaded in Storage",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(UpdateTeacher.this, "Not  Uploaded!", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void uploadData() {

        HashMap<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("post",post);
        map.put("email",email);
        map.put("downloadUrl",downloadUrl);
        database.getReference().child("Teacher").child(uniqueKey).updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        pd.dismiss();
                        Toast.makeText(UpdateTeacher.this,"Teacher data Updated Successfully!",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                pd.dismiss();
                Toast.makeText(UpdateTeacher.this,"Error in Updating Data",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }
}