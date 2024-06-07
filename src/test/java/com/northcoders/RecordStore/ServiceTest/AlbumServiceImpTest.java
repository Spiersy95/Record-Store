package com.northcoders.RecordStore.ServiceTest;

import com.northcoders.RecordStore.Exceptions.InvalidIdException;
import com.northcoders.RecordStore.Repository.AlbumRepository;
import com.northcoders.RecordStore.Service.AlbumServiceImp;
import com.northcoders.RecordStore.models.Album;
import com.northcoders.RecordStore.models.Genre;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AlbumServiceImpTest {

    @Mock
    AlbumRepository albumRepository;

    @InjectMocks
    AlbumServiceImp albumServiceimp;

    @Test
    public void getAlbumsById() throws InvalidIdException {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album(2L,
                "Enter the 36 chambers",
                "Wu-Tang-Clan",
                Genre.HIP_HOP,
                200));
        albums.add(new Album(500L,
                "Dark side of the moon",
                "Pink Floyd",
                Genre.ROCK,
                2));

        when(albumRepository.findById(2L)).thenReturn(Optional.ofNullable(albums.getFirst()));
        when(albumRepository.findById(500L)).thenReturn(Optional.ofNullable(albums.getLast()));

        when(albumRepository.findById(1L)).thenReturn(Optional.empty());
        when(albumRepository.findById(3L)).thenReturn(Optional.empty());

        assertEquals(albums.getFirst(), albumServiceimp.getAlbumById(2L));
        assertEquals(albums.getLast(), albumServiceimp.getAlbumById(500L));
        assertThrows(InvalidIdException.class, () -> albumServiceimp.getAlbumById(1L));
        assertThrows(InvalidIdException.class, () -> albumServiceimp.getAlbumById(3L));
    }



}
