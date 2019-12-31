package lk.catwalk.catwalk.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import lk.catwalk.catwalk.adapter.ArticleItem;
import lk.catwalk.catwalk.crawler.Crawler;
import lk.catwalk.catwalk.model.Article;

public class Articles extends Fragment {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseFirestore firebaseFirestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles,container,false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.article_list);
        swipeRefreshLayout = view.findViewById(R.id.article_refresh);
        fetchData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });

        return view;
    }

    private void fetchData() {
        swipeRefreshLayout.setRefreshing(true);
        final List<Article> articles = new ArrayList<Article>();
        final Crawler crawler = new Crawler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = crawler.article();
                    if (response.code()==200){
                        ResponseBody responseBody = response.body();
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        JSONArray items = jsonObject.getJSONArray("articles");
                        for (int i=0;i<items.length();i++){
                            JSONObject sItem = items.getJSONObject(i);

                            Article article = new Article(sItem.getString("title"),sItem.getString("Content"),sItem.getString("poster"),sItem.getString("publishedDate"),sItem.getString("author"),sItem.getString("source"));
                            articles.add(article);
                        }
                        swipeRefreshLayout.setRefreshing(false);
                        if (getActivity()!=null){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    ArticleItem recentItem  = new ArticleItem(articles,getActivity());
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
