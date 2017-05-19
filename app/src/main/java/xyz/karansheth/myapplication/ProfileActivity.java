package xyz.karansheth.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
