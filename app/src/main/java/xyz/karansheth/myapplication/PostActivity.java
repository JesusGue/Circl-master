package xyz.karansheth.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText jobTitle;
    private EditText date;
    private EditText time;
    private EditText venue;
    private EditText jobDescription;

    private int x;

    private Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        jobTitle = (EditText) findViewById(R.id.jobTitleEditText);
        date = (EditText) findViewById(R.id.dateEditText);
        time = (EditText) findViewById(R.id.enterTimeEditText);
        venue = (EditText) findViewById(R.id.venueEditText);
        jobDescription = (EditText) findViewById(R.id.descriptionEditText);
        post = (Button) findViewById(R.id.postButton);

        x = 0;

        post.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                x = createPost(x) + 1;

                Intent intent = new Intent(getApplicationContext(), UserFeedActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * I was able to write the data to the Database but if someone else was to create a post, it would overwrite the values.
     * I need to find a way to create the parents such as 'Job Title' but add children underneath that.
     * I found a video, and I have it saved, make sure to watch later to be able to replicate the results for the database.
     * All that is left is to call the database and create the post.
     */
    public int createPost(int x)
    {
        //Create the parent Job Title
        String valueJobTitle = jobTitle.getText().toString();
        mDatabase.child("Data" + x).child("Job Title" + x).setValue(valueJobTitle);


        //Create the date parent Date
        String valueDate = date.getText().toString();
        mDatabase.child("Data" + x).child("Date" + x).setValue(valueDate);

        //Create the time parent Time
        String valueTime = time.getText().toString();
        mDatabase.child("Data" + x).child("Time" + x).setValue(valueTime);

        //Create the venue parent Venue
        String valueVenue = venue.getText().toString();
        mDatabase.child("Data" + x).child("Venue" + x).setValue(valueVenue);

        //Crate the job description Job Description
        String valueJobDescription = jobDescription.getText().toString();
        mDatabase.child("Data" + x).child("Job Description" + x).setValue(valueJobDescription);

        return x;
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
