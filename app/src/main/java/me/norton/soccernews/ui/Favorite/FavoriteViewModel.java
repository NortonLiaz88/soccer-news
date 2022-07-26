package me.norton.soccernews.ui.Favorite;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import me.norton.soccernews.data.SoccerNewsRepository;
import me.norton.soccernews.domain.News;

public class FavoriteViewModel extends ViewModel {

    public FavoriteViewModel() {

    }

    public LiveData<List<News>> loadFavoriteNews() {
        return SoccerNewsRepository.getInstance().getLocalDb().newsDao().loadFavoriteNews();
    }

    public void saveNews(News news) {
        AsyncTask.execute(() -> SoccerNewsRepository.getInstance().getLocalDb().newsDao().insert(news));
    }

}