package com.northcoders.RecordStore.models.DtO;

import com.northcoders.RecordStore.models.Genre;

public record AlbumDtO(String ArtistName, String AlbumTitle, int releaseYear, Genre genre) {

}
