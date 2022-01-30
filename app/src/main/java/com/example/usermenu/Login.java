package com.example.usermenu;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class Login extends AppCompatActivity {


    

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Retrieving the value using its keys the file name
        // must be same in both saving and retrieving the data
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);


        if (sh.contains("email") && sh.contains("password") && sh.contains("cusId")  && sh.contains("name")  && sh.contains("phone") ) {
            //open MainActivity if success
            Intent intent = new Intent(this, ThirdPage.class);
            intent.putExtra("cusId", sh.getString("cusId", ""));
            intent.putExtra("name", sh.getString("name", ""));
            intent.putExtra("phone", sh.getString("phone", ""));
            startActivity(intent);
            finish();
        }
        else
        {

            final Button loginBtn = findViewById(R.id.loginBtn);
            final TextView createAccBtn = findViewById(R.id.txtCreate);


            final EditText email = findViewById(R.id.email);
            final EditText password = findViewById(R.id.password);


            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String emailTxt = email.getText().toString();
                    String passwordTxt = password.getText().toString();


                    if (emailTxt.isEmpty() || passwordTxt.isEmpty()) {
                        Toast.makeText(Login.this, "Please enter email or password!", Toast.LENGTH_SHORT).show();
                    } else {
                        databaseReference.orderByChild("email").equalTo(emailTxt).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                //check if pone number is exist in database
                                if (snapshot.exists()) {

                                    com.example.usermenu.Users user = new com.example.usermenu.Users();
                                    //mobile is exist in firebase database
                                    // now get password of user from firebase and match it with user entered password
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        user = dataSnapshot.getValue(com.example.usermenu.Users.class);
                                    }
                                    final String getPassword = user.getPassword();

                                    if (getPassword.equals(passwordTxt)) {
                                        Toast.makeText(Login.this, "Successful Logged in", Toast.LENGTH_SHORT).show();
                                        //get user details
                                        String idTxt = user.getcusId();
                                        String nameTxt = user.getFullname();
                                        String phoneTxt = user.getPhone();


                                        // Storing data into SharedPreferences
                                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

                                        // Creating an Editor object to edit(write to the file)
                                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

                                        // Storing the key and its value as the data fetched from edittext
                                        myEdit.putString("cusId", idTxt);
                                        myEdit.putString("email", emailTxt);
                                        myEdit.putString("password", passwordTxt);
                                        myEdit.putString("name", nameTxt);
                                        myEdit.putString("phone", phoneTxt);

                                        // Once the changes have been made,
                                        // we need to commit to apply those changes made,
                                        // otherwise, it will throw an error
                                        myEdit.commit();

                                        //open MainActivity if success
                                        Intent intent = new Intent(Login.this, ThirdPage.class);
                                        intent.putExtra("cusId", sh.getString("cusId", ""));
                                        intent.putExtra("name", sh.getString("name", ""));
                                        intent.putExtra("phone", sh.getString("phone", ""));
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(Login.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Login.this, "Email not registered", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                }
            });

            createAccBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //open register activity
                    startActivity(new Intent(Login.this, com.example.usermenu.Register.class));
                }
            });
        }


    }
}