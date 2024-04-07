/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.caij.puremusic.media.util.metadata.data;

import com.caij.puremusic.media.util.metadata.Util;
import com.google.common.base.Objects;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.annotation.ElementType.*;


public final class MediaMetadata {

  /** A builder for {@link MediaMetadata} instances. */
  public static final class Builder {

    private CharSequence title;
    private CharSequence artist;
    private CharSequence albumTitle;
    private CharSequence albumArtist;
    private CharSequence displayTitle;
    private CharSequence subtitle;
    private CharSequence description;
//    private Rating userRating;
//    private Rating overallRating;
    private byte[] artworkData;
    private @PictureType Integer artworkDataType;
    private String artworkUri;
    private Integer trackNumber;
    private Integer totalTrackCount;

    @SuppressWarnings("deprecation") // Builder for deprecated field.
    private @FolderType Integer folderType;

    private Boolean isBrowsable;
    private Boolean isPlayable;
    private Integer recordingYear;
    private Integer recordingMonth;
    private Integer recordingDay;
    private Integer releaseYear;
    private Integer releaseMonth;
    private Integer releaseDay;
    private CharSequence writer;
    private CharSequence composer;
    private CharSequence conductor;
    private Integer discNumber;
    private Integer totalDiscCount;
    private CharSequence genre;
    private CharSequence compilation;
    private CharSequence station;
    private @MediaType Integer mediaType;
    private Map<String, Object> extras;

    public Builder() {}

    @SuppressWarnings("deprecation") // Assigning from deprecated fields.
    private Builder(MediaMetadata mediaMetadata) {
      this.title = mediaMetadata.title;
      this.artist = mediaMetadata.artist;
      this.albumTitle = mediaMetadata.albumTitle;
      this.albumArtist = mediaMetadata.albumArtist;
      this.displayTitle = mediaMetadata.displayTitle;
      this.subtitle = mediaMetadata.subtitle;
      this.description = mediaMetadata.description;
      this.artworkData = mediaMetadata.artworkData;
      this.artworkDataType = mediaMetadata.artworkDataType;
      this.artworkUri = mediaMetadata.artworkUri;
      this.trackNumber = mediaMetadata.trackNumber;
      this.totalTrackCount = mediaMetadata.totalTrackCount;
      this.folderType = mediaMetadata.folderType;
      this.isBrowsable = mediaMetadata.isBrowsable;
      this.isPlayable = mediaMetadata.isPlayable;
      this.recordingYear = mediaMetadata.recordingYear;
      this.recordingMonth = mediaMetadata.recordingMonth;
      this.recordingDay = mediaMetadata.recordingDay;
      this.releaseYear = mediaMetadata.releaseYear;
      this.releaseMonth = mediaMetadata.releaseMonth;
      this.releaseDay = mediaMetadata.releaseDay;
      this.writer = mediaMetadata.writer;
      this.composer = mediaMetadata.composer;
      this.conductor = mediaMetadata.conductor;
      this.discNumber = mediaMetadata.discNumber;
      this.totalDiscCount = mediaMetadata.totalDiscCount;
      this.genre = mediaMetadata.genre;
      this.compilation = mediaMetadata.compilation;
      this.station = mediaMetadata.station;
      this.mediaType = mediaMetadata.mediaType;
      this.extras = mediaMetadata.extras;
    }

    /** Sets the title. */
    public Builder setTitle(CharSequence title) {
      this.title = title;
      return this;
    }

    /** Sets the artist. */
    public Builder setArtist(CharSequence artist) {
      this.artist = artist;
      return this;
    }

    /** Sets the album title. */
    public Builder setAlbumTitle(CharSequence albumTitle) {
      this.albumTitle = albumTitle;
      return this;
    }

    /** Sets the album artist. */
    public Builder setAlbumArtist(CharSequence albumArtist) {
      this.albumArtist = albumArtist;
      return this;
    }

    /** Sets the display title. */
    public Builder setDisplayTitle(CharSequence displayTitle) {
      this.displayTitle = displayTitle;
      return this;
    }

    /**
     * Sets the subtitle.
     *
     * <p>This is the secondary title of the media, unrelated to closed captions.
     */
    public Builder setSubtitle(CharSequence subtitle) {
      this.subtitle = subtitle;
      return this;
    }

    /** Sets the description. */
    public Builder setDescription(CharSequence description) {
      this.description = description;
      return this;
    }

//    public Builder setUserRating(Rating userRating) {
//      this.userRating = userRating;
//      return this;
//    }
//
//    /** Sets the overall {@link Rating}. */
//    public Builder setOverallRating(Rating overallRating) {
//      this.overallRating = overallRating;
//      return this;
//    }

    /**
     * @deprecated Use {@link #setArtworkData(byte[] data, Integer pictureType)} or {@link
     *     #maybeSetArtworkData(byte[] data, int pictureType)}, providing a {@link PictureType}.
     */
    @Deprecated
    public Builder setArtworkData(byte[] artworkData) {
      return setArtworkData(artworkData, /* artworkDataType= */ null);
    }

    /**
     * Sets the artwork data as a compressed byte array with an associated {@link PictureType
     * artworkDataType}.
     */
    public Builder setArtworkData(
        byte[] artworkData, @PictureType Integer artworkDataType) {
      this.artworkData = artworkData == null ? null : artworkData.clone();
      this.artworkDataType = artworkDataType;
      return this;
    }

    /**
     * Sets the artwork data as a compressed byte array in the event that the associated {@link
     * PictureType} is {@link #PICTURE_TYPE_FRONT_COVER}, the existing {@link PictureType} is not
     * {@link #PICTURE_TYPE_FRONT_COVER}, or the current artworkData is not set.
     *
     * <p>Use {@link #setArtworkData(byte[], Integer)} to set the artwork data without checking the
     * {@link PictureType}.
     */
    public Builder maybeSetArtworkData(byte[] artworkData, @PictureType int artworkDataType) {
      if (this.artworkData == null
          || Util.areEqual(artworkDataType, PICTURE_TYPE_FRONT_COVER)
          || !Util.areEqual(this.artworkDataType, PICTURE_TYPE_FRONT_COVER)) {
        this.artworkData = artworkData.clone();
        this.artworkDataType = artworkDataType;
      }
      return this;
    }

