package lk.catwalk.catwalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ArticleView extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imageView;
    private TextView title,author,time,likes,content;
    private FloatingActionButton bookmark,heart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);

        toolbar = findViewById(R.id.article_view_toolbar);
        imageView = findViewById(R.id.article_view_image);
        title = findViewById(R.id.article_view_title);
        author = findViewById(R.id.article_view_author);
        time = findViewById(R.id.article_view_time);
        likes = findViewById(R.id.article_view_likesCount);
        content = findViewById(R.id.article_view_content);
        bookmark = findViewById(R.id.article_view_bookmark);
        heart = findViewById(R.id.article_view_like);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(getIntent().getStringExtra("title")!=null){
            title.setText(getIntent().getStringExtra("title"));
            author.setText(getIntent().getStringExtra("author"));
            time.setText(getIntent().getStringExtra("time"));
            content.setText(getIntent().getStringExtra("content"));
            Picasso.get().load(getIntent().getStringExtra("image")).into(imageView);

        }

    }
}
