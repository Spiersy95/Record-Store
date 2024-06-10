package com.northcoders.RecordStore.models.DtO;

import com.northcoders.RecordStore.models.Genre;

public class AlbumDtO {

    private String albumName;
    private String artistName;
    private int releaseYear;
    private Genre genre;

    public AlbumDtO(String albumName, String artistName, int releaseYear, Genre genre) {
        this.albumName = albumName;
        this.artistName = artistName;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void cleanAlbumName(){
        String cleanName = this.getAlbumName().trim();
        this.setAlbumName(cleanName.replace(" ", "-"));
    }

    public void cleanArtistName(){
        String cleanName = this.getArtistName().trim();
        this.setArtistName(cleanName.replace(" ", "-"));
    }

    public boolean isValidAlbum(){
        if (this.getArtistName() == null || this.getArtistName().trim().isEmpty()){
            return false;
        }

    }
}