    public Builder setArtworkUri(String artworkUri) {
      this.artworkUri = artworkUri;
      return this;
    }

    /** Sets the track number. */
    public Builder setTrackNumber(Integer trackNumber) {
      this.trackNumber = trackNumber;
      return this;
    }

    /** Sets the total number of tracks. */
    public Builder setTotalTrackCount(Integer totalTrackCount) {
      this.totalTrackCount = totalTrackCount;
      return this;
    }

    /**
     * Sets the {@link FolderType}.
     *
     * @deprecated Use {@link #setIsBrowsable} to indicate if an item is a browsable folder and use
     *     {@link #setMediaType} to indicate the type of the folder.
     */
    @SuppressWarnings("deprecation") // Using deprecated type.
    @Deprecated
    public Builder setFolderType(@FolderType Integer folderType) {
      this.folderType = folderType;
      return this;
    }

    /** Sets whether the media is a browsable folder. */
    public Builder setIsBrowsable(Boolean isBrowsable) {
      this.isBrowsable = isBrowsable;
      return this;
    }

    /** Sets whether the media is playable. */
    public Builder setIsPlayable(Boolean isPlayable) {
      this.isPlayable = isPlayable;
      return this;
    }

    public Builder setYear(Integer year) {
      return setRecordingYear(year);
    }

    /** Sets the year of the recording date. */
    public Builder setRecordingYear(Integer recordingYear) {
      this.recordingYear = recordingYear;
      return this;
    }

    /**
     * Sets the month of the recording date.
     *
     * <p>Value should be between 1 and 12.
     */
    public Builder setRecordingMonth(Integer recordingMonth) {
      this.recordingMonth = recordingMonth;
      return this;
    }

    /**
     * Sets the day of the recording date.
     *
     * <p>Value should be between 1 and 31.
     */
    public Builder setRecordingDay(Integer recordingDay) {
      this.recordingDay = recordingDay;
      return this;
    }

    /** Sets the year of the release date. */
    public Builder setReleaseYear(Integer releaseYear) {
      this.releaseYear = releaseYear;
      return this;
    }

    /**
     * Sets the month of the release date.
     *
     * <p>Value should be between 1 and 12.
     */
    public Builder setReleaseMonth(Integer releaseMonth) {
      this.releaseMonth = releaseMonth;
      return this;
    }

    /**
     * Sets the day of the release date.
     *
     * <p>Value should be between 1 and 31.
     */
    public Builder setReleaseDay(Integer releaseDay) {
      this.releaseDay = releaseDay;
      return this;
    }

    /** Sets the writer. */
    public Builder setWriter(CharSequence writer) {
      this.writer = writer;
      return this;
    }

    /** Sets the composer. */
    public Builder setComposer(CharSequence composer) {
      this.composer = composer;
      return this;
    }

    /** Sets the conductor. */
    public Builder setConductor(CharSequence conductor) {
      this.conductor = conductor;
      return this;
    }

    /** Sets the disc number. */
    public Builder setDiscNumber(Integer discNumber) {
      this.discNumber = discNumber;
      return this;
    }

    /** Sets the total number of discs. */
    public Builder setTotalDiscCount(Integer totalDiscCount) {
      this.totalDiscCount = totalDiscCount;
      return this;
    }

    /** Sets the genre. */
    public Builder setGenre(CharSequence genre) {
      this.genre = genre;
      return this;
    }

    /** Sets the compilation. */
    public Builder setCompilation(CharSequence compilation) {
      this.compilation = compilation;
      return this;
    }

    /** Sets the name of the station streaming the media. */
    public Builder setStation(CharSequence station) {
      this.station = station;
      return this;
    }

    /** Sets the {@link MediaType}. */
    public Builder setMediaType(@MediaType Integer mediaType) {
      this.mediaType = mediaType;
      return this;
    }


    public Builder setExtras(Map<String, Object> extras) {
      this.extras = extras;
      return this;
    }

    /**
     * Sets all fields supported by the {@link Metadata.Entry entries} within the {@link Metadata}.
     *
     * <p>Fields are only set if the {@link Metadata.Entry} has an implementation for {@link
     * Metadata.Entry#populateMediaMetadata(Builder)}.
     *
     * <p>In the event that multiple {@link Metadata.Entry} objects within the {@link Metadata}
     * relate to the same {@link MediaMetadata} field, then the last one will be used.
     */
    public Builder populateFromMetadata(Metadata metadata) {
      for (int i = 0; i < metadata.length(); i++) {
        Metadata.Entry entry = metadata.get(i);
        entry.populateMediaMetadata(this);
      }
      return this;
    }

    /**
     * Sets all fields supported by the {@link Metadata.Entry entries} within the list of {@link
     * Metadata}.
     *
     * <p>Fields are only set if the {@link Metadata.Entry} has an implementation for {@link
     * Metadata.Entry#populateMediaMetadata(Builder)}.
     *
     * <p>In the event that multiple {@link Metadata.Entry} objects within any of the {@link
     * Metadata} relate to the same {@link MediaMetadata} field, then the last one will be used.
     */
    public Builder populateFromMetadata(List<Metadata> metadataList) {
      for (int i = 0; i < metadataList.size(); i++) {
        Metadata metadata = metadataList.get(i);
        for (int j = 0; j < metadata.length(); j++) {
          Metadata.Entry entry = metadata.get(j);
          entry.populateMediaMetadata(this);
        }
      }
      return this;
    }

