package com.example.catwalk;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {
    TextView txtemail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        txtemail=(TextView)findViewById(R.id.tvEmail);
        txtemail.setText(getIntent().getExtras().getString("Email"));


    }


}
