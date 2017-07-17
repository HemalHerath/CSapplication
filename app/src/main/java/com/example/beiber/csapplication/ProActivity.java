package com.example.beiber.csapplication;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

/**
 * Created by BEIBER on 7/15/2017.
 */

public class ProActivity extends AppCompatActivity {

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ImageView proView;
    private EditText name,address,tel;
    private Uri imgUri;
    private FirebaseAuth firebaseAuth;
    private TextView textviewUserEmail;

    public static final String STORAGE_PATH="image/";
    public static final String DATABASE_PATH="users/profiles";
    public static final int REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        textviewUserEmail = (TextView)findViewById(R.id.textviewUserEmail);
        textviewUserEmail.setText(user.getEmail());

        proView = (ImageView) findViewById(R.id.proView);
        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        tel = (EditText) findViewById(R.id.tel);
    }

    public void browse(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select image"),REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            imgUri=data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                proView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @SuppressWarnings("VisibleForTests")
    public void upload(View v){

        final String Name = name.getText().toString().trim();
        final String Address = address.getText().toString().trim();
        final String Tel = tel.getText().toString().trim();

        if(TextUtils.isEmpty(Name) || TextUtils.isEmpty(Address) || TextUtils.isEmpty(Tel)) {

            Toast.makeText(this, "All fields must be filled",Toast.LENGTH_LONG).show();
        }else {

            if (imgUri != null) {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                //get the storage reference
                StorageReference ref = storageReference.child(STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imgUri));

                //Add file to the reference
                ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Upload Finished", Toast.LENGTH_SHORT).show();

                        ProUpload proUpload = new ProUpload(name.getText().toString(), address.getText().toString(),
                                tel.getText().toString(), taskSnapshot.getDownloadUrl().toString());

                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        databaseReference.child(user.getUid()).setValue(proUpload);

//                    String uploadId = databaseReference.push().getKey();
//                    databaseReference.child(uploadId).setValue(proUpload);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage((int) progress + "% " + "Uploaded");

                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Please Select image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
