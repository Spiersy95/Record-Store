package com.northcoders.RecordStore.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.northcoders.RecordStore.Controllers.AlbumController;
import com.northcoders.RecordStore.Exceptions.InvalidIdException;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

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
                "Enter the 36 chambers",
                "Wu-Tang-Clan",
                Genre.HIP_HOP,
                200));
        albums.add(new Album(500L,
                "Dark side of the moon",
                "Pink Floyd",
                Genre.ROCK,
                2));

        when(mockAlbumServiceImp.getAlbumById(2L)).thenReturn(albums.getFirst());
        when(mockAlbumServiceImp.getAlbumById(500L)).thenReturn(albums.getLast());

        when(mockAlbumServiceImp.getAlbumById(1L)).thenThrow(InvalidIdException.class);
        when(mockAlbumServiceImp.getAlbumById(3L)).thenThrow(InvalidIdException.class);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/albums?id=2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumName").value("Enter the 36 chambers"));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums?id=500"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumName").value("Dark side of the moon"));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums?id=1"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/albums?id=3"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
