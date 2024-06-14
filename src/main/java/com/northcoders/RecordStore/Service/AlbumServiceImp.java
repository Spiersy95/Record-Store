package com.northcoders.RecordStore.Service;

import com.northcoders.RecordStore.Exceptions.*;

import com.northcoders.RecordStore.Formatters.NameFormatter;
import com.northcoders.RecordStore.Repository.AlbumRepository;
import com.northcoders.RecordStore.Validation.StringValidator;
import com.northcoders.RecordStore.Validation.ReleaseYearValidator;
import com.northcoders.RecordStore.models.Album;
import com.northcoders.RecordStore.models.Genre;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumServiceImp implements AlbumService {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    StringValidator stringValidator;

    @Autowired
    ReleaseYearValidator releaseYearValidator;

    @Autowired
    NameFormatter nameFormatter;

    @Override
    public Album getAlbumById(Long id) throws InvalidIdException {
        Optional<Album> possibleAlbum = albumRepository.findById(id);
        if (possibleAlbum.isEmpty()){
            throw new InvalidIdException("Sorry there is no album by this id");
        }
        return possibleAlbum.get();
    }

    @Override
    public List<Album> getAllAlbums(){
        List<Album> albums = new ArrayList<>();
        albumRepository.findAll().forEach(albums::add);
        if(albums.isEmpty()){
            throw new EmptyDatabaseException("Sorry the store has nothing in stock at the moment come by later");
        }
        return albums;
    }

    @Override
    public Album addAlbum(Album album){
        if(!stringValidator.validate(album.getArtistName())){
            throw new MissingArtistException("Sorry we must have a non-empty artist name");
        }

        album.setArtistName(nameFormatter.format(album.getArtistName()));

        if(!releaseYearValidator.validate(album.getReleaseYear())){
            throw new UnavailableYearException("Sorry this is an invalid year");
        }

        if(!stringValidator.validate(album.getAlbumName())){
            album.setAlbumName("Untitled");
        } else {
            album.setAlbumName(nameFormatter.format(album.getAlbumName()));
        }

        albumRepository.save(album);
        return album;
    }

    @Override
    public void removeAlbum(Long id){
        Optional<Album> albumToBeDeleted = albumRepository.findById(id);
        if (albumToBeDeleted.isEmpty()){
            throw new InvalidIdException("sorry there is no album with this id");
        } else {
            albumRepository.delete(albumToBeDeleted.get());
        }

    }

    @Override
    public Album updateAlbum(Album album, Long id){
        Optional<Album> oldOptionalAlbum = albumRepository.findById(id);

        if (oldOptionalAlbum.isEmpty()){
            throw new InvalidIdException("Sorry there that id does not correspond to anything we have in store");
        }

        Album oldAlbum = oldOptionalAlbum.get();
        if (!stringValidator.validate(album.getArtistName())) {
            throw new InvalidArtistNameException("Sorry the artist name you supplied is invalid");
        }

        oldAlbum.setArtistName(nameFormatter.format(album.getAlbumName()));


        if (stringValidator.validate(album.getAlbumName())){
            oldAlbum.setAlbumName(nameFormatter.format(album.getAlbumName()));
        } else {
            oldAlbum.setAlbumName("Untitled");
        }

        if (!releaseYearValidator.validate(album.getReleaseYear())){
            throw new UnavailableYearException("Sorry no album was released the time you think this was");
        }
        oldAlbum.setReleaseYear(album.getReleaseYear());

        return albumRepository.save(oldAlbum);
    }

    public List<Album> findAllByArtistName(String artistName){
        List<Album> albumsByArtist = albumRepository.findAllByArtistName(artistName);
        if (albumsByArtist.isEmpty()){
            throw new MissingArtistException("Sorry the artist does not have any albums in store");
        }
        return albumsByArtist;
    }

    @Override
    public List<Album> findAllByReleaseYear(int releaseYear){
        List<Album> releaseYearAlbums = albumRepository.findAllByReleaseYear(releaseYear);
        if (releaseYearAlbums.isEmpty()){
            throw new UnavailableYearException("Sorry we have no albums from this year in stock");
        }
        return releaseYearAlbums;
    }

    @Override
    public List<Album> findAllByGenre(Genre genre){
        List<Album> genreAlbums = albumRepository.findAllByGenre(genre);
        if (genreAlbums.isEmpty()){
            throw new InvalidGenreException("Not sure where this exception comes from");
        }
        return genreAlbums;
    }

    @Override
    public List<Album> findAllByAlbumName(String albumName){
        List<Album> albums = albumRepository.findAllByAlbumName(albumName);
        if (albums.isEmpty()){
            throw new MissingAlbumException("Sorry we have no albums by that name in store");
        }
        return albums;
    }


}
