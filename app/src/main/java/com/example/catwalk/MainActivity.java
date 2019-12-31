package com.example.catwalk;


import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {
    private TextInputLayout fname,lname,email,password,repassword,dob;

    TextView gender;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    SignInButton signIn;
    GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN=1;
    private String TAG="MainActivity";
    AuthCredential credential;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fname= findViewById(R.id.firstName);
        lname= findViewById(R.id.lastName);
        email=findViewById(R.id.emailSubmit);
        password= findViewById(R.id.passwordSubmit);
        repassword=findViewById(R.id.retypePassword);
        dob=findViewById(R.id.dateofbirth);
        gender=(TextView)findViewById(R.id.gender);
        firebaseAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        signIn=(SignInButton)findViewById(R.id.sign_in_button);

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }



    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this,"You not able to login google",Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }




    private void updateUI(FirebaseUser user) {

    }



    private boolean validateFname() {
        String fnameInput=fname.getEditText().getText().toString().trim();
        if(fnameInput.isEmpty()){
            fname.setError("Fiend can,t be empty");
            return false;
        }else if(fnameInput.length()>15){
            fname.setError("First name is too long");
            return false;

        }else{
            fname.setError(null);
            return true;
        }
    }
    private boolean validateLname() {
        String lnameInput=lname.getEditText().getText().toString().trim();
        if(lnameInput.isEmpty()){
            lname.setError("Fiend can,t be empty");
            return false;
        }else if(lnameInput.length()>15){
            lname.setError("Last name is too long");
            return false;

        }else{
            lname.setError(null);
            return true;
        }
    } private boolean validateEmail() {
        String emailInput=email.getEditText().getText().toString().trim();
        if(emailInput.isEmpty()){
            email.setError("Fiend can,t be empty");
            return false;
        }
        else{
            email.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput=password.getEditText().getText().toString().trim();
        if(passwordInput.isEmpty()){
            password.setError("Fiend can,t be empty");
            return false;
        }else if(passwordInput.length()<8){
            password.setError("Password is not strong");
            return false;
        }

        else{
            password.setError(null);
            return true;
        }
    }
    private boolean validateRepassword() {
        String repasswordInput=repassword.getEditText().getText().toString().trim();
        if(repasswordInput.isEmpty()){
            repassword.setError("Fiend can,t be empty");
            return false;
        }else if(repasswordInput.length()<8){
            repassword.setError("Password is not strong");
            return false;
        }

        else{
            repassword.setError(null);
            return true;
        }
    }
    private boolean validateDob() {
        String dobInput=dob.getEditText().getText().toString().trim();
        if(dobInput.isEmpty()){
            dob.setError("Fiend can,t be empty");
            return false;
        }

        else{
            dob.setError(null);
            return true;
        }
    }


    public  void btnsubmit_Click(View v){

        if(!validateFname() | !validateLname() | !validateEmail() | !validatePassword() | !validateRepassword() | !validateDob()) {
            return;
        }

            final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Please wait...", "Processing...", true);
            (firebaseAuth.createUserWithEmailAndPassword(email.getEditText().getText().toString(), password.getEditText().getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Registration Successfull", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(MainActivity.this, Login.class);
                        startActivity(i);
                    } else {
                        Log.e("Error", task.getException().toString());
                        Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
            });



    }


}