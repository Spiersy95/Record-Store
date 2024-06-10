package com.northcoders.RecordStore.ServiceTest;

import com.northcoders.RecordStore.Exceptions.*;
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
import static org.mockito.Mockito.*;

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
                "36 chambers",
                "Wu-Tang-Clan",
                1993,
                Genre.HIP_HOP));
        albums.add(new Album(500L,
                "Dark side of the moon",
                "Pink Floyd",
                1973,
                Genre.ROCK));

        when(albumRepository.findById(2L)).thenReturn(Optional.ofNullable(albums.getFirst()));
        when(albumRepository.findById(500L)).thenReturn(Optional.ofNullable(albums.getLast()));

        when(albumRepository.findById(1L)).thenReturn(Optional.empty());
        when(albumRepository.findById(3L)).thenReturn(Optional.empty());

        assertEquals(albums.getFirst(), albumServiceimp.getAlbumById(2L));
        assertEquals(albums.getLast(), albumServiceimp.getAlbumById(500L));
        assertThrows(InvalidIdException.class, () -> albumServiceimp.getAlbumById(1L));
        assertThrows(InvalidIdException.class, () -> albumServiceimp.getAlbumById(3L));
    }

    @Test
    public void getAllAlbums() throws EmptyDatabaseException {
        List<Album> albums = new ArrayList<>();
        List<Album> emptyAlbums = new ArrayList<>();
        albums.add(new Album(2L,
                "36 chambers",
                "Wu-Tang-Clan",
                1993,
                Genre.HIP_HOP));
        albums.add(new Album(500L,
                "Dark side of the moon",
                "Pink Floyd",
                1973,
                Genre.ROCK));

        when(albumRepository.findAll()).thenReturn(emptyAlbums);
        assertThrows(EmptyDatabaseException.class, () -> albumServiceimp.getAllAlbums());

        when(albumRepository.findAll()).thenReturn(albums);


        assertEquals(albums, albumServiceimp.getAllAlbums());

    }

    @Test
    void addAlbumTest(){
        Album album = new Album(2L,
                "36 Chambers",
                "Wu-Tang-Clan",
                1993,
                Genre.HIP_HOP);

        when(albumRepository.save(album)).thenReturn(album);

        albumServiceimp.addAlbum(album);

        verify(albumRepository, times(1)).save(album);
    }

    @Test
    void removeAlbumTest(){
        Album album = new Album(2L,
                "36 Chambers",
                "Wu-Tang-Clan",
                1993,
                Genre.HIP_HOP);

        albumServiceimp.removeAlbum(album);

        verify(albumRepository, times(1)).delete(album);
    }

    @Test
    void findAlbumsByArtistNameTest() throws MissingArtistException {
        List<Album> emptyList = new ArrayList<>();
        List<Album> firstList = new ArrayList<>();
        firstList.add(new Album(2L,
                "Wu-Tang Forever",
                "Wu-Tang-Clan",
                1997,
                Genre.HIP_HOP));
        new Album(1L,
                "36 Chambers",
                "Wu-Tang-Clan",
                1993,
                Genre.HIP_HOP);

        List<Album> secondList = new ArrayList<>();

        secondList.add(new Album(500L,
                "Dark side of the moon",
                "Pink Floyd",
                1973,
                Genre.ROCK));

        when(albumRepository.findAllByArtistName("Wu-Tang-Clan")).thenReturn(firstList);
        when(albumRepository.findAllByArtistName("Pink Floyd")).thenReturn(secondList);
        when(albumRepository.findAllByArtistName("")).thenReturn(emptyList);

        assertEquals(firstList, albumServiceimp.findAllByArtistName("Wu-Tang-Clan"));
        assertEquals(secondList, albumServiceimp.findAllByArtistName("Pink Floyd"));
        assertThrows(MissingArtistException.class,() -> albumServiceimp.findAllByArtistName(""));

    }

    @Test
    void findAllAlbumsReleaseYearTest() throws UnavailableYearException {
        List<Album> emptyList = new ArrayList<>();
        List<Album> firstList = new ArrayList<>(List.of(
                new Album(1L,
                        "Raising Hell",
                        "Run-D.M.C.",
                        1986,
                        Genre.HIP_HOP),
                new Album(200L,
                        "Licensed to Ill",
                        "Beastie Boys",
                        1986,
                        Genre.HIP_HOP)
        ));

        List<Album> secondList = new ArrayList<>(List.of(
                new Album(4L,
                        "Kind of blue",
                        "Miles Davis",
                        1959,
                        Genre.JAZZ)
        ));

        when(albumRepository.findAllByReleaseYear(1986)).thenReturn(firstList);
        when(albumRepository.findAllByReleaseYear(1959)).thenReturn(secondList);
        when(albumRepository.findAllByReleaseYear(2)).thenReturn(emptyList);

        assertEquals(firstList, albumServiceimp.findAllByReleaseYear(1986));
        assertEquals(firstList, albumServiceimp.findAllByReleaseYear(1986));
        assertThrows(UnavailableYearException.class,() -> albumServiceimp.findAllByReleaseYear(2));

    }

    @Test
    void getAlbumsByGenreTest(){}
    void findAllAlbumsGenreTest() throws InvalidGenreException {
        List<Album> emptyList = new ArrayList<>();
        List<Album> firstList = new ArrayList<>(List.of(
                new Album(1L,
                        "Raising Hell",
                        "Run-D.M.C.",
                        1986,
                        Genre.HIP_HOP),
                new Album(200L,
                        "Licensed to Ill",
                        "Beastie Boys",
                        1986,
                        Genre.HIP_HOP)
        ));

        List<Album> secondList = new ArrayList<>(List.of(
                new Album(4L,
                        "Kind of blue",
                        "Miles Davis",
                        1959,
                        Genre.JAZZ)
        ));

        when(albumRepository.findAllByGenre(Genre.HIP_HOP)).thenReturn(firstList);
        when(albumRepository.findAllByGenre(Genre.JAZZ)).thenReturn(secondList);
        when(albumRepository.findAllByGenre(Genre.ROCK)).thenReturn(emptyList);

        assertEquals(firstList, albumServiceimp.findAllByGenre(Genre.HIP_HOP));
        assertEquals(secondList, albumServiceimp.findAllByGenre(Genre.JAZZ));
        assertThrows(UnavailableYearException.class, () -> albumServiceimp.findAllByGenre(Genre.ROCK));
    }

    @Test
    void findAllAlbumsByAlbumName() throws MissingAlbumException {
        List<Album> emptyList = new ArrayList<>();
        List<Album> firstList = new ArrayList<>(List.of(
                new Album(1L,
                        "Up",
                        "R.E.M.",
                        1998,
                        Genre.ROCK),
                new Album(200L,
                        "Up",
                        "Peter-Gabriel",
                        2002,
                        Genre.ROCK)
        ));

        List<Album> secondList = new ArrayList<>(List.of(
                new Album(4L,
                        "Kind-of-blue",
                        "Miles-Davis",
                        1959,
                        Genre.JAZZ)
        ));

        when(albumRepository.findAllByAlbumName("Up")).thenReturn(firstList);
        when(albumRepository.findAllByAlbumName("Kind-of-blue")).thenReturn(secondList);
        when(albumRepository.findAllByAlbumName(" ")).thenReturn(emptyList);

        assertEquals(firstList, albumServiceimp.findAllByAlbumName("Up"));
        assertEquals(secondList, albumServiceimp.findAllByAlbumName("Kind-of-blue"));
        assertThrows(MissingAlbumException.class, () -> albumServiceimp.findAllByAlbumName(" "));
    }
}
