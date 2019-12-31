package lk.catwalk.catwalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import lk.catwalk.catwalk.adapter.ResultCard;
import lk.catwalk.catwalk.crawler.Crawler;
import lk.catwalk.catwalk.model.Dress;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchList extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton filter;
    private RecyclerView resultList;
    private TextView resultCount;
    private FirebaseFirestore firebaseFirestore;
    private String keyword;
    String currentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        toolbar = findViewById(R.id.searchList_toolbar);
        filter = findViewById(R.id.searchList_filter);
        resultList = findViewById(R.id.searchList_list);
        resultCount = findViewById(R.id.searchList_searchresult);
        keyword = "";


        setSupportActionBar(toolbar);
        if(getIntent().getStringExtra("keyword")!=null){
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            if (user!=null){
                currentUser = user.getEmail();
            }else{
                currentUser = "";
            }
            keyword = getIntent().getStringExtra("keyword");
            loaddata();

        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(keyword);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void loaddata() {
        final List<Dress> dresses = new ArrayList<Dress>();
        final Crawler crawler = new Crawler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = crawler.trend(keyword);
                    if (response.code()==200){
                        ResponseBody responseBody = response.body();
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        JSONArray items = jsonObject.getJSONArray("trends");
                        final int totoal = jsonObject.getInt("total");
                        for (int i=0;i<items.length();i++){
                            JSONObject sItem = items.getJSONObject(i);
                            Dress dress = new Dress(sItem.getString("title"),sItem.getDouble("price"),sItem.getString("shop"),sItem.getString("link"),sItem.getString("image"));
                            dresses.add(dress);
                        }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    resultCount.setText(String.valueOf(totoal)+" Result Found");
                                    resultList.setLayoutManager(new LinearLayoutManager(SearchList.this));
                                    ResultCard recentItem  = new ResultCard(dresses,SearchList.this,currentUser);
                                    resultList.setAdapter(recentItem);
                                }
                            });

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }
}
