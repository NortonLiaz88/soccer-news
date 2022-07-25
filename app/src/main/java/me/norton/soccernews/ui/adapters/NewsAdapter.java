package me.norton.soccernews.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.norton.soccernews.R;
import me.norton.soccernews.databinding.NewItemBinding;
import me.norton.soccernews.domain.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> news;

    private final NewsListener favoriteListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewItemBinding binding = NewItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = this.news.get(position);
        holder.binding.tvTitle.setText(news.getTitle());
        holder.binding.tvDescription.setText(news.getDescription());
        Context context =  holder.itemView.getContext();

        Picasso.get().load(news.getImage()).into(holder.binding.ivThumbnail);

        holder.binding.btOpenLink.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(news.getLink()));
            holder.itemView.getContext().startActivity(i);
        });

        // Implementação da funcionaldiade de compartilhar
        holder.binding.ivShare.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, news.getLink());
            holder.itemView.getContext().startActivity(Intent.createChooser(i, "Share via"));
        });


        // Implemetação da funcionalidade favoritar
        holder.binding.ivFav.setOnClickListener(view -> {
            news.setFavorite(!news.isFavorite());
            this.favoriteListener.click(news);
            notifyItemChanged(position);
        });

        int favoriteColor = news.isFavorite() ? R.color.purple_500 : R.color.favorite_inactive;
        holder.binding.ivFav.setColorFilter(context.getResources().getColor(favoriteColor));

    }

    @Override
    public int getItemCount() {
        return this.news.size();
    }

    public NewsAdapter(List<News> news, NewsListener favoriteListener) {
        this.news = news;
        this.favoriteListener = favoriteListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final NewItemBinding binding;

        public ViewHolder(NewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface NewsListener {
       void click(News news);
    }
}
