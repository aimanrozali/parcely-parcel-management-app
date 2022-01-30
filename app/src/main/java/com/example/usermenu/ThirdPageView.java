package com.example.usermenu;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThirdPageView extends AppCompatActivity {
    private RecyclerView recyclerView;
    ParcelAdapter adapter; // Create Object of the Adapter class
    DatabaseReference dbase; // Create object of the
    // Firebase Realtime Database
    //Customer id from display page
    String cusId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_page_view);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cusId = extras.getString("cusId");
            //The key argument here must match that used in the other activity
        }




    }

}
