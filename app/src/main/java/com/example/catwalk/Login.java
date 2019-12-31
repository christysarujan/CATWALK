package com.example.catwalk;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText emaillogin,passwordlogin;
    TextView info;
    Button login;
    //TextView error;
    int counter=3;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emaillogin=(EditText)findViewById(R.id.email);
        passwordlogin=(EditText) findViewById(R.id.password);
        info=(TextView) findViewById(R.id.info);

        info.setText("No of attempts reamining:3");
       // error=(TextView) findViewById(R.id.info2);
       firebaseAuth=FirebaseAuth.getInstance();





    }

    public void btnlogin_Click(View v){
        final ProgressDialog progressDialog=ProgressDialog.show(Login.this,"Please wait...","Processing...",true);
        (firebaseAuth.signInWithEmailAndPassword(emaillogin.getText().toString(),passwordlogin.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful())
                {
                    Toast.makeText(Login.this,"Login Successfull...",Toast.LENGTH_LONG).show();
                    CheckDataEntered();
                    Intent i=new Intent(Login.this,UserProfile.class);
                    i.putExtra("Email",firebaseAuth.getCurrentUser().getEmail());
                    startActivity(i);
                }
                else {

                    Toast.makeText(Login.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                }
            }
        });
    }


    boolean isEmail(EditText text) {
        CharSequence email=text.getText().toString();
        return (!TextUtils.isEmpty(email)&&Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }







 boolean validatePassword( String password) {

        if(password.isEmpty()) {
            return true;
        }

        else if(password.length()<8) {


            return true;
        }



        else{
            return false;
        }

    }
   void CheckDataEntered(){

       if (isEmail(emaillogin)==false) {
           emaillogin.setError("Enter Valid Email");
           emaillogin.requestFocus();
       }else
            if(validatePassword(passwordlogin.getText().toString())){


             passwordlogin.setError("Enter valid password");


          passwordlogin.setError("Password length is not enough");
          passwordlogin.requestFocus();
       }
       else{
          // Toast.makeText(Login.this,"Input validation Success",Toast.LENGTH_LONG).show();

           counter--;

           info.setText("No of attempts remaining"+String.valueOf(counter));
           if(counter==0)
           {
               login.setEnabled(false);
           }
       }



   }
  /* private void AfterDataCheched(){
        if((isEmail(emaillogin)==true)&&(validatePassword(passwordlogin.getText().toString())==false))
        {
            Intent intent=new Intent(Login.this,UserProfile.class);
            startActivity(intent);
        }

   }*/


}
