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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        FetchNews();
    }

    public void FetchNews(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        Query query = myRef.child("news").limitToLast(3);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("katthatt");
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    NewsContent nContent = singleSnapshot.getValue(NewsContent.class);
                    System.out.println("------------------------------------------");
                    System.out.println(nContent.getNewsPubDate());
                    System.out.println(nContent.getNewsTitle());
                    System.out.println(nContent.getNewsDescription());
                    System.out.println(nContent.getNewsLink());
                    System.out.println("------------------------------------------");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /*Query query = myRef.child("news").limitToLast(3);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("något händer");
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    NewsContent nContent = new NewsContent();
                    nContent.setNewsTitle(singleSnapshot.child("newsTitle").getValue(NewsContent.class).getNewsTitle());

                    //Log.d(TAG, "showdata: title: " + nContent.getNewsTitle());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }
}
