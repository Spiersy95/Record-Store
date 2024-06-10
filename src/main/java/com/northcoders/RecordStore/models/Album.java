package com.northcoders.RecordStore.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="album_name")
    private String albumName;

    @Column(name="artist_name", nullable = false)
    private String artistName;

    @Column(name = "release_year")
    private int releaseYear;

    @Column (name= "genre")
    private Genre genre;


}
