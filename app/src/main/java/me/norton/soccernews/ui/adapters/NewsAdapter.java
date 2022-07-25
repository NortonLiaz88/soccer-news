package me.norton.soccernews.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.norton.soccernews.databinding.NewItemBinding;
import me.norton.soccernews.domain.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> news;

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

    }

    @Override
    public int getItemCount() {
        return this.news.size();
    }

    public NewsAdapter(List<News> news) {
        this.news = news;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final NewItemBinding binding;

        public ViewHolder(NewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
