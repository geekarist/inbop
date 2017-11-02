package me.cpele.inbop.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import me.cpele.inbop.apiclient.model.Place;

@Database(version = 1, entities = {Place.class}, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PlacesDao getPlacesDao();
}
