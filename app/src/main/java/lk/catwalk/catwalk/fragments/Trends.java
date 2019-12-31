package lk.catwalk.catwalk.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import lk.catwalk.catwalk.R;
import lk.catwalk.catwalk.adapter.RecentItem;
import lk.catwalk.catwalk.crawler.Crawler;
import lk.catwalk.catwalk.model.Dress;

public class Trends extends Fragment {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseFirestore firebaseFirestore;
    String currentUser;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trends,container,false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.trends_list);
        swipeRefreshLayout = view.findViewById(R.id.trends_refresh);

        mAuth = FirebaseAuth.getInstance();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null){
            currentUser = user.getEmail();
        }else{
            currentUser = "";
        }
        fetchData();
        return view;
    }


    private void fetchData(){
        swipeRefreshLayout.setRefreshing(true);
        final List<Dress> dresses = new ArrayList<Dress>();
        final Crawler crawler = new Crawler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = crawler.trend();
                    if (response.code()==200){
                        ResponseBody responseBody = response.body();
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        JSONArray items = jsonObject.getJSONArray("trends");
                        for (int i=0;i<items.length();i++){
                            JSONObject sItem = items.getJSONObject(i);
                            Dress dress = new Dress(sItem.getString("title"),sItem.getDouble("price"),sItem.getString("shop"),sItem.getString("link"),sItem.getString("image"));
                            dresses.add(dress);
                        }
                        swipeRefreshLayout.setRefreshing(false);
                       if (getActivity()!=null){
                           getActivity().runOnUiThread(new Runnable() {
                               @Override
                               public void run() {

                                   recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2 ));
                                   RecentItem recentItem  = new RecentItem(dresses,getActivity(),currentUser);
                                   recyclerView.setAdapter(recentItem);
                               }
                           });
                       }
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
