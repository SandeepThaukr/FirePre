package com.example.my.firepre;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText et1,et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        mAuth = FirebaseAuth.getInstance();
    }
    public void register(View v){
        Task<AuthResult> task=mAuth.createUserWithEmailAndPassword(et1.getText().toString(),et2.getText().toString());
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this,"Auth Success",Toast.LENGTH_LONG).show();
                    Intent i= new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(i);
                }
                    else{
                    Toast.makeText(MainActivity.this,"Auth Not Success",Toast.LENGTH_LONG).show();
                }


            }
        });

}
public void login(View v){

    Task<AuthResult> task=mAuth.signInWithEmailAndPassword(et1.getText().toString(),et2.getText().toString());
    task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                Toast.makeText(MainActivity.this,"Login Success",Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(MainActivity.this,"Login Not Success",Toast.LENGTH_LONG).show();
            }


        }
    });
}
}
