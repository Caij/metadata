package org.jaudiotagger.x.ape;

import davaguine.jmac.info.APEInfo;
import davaguine.jmac.info.APETag;
import org.jaudiotagger.audio.AudioHeader;

public class ApeAudioHeader implements AudioHeader {

    private final APEInfo apeInfo;

    public ApeAudioHeader(APEInfo apeInfo) {
        this.apeInfo = apeInfo;
    }

    @Override
    public String getEncodingType() {
        throw new IllegalArgumentException();
    }

    @Override
    public Integer getByteRate() {
        return apeInfo.getApeInfoAverageBitrate();
    }

    @Override
    public String getBitRate() {
        return String.valueOf(apeInfo.getApeInfoAverageBitrate());
    }

    @Override
    public long getBitRateAsNumber() {
        return apeInfo.getApeInfoAverageBitrate();
    }

    @Override
    public Long getAudioDataLength() {
        throw new IllegalArgumentException();
    }

    @Override
    public Long getAudioDataStartPosition() {
        return null;
    }

    @Override
    public Long getAudioDataEndPosition() {
        return null;
    }

    @Override
    public String getSampleRate() {
        return String.valueOf(apeInfo.getApeInfoSampleRate());
    }

    @Override
    public int getSampleRateAsNumber() {
        return apeInfo.getApeInfoSampleRate();
    }

    @Override
    public String getFormat() {
        throw new IllegalArgumentException();
    }

    @Override
    public String getChannels() {
        throw new IllegalArgumentException();
    }

    @Override
    public boolean isVariableBitRate() {
        return false;
    }

    @Override
    public int getTrackLength() {
        return apeInfo.getApeInfoLengthMs() / 1000;
    }

    @Override
    public double getPreciseTrackLength() {
        throw new IllegalArgumentException();
    }

    @Override
    public int getBitsPerSample() {
        return apeInfo.getApeInfoBitsPerSample();
    }

    @Override
    public boolean isLossless() {
        return false;
    }

    @Override
    public Long getNoOfSamples() {
        return null;
    }
}
