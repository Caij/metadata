package com.caij.puremusic.media.util.metadata;

import java.util.Arrays;

public class MetadataWrapper {
    private String title;
    private String album;
    private String artist;
    private String albumArtist;
    private String compose;
    private Integer year;
    private Integer track;
    private Long during;
    private byte[] artwork;
    private String lyric;
    private Integer bitrate;
    private Integer sampleRate;

    public MetadataWrapper(String title, String album, String artist, String albumArtist, String compose,
                           Integer year, Integer track, Long during, byte[] artwork, String lyric,
                           Integer bitrate, Integer sampleRate) {
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.albumArtist = albumArtist;
        this.compose = compose;
        this.year = year;
        this.track = track;
        this.during = during;
        this.artwork = artwork;
        this.lyric = lyric;
        this.bitrate = bitrate;
        this.sampleRate = sampleRate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    public String getCompose() {
        return compose;
    }

    public void setCompose(String compose) {
        this.compose = compose;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getTrack() {
        return track;
    }

    public void setTrack(Integer track) {
        this.track = track;
    }

    public Long getDuring() {
        return during;
    }

    public void setDuring(Long during) {
        this.during = during;
    }

    public byte[] getArtwork() {
        return artwork;
    }

    public void setArtwork(byte[] artwork) {
        this.artwork = artwork;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public Integer getBitrate() {
        return bitrate;
    }

    public void setBitrate(Integer bitrate) {
        this.bitrate = bitrate;
    }

    public Integer getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(Integer sampleRate) {
        this.sampleRate = sampleRate;
    }

    @Override
    public String toString() {
        return "MetadataWrapper{" +
                "title='" + title + '\'' +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", albumArtist='" + albumArtist + '\'' +
                ", compose='" + compose + '\'' +
                ", year=" + year +
                ", track=" + track +
                ", during=" + during +
                ", artwork=" + Arrays.toString(artwork) +
                ", lyric='" + lyric + '\'' +
                ", bitrate=" + bitrate +
                ", sampleRate=" + sampleRate +
                '}';
    }
}
