package com.northcoders.RecordStore.Repository;

import com.northcoders.RecordStore.models.Album;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;


public interface AlbumRepository extends CrudRepository<Album, Long> {


}