    /**
     * Populates all the fields from {@code mediaMetadata}.
     *
     * <p>Fields are populated when they are non-null with an exception that both {@code artworkUri}
     * and {@code artworkData} are populated, when at least one of them is non-null.
     */
    @SuppressWarnings("deprecation") // Populating deprecated fields.
    public Builder populate(MediaMetadata mediaMetadata) {
      if (mediaMetadata == null) {
        return this;
      }
      if (mediaMetadata.title != null) {
        setTitle(mediaMetadata.title);
      }
      if (mediaMetadata.artist != null) {
        setArtist(mediaMetadata.artist);
      }
      if (mediaMetadata.albumTitle != null) {
        setAlbumTitle(mediaMetadata.albumTitle);
      }
      if (mediaMetadata.albumArtist != null) {
        setAlbumArtist(mediaMetadata.albumArtist);
      }
      if (mediaMetadata.displayTitle != null) {
        setDisplayTitle(mediaMetadata.displayTitle);
      }
      if (mediaMetadata.subtitle != null) {
        setSubtitle(mediaMetadata.subtitle);
      }
      if (mediaMetadata.description != null) {
        setDescription(mediaMetadata.description);
      }
//      if (mediaMetadata.userRating != null) {
//        setUserRating(mediaMetadata.userRating);
//      }
//      if (mediaMetadata.overallRating != null) {
//        setOverallRating(mediaMetadata.overallRating);
//      }
      if (mediaMetadata.artworkUri != null || mediaMetadata.artworkData != null) {
        setArtworkUri(mediaMetadata.artworkUri);
        setArtworkData(mediaMetadata.artworkData, mediaMetadata.artworkDataType);
      }
      if (mediaMetadata.trackNumber != null) {
        setTrackNumber(mediaMetadata.trackNumber);
      }
      if (mediaMetadata.totalTrackCount != null) {
        setTotalTrackCount(mediaMetadata.totalTrackCount);
      }
      if (mediaMetadata.folderType != null) {
        setFolderType(mediaMetadata.folderType);
      }
      if (mediaMetadata.isBrowsable != null) {
        setIsBrowsable(mediaMetadata.isBrowsable);
      }
      if (mediaMetadata.isPlayable != null) {
        setIsPlayable(mediaMetadata.isPlayable);
      }
      if (mediaMetadata.year != null) {
        setRecordingYear(mediaMetadata.year);
      }
      if (mediaMetadata.recordingYear != null) {
        setRecordingYear(mediaMetadata.recordingYear);
      }
      if (mediaMetadata.recordingMonth != null) {
        setRecordingMonth(mediaMetadata.recordingMonth);
      }
      if (mediaMetadata.recordingDay != null) {
        setRecordingDay(mediaMetadata.recordingDay);
      }
      if (mediaMetadata.releaseYear != null) {
        setReleaseYear(mediaMetadata.releaseYear);
      }
      if (mediaMetadata.releaseMonth != null) {
        setReleaseMonth(mediaMetadata.releaseMonth);
      }
      if (mediaMetadata.releaseDay != null) {
        setReleaseDay(mediaMetadata.releaseDay);
      }
      if (mediaMetadata.writer != null) {
        setWriter(mediaMetadata.writer);
      }
      if (mediaMetadata.composer != null) {
        setComposer(mediaMetadata.composer);
      }
      if (mediaMetadata.conductor != null) {
        setConductor(mediaMetadata.conductor);
      }
      if (mediaMetadata.discNumber != null) {
        setDiscNumber(mediaMetadata.discNumber);
      }
      if (mediaMetadata.totalDiscCount != null) {
        setTotalDiscCount(mediaMetadata.totalDiscCount);
      }
      if (mediaMetadata.genre != null) {
        setGenre(mediaMetadata.genre);
      }
      if (mediaMetadata.compilation != null) {
        setCompilation(mediaMetadata.compilation);
      }
      if (mediaMetadata.station != null) {
        setStation(mediaMetadata.station);
      }
      if (mediaMetadata.mediaType != null) {
        setMediaType(mediaMetadata.mediaType);
      }
      if (mediaMetadata.extras != null) {
        setExtras(mediaMetadata.extras);
      }

      return this;
    }

    /** Returns a new {@link MediaMetadata} instance with the current builder values. */
    public MediaMetadata build() {
      return new MediaMetadata(/* builder= */ this);
    }
  }

  /**
   * The type of content described by the media item.
   *
   * <p>One of {@link #MEDIA_TYPE_MIXED}, {@link #MEDIA_TYPE_MUSIC}, {@link
   * #MEDIA_TYPE_AUDIO_BOOK_CHAPTER}, {@link #MEDIA_TYPE_PODCAST_EPISODE}, {@link
   * #MEDIA_TYPE_RADIO_STATION}, {@link #MEDIA_TYPE_NEWS}, {@link #MEDIA_TYPE_VIDEO}, {@link
   * #MEDIA_TYPE_TRAILER}, {@link #MEDIA_TYPE_MOVIE}, {@link #MEDIA_TYPE_TV_SHOW}, {@link
   * #MEDIA_TYPE_ALBUM}, {@link #MEDIA_TYPE_ARTIST}, {@link #MEDIA_TYPE_GENRE}, {@link
   * #MEDIA_TYPE_PLAYLIST}, {@link #MEDIA_TYPE_YEAR}, {@link #MEDIA_TYPE_AUDIO_BOOK}, {@link
   * #MEDIA_TYPE_PODCAST}, {@link #MEDIA_TYPE_TV_CHANNEL}, {@link #MEDIA_TYPE_TV_SERIES}, {@link
   * #MEDIA_TYPE_TV_SEASON}, {@link #MEDIA_TYPE_FOLDER_MIXED}, {@link #MEDIA_TYPE_FOLDER_ALBUMS},
   * {@link #MEDIA_TYPE_FOLDER_ARTISTS}, {@link #MEDIA_TYPE_FOLDER_GENRES}, {@link
   * #MEDIA_TYPE_FOLDER_PLAYLISTS}, {@link #MEDIA_TYPE_FOLDER_YEARS}, {@link
   * #MEDIA_TYPE_FOLDER_AUDIO_BOOKS}, {@link #MEDIA_TYPE_FOLDER_PODCASTS}, {@link
   * #MEDIA_TYPE_FOLDER_TV_CHANNELS}, {@link #MEDIA_TYPE_FOLDER_TV_SERIES}, {@link
   * #MEDIA_TYPE_FOLDER_TV_SHOWS}, {@link #MEDIA_TYPE_FOLDER_RADIO_STATIONS}, {@link
   * #MEDIA_TYPE_FOLDER_NEWS}, {@link #MEDIA_TYPE_FOLDER_VIDEOS}, {@link
   * #MEDIA_TYPE_FOLDER_TRAILERS} or {@link #MEDIA_TYPE_FOLDER_MOVIES}.
   */
  @Documented
  @Retention(RetentionPolicy.SOURCE)
  @Target(TYPE_USE)
  public @interface MediaType {}

