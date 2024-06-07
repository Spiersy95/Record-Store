package com.northcoders.RecordStore.Service;

import com.northcoders.RecordStore.Exceptions.InvalidIdException;
import com.northcoders.RecordStore.models.Album;

public interface AlbumService {

    public Album getAlbumById(Long id) throws InvalidIdException;
}
