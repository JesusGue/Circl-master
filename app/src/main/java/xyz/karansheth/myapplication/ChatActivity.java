package xyz.karansheth.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }
    public void goHome(View view) {
        Intent intent = new Intent(getApplicationContext(), UserFeedActivity.class);
        startActivity(intent);
    }
}
