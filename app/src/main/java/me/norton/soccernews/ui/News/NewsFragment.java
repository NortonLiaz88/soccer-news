package me.norton.soccernews.ui.News;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import me.norton.soccernews.R;
import me.norton.soccernews.databinding.FragmentNewsBinding;
import me.norton.soccernews.ui.adapters.NewsAdapter;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private NewsViewModel newsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         newsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        observeNews();


        setupStates();

        binding.srNews.setOnRefreshListener(newsViewModel::findNews);

        return root;
    }

    private void setupStates() {
        newsViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case DOING:
                    binding.srNews.setRefreshing(true);
                    // TODO: Incluir SwipeRefreshLayout (loading)
                    break;
                case DONE:
                    binding.srNews.setRefreshing(false);
                    // TODO: Finalizar SwipeRefreshLayout (loading)
                    break;
                case ERROR:
                    binding.srNews.setRefreshing(false);
                    Snackbar.make(binding.srNews, R.string.error_network , Snackbar.LENGTH_SHORT).show();
                    // TODO: Finalizar SwipeRefreshLayout (loading)
                    // TODO: Mostrat mensagem de erro genérico (loading)


            }
        });
    }

    private void observeNews() {
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            binding.rvNews.setAdapter(new NewsAdapter(news, favoriteNews -> {
                newsViewModel.saveNews(favoriteNews);
            }));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onRefresh() {
        newsViewModel.findNews();
    }
}