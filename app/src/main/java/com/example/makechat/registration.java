package com.example.makechat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

public class registration extends AppCompatActivity {
      TextView login;
      EditText regEmail,regUserName,regPass;
      Button regBtn;
      ShapeableImageView imgUser;
      FirebaseAuth auth;
      Uri imageUri;
      String imageUrl;
      FirebaseDatabase database;
      FirebaseStorage storage;
      ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        getSupportActionBar().hide();
        regUserName=findViewById(R.id.edtUser);
        regEmail=findViewById(R.id.edtEmailreg);
        regPass=findViewById(R.id.edtpassreg);
        login=findViewById(R.id.txtlogin);
        regBtn=findViewById(R.id.buttonreg);
        imgUser=findViewById(R.id.imgUser);
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registration.this,login.class));
                finish();
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=regUserName.getText().toString();
                String email=regEmail.getText().toString();
                String pass=regPass.getText().toString();
                String status="Hey, I am using Application";
                progressDialog.show();
                if(name.isEmpty()){
                    progressDialog.dismiss();
                    regUserName.setError("Enter Name");
                }
                else if(email.isEmpty()){
                    progressDialog.dismiss();
                    regEmail.setError("Enter Email");
                }
                else if(pass.isEmpty()){
                    progressDialog.dismiss();
                    regPass.setError("Enter password");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    progressDialog.dismiss();
                    regEmail.setError("Enter Valid Email");
                }
                else if(pass.length()<6){
                    progressDialog.dismiss();
                    regPass.setError("Enter password more than 6 letters");
                }
                else{
                    auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                String id=task.getResult().getUser().getUid();
                                DatabaseReference reference=database.getReference().child("user").child(id);
                                StorageReference storageReference=storage.getReference().child("Upload").child(id);
                                if(imageUri!=null){
                                    storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if(task.isSuccessful()){
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageUrl=uri.toString();
                                                        Users users=new Users(id,name,email,pass,imageUrl,status);
                                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    Intent intent=new Intent(registration.this,MainActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                                else{
                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(registration.this, "Error in creating User", Toast.LENGTH_SHORT).show();
                                                                }

                                                            }
                                                        });
                                                    }
                                                });
                                            }

                                        }
                                    });
                                }

                                else{
                                    progressDialog.show();
                                    String status="Hey, I am using Application";
                                    imageUrl="https://firebasestorage.googleapis.com/v0/b/make-chat-3f202.appspot.com/o/man.png?alt=media&token=424a0a6d-efca-4065-a4e7-735dd657f68a";
                                    Users users=new Users(id,name,email,pass,imageUrl,status);
                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Intent intent=new Intent(registration.this,MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else{
                                                progressDialog.dismiss();
                                                Toast.makeText(registration.this, "Error in creating User", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                                }
                            }

                            else{
                                progressDialog.dismiss();
                                Toast.makeText(registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),10);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){
            if(data!=null){
                 imageUri=data.getData();
                 imgUser.setImageURI(imageUri);
            }
        }
    }
}