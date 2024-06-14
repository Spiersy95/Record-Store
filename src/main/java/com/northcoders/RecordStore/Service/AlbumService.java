package com.northcoders.RecordStore.Service;

import com.northcoders.RecordStore.Exceptions.*;
import com.northcoders.RecordStore.models.Album;

import com.northcoders.RecordStore.models.Genre;
import org.springframework.cache.annotation.Cacheable;


import java.util.List;

public interface AlbumService {

    @Cacheable(value = "album", key = "#id")
    Album getAlbumById(Long id) throws InvalidIdException;

    List<Album> getAllAlbums() throws EmptyDatabaseException;
    Album addAlbum(Album album);
    void removeAlbum(Long id) throws InvalidIdException;
    Album updateAlbum(Album album, Long id) throws InvalidIdException;
    List<Album> findAllByArtistName(String artistName) throws MissingArtistException;
    List<Album> findAllByReleaseYear(int releaseYear) throws UnavailableYearException;
    List<Album> findAllByGenre(Genre genre) throws InvalidGenreException;
    List<Album> findAllByAlbumName(String AlbumName) throws MissingAlbumException;
}
