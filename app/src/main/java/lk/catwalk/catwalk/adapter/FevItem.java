package lk.catwalk.catwalk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lk.catwalk.catwalk.R;
import lk.catwalk.catwalk.model.Dress;

public class FevItem extends RecyclerView.Adapter<FevItem.ViewHolder> {
    private List<Dress> dresses;
    private Context context;

    public FevItem(List<Dress> items,Context context) {
        this.dresses = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fev_item,parent,false);
        return new FevItem.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

    }

    @Override
    public int getItemCount() {
        return dresses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title,price,place;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fev_item_img);
            title = itemView.findViewById(R.id.fev_item_title);
            price = itemView.findViewById(R.id.fev_item_price);
            place = itemView.findViewById(R.id.fev_item_place);
            linearLayout = itemView.findViewById(R.id.fev_item_click);

        }
    }
}