  /** Media of undetermined type or a mix of multiple {@linkplain MediaType media types}. */
  public static final int MEDIA_TYPE_MIXED = 0;

  /** {@link MediaType} for music. */
  public static final int MEDIA_TYPE_MUSIC = 1;

  /** {@link MediaType} for an audio book chapter. */
  public static final int MEDIA_TYPE_AUDIO_BOOK_CHAPTER = 2;

  /** {@link MediaType} for a podcast episode. */
  public static final int MEDIA_TYPE_PODCAST_EPISODE = 3;

  /** {@link MediaType} for a radio station. */
  public static final int MEDIA_TYPE_RADIO_STATION = 4;

  /** {@link MediaType} for news. */
  public static final int MEDIA_TYPE_NEWS = 5;

  /** {@link MediaType} for a video. */
  public static final int MEDIA_TYPE_VIDEO = 6;

  /** {@link MediaType} for a movie trailer. */
  public static final int MEDIA_TYPE_TRAILER = 7;

  /** {@link MediaType} for a movie. */
  public static final int MEDIA_TYPE_MOVIE = 8;

  /** {@link MediaType} for a TV show. */
  public static final int MEDIA_TYPE_TV_SHOW = 9;

  /**
   * {@link MediaType} for a group of items (e.g., {@link #MEDIA_TYPE_MUSIC music}) belonging to an
   * album.
   */
  public static final int MEDIA_TYPE_ALBUM = 10;

  /**
   * {@link MediaType} for a group of items (e.g., {@link #MEDIA_TYPE_MUSIC music}) from the same
   * artist.
   */
  public static final int MEDIA_TYPE_ARTIST = 11;

  /**
   * {@link MediaType} for a group of items (e.g., {@link #MEDIA_TYPE_MUSIC music}) of the same
   * genre.
   */
  public static final int MEDIA_TYPE_GENRE = 12;

  /**
   * {@link MediaType} for a group of items (e.g., {@link #MEDIA_TYPE_MUSIC music}) forming a
   * playlist.
   */
  public static final int MEDIA_TYPE_PLAYLIST = 13;

  /**
   * {@link MediaType} for a group of items (e.g., {@link #MEDIA_TYPE_MUSIC music}) from the same
   * year.
   */
  public static final int MEDIA_TYPE_YEAR = 14;

  /**
   * {@link MediaType} for a group of items forming an audio book. Items in this group are typically
   * of type {@link #MEDIA_TYPE_AUDIO_BOOK_CHAPTER}.
   */
  public static final int MEDIA_TYPE_AUDIO_BOOK = 15;

  /**
   * {@link MediaType} for a group of items belonging to a podcast. Items in this group are
   * typically of type {@link #MEDIA_TYPE_PODCAST_EPISODE}.
   */
  public static final int MEDIA_TYPE_PODCAST = 16;

  /**
   * {@link MediaType} for a group of items that are part of a TV channel. Items in this group are
   * typically of type {@link #MEDIA_TYPE_TV_SHOW}, {@link #MEDIA_TYPE_TV_SERIES} or {@link
   * #MEDIA_TYPE_MOVIE}.
   */
  public static final int MEDIA_TYPE_TV_CHANNEL = 17;

  /**
   * {@link MediaType} for a group of items that are part of a TV series. Items in this group are
   * typically of type {@link #MEDIA_TYPE_TV_SHOW} or {@link #MEDIA_TYPE_TV_SEASON}.
   */
  public static final int MEDIA_TYPE_TV_SERIES = 18;

  /**
   * {@link MediaType} for a group of items that are part of a TV series. Items in this group are
   * typically of type {@link #MEDIA_TYPE_TV_SHOW}.
   */
  public static final int MEDIA_TYPE_TV_SEASON = 19;

  /** {@link MediaType} for a folder with mixed or undetermined content. */
  public static final int MEDIA_TYPE_FOLDER_MIXED = 20;

  /** {@link MediaType} for a folder containing {@linkplain #MEDIA_TYPE_ALBUM albums}. */
  public static final int MEDIA_TYPE_FOLDER_ALBUMS = 21;

  /** {@link MediaType} for a folder containing {@linkplain #FIELD_ARTIST artists}. */
  public static final int MEDIA_TYPE_FOLDER_ARTISTS = 22;

  /** {@link MediaType} for a folder containing {@linkplain #MEDIA_TYPE_GENRE genres}. */
  public static final int MEDIA_TYPE_FOLDER_GENRES = 23;

  /** {@link MediaType} for a folder containing {@linkplain #MEDIA_TYPE_PLAYLIST playlists}. */
  public static final int MEDIA_TYPE_FOLDER_PLAYLISTS = 24;

  /** {@link MediaType} for a folder containing {@linkplain #MEDIA_TYPE_YEAR years}. */
  public static final int MEDIA_TYPE_FOLDER_YEARS = 25;

  /** {@link MediaType} for a folder containing {@linkplain #MEDIA_TYPE_AUDIO_BOOK audio books}. */
  public static final int MEDIA_TYPE_FOLDER_AUDIO_BOOKS = 26;

  /** {@link MediaType} for a folder containing {@linkplain #MEDIA_TYPE_PODCAST podcasts}. */
  public static final int MEDIA_TYPE_FOLDER_PODCASTS = 27;

  /** {@link MediaType} for a folder containing {@linkplain #MEDIA_TYPE_TV_CHANNEL TV channels}. */
  public static final int MEDIA_TYPE_FOLDER_TV_CHANNELS = 28;

  /** {@link MediaType} for a folder containing {@linkplain #MEDIA_TYPE_TV_SERIES TV series}. */
  public static final int MEDIA_TYPE_FOLDER_TV_SERIES = 29;

