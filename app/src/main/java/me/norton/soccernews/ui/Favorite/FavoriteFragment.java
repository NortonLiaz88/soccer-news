package me.norton.soccernews.ui.Favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import me.norton.soccernews.MainActivity;
import me.norton.soccernews.databinding.FragmentFavoritesBinding;
import me.norton.soccernews.databinding.FragmentNewsBinding;
import me.norton.soccernews.domain.News;
import me.norton.soccernews.ui.adapters.NewsAdapter;

public class FavoriteFragment extends Fragment {

    private FragmentFavoritesBinding binding;
    private FavoriteViewModel favoritesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        favoritesViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);

        loadFavoriteNews();

        return binding.getRoot();
    }

    private void loadFavoriteNews() {
        favoritesViewModel.loadFavoriteNews().observe(getViewLifecycleOwner(), localNews -> {
            binding.rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvFavorites.setAdapter(new NewsAdapter(localNews, updatedNews -> {
                favoritesViewModel.saveNews(updatedNews);
                loadFavoriteNews();
            }));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}