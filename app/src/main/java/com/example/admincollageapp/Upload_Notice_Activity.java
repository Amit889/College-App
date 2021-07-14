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
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.admincollageapp.Model.NoticeModel;
import com.example.admincollageapp.databinding.ActivityUploadNoticeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Upload_Notice_Activity extends AppCompatActivity {

    ActivityUploadNoticeBinding binding;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private Uri imageUri;
    private String downloadUrl;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUploadNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

                           binding.uploadNoticeFinalImage.setImageURI(imageUri);
                        }
                    }
                });

        //Initialising FirebaseDatabase and FirebaseStorage
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        //Initialising Progress dialog
        pd = new ProgressDialog(this);

        binding.uploadNoticeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImage= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                someActivityResultLauncher.launch(pickImage);
            }
        });

        binding.uploadNoticeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String str= binding.uplaodNoticeEditText.getText().toString().trim();
                if(str.isEmpty()){
                    binding.uplaodNoticeEditText.setError("Empty");

                }else if(imageUri==null){
                    Toast.makeText(Upload_Notice_Activity.this,"Please Upload Image",Toast.LENGTH_SHORT).show();

                }else{
                    uploadImage(str);

                }
            }
        });
    }
   private void uploadImage(String str){
                pd.setMessage("Uploading.......");
                pd.show();
       //uploading Image on database
         String myId="mystringImageId"+Math.random();
       try {
            myId = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri).toString();
       } catch (IOException e) {
           e.printStackTrace();
       }

       final StorageReference reference = storage.getReference().child("Notice").child(myId);

             reference.putFile(imageUri)
               .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               Toast.makeText(Upload_Notice_Activity.this,"Notice Uploaded in Storage",Toast.LENGTH_SHORT).show();
                       reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                       @Override
                                       public void onSuccess(Uri uri) {
                                           downloadUrl =  String.valueOf(uri);
                                           uploadData(str);
                                       }
                                   });
                   }
               }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull  Exception e) {
                     pd.dismiss();
                     Toast.makeText(Upload_Notice_Activity.this, "Not  Uploaded!", Toast.LENGTH_SHORT).show();
                 }
             });

   }
    private void uploadData(String title) {
          String uniqueKey = database.getReference().child("Notice").push().getKey();

          // getting current date
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat  curDate = new SimpleDateFormat("dd-MM-yy");
        String date = curDate.format(calForDate.getTime());

        // getting current time
        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat  curTime = new SimpleDateFormat("hh:mm a");
        String time = curTime.format(calForTime.getTime());

        NoticeModel model =new NoticeModel(title,downloadUrl,date,time,uniqueKey);

        database.getReference().child("Notice").child(uniqueKey).setValue(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        pd.dismiss();
                        Toast.makeText(Upload_Notice_Activity.this,"Notice Uploaded Successfully!",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                pd.dismiss();
                Toast.makeText(Upload_Notice_Activity.this,"Error in Uploading notice",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }


}