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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText input_email;
    private EditText input_password;
    TextView signupLink;
    Button logInButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        input_email = (EditText) findViewById(R.id.emailEditText);
        input_password = (EditText) findViewById(R.id.passwordEditText);
        logInButton = (Button) findViewById(R.id.logInButton);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null ) {
                    Log.e(TAG, firebaseUser.isEmailVerified() ? "User is signed in and email is verified" : "Email is not verified");
                } else {
                    Log.e(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    public void signUp(View view)   {
        Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
        startActivity(intent);
    }

    public void logIn(View view)    {

        signIn();

    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null ) {
            //Toast.makeText(LoginActivity.this, input_email.getText().toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), UserFeedActivity.class);
            startActivity(intent);

        }

    }

    /**
     * This method the main method that logs the user in.
     */
    private void signIn()   {

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");

        String email = input_email.getText().toString();
        String password = input_password.getText().toString();


        if(validate(email)) {
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI
                                if (mAuth.getCurrentUser().isEmailVerified()) {
                                    //Toast.makeText(LoginActivity.this, input_email.getText().toString(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), UserFeedActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Email not verified", Toast.LENGTH_SHORT).show();
                                    mAuth.signOut();
                                }
                            }else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            progressDialog.dismiss();
                        }
                    });
        }

    }

    /**
     * This method checks whether or not the email that is entered is a SJSU email
     * @param email
     * @return
     */
    public boolean validate(String email) {
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
        return true;
    }

}
