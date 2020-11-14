package com.mahmoud.bashir.movies_app.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mahmoud.bashir.movies_app.models.TVShow;

@Database(entities = {TVShow.class},version = 1,exportSchema = false)
public abstract class TVShowDatabase extends RoomDatabase {
    private static TVShowDatabase tVShowDatabaseinstance;

    public static synchronized TVShowDatabase getTVShowDatabase(Context context){
        if (tVShowDatabaseinstance == null){
            tVShowDatabaseinstance = Room.databaseBuilder(context,
                    TVShowDatabase.class,
                    "tv_show_db")
                    .build();
        }
        return tVShowDatabaseinstance;
    }

    public abstract TVShowDao tvShowDao();
}
