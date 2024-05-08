package org.jaudiotagger.x.ape;

import davaguine.jmac.info.APETag;
import org.jaudiotagger.tag.*;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.images.StandardArtwork;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ApeTag implements Tag {

    APETag apeTag;

    public ApeTag(APETag apeTag) {
        this.apeTag = apeTag;
    }

    public ApeTag() {

    }

    @Override
    public void setField(FieldKey genericKey, String... value) throws KeyNotFoundException, FieldDataInvalidException {

    }

    @Override
    public void addField(FieldKey genericKey, String... value) throws KeyNotFoundException, FieldDataInvalidException {

    }

    @Override
    public void deleteField(FieldKey fieldKey) throws KeyNotFoundException {

    }

    @Override
    public void deleteField(String key) throws KeyNotFoundException {

    }

    @Override
    public List<TagField> getFields(String id) {
        return null;
    }

    @Override
    public List<TagField> getFields(FieldKey id) throws KeyNotFoundException {
        return null;
    }

    @Override
    public Iterator<TagField> getFields() {
        return null;
    }

    @Override
    public String getFirst(String id) {
        try {
            String v1 = apeTag.GetFieldString(id);
            if (v1 == null && id.equals(FieldKey.LYRICS.name())) {
                return apeTag.GetFieldString("UNSYNCEDLYRICS");
            }
            return v1;
        } catch (Exception ignore) {

        }
        return null;
    }

    @Override
    public String getFirst(FieldKey id) throws KeyNotFoundException {
        String name = id.name();
        return getFirst(name);
    }

    @Override
    public List<String> getAll(FieldKey id) throws KeyNotFoundException {
        String value = getFirst(id);
        if (value != null) {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(value);
            return arrayList;
        }
        return null;
    }

    @Override
    public String getValue(FieldKey id, int n) {
        throw new IllegalArgumentException();
    }

    @Override
    public TagField getFirstField(String id) {
        throw new IllegalArgumentException();
    }

    @Override
    public TagField getFirstField(FieldKey id) {
        throw new IllegalArgumentException();
    }

    @Override
    public boolean hasCommonFields() {
        throw new IllegalArgumentException();
    }

    @Override
    public boolean hasField(FieldKey fieldKey) {
        return getFirst(fieldKey) != null;
    }

    @Override
    public boolean hasField(String id) {
        return getFirst(id) != null;
    }

    @Override
    public boolean isEmpty() {
        try {
            return !apeTag.GetHasAPETag() && !apeTag.GetHasID3Tag();
        } catch (IOException e) {
            return true;
        }
    }

    @Override
    public int getFieldCount() {
        throw new IllegalArgumentException();
    }

    @Override
    public int getFieldCountIncludingSubValues() {
        throw new IllegalArgumentException();
    }

    @Override
    public boolean setEncoding(Charset enc) throws FieldDataInvalidException {
        throw new IllegalArgumentException();
    }

    @Override
    public List<Artwork> getArtworkList() {
        throw new IllegalArgumentException();
    }

    @Override
    public Artwork getFirstArtwork() {
        try {
            byte[] art = apeTag.GetFieldBinary(APETag.APE_TAG_FIELD_COVER_ART_FRONT);
            StandardArtwork artwork = new StandardArtwork();
            artwork.setBinaryData(art);
            return artwork;
        } catch (IOException e) {

        }
        return null;
    }

    @Override
    public void deleteArtworkField() throws KeyNotFoundException {
        throw new IllegalArgumentException();
    }

    @Override
    public TagField createField(Artwork artwork) throws FieldDataInvalidException {
        throw new IllegalArgumentException();
    }

    @Override
    public void setField(Artwork artwork) throws FieldDataInvalidException {
        throw new IllegalArgumentException();
    }

    @Override
    public void addField(Artwork artwork) throws FieldDataInvalidException {
        throw new IllegalArgumentException();
    }

    @Override
    public void setField(TagField field) throws FieldDataInvalidException {
        throw new IllegalArgumentException();
    }

    @Override
    public void addField(TagField field) throws FieldDataInvalidException {
        throw new IllegalArgumentException();
    }

    @Override
    public TagField createField(FieldKey genericKey, String... value) throws KeyNotFoundException, FieldDataInvalidException {
        throw new IllegalArgumentException();
    }

    @Override
    public TagField createCompilationField(boolean value) throws KeyNotFoundException, FieldDataInvalidException {
        throw new IllegalArgumentException();
    }
}
