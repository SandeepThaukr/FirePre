package com.example.my.firepre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity {
    EditText et1,et2,et3,et4;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        et3=(EditText)findViewById(R.id.et3);
        et4=(EditText)findViewById(R.id.et4);
        mAuth = FirebaseAuth.getInstance();
    }
    public void submit(View v){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User_info/"+mAuth.getUid());
        myRef.child("name").setValue(et1.getText().toString());
        myRef.child("gender").setValue(et2.getText().toString());
        myRef.child("mobile").setValue(et3.getText().toString());
        myRef.child("address").setValue(et4.getText().toString());

        Intent i = new Intent(Main2Activity.this,Main3Activity.class);
        startActivity(i);

    }
}
