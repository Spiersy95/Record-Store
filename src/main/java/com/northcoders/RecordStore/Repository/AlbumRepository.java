package com.northcoders.RecordStore.Repository;

import com.northcoders.RecordStore.models.Album;

import com.northcoders.RecordStore.models.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Long> {
    List<Album> findAllByArtistName(String artistName);
    List<Album> findAllByReleaseYear(int releaseYear);
    List<Album> findAllByGenre(Genre genre);
    List<Album> findAllByAlbumName(String AlbumName);


}
