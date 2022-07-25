package me.norton.soccernews.data.remote;

import java.util.List;

import me.norton.soccernews.domain.News;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SoccerNewsAPI {

    @GET("news.json")
    Call<List<News>> getNews();
}
