package com.northcoders.RecordStore.Controllers;

import com.northcoders.RecordStore.Exceptions.InvalidIdException;
import com.northcoders.RecordStore.Service.AlbumService;
import com.northcoders.RecordStore.models.Album;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/albums")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @GetMapping
    public ResponseEntity<Album> getAlbumById(@RequestParam(name = "id")Long id){
        try{
            Album album = albumService.getAlbumById(id);
            return new ResponseEntity<>(album, HttpStatus.OK);
        } catch (InvalidIdException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sorry there are no albums which have this id");
        }
    }


}
