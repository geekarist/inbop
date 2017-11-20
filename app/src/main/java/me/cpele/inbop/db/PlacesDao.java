package me.cpele.inbop.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.WorkerThread;

import java.util.List;

import me.cpele.inbop.apiclient.model.Place;

@Dao
public interface PlacesDao {

    @Query("DELETE FROM Place")
    @WorkerThread
    void removeAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @WorkerThread
    void insert(List<Place> places);

    @Query("SELECT * FROM Place WHERE Place.id = :id")
    LiveData<Place> findPlaceById(String id);

    @Query("SELECT * FROM Place WHERE Place.id = :id")
    @WorkerThread
    Place findPlaceByIdSync(String id);

    @Query("UPDATE Place SET starred = NOT(Place.starred) WHERE Place.id = :id")
    @WorkerThread
    void toggleStar(String id);
}
