package com.example.my.firepre;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.Permission;
import java.security.Permissions;

public class Main3Activity extends AppCompatActivity {

    private StorageReference mStorageRef;

    ImageView imageView;
    boolean cam_pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        imageView=(ImageView)findViewById(R.id.iview);
       // int status = ContextCompat.checkSelfPermission(Main3Activity.this,Manifest.permission.CAM);
       // if(status=)
        mStorageRef= FirebaseStorage.getInstance().getReference(FirebaseAuth.getInstance().getUid());
    }
    public  void cemara(View v){
        Intent i =new Intent("android.media.action." +
                "IMAGE_CAPTURE");
        startActivityForResult(i,124);
    }
    public  void gellary(View v){
        if(true){
            Intent i =new Intent();
            i.setAction(Intent.ACTION_GET_CONTENT);
            i.setType("*image/*");
            startActivityForResult(i,125);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 124 && resultCode==RESULT_OK){
            Object o =   data.getExtras().get("data");
            imageView.setImageBitmap((Bitmap)o);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ((Bitmap)o).compress(Bitmap.CompressFormat.PNG, 0
                    /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();
            ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
            mStorageRef.child("profile.png").putStream(bs).
                    addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Main3Activity.this,
                                        "File Uploaded successfully",
                                        Toast.LENGTH_LONG).show();

                                Uri u =   task.getResult().getDownloadUrl();
                                String s =  u.toString();
                                FirebaseDatabase database =
                                        FirebaseDatabase.getInstance();
                                DatabaseReference myRef =
                                        database.getReference("User_info/"+FirebaseAuth.getInstance().getUid());
                                myRef.child("profile_url").setValue(s);
                            }else{
                                Toast.makeText(Main3Activity.this,
                                        "File Uploaded Failed",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }


        if(requestCode==125 && resultCode==RESULT_OK){
            Uri u = data.getData();
            imageView.setImageURI(u);
            File f = new File(u.getPath());
            mStorageRef.child(f.getName()).putFile(u).
                    addOnCompleteListener(new OnCompleteListener
                            <UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.
                                TaskSnapshot> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(Main3Activity.this,
                                        "File Uploaded successfully",
                                        Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(Main3Activity.this,
                                        "Failed to  Upload",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });


        }
        }
    }

