package com.example.usermenu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    //create object of DatabaseReference class to access firebase's Realtime Database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://fir-f9b19-default-rtdb.firebaseio.com/").getReference(Users.class.getSimpleName());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText fullName = findViewById(R.id.fullName);
        final EditText phoneNumber = findViewById(R.id.phoneNumber);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);

        final Button registerBtn = findViewById(R.id.registerBtn);
        final TextView loginNowBtn = findViewById(R.id.loginNowBtn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               //get data from EditText into String variable
                                               final String fullNameTxt = fullName.getText().toString();
                                               final String phoneTxt = phoneNumber.getText().toString();
                                               final String emailTxt = email.getText().toString();
                                               final String passwordTxt = password.getText().toString();

                                               //check if user full all the fields before sending the data into firebase
                                               if (fullNameTxt.isEmpty() || phoneTxt.isEmpty() || emailTxt.isEmpty() || passwordTxt.isEmpty()) {
                                                   Toast.makeText(Register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                                               } else {

                                                   databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                       @Override
                                                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                           long maxId = (snapshot.getChildrenCount() + 1);
                                                           String cusId = String.valueOf(maxId);

                                                           databaseReference.orderByChild("phone").equalTo(phoneTxt).addListenerForSingleValueEvent(new ValueEventListener() {
                                                               @Override
                                                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                   //check if phone number is not registered before
                                                                   if (snapshot.exists()) {
                                                                       Toast.makeText(Register.this, "Phone is already registered", Toast.LENGTH_SHORT).show();
                                                                   } else {
                                                                       databaseReference.orderByChild("email").equalTo(emailTxt).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                           @Override
                                                                           public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                               if (snapshot.exists()) {
                                                                                   
                                                                               } else {
                                                                                   //send data to realtime database
                                                                                   //using phone number as unique identifier of every user
                                                                                   //so all the other details of user comes under phone number
                                                                                   databaseReference.child(cusId).child("cusId").setValue(cusId);
                                                                                   databaseReference.child(cusId).child("phone").setValue(phoneTxt);
                                                                                   databaseReference.child(cusId).child("fullname").setValue(fullNameTxt);
                                                                                   databaseReference.child(cusId).child("email").setValue(emailTxt);
                                                                                   databaseReference.child(cusId).child("password").setValue(passwordTxt);
                                                                                   databaseReference.child(cusId).child("password").setValue(passwordTxt);
                                                                                   databaseReference.child(cusId).child("password").setValue(passwordTxt);


                                                                                   //show a success message then finish the activity
                                                                                   Toast.makeText(Register.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                                                                   finish();
                                                                               }
                                                                           }

                                                                           @Override
                                                                           public void onCancelled(@NonNull DatabaseError error) {
                                                                           }
                                                                       });

                                                                   }
                                                               }

                                                               @Override
                                                               public void onCancelled(@NonNull DatabaseError error) {

                                                               }
                                                           });

                                                       }

                                                       @Override
                                                       public void onCancelled(@NonNull DatabaseError error) {

                                                       }

                                                   });
                                               }
                                           }
                                       });


        loginNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}