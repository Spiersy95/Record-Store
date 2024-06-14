package com.northcoders.RecordStore.ControllerTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.northcoders.RecordStore.Controllers.AlbumController;
import com.northcoders.RecordStore.Exceptions.*;
import com.northcoders.RecordStore.Service.AlbumServiceImp;
import com.northcoders.RecordStore.models.Album;
import com.northcoders.RecordStore.models.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@SpringBootTest
public class AlbumControllerTests {

    @Mock
    private AlbumServiceImp mockAlbumServiceImp;

    @InjectMocks
    private AlbumController albumController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup(){
        mockMvcController = MockMvcBuilders.standaloneSetup(albumController).build();
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("GET/ by id")
    void getAlbumByIdTest() throws Exception {
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

        when(mockAlbumServiceImp.getAlbumById(2L)).thenReturn(albums.getFirst());
        when(mockAlbumServiceImp.getAlbumById(500L)).thenReturn(albums.getLast());

        when(mockAlbumServiceImp.getAlbumById(1L)).thenThrow(InvalidIdException.class);
        when(mockAlbumServiceImp.getAlbumById(3L)).thenThrow(InvalidIdException.class);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/album/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumName").value("36 chambers"));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/album/500"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumName").value("Dark side of the moon"));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/album/1"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/album/3"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("GET/ allAlbumsThrows")
    void getAllAlbumsThrowsTest() throws Exception {

        //Arrange part 1


        when(mockAlbumServiceImp.getAllAlbums()).thenThrow(EmptyDatabaseException.class);

        // act and assert part 1
        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        //Arrange part 2
    }
        @Test
        @DisplayName("GET/ allAlbums")
        void getAllAlbumsTest() throws Exception {

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

        System.out.println("Now");
        when(mockAlbumServiceImp.getAllAlbums()).thenReturn(albums);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].albumName").value("36 chambers"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].albumName").value("Dark side of the moon"));

    }

    @Test
    @DisplayName("post/ album")
    void postAlbumTest() throws Exception {


       Album album = new Album(2L,
               "36 chambers",
               "Wu-Tang-Clan",
               1993,
               Genre.HIP_HOP);


        when(mockAlbumServiceImp.addAlbum(album)).thenReturn(album);



        this.mockMvcController.perform(
                        MockMvcRequestBuilders.post("/api/v1/album")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(album)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumName").value("36 chambers"));

        verify(mockAlbumServiceImp, times(1)).addAlbum(album);

    }

    @Test
    void deleteAlbumTest() throws Exception {
        Album album = new Album(2L,
                "36 chambers",
                "Wu-Tang-Clan",
                1993,
                Genre.HIP_HOP);

        String expectedString = "Deleted Album with ID: 2";



        MvcResult result = this.mockMvcController.perform(
                MockMvcRequestBuilders.delete("/api/v1/album/2"))
                .andExpect(MockMvcResultMatchers.status().isAccepted()).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        assertEquals(expectedString, result.getResponse().getContentAsString());

        verify(mockAlbumServiceImp, times(1)).removeAlbum(2L);
    }

    @Test
    void findAlbumsByArtistNameTest() throws Exception {

        List<Album> returnAlbums1 = new ArrayList<>();
        returnAlbums1.add(new Album(1L,
                "36 Chambers",
                "Wu-Tang-Clan",
                1993,
                Genre.HIP_HOP));
        returnAlbums1.add(new Album(2L,
                "Wu-Tang Forever",
                "Wu-Tang-Clan",
                1997,
                Genre.HIP_HOP));

        List<Album> returnAlbums2 = new ArrayList<>();
        returnAlbums2.add(new Album(500L,
                "Dark side of the moon",
                "Pink Floyd",
                1973,
                Genre.ROCK));

        when(mockAlbumServiceImp.findAllByArtistName("Wu-Tang-Clan")).thenReturn(returnAlbums1);
        when(mockAlbumServiceImp.findAllByArtistName("Pink Floyd")).thenReturn(returnAlbums2);
        when(mockAlbumServiceImp.findAllByArtistName("Aqua")).thenThrow(MissingArtistException.class);
        when(mockAlbumServiceImp.findAllByArtistName("")).thenThrow(MissingArtistException.class);


        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums/artist/Wu-Tang-Clan"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].albumName").value("36 Chambers"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].albumName").value("Wu-Tang Forever"));

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/albums/artist/Pink Floyd"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(500L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].albumName").value("Dark side of the moon"));

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/albums/artist/Aqua"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getAlbumsByReleaseYearTest() throws Exception {

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

        when(mockAlbumServiceImp.findAllByReleaseYear(1986)).thenReturn(firstList);
        when(mockAlbumServiceImp.findAllByReleaseYear(1959)).thenReturn(secondList);
        when(mockAlbumServiceImp.findAllByReleaseYear(200)).thenThrow(UnavailableYearException.class);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums/1986"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].albumName").value("Raising Hell"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(200L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].albumName").value("Licensed to Ill"));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums/1959"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(4L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].albumName").value("Kind of blue"));

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/albums/200"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void getAlbumsByGenreTest() throws Exception {
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

        when(mockAlbumServiceImp.findAllByGenre(Genre.HIP_HOP)).thenReturn(firstList);
        when(mockAlbumServiceImp.findAllByGenre(Genre.JAZZ)).thenReturn(secondList);
        when(mockAlbumServiceImp.findAllByGenre(Genre.ROCK)).thenThrow(InvalidGenreException.class);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums/genre/HIP_HOP"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].albumName").value("Raising Hell"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(200L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].albumName").value("Licensed to Ill"));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums/genre/JAZZ"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(4L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].albumName").value("Kind of blue"));

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/albums/genre/ROCK"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getAlbumsByAlbumTitle() throws Exception {
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

        when(mockAlbumServiceImp.findAllByAlbumName("Up")).thenReturn(firstList);
        when(mockAlbumServiceImp.findAllByAlbumName("Kind-of-blue")).thenReturn(secondList);
        when(mockAlbumServiceImp.findAllByAlbumName("fdsfds")).thenThrow(MissingAlbumException.class);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums/listOfAlbumsCalled--Up"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].artistName").value("R.E.M."))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(200L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].artistName").value("Peter-Gabriel"));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums/listOfAlbumsCalled--Kind-of-blue"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(4L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].artistName").value("Miles-Davis"));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums/listOfAlbumsCalled--fdsfds"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void putAlbumTest(){

    }





}