  /** {@link MediaType} for a folder containing {@linkplain #MEDIA_TYPE_TV_SHOW TV shows}. */
  public static final int MEDIA_TYPE_FOLDER_TV_SHOWS = 30;

  /**
   * {@link MediaType} for a folder containing {@linkplain #MEDIA_TYPE_RADIO_STATION radio
   * stations}.
   */
  public static final int MEDIA_TYPE_FOLDER_RADIO_STATIONS = 31;

  /** {@link MediaType} for a folder containing {@linkplain #MEDIA_TYPE_NEWS news}. */
  public static final int MEDIA_TYPE_FOLDER_NEWS = 32;

  /** {@link MediaType} for a folder containing {@linkplain #MEDIA_TYPE_VIDEO videos}. */
  public static final int MEDIA_TYPE_FOLDER_VIDEOS = 33;

  /** {@link MediaType} for a folder containing {@linkplain #MEDIA_TYPE_TRAILER movie trailers}. */
  public static final int MEDIA_TYPE_FOLDER_TRAILERS = 34;

  /** {@link MediaType} for a folder containing {@linkplain #MEDIA_TYPE_MOVIE movies}. */
  public static final int MEDIA_TYPE_FOLDER_MOVIES = 35;

  /**
   * The folder type of the media item.
   *
   * <p>This can be used as the type of a browsable bluetooth folder (see section 6.10.2.2 of the <a
   * href="https://www.bluetooth.com/specifications/specs/a-v-remote-control-profile-1-6-2/">Bluetooth
   * AVRCP 1.6.2</a>).
   *
   * <p>One of {@link #FOLDER_TYPE_NONE}, {@link #FOLDER_TYPE_MIXED}, {@link #FOLDER_TYPE_TITLES},
   * {@link #FOLDER_TYPE_ALBUMS}, {@link #FOLDER_TYPE_ARTISTS}, {@link #FOLDER_TYPE_GENRES}, {@link
   * #FOLDER_TYPE_PLAYLISTS} or {@link #FOLDER_TYPE_YEARS}.
   *
   * @deprecated Use {@link #isBrowsable} to indicate if an item is a browsable folder and use
   *     {@link #mediaType} to indicate the type of the folder.
   */
  // @Target list includes both 'default' targets and TYPE_USE, to ensure backwards compatibility
  // with Kotlin usages from before TYPE_USE was added.
  @Documented
  @Retention(RetentionPolicy.SOURCE)
  @Target({FIELD, METHOD, PARAMETER, LOCAL_VARIABLE, TYPE_USE})
  @Deprecated
  @SuppressWarnings("deprecation") // Defining deprecated constants.
  public @interface FolderType {}

  /**
   * Type for an item that is not a folder.
   *
   * @deprecated Use {@link #isBrowsable} set to false instead.
   */
  @Deprecated public static final int FOLDER_TYPE_NONE = -1;

  /**
   * Type for a folder containing media of mixed types.
   *
   * @deprecated Use {@link #isBrowsable} set to true and {@link #mediaType} set to {@link
   *     #MEDIA_TYPE_FOLDER_MIXED} instead.
   */
  @Deprecated public static final int FOLDER_TYPE_MIXED = 0;

  /**
   * Type for a folder containing only playable media.
   *
   * @deprecated Use {@link #isBrowsable} set to true instead.
   */
  @Deprecated public static final int FOLDER_TYPE_TITLES = 1;

  /**
   * Type for a folder containing media categorized by album.
   *
   * @deprecated Use {@link #isBrowsable} set to true and {@link #mediaType} set to {@link
   *     #MEDIA_TYPE_FOLDER_ALBUMS} instead.
   */
  @Deprecated public static final int FOLDER_TYPE_ALBUMS = 2;

  /**
   * Type for a folder containing media categorized by artist.
   *
   * @deprecated Use {@link #isBrowsable} set to true and {@link #mediaType} set to {@link
   *     #MEDIA_TYPE_FOLDER_ARTISTS} instead.
   */
  @Deprecated public static final int FOLDER_TYPE_ARTISTS = 3;

  /**
   * Type for a folder containing media categorized by genre.
   *
   * @deprecated Use {@link #isBrowsable} set to true and {@link #mediaType} set to {@link
   *     #MEDIA_TYPE_FOLDER_GENRES} instead.
   */
  @Deprecated public static final int FOLDER_TYPE_GENRES = 4;

  /**
   * Type for a folder containing a playlist.
   *
   * @deprecated Use {@link #isBrowsable} set to true and {@link #mediaType} set to {@link
   *     #MEDIA_TYPE_FOLDER_PLAYLISTS} instead.
   */
  @Deprecated public static final int FOLDER_TYPE_PLAYLISTS = 5;

  /**
   * Type for a folder containing media categorized by year.
   *
   * @deprecated Use {@link #isBrowsable} set to true and {@link #mediaType} set to {@link
   *     #MEDIA_TYPE_FOLDER_YEARS} instead.
   */
  @Deprecated public static final int FOLDER_TYPE_YEARS = 6;

