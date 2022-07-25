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
import me.norton.soccernews.databinding.FragmentNewsBinding;
import me.norton.soccernews.domain.News;
import me.norton.soccernews.ui.adapters.NewsAdapter;

public class FavoriteFragment extends Fragment {

    private FragmentNewsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoriteViewModel favoriteViewModel =
                new ViewModelProvider(this).get(FavoriteViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);

        loadFavoriteNews();

        View root = binding.getRoot();



        return root;
    }

    private void loadFavoriteNews() {
        MainActivity activity = (MainActivity) getActivity();
        if(activity != null) {
            List<News> favoriteNews = activity.getDb().newsDao().loadFavoriteNews();
            binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvNews.setAdapter(new NewsAdapter(favoriteNews, updatedNews -> {
                if(activity != null) {
                    activity.getDb().newsDao().insert(updatedNews);
                    loadFavoriteNews();
                }
            }));
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}