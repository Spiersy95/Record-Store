package com.northcoders.RecordStore.Service;

import com.northcoders.RecordStore.Exceptions.InvalidIdException;
import com.northcoders.RecordStore.Repository.AlbumRepository;
import com.northcoders.RecordStore.models.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlbumServiceImp implements AlbumService {

    @Autowired
    AlbumRepository albumRepository;

    public Album getAlbumById(Long id) throws InvalidIdException {
        Optional<Album> possibleAlbum = albumRepository.findById(id);
        if (possibleAlbum.isEmpty()){
            throw new InvalidIdException();
        }
        return possibleAlbum.get();
    }
}
