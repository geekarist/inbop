package me.cpele.inbop.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import me.cpele.inbop.apiclient.model.Place;

@Dao
public interface PlacesDao {

    @Query("DELETE FROM Place")
    void removeAll();

    @Insert
    void insert(List<Place> places);

    @Query("SELECT * FROM Place WHERE Place.id = :id")
    Place findPlaceById(String id);

    @Query("UPDATE Place SET starred = NOT(Place.starred) WHERE Place.id = :id")
    void toggleStar(String id);
}
