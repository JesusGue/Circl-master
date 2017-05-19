package xyz.karansheth.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationActivity extends AppCompatActivity {

    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    Button signUpButton;

    public long count;


    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        emailEditText = (EditText) findViewById(R.id.emailRegEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordRegEditText);
        confirmPasswordEditText = (EditText) findViewById(R.id.passwordConfirmEditText);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(v);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void logIn(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
    public void signUp(View view)   {

        final ProgressDialog progressDialog = new ProgressDialog(RegistrationActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");

        final String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String firstname = firstNameEditText.getText().toString();
        String lastname = lastNameEditText.getText().toString();
        String name =  firstname + " " + lastname;
        String confirmPassword = confirmPasswordEditText.getText().toString();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                count = dataSnapshot.getChildrenCount();
                System.out.println(count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        count++;
        System.out.println("New count: " + count);

        mDatabase.child("Users").child("Name" + count).setValue(firstname + " " + lastname);



        if(validate(email,password,confirmPassword,firstname,lastname)) {
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendVerificationEmail();
                                mAuth.getInstance().signOut();



                                // Sign in success, update UI with the signed-in user's information

                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.putExtra("Email",emailEditText.getText().toString());
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();

                        }
                    });


        }
    }

    public boolean validate(String email, String password, String confirmPassword, String firstname, String lastname) {
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()) {
            Toast.makeText(this, "Enter valid SJSU Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        int position = email.indexOf("@");
        String domain = email.substring(position+1);
        if(!domain.equalsIgnoreCase("sjsu.edu")) {
            Toast.makeText(this, "sjsu.edu Email required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.equals(confirmPassword))   {
            Toast.makeText(this, "Passwords Dont Match", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(firstname.isEmpty() || lastname.isEmpty() || password.isEmpty() || confirmPassword.isEmpty())    {
            Toast.makeText(this, "Please complete required fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    public void sendVerificationEmail() {
        FirebaseUser user = mAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i("Test", "Email sent.");
                        }
                    }
                });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)   {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

}
