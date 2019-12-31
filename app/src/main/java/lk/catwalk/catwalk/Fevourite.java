package lk.catwalk.catwalk;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lk.catwalk.catwalk.model.Dress;

public class Fevourite extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView fevList;
    private FirebaseFirestore firebaseFirestore;
    String currentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fevourite);
        toolbar = findViewById(R.id.fevlist_toolbar);
        fevList = findViewById(R.id.fevlist_list);


        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null){
            currentUser = user.getEmail();
        }else{
            currentUser = "";
        }
        loaddata();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Favourites");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void loaddata() {
        Map<String,Object> find = new HashMap<>();

        final List<Dress> dresses = new ArrayList<Dress>();
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("users").document(currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot snapshot = task.getResult();
                    assert snapshot != null;
                    if (snapshot.exists()){
                        DocumentReference file = snapshot.getDocumentReference("fevItem");

                    }

                }
            }
        });



    }
}
