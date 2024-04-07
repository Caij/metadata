package org.jaudiotagger.tag.images;

import org.jaudiotagger.audio.flac.metadatablock.MetadataBlockDataPicture;
import org.jaudiotagger.tag.TagOptionSingleton;

import java.io.File;
import java.io.IOException;

/**
 * Get appropriate Artwork class
 */
public class ArtworkFactory {


    public static Artwork getNew() {
        //Normal
        return new StandardArtwork();
    }

    /**
     * Create Artwork instance from A Flac Metadata Block
     *
     * @param coverArt
     * @return
     */
    public static Artwork createArtworkFromMetadataBlockDataPicture(MetadataBlockDataPicture coverArt) {
        //Normal
        return StandardArtwork.createArtworkFromMetadataBlockDataPicture(coverArt);
    }

    /**
     * Create Artwork instance from an image file
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static Artwork createArtworkFromFile(File file) throws IOException {
        //Normal
        return StandardArtwork.createArtworkFromFile(file);
    }

    /**
     * Create Artwork instance from an image file
     *
     * @param link
     * @return
     * @throws IOException
     */
    public static Artwork createLinkedArtworkFromURL(String link) throws IOException {
        //Normal
        return StandardArtwork.createLinkedArtworkFromURL(link);
    }
}