  /**
   * The picture type of the artwork.
   *
   * <p>Values sourced from the ID3 v2.4 specification (See section 4.14 of
   * https://id3.org/id3v2.4.0-frames).
   *
   * <p>One of {@link #PICTURE_TYPE_OTHER}, {@link #PICTURE_TYPE_FILE_ICON}, {@link
   * #PICTURE_TYPE_FILE_ICON_OTHER}, {@link #PICTURE_TYPE_FRONT_COVER}, {@link
   * #PICTURE_TYPE_BACK_COVER}, {@link #PICTURE_TYPE_LEAFLET_PAGE}, {@link #PICTURE_TYPE_MEDIA},
   * {@link #PICTURE_TYPE_LEAD_ARTIST_PERFORMER}, {@link #PICTURE_TYPE_ARTIST_PERFORMER}, {@link
   * #PICTURE_TYPE_CONDUCTOR}, {@link #PICTURE_TYPE_BAND_ORCHESTRA}, {@link #PICTURE_TYPE_COMPOSER},
   * {@link #PICTURE_TYPE_LYRICIST}, {@link #PICTURE_TYPE_RECORDING_LOCATION}, {@link
   * #PICTURE_TYPE_DURING_RECORDING}, {@link #PICTURE_TYPE_DURING_PERFORMANCE}, {@link
   * #PICTURE_TYPE_MOVIE_VIDEO_SCREEN_CAPTURE}, {@link #PICTURE_TYPE_A_BRIGHT_COLORED_FISH}, {@link
   * #PICTURE_TYPE_ILLUSTRATION}, {@link #PICTURE_TYPE_BAND_ARTIST_LOGO} or {@link
   * #PICTURE_TYPE_PUBLISHER_STUDIO_LOGO}.
   */
  // @Target list includes both 'default' targets and TYPE_USE, to ensure backwards compatibility
  // with Kotlin usages from before TYPE_USE was added.
  @Documented
  @Retention(RetentionPolicy.SOURCE)
  @Target({FIELD, METHOD, PARAMETER, LOCAL_VARIABLE, TYPE_USE})
  public @interface PictureType {}

  public static final int PICTURE_TYPE_OTHER = 0x00;
  public static final int PICTURE_TYPE_FILE_ICON = 0x01;
  public static final int PICTURE_TYPE_FILE_ICON_OTHER = 0x02;
  public static final int PICTURE_TYPE_FRONT_COVER = 0x03;
  public static final int PICTURE_TYPE_BACK_COVER = 0x04;
  public static final int PICTURE_TYPE_LEAFLET_PAGE = 0x05;
  public static final int PICTURE_TYPE_MEDIA = 0x06;
  public static final int PICTURE_TYPE_LEAD_ARTIST_PERFORMER = 0x07;
  public static final int PICTURE_TYPE_ARTIST_PERFORMER = 0x08;
  public static final int PICTURE_TYPE_CONDUCTOR = 0x09;
  public static final int PICTURE_TYPE_BAND_ORCHESTRA = 0x0A;
  public static final int PICTURE_TYPE_COMPOSER = 0x0B;
  public static final int PICTURE_TYPE_LYRICIST = 0x0C;
  public static final int PICTURE_TYPE_RECORDING_LOCATION = 0x0D;
  public static final int PICTURE_TYPE_DURING_RECORDING = 0x0E;
  public static final int PICTURE_TYPE_DURING_PERFORMANCE = 0x0F;
  public static final int PICTURE_TYPE_MOVIE_VIDEO_SCREEN_CAPTURE = 0x10;
  public static final int PICTURE_TYPE_A_BRIGHT_COLORED_FISH = 0x11;
  public static final int PICTURE_TYPE_ILLUSTRATION = 0x12;
  public static final int PICTURE_TYPE_BAND_ARTIST_LOGO = 0x13;
  public static final int PICTURE_TYPE_PUBLISHER_STUDIO_LOGO = 0x14;

  /** Empty {@link MediaMetadata}. */
  public static final MediaMetadata EMPTY = new Builder().build();

  /** Optional title. */
  public final CharSequence title;

  /** Optional artist. */
  public final CharSequence artist;

  /** Optional album title. */
  public final CharSequence albumTitle;

  /** Optional album artist. */
  public final CharSequence albumArtist;

  /** Optional display title. */
  public final CharSequence displayTitle;

  /**
   * Optional subtitle.
   *
   * <p>This is the secondary title of the media, unrelated to closed captions.
   */
  public final CharSequence subtitle;

  /** Optional description. */
  public final CharSequence description;

//  /** Optional user {@link Rating}. */
//  public final Rating userRating;
//
//  /** Optional overall {@link Rating}. */
//  public final Rating overallRating;

  /** Optional artwork data as a compressed byte array. */
  public final byte[] artworkData;

  /** Optional {@link PictureType} of the artwork data. */
  public final @PictureType Integer artworkDataType;

  public final String artworkUri;

  /** Optional track number. */
  public final Integer trackNumber;

  /** Optional total number of tracks. */
  public final Integer totalTrackCount;

  /**
   * Optional {@link FolderType}.
   *
   * @deprecated Use {@link #isBrowsable} to indicate if an item is a browsable folder and use
   *     {@link #mediaType} to indicate the type of the folder.
   */
  @SuppressWarnings("deprecation") // Defining field of deprecated type.
  @Deprecated
  public final @FolderType Integer folderType;

  /** Optional boolean to indicate that the media is a browsable folder. */
  public final Boolean isBrowsable;

  /** Optional boolean to indicate that the media is playable. */
  public final Boolean isPlayable;

  /**
   * @deprecated Use {@link #recordingYear} instead.
   */
  @Deprecated public final Integer year;

  /** Optional year of the recording date. */
  public final Integer recordingYear;

  /**
   * Optional month of the recording date.
   *
   * <p>Note that there is no guarantee that the month and day are a valid combination.
   */
  public final Integer recordingMonth;

  /**
   * Optional day of the recording date.
   *
   * <p>Note that there is no guarantee that the month and day are a valid combination.
   */
  public final Integer recordingDay;

  /** Optional year of the release date. */
  public final Integer releaseYear;

  /**
   * Optional month of the release date.
   *
   * <p>Note that there is no guarantee that the month and day are a valid combination.
   */
  public final Integer releaseMonth;

  /**
   * Optional day of the release date.
   *
   * <p>Note that there is no guarantee that the month and day are a valid combination.
   */
  public final Integer releaseDay;

  /** Optional writer. */
  public final CharSequence writer;

  /** Optional composer. */
  public final CharSequence composer;

  /** Optional conductor. */
  public final CharSequence conductor;

  /** Optional disc number. */
  public final Integer discNumber;

  /** Optional total number of discs. */
  public final Integer totalDiscCount;

  /** Optional genre. */
  public final CharSequence genre;

  /** Optional compilation. */
  public final CharSequence compilation;

  /** Optional name of the station streaming the media. */
  public final CharSequence station;

  /** Optional {@link MediaType}. */
  public final @MediaType Integer mediaType;

