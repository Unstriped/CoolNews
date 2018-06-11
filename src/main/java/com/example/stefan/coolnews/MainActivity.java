package com.example.stefan.coolnews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private Context mContext;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;
    private TextView mFeedPubDateTextView;
    private TextView mFeedTitleTextView;
    private TextView mFeedLinkTextView;
    private TextView mFeedDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.RED));
        mContext = getApplicationContext();

        FetchNews();
    }

    public void FetchNews(){





        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        Query query = myRef.child("news").limitToLast(3);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                LinearLayout myRoot = (LinearLayout) findViewById(R.id.my_root);
                LinearLayout a = new LinearLayout(mContext);
                myRoot.removeAllViews();
                a.setOrientation(LinearLayout.VERTICAL);

                Log.v(TAG, "Fetching News...");
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    NewsContent nContent = singleSnapshot.getValue(NewsContent.class);
                    String date  = nContent.getNewsPubDate();
                    String title  = nContent.getNewsTitle();
                    String description  = nContent.getNewsDescription();
                    String link  = nContent.getNewsLink();

                    // temp fix to hide empty news
                    if(description == null){
                        continue;
                    }


                    TextView dateView = new TextView(mContext);
                    dateView.setText(date);
                    dateView.setTypeface(Typeface.MONOSPACE);

                    TextView titleView = new TextView(mContext);
                    titleView.setText(title);
                    titleView.setTypeface(Typeface.DEFAULT_BOLD);

                    TextView descView = new TextView(mContext);
                    descView.setText(description);

                    TextView linkView = new TextView(mContext);
                    linkView.setText(link);
                    linkView.setPadding(0,0,0,30);
                    linkView.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));

                    a.addView(dateView,0);
                    a.addView(titleView,1);
                    a.addView(descView,2);
                    a.addView(linkView,3);
                }

                myRoot.addView(a);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Error :(");
            }
        });
    }
}
