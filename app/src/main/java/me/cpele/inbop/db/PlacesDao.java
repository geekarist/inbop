package me.cpele.inbop.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import me.cpele.inbop.apiclient.model.Place;

@Dao
public interface PlacesDao {

    @Query("DELETE FROM Place")
    void removeAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Place> places);

    @Query("SELECT * FROM Place WHERE Place.id = :id")
    LiveData<Place> findPlaceById(String id);

    @Query("SELECT * FROM Place WHERE Place.id = :id")
    Place findPlaceByIdSync(String id);

    @Query("UPDATE Place SET starred = NOT(Place.starred) WHERE Place.id = :id")
    void toggleStar(String id);
}
