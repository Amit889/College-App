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
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Toast;

import com.example.admincollageapp.Model.NoticeModel;
import com.example.admincollageapp.databinding.ActivityUploadPdfBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Upload_Pdf_Activity extends AppCompatActivity {

    ActivityUploadPdfBinding binding;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private String downloadUrl;
    private Uri pdfUri;
    private String pdfName;
    private String title;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadPdfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Initialising
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        //Initialising progressDialog
        pd = new ProgressDialog(this);

        //Taking pdf from phone Storage
        // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes

                            Intent intent = result.getData();
                            if (intent == null) return;
                            pdfUri = intent.getData();

                            if(pdfUri!=null){
                                if(pdfUri.toString().startsWith("content://")){
                                    Cursor cursor=null;

                                    try {
                                        cursor = Upload_Pdf_Activity.this.getContentResolver()
                                                .query(pdfUri,null,null,null,null);
                                        if(cursor!=null&&cursor.moveToFirst()){
                                            pdfName=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }else if(pdfUri.toString().startsWith("file://")){
                                    pdfName = new File(pdfUri.toString()).getName();
                                }
                                binding.uploadPdfPdfName.setText(pdfName);
                            }
                            // binding.uploadNoticeFinalImage.setImageURI(imageUri);
                        }
                    }
                });

        binding.uploadPdfCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImage = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                pickImage.setType("application/pdf");
                pickImage.setAction(Intent.ACTION_GET_CONTENT);
                someActivityResultLauncher.launch(pickImage);
            }
        });

        //uploading data on firebase
        binding.uploadPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 title = binding.uplaodPdfEditText.getText().toString().trim();
                if (title.isEmpty()) {
                    binding.uplaodPdfEditText.setError("please write pdf name");

                } else if (pdfUri == null) {
                    Toast.makeText(Upload_Pdf_Activity.this, "Please select Pdf",
                            Toast.LENGTH_SHORT).show();
                } else {
                    uploadPdf();
                }
            }
        });
    }

    private void uploadPdf() {
        pd.setTitle("please wait......");
        pd.setMessage("Uploading Pdf");
        pd.show();


        final StorageReference reference = storage.getReference().child("Pdf/"+System.currentTimeMillis()+
                pdfName);

        reference.putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(Upload_Pdf_Activity.this, "Pdf Uploaded in Storage", Toast.LENGTH_SHORT).show();
                        Task<Uri>  task= taskSnapshot.getStorage().getDownloadUrl();
                        //this while lopp is to make pause until pdf not get Uploaded
                        while(!task.isComplete());

                        Uri tempUri= task.getResult();
                        downloadUrl= tempUri.toString();
                        uploadData();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Upload_Pdf_Activity.this, "Not  Uploaded!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadData() {
        String uniqueKey = database.getReference().child("Pdf").push().getKey();



        NoticeModel model = new NoticeModel(title,pdfName, downloadUrl);

        database.getReference().child("Pdf").child(uniqueKey).setValue(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        pd.dismiss();
                        Toast.makeText(Upload_Pdf_Activity.this, "Pdf Uploaded Successfully!",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Upload_Pdf_Activity.this, "Error in Uploading Pdf",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}