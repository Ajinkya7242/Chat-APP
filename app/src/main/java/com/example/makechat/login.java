package com.example.makechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    EditText edtLogEmail,edtLogPass;
    TextView createAccount;
    Button btnLog;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        edtLogEmail=findViewById(R.id.edtUser);
        edtLogPass=findViewById(R.id.edtpassreg);
        btnLog=findViewById(R.id.buttonreg);
        createAccount=findViewById(R.id.txtlogin);
       auth=FirebaseAuth.getInstance();
       btnLog.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String Email=edtLogEmail.getText().toString();
               String pass=edtLogPass.getText().toString();

               if(Email.isEmpty()){
                   edtLogEmail.setError("plz Enter Email..");
               }
               else if(pass.isEmpty()){
                   edtLogPass.setError("PLz Enter Password");
               }
               else if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                  edtLogEmail.setError("Enter Valid Email");
               }
               else if(pass.length()<6){
                   edtLogPass.setError("Enter Password more than 6 letters");
               }
               else{

                   auth.signInWithEmailAndPassword(Email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()){
                               try{
                                   startActivity(new Intent(login.this,MainActivity.class));
                                   finish();

                               }catch (Exception e){
                                   Toast.makeText(login.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                               }
                           }else{
                               Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }

           }
       });

       createAccount.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(login.this,registration.class));
               finish();
           }
       });
    }
}