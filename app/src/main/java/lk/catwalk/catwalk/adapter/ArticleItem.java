package lk.catwalk.catwalk.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lk.catwalk.catwalk.ArticleView;
import lk.catwalk.catwalk.R;
import lk.catwalk.catwalk.model.Article;

public class ArticleItem extends RecyclerView.Adapter<ArticleItem.ViewHolder> {
    private List<Article> articles;
    private Context context;

    public ArticleItem(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.article_item,parent,false);
        ArticleItem.ViewHolder viewHolder = new ArticleItem.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Article item = articles.get(position);
        String title = item.getTitle().substring(0, Math.min(item.getTitle().length(), 45))+"...";
        holder.title.setText(title);
        Picasso.get().load(item.getImage()).into(holder.image);
        String spl = item.getContent().substring(0, Math.min(item.getContent().length(), 70))+"...";
        holder.content.setText(spl);
        holder.time.setText(item.getPublishedDate());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleView.class);
                intent.putExtra("title",item.getTitle());
                intent.putExtra("time",item.getPublishedDate());
                intent.putExtra("content",item.getContent());
                intent.putExtra("author",item.getAuthor());
                intent.putExtra("image",item.getImage());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView title,content,time;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.article_item_click);
            time = itemView.findViewById(R.id.article_item_time);
            title = itemView.findViewById(R.id.article_item_title);
            content = itemView.findViewById(R.id.article_item_des);
            image = itemView.findViewById(R.id.article_item_img);

        }
    }
}
