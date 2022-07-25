package me.norton.soccernews.data;

import androidx.room.Room;

import me.norton.soccernews.App;
import me.norton.soccernews.data.local.SoccerNewsDb;
import me.norton.soccernews.data.remote.SoccerNewsAPI;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SoccerNewsRepository {
    // region Constantes
    private static final String REMOTE_API_URL = "https://digitalinnovationone.github.io/soccer-news-api/";
    private static final String LOCAL_DB_NAME = "soccer-news";
    // endregion


    // region Atributos
    private SoccerNewsAPI remoteApi;
    private SoccerNewsDb localDb;

    public SoccerNewsAPI getRemoteApi() {
        return  remoteApi;
    }

    public SoccerNewsDb getLocalDb()
    {
        return localDb;
    }
    // endregion

    //region Singleton
    private SoccerNewsRepository() {
        remoteApi = new Retrofit.Builder()
                .baseUrl("https://digitalinnovationone.github.io/soccer-news-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(SoccerNewsAPI.class);
        localDb = Room.databaseBuilder(App.getInstance(), SoccerNewsDb.class, LOCAL_DB_NAME).allowMainThreadQueries().build();


    }

    public static SoccerNewsRepository getInstance() {return LazyHolder.INSTANCE; };

    private static class LazyHolder {
        private static final SoccerNewsRepository INSTANCE = new SoccerNewsRepository();
    }
    //endregion
}