  /**
   * considered in the {@link #equals(Object)} or {@link #hashCode()}.
   */
  public final Map<String, Object> extras;

  @SuppressWarnings("deprecation") // Assigning deprecated fields.
  private MediaMetadata(Builder builder) {
    // Handle compatibility for deprecated fields.
    Boolean isBrowsable = builder.isBrowsable;
    Integer folderType = builder.folderType;
    Integer mediaType = builder.mediaType;
    if (isBrowsable != null) {
      if (!isBrowsable) {
        folderType = FOLDER_TYPE_NONE;
      } else if (folderType == null || folderType == FOLDER_TYPE_NONE) {
        folderType = mediaType != null ? getFolderTypeFromMediaType(mediaType) : FOLDER_TYPE_MIXED;
      }
    } else if (folderType != null) {
      isBrowsable = folderType != FOLDER_TYPE_NONE;
      if (isBrowsable && mediaType == null) {
        mediaType = getMediaTypeFromFolderType(folderType);
      }
    }
    this.title = builder.title;
    this.artist = builder.artist;
    this.albumTitle = builder.albumTitle;
    this.albumArtist = builder.albumArtist;
    this.displayTitle = builder.displayTitle;
    this.subtitle = builder.subtitle;
    this.description = builder.description;
    this.artworkData = builder.artworkData;
    this.artworkDataType = builder.artworkDataType;
    this.artworkUri = builder.artworkUri;
    this.trackNumber = builder.trackNumber;
    this.totalTrackCount = builder.totalTrackCount;
    this.folderType = folderType;
    this.isBrowsable = isBrowsable;
    this.isPlayable = builder.isPlayable;
    this.year = builder.recordingYear;
    this.recordingYear = builder.recordingYear;
    this.recordingMonth = builder.recordingMonth;
    this.recordingDay = builder.recordingDay;
    this.releaseYear = builder.releaseYear;
    this.releaseMonth = builder.releaseMonth;
    this.releaseDay = builder.releaseDay;
    this.writer = builder.writer;
    this.composer = builder.composer;
    this.conductor = builder.conductor;
    this.discNumber = builder.discNumber;
    this.totalDiscCount = builder.totalDiscCount;
    this.genre = builder.genre;
    this.compilation = builder.compilation;
    this.station = builder.station;
    this.mediaType = mediaType;
    this.extras = builder.extras;
  }

  /** Returns a new {@link Builder} instance with the current {@link MediaMetadata} fields. */
  public Builder buildUpon() {
    return new Builder(/* mediaMetadata= */ this);
  }

