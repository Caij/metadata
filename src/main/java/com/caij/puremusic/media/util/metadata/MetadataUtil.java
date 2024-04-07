package com.caij.puremusic.media.util.metadata;

import com.caij.puremusic.media.util.metadata.data.Format;
import com.caij.puremusic.media.util.metadata.data.MediaMetadata;
import com.caij.puremusic.media.util.metadata.data.Metadata;
import com.caij.puremusic.media.util.metadata.data.id3.BinaryFrame;
import com.caij.puremusic.media.util.metadata.data.vorbis.VorbisComment;

public class MetadataUtil {

    public static MediaMetadata.Builder populateFromMetadata(MediaMetadata.Builder builder, Metadata metadata) {
        for (int i = 0; i < metadata.length(); i++) {
            Metadata.Entry entry = metadata.get(i);
            if (entry instanceof VorbisComment) {
                VorbisComment vorbisComment = (VorbisComment) entry;
                String key = vorbisComment.key;
                String value = vorbisComment.value;
                switch (key) {
                    case "TRACKNUMBER":
                        try {
                            builder.setTrackNumber(Integer.parseInt(value));
                        } catch (NumberFormatException ignored) {
                        }
                        break;
                    case "TRACKTOTAL":
                    case "TOTALTRACKS":
                        try {
                            builder.setTotalTrackCount(Integer.parseInt(value));
                        } catch (NumberFormatException ignored) {
                        }
                        break;
                    case "DATE":
                    case "ORIGINALDATE":
                        try {
                            String[] dates = value.split("-");
                            switch (dates.length) {
                                case 1:
                                    builder.setRecordingYear(Integer.parseInt(dates[0]));
                                    break;
                                case 2:
                                    builder.setRecordingYear(Integer.parseInt(dates[0]));
                                    builder.setRecordingMonth(Integer.parseInt(dates[1]));
                                    break;
                                default:
                                    builder.setRecordingYear(Integer.parseInt(dates[0]));
                                    builder.setRecordingMonth(Integer.parseInt(dates[1]));
                                    builder.setRecordingDay(Integer.parseInt(dates[2]));
                                    break;
                            }
                        } catch (NumberFormatException ignored) {
                        }
                        break;
                    case "ORIGINALYEAR":
                        try {
                            builder.setRecordingYear(Integer.parseInt(value));
                        } catch (NumberFormatException ignored) {
                        }
                        break;
                }
            }
            entry.populateMediaMetadata(builder);
        }
        return builder;
    }

    public static MetadataWrapper parse(Format format, MediaMetadata metadata, long duration) {
        Integer bitrate = null;
        Integer sampleRate = null;
        if (format.bitrate != Format.NO_VALUE) {
            bitrate = format.bitrate;
            sampleRate = format.sampleRate;
        }
        return new MetadataWrapper(
                metadata.title != null ? metadata.title.toString() : null,
                metadata.albumTitle != null ? metadata.albumTitle.toString() : null,
                metadata.artist != null ? metadata.artist.toString() : null,
                metadata.albumArtist != null ? metadata.albumArtist.toString() : null,
                metadata.composer != null ? metadata.composer.toString() : null,
                metadata.recordingYear,
                metadata.trackNumber,
                duration,
                metadata.artworkData,
                getLyrics(format.metadata),
                bitrate,
                sampleRate);
    }

    private static String getLyrics(Metadata metadata) {
        if (metadata == null) return null;
        for (int i = 0; i < metadata.length(); i++) {
            Metadata.Entry entry = metadata.get(i);
            if (entry instanceof VorbisComment && "lyrics".equalsIgnoreCase(((VorbisComment) entry).key)) {
                return ((VorbisComment) entry).value;
            } else if (entry instanceof BinaryFrame && "USLT".equalsIgnoreCase(((BinaryFrame) entry).id)) {
                byte[] data = ((BinaryFrame) entry).data;
                return UsltFrameDecoder.decode(new ParsableByteArray(data), data.length);
            }
        }
        return null;
    }
}
