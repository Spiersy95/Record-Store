package com.northcoders.RecordStore.Service;

import com.northcoders.RecordStore.Exceptions.*;
import com.northcoders.RecordStore.models.Album;
import com.northcoders.RecordStore.models.Genre;


import java.util.List;

public interface AlbumService {

    public Album getAlbumById(Long id) throws InvalidIdException;
    public List<Album> getAllAlbums() throws EmptyDatabaseException;
    public void addAlbum(Album album);
    public void removeAlbum(Album album);
    public Album updateAlbum(Album album) throws InvalidIdException;
    public List<Album> findAllByArtistName(String artistName) throws MissingArtistException;
    public List<Album> findAllByReleaseYear(int releaseYear) throws UnavailableYearException;
    public List<Album> findAllByGenre(Genre genre) throws InvalidGenreException;
    public List<Album> findAllByAlbumName(String AlbumName) throws MissingAlbumException;
}
