package xyz.karansheth.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserFeedActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private ArrayList<String> posts = new ArrayList<>();

    private ListView feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);
        System.out.println("This is the UserFeedActivity and it will be displayed in the Logs");

        feed = (ListView)findViewById(R.id.feed);
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = mDatabase.getReferenceFromUrl("https://myapplication4-b497d.firebaseio.com/Data0");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, posts);
        feed.setAdapter(arrayAdapter);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                posts.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void goPost(View view) {
        Intent intent = new Intent(getApplicationContext(), PostActivity.class);
        startActivity(intent);
    }
    public void goProfile(View view) {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }
    public void goHome(View view) {
        System.out.println("Starting the new activity here I am do not miss me look for me");
        Intent intent = new Intent(getApplicationContext(), UserFeedActivity.class);
        startActivity(intent);
    }
    public void goChat(View view) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        startActivity(intent);
    }
    public void logOut (View view) {
        mAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
