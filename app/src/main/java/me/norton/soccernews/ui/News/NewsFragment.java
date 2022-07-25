package me.norton.soccernews.ui.News;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import me.norton.soccernews.MainActivity;
import me.norton.soccernews.data.local.AppDatabase;
import me.norton.soccernews.databinding.FragmentNewsBinding;
import me.norton.soccernews.ui.adapters.NewsAdapter;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewsViewModel newsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            binding.rvNews.setAdapter(new NewsAdapter(news, updatedNews -> {
                MainActivity activity = (MainActivity) getActivity();
                if(activity != null) {
                    activity.getDb().newsDao().insert(updatedNews);
                }
            }));
        });
        newsViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case DOING:
                    // TODO: Incluir SwipeRefreshLayout (loading)
                    break;
                case DONE:
                    // TODO: Finalizar SwipeRefreshLayout (loading)
                    break;
                case ERROR:
                    // TODO: Finalizar SwipeRefreshLayout (loading)
                    // TODO: Mostrat mensagem de erro genérico (loading)


            }
        });

            return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}