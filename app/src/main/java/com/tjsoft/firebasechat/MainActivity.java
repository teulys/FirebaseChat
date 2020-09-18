package com.tjsoft.firebasechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tjsoft.firebasechat.adapters.UserAdapter;
import com.tjsoft.firebasechat.model.entities.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "MainActivity";
    private FirebaseAnalytics mFirebaseAnalytics;
    private AdView mAdView;
    private FirebaseDatabase database;
    private ListView listView;
    private User myUser;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        try {
            myUser = (User) getIntent().getSerializableExtra("myUser");
        } catch (Exception ex) { }

        listView = findViewById(R.id.lvCharUsers);

        listView.setOnItemClickListener(this);

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userList = new ArrayList<>();
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    userList.add(user);
                }

                UserAdapter adapter = new UserAdapter(userList);
                listView.setAdapter(adapter);
                //Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    public void onClick(View view) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User chatUser = userList.get(position);

        Intent intent = new Intent(this, ChatRoomActivity.class);
        intent.putExtra("myUser", myUser);
        intent.putExtra("chatUser", chatUser);

        startActivity(intent);
    }
}
