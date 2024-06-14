package com.northcoders.RecordStore.Controllers;

import com.northcoders.RecordStore.Exceptions.*;

import com.northcoders.RecordStore.Service.AlbumService;
import com.northcoders.RecordStore.models.Album;

import com.northcoders.RecordStore.models.Genre;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Operation(summary = "Gets all the records in the shop", description = "Returns a list of all the records in the shop in json format")
    @GetMapping("/albums")
    public ResponseEntity<List<Album>> getAllAlbums(){
            List<Album> albums = albumService.getAllAlbums();
            return new ResponseEntity<>(albums, HttpStatus.FOUND);
    }

    @Operation(summary = "Get a record by id", description = "Returns a record as per the id")
    @GetMapping("/album/id/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id){
        Album album = albumService.getAlbumById(id);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @Operation(summary = "Post to the record database", description = "Adds a record to our stock list")
    @PostMapping("/album")
    public ResponseEntity<Album> postAlbum(@RequestBody Album album){
            albumService.addAlbum(album);
            return new ResponseEntity<>(album, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Delete a record by id", description = "deletes a product a record from the databse using its id")
    @DeleteMapping("/album/{id}")
    public ResponseEntity<String> deleteAlbum(@PathVariable Long id){
        albumService.removeAlbum(id);
        return new ResponseEntity<>(String.format("Deleted Album with ID: %d" , id ), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Updates a record by id", description = "Updates a record in shop/amend errors")
    @PutMapping("/album/{id}")
    public ResponseEntity<Album> putAlbum(@RequestBody Album album, @PathVariable Long id){
        try{
            Album updatedAlbum = albumService.updateAlbum(album, id);
            return new ResponseEntity<>(updatedAlbum, HttpStatus.ACCEPTED);
        } catch (InvalidIdException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry this record could not be found to update");
        }

    }

    @Operation(summary = "Gets a records by an artist ", description = "Returns all the records associted with an artist")
    @GetMapping("/albums/artist/{artistName}")
    public ResponseEntity<List<Album>> getAlbumsByArtist(@PathVariable(name = "artistName")String artistName){
        List<Album> albums = albumService.findAllByArtistName(artistName);
        return new ResponseEntity<>(albums, HttpStatus.FOUND);

    }

    @Operation(summary = "Get all records sold in store from a given year", description = "Returns all records from a given year")
    @GetMapping("/albums/year/{releaseYear}")
    public ResponseEntity<List<Album>> getAlbumsByReleaseYear(@PathVariable(name = "releaseYear") int releaseYear){
        List<Album> albums = albumService.findAllByReleaseYear(releaseYear);
        return new ResponseEntity<>(albums, HttpStatus.FOUND);
    }

    @Operation(summary = "Get a product by id", description = "Returns a product as per the id")
    @GetMapping("/albums/genre/{genre}")
    public ResponseEntity<List<Album>> getAlbumsByGenre(@PathVariable(name = "genre") Genre genre){
        List<Album> albums = albumService.findAllByGenre(genre);
        return new ResponseEntity<>(albums, HttpStatus.FOUND);
    }

    @Operation(summary = "Get all the records in store by a given artist", description = "Returns all records by an artist " +
            "we have in stock")
    @GetMapping("/albums/albumName/{albumName}")
    public ResponseEntity<List<Album>> getAlbumsByArtistName(@PathVariable(name= "albumName") String albumName){
        List<Album> albums = albumService.findAllByAlbumName(albumName);
        return new ResponseEntity<>(albums, HttpStatus.FOUND);

    }

}
