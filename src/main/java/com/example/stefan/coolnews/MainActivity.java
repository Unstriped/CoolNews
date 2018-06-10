package com.example.stefan.coolnews;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


// Fetches news from Firebase server
// Server gets populated with Zapier with news from DN.se (rss feed)

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FetchNews();
    }

    public void FetchNews(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        Query query = myRef.child("news").limitToLast(3);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v(TAG, "Fetching News...");
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    NewsContent nContent = singleSnapshot.getValue(NewsContent.class);

                    Log.v(TAG, "------------------------------------------------------------------------------------");
                    Log.v(TAG, nContent.getNewsPubDate());
                    Log.v(TAG, nContent.getNewsTitle());
                    Log.v(TAG, nContent.getNewsDescription());
                    Log.v(TAG, nContent.getNewsLink());
                    Log.v(TAG, "------------------------------------------------------------------------------------");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Error :(");
            }
        });
    }
}