  /** Note: Equality checking does not consider {@link #extras}. */
  @SuppressWarnings("deprecation") // Comparing deprecated fields.
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    MediaMetadata that = (MediaMetadata) obj;
    return Util.areEqual(title, that.title)
        && Util.areEqual(artist, that.artist)
        && Util.areEqual(albumTitle, that.albumTitle)
        && Util.areEqual(albumArtist, that.albumArtist)
        && Util.areEqual(displayTitle, that.displayTitle)
        && Util.areEqual(subtitle, that.subtitle)
        && Util.areEqual(description, that.description)
//        && Util.areEqual(userRating, that.userRating)
//        && Util.areEqual(overallRating, that.overallRating)
        && Arrays.equals(artworkData, that.artworkData)
        && Util.areEqual(artworkDataType, that.artworkDataType)
        && Util.areEqual(artworkUri, that.artworkUri)
        && Util.areEqual(trackNumber, that.trackNumber)
        && Util.areEqual(totalTrackCount, that.totalTrackCount)
        && Util.areEqual(folderType, that.folderType)
        && Util.areEqual(isBrowsable, that.isBrowsable)
        && Util.areEqual(isPlayable, that.isPlayable)
        && Util.areEqual(recordingYear, that.recordingYear)
        && Util.areEqual(recordingMonth, that.recordingMonth)
        && Util.areEqual(recordingDay, that.recordingDay)
        && Util.areEqual(releaseYear, that.releaseYear)
        && Util.areEqual(releaseMonth, that.releaseMonth)
        && Util.areEqual(releaseDay, that.releaseDay)
        && Util.areEqual(writer, that.writer)
        && Util.areEqual(composer, that.composer)
        && Util.areEqual(conductor, that.conductor)
        && Util.areEqual(discNumber, that.discNumber)
        && Util.areEqual(totalDiscCount, that.totalDiscCount)
        && Util.areEqual(genre, that.genre)
        && Util.areEqual(compilation, that.compilation)
        && Util.areEqual(station, that.station)
        && Util.areEqual(mediaType, that.mediaType);
  }

  @SuppressWarnings("deprecation") // Hashing deprecated fields.
  @Override
  public int hashCode() {
    return Objects.hashCode(
        title,
        artist,
        albumTitle,
        albumArtist,
        displayTitle,
        subtitle,
        description,
        Arrays.hashCode(artworkData),
        artworkDataType,
        artworkUri,
        trackNumber,
        totalTrackCount,
        folderType,
        isBrowsable,
        isPlayable,
        recordingYear,
        recordingMonth,
        recordingDay,
        releaseYear,
        releaseMonth,
        releaseDay,
        writer,
        composer,
        conductor,
        discNumber,
        totalDiscCount,
        genre,
        compilation,
        station,
        mediaType);
  }

  // Bundleable implementation.

  private static final String FIELD_TITLE = Util.intToStringMaxRadix(0);
  private static final String FIELD_ARTIST = Util.intToStringMaxRadix(1);
  private static final String FIELD_ALBUM_TITLE = Util.intToStringMaxRadix(2);
  private static final String FIELD_ALBUM_ARTIST = Util.intToStringMaxRadix(3);
  private static final String FIELD_DISPLAY_TITLE = Util.intToStringMaxRadix(4);
  private static final String FIELD_SUBTITLE = Util.intToStringMaxRadix(5);
  private static final String FIELD_DESCRIPTION = Util.intToStringMaxRadix(6);
  // 7 is reserved to maintain backward compatibility for a previously defined field.
  private static final String FIELD_USER_RATING = Util.intToStringMaxRadix(8);
  private static final String FIELD_OVERALL_RATING = Util.intToStringMaxRadix(9);
  private static final String FIELD_ARTWORK_DATA = Util.intToStringMaxRadix(10);
  private static final String FIELD_ARTWORK_URI = Util.intToStringMaxRadix(11);
  private static final String FIELD_TRACK_NUMBER = Util.intToStringMaxRadix(12);
  private static final String FIELD_TOTAL_TRACK_COUNT = Util.intToStringMaxRadix(13);
  private static final String FIELD_FOLDER_TYPE = Util.intToStringMaxRadix(14);
  private static final String FIELD_IS_PLAYABLE = Util.intToStringMaxRadix(15);
  private static final String FIELD_RECORDING_YEAR = Util.intToStringMaxRadix(16);
  private static final String FIELD_RECORDING_MONTH = Util.intToStringMaxRadix(17);
  private static final String FIELD_RECORDING_DAY = Util.intToStringMaxRadix(18);
  private static final String FIELD_RELEASE_YEAR = Util.intToStringMaxRadix(19);
  private static final String FIELD_RELEASE_MONTH = Util.intToStringMaxRadix(20);
  private static final String FIELD_RELEASE_DAY = Util.intToStringMaxRadix(21);
  private static final String FIELD_WRITER = Util.intToStringMaxRadix(22);
  private static final String FIELD_COMPOSER = Util.intToStringMaxRadix(23);
  private static final String FIELD_CONDUCTOR = Util.intToStringMaxRadix(24);
  private static final String FIELD_DISC_NUMBER = Util.intToStringMaxRadix(25);
  private static final String FIELD_TOTAL_DISC_COUNT = Util.intToStringMaxRadix(26);
  private static final String FIELD_GENRE = Util.intToStringMaxRadix(27);
  private static final String FIELD_COMPILATION = Util.intToStringMaxRadix(28);
  private static final String FIELD_ARTWORK_DATA_TYPE = Util.intToStringMaxRadix(29);
  private static final String FIELD_STATION = Util.intToStringMaxRadix(30);
  private static final String FIELD_MEDIA_TYPE = Util.intToStringMaxRadix(31);
  private static final String FIELD_IS_BROWSABLE = Util.intToStringMaxRadix(32);
  private static final String FIELD_EXTRAS = Util.intToStringMaxRadix(1000);


  @SuppressWarnings("deprecation") // Converting deprecated field.
  private static @FolderType int getFolderTypeFromMediaType(@MediaType int mediaType) {
    switch (mediaType) {
      case MEDIA_TYPE_ALBUM:
      case MEDIA_TYPE_ARTIST:
      case MEDIA_TYPE_AUDIO_BOOK:
      case MEDIA_TYPE_AUDIO_BOOK_CHAPTER:
      case MEDIA_TYPE_FOLDER_MOVIES:
      case MEDIA_TYPE_FOLDER_NEWS:
      case MEDIA_TYPE_FOLDER_RADIO_STATIONS:
      case MEDIA_TYPE_FOLDER_TRAILERS:
      case MEDIA_TYPE_FOLDER_VIDEOS:
      case MEDIA_TYPE_GENRE:
      case MEDIA_TYPE_MOVIE:
      case MEDIA_TYPE_MUSIC:
      case MEDIA_TYPE_NEWS:
      case MEDIA_TYPE_PLAYLIST:
      case MEDIA_TYPE_PODCAST:
      case MEDIA_TYPE_PODCAST_EPISODE:
      case MEDIA_TYPE_RADIO_STATION:
      case MEDIA_TYPE_TRAILER:
      case MEDIA_TYPE_TV_CHANNEL:
      case MEDIA_TYPE_TV_SEASON:
      case MEDIA_TYPE_TV_SERIES:
      case MEDIA_TYPE_TV_SHOW:
      case MEDIA_TYPE_VIDEO:
      case MEDIA_TYPE_YEAR:
        return FOLDER_TYPE_TITLES;
      case MEDIA_TYPE_FOLDER_ALBUMS:
        return FOLDER_TYPE_ALBUMS;
      case MEDIA_TYPE_FOLDER_ARTISTS:
        return FOLDER_TYPE_ARTISTS;
      case MEDIA_TYPE_FOLDER_GENRES:
        return FOLDER_TYPE_GENRES;
      case MEDIA_TYPE_FOLDER_PLAYLISTS:
        return FOLDER_TYPE_PLAYLISTS;
      case MEDIA_TYPE_FOLDER_YEARS:
        return FOLDER_TYPE_YEARS;
      case MEDIA_TYPE_FOLDER_AUDIO_BOOKS:
      case MEDIA_TYPE_FOLDER_MIXED:
      case MEDIA_TYPE_FOLDER_TV_CHANNELS:
      case MEDIA_TYPE_FOLDER_TV_SERIES:
      case MEDIA_TYPE_FOLDER_TV_SHOWS:
      case MEDIA_TYPE_FOLDER_PODCASTS:
      case MEDIA_TYPE_MIXED:
      default:
        return FOLDER_TYPE_MIXED;
    }
  }

  @SuppressWarnings("deprecation") // Converting deprecated field.
  private static @MediaType int getMediaTypeFromFolderType(@FolderType int folderType) {
    switch (folderType) {
      case FOLDER_TYPE_ALBUMS:
        return MEDIA_TYPE_FOLDER_ALBUMS;
      case FOLDER_TYPE_ARTISTS:
        return MEDIA_TYPE_FOLDER_ARTISTS;
      case FOLDER_TYPE_GENRES:
        return MEDIA_TYPE_FOLDER_GENRES;
      case FOLDER_TYPE_PLAYLISTS:
        return MEDIA_TYPE_FOLDER_PLAYLISTS;
      case FOLDER_TYPE_TITLES:
        return MEDIA_TYPE_MIXED;
      case FOLDER_TYPE_YEARS:
        return MEDIA_TYPE_FOLDER_YEARS;
      case FOLDER_TYPE_MIXED:
      case FOLDER_TYPE_NONE:
      default:
        return MEDIA_TYPE_FOLDER_MIXED;
    }
  }
}
