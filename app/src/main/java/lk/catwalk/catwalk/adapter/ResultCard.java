package lk.catwalk.catwalk.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lk.catwalk.catwalk.R;
import lk.catwalk.catwalk.model.Dress;

public class ResultCard extends RecyclerView.Adapter<ResultCard.ViewHolder> {
    List<Dress> dresses;
    Context context;
    private boolean toggle;
    private String user;

    public ResultCard(List<Dress> dresses, Context context,String user) {
        this.dresses = dresses;
        this.context = context;
        toggle = false;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dress_card_item,parent,false);
        return new ResultCard.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Dress item = dresses.get(position);
        holder.title.setText(item.getTitle());
        holder.place.setText(item.getShop());
        holder.price.setText(String.valueOf(item.getPrice()));
        if(URLUtil.isValidUrl(item.getImage())){
            Picasso.get().load(item.getImage()).into(holder.imageView);
        }else{
            System.out.println("not good"+item.getImage());
            Picasso.get().load("https:"+item.getImage()).into(holder.imageView);
        }

        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        final Map<String,Object> data = new HashMap<>();
        data.put("title",item.getTitle());
        data.put("price",item.getPrice());
        data.put("shop",item.getShop());
        data.put("url",item.getUrl());
        data.put("image",item.getImage());


        final DocumentReference reference = firebaseFirestore.collection("users").document(user);
        final String uid = firebaseFirestore.collection("users").document().getId();
        Map<String,Object> hashref = new HashMap<>();
        hashref.put(uid,data);

        final Map<String,Object> refer = new HashMap<>();
        refer.put("fevItem",hashref);

        holder.fev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!item.isLike()){
                    reference.set(refer, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    holder.fev.setImageDrawable(context.getResources().getDrawable(R.drawable.hearton));
                                    item.setLike(true);
                                    item.setUid(uid);
                                    Log.i("uid",item.getUid());
                                }
                            });
                }else{
                    if (item.getUid()!=null){

                        final Map<String,Object> delete = new HashMap<>();
                        delete.put("fevItem."+item.getUid(), FieldValue.delete());
                        reference.update(delete).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                holder.fev.setImageDrawable(context.getResources().getDrawable(R.drawable.heartoff));
                                item.setLike(false);
                                item.setUid(null);
                            }
                        });
                    }

                }
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(item.getUrl()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dresses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title,price,place;
        FloatingActionButton fev;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.find_item_img);
            title = itemView.findViewById(R.id.find_item_title);
            price = itemView.findViewById(R.id.find_item_price);
            place = itemView.findViewById(R.id.find_item_place);
            fev = itemView.findViewById(R.id.find_item_fev);
            linearLayout = itemView.findViewById(R.id.find_item_click);

        }
    }
}
