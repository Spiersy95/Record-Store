package com.northcoders.RecordStore.Controllers;

import com.northcoders.RecordStore.Exceptions.*;

import com.northcoders.RecordStore.Service.AlbumService;
import com.northcoders.RecordStore.models.Album;

import com.northcoders.RecordStore.models.Genre;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @GetMapping("/albums")
    public ResponseEntity<List<Album>> getAllAlbums(){
        try{
            List<Album> albums = albumService.getAllAlbums();
            return new ResponseEntity<>(albums, HttpStatus.FOUND);
        } catch (EmptyDatabaseException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry there is nothing in stock at the moment");
        }
    }

    @GetMapping("/album")
    public ResponseEntity<Album> getAlbumById(@RequestParam(name = "id")Long id){
        try{
            Album album = albumService.getAlbumById(id);
            return new ResponseEntity<>(album, HttpStatus.OK);
        } catch (InvalidIdException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sorry there are no albums which have this id");
        }
    }

    @PostMapping("/album")
    public ResponseEntity<Album> postAlbum(@RequestBody Album album){
        albumService.addAlbum(album);
        return new ResponseEntity<>(album, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/album")
    public ResponseEntity<String> deleteAlbum(@RequestBody Album album){
        albumService.removeAlbum(album);
        return new ResponseEntity<>(String.format("Deleted Album: %s by artist: %s", album.getAlbumName(), album.getArtistName()), HttpStatus.ACCEPTED);
    }

    @PutMapping("/album")
    public ResponseEntity<Album> putAlbum(@RequestBody Album album){
        try{
            Album updatedAlbum = albumService.updateAlbum(album);
            return new ResponseEntity<>(updatedAlbum, HttpStatus.ACCEPTED);
        } catch (InvalidIdException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry this record could not be found to update");
        }

    }


    @GetMapping("/albums/artist/{artistName}")
    public ResponseEntity<List<Album>> getAlbumsByArtist(@PathVariable(name = "artistName")String artistName){
        try{
            List<Album> albums = albumService.findAllByArtistName(artistName);
            return new ResponseEntity<>(albums, HttpStatus.FOUND);
        } catch(MissingArtistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry this artist is not in store");
        }

    }

    @GetMapping("/albums/{releaseYear}")
    public ResponseEntity<List<Album>> getAlbumsByReleaseYear(@PathVariable(name = "releaseYear") int releaseYear){
        try{
            List<Album> albums = albumService.findAllByReleaseYear(releaseYear);
            return new ResponseEntity<>(albums, HttpStatus.FOUND);
        } catch (UnavailableYearException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry we have no albums from this year in stock");
        }
    }

    @GetMapping("/albums/genre/{genre}")
    public ResponseEntity<List<Album>> getAlbumsByGenre(@PathVariable(name = "genre") Genre genre){
        try{
            List<Album> albums = albumService.findAllByGenre(genre);
            return new ResponseEntity<>(albums, HttpStatus.FOUND);
        } catch (InvalidGenreException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry we do not have this genre on store");
        }
    }

    @GetMapping("/albums/listOfAlbumsCalled{albumName}")
    public ResponseEntity<List<Album>> getAlbumsByArtistName(@PathVariable(name= "albumName") String albumName){
        try{
            List<Album> albums = albumService.findAllByAlbumName(albumName);
            return new ResponseEntity<>(albums, HttpStatus.FOUND);
        } catch (MissingAlbumException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry we have no artist by this name in store");
        }
    }

}
