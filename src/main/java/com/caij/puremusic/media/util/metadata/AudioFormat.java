package com.caij.puremusic.media.util.metadata;

public class AudioFormat {

    //---------------------------------------------------------
    // Constants
    //--------------------
    /** Invalid audio data format */
    public static final int ENCODING_INVALID = 0;
    /** Default audio data format */
    public static final int ENCODING_DEFAULT = 1;

    // These values must be kept in sync with core/jni/android_media_AudioFormat.h
    // Also sync av/services/audiopolicy/managerdefault/ConfigParsingUtils.h
    /** Audio data format: PCM 16 bit per sample. Guaranteed to be supported by devices. */
    public static final int ENCODING_PCM_16BIT = 2;
    /** Audio data format: PCM 8 bit per sample. Not guaranteed to be supported by devices. */
    public static final int ENCODING_PCM_8BIT = 3;
    /** Audio data format: single-precision floating-point per sample */
    public static final int ENCODING_PCM_FLOAT = 4;
    /** Audio data format: AC-3 compressed, also known as Dolby Digital */
    public static final int ENCODING_AC3 = 5;
    /** Audio data format: E-AC-3 compressed, also known as Dolby Digital Plus or DD+ */
    public static final int ENCODING_E_AC3 = 6;
    /** Audio data format: DTS compressed */
    public static final int ENCODING_DTS = 7;
    /** Audio data format: DTS HD compressed */
    public static final int ENCODING_DTS_HD = 8;
    /** Audio data format: MP3 compressed */
    public static final int ENCODING_MP3 = 9;
    /** Audio data format: AAC LC compressed */
    public static final int ENCODING_AAC_LC = 10;
    /** Audio data format: AAC HE V1 compressed */
    public static final int ENCODING_AAC_HE_V1 = 11;
    /** Audio data format: AAC HE V2 compressed */
    public static final int ENCODING_AAC_HE_V2 = 12;

    /** Audio data format: compressed audio wrapped in PCM for HDMI
     * or S/PDIF passthrough.
     * For devices whose SDK version is less than {@link android.os.Build.VERSION_CODES#S}, the
     * channel mask of IEC61937 track must be {@link #CHANNEL_OUT_STEREO}.
     * Data should be written to the stream in a short[] array.
     * If the data is written in a byte[] array then there may be endian problems
     * on some platforms when converting to short internally.
     */
    public static final int ENCODING_IEC61937 = 13;
    /** Audio data format: DOLBY TRUEHD compressed
     **/
    public static final int ENCODING_DOLBY_TRUEHD = 14;
    /** Audio data format: AAC ELD compressed */
    public static final int ENCODING_AAC_ELD = 15;
    /** Audio data format: AAC xHE compressed */
    public static final int ENCODING_AAC_XHE = 16;
    /** Audio data format: AC-4 sync frame transport format */
    public static final int ENCODING_AC4 = 17;
    /** Audio data format: E-AC-3-JOC compressed
     * E-AC-3-JOC streams can be decoded by downstream devices supporting {@link #ENCODING_E_AC3}.
     * Use {@link #ENCODING_E_AC3} as the AudioTrack encoding when the downstream device
     * supports {@link #ENCODING_E_AC3} but not {@link #ENCODING_E_AC3_JOC}.
     **/
    public static final int ENCODING_E_AC3_JOC = 18;
    /** Audio data format: Dolby MAT (Metadata-enhanced Audio Transmission)
     * Dolby MAT bitstreams are used to transmit Dolby TrueHD, channel-based PCM, or PCM with
     * metadata (object audio) over HDMI (e.g. Dolby Atmos content).
     **/
    public static final int ENCODING_DOLBY_MAT = 19;
    /** Audio data format: OPUS compressed. */
    public static final int ENCODING_OPUS = 20;

    /** @hide
     * We do not permit legacy short array reads or writes for encodings
     * introduced after this threshold.
     */
    public static final int ENCODING_LEGACY_SHORT_ARRAY_THRESHOLD = ENCODING_OPUS;

    /** Audio data format: PCM 24 bit per sample packed as 3 bytes.
     *
     * The bytes are in little-endian order, so the least significant byte
     * comes first in the byte array.
     *
     * Not guaranteed to be supported by devices, may be emulated if not supported. */
    public static final int ENCODING_PCM_24BIT_PACKED = 21;
    /** Audio data format: PCM 32 bit per sample.
     * Not guaranteed to be supported by devices, may be emulated if not supported. */
    public static final int ENCODING_PCM_32BIT = 22;

    /** Audio data format: MPEG-H baseline profile, level 3 */
    public static final int ENCODING_MPEGH_BL_L3 = 23;
    /** Audio data format: MPEG-H baseline profile, level 4 */
    public static final int ENCODING_MPEGH_BL_L4 = 24;
    /** Audio data format: MPEG-H low complexity profile, level 3 */
    public static final int ENCODING_MPEGH_LC_L3 = 25;
    /** Audio data format: MPEG-H low complexity profile, level 4 */
    public static final int ENCODING_MPEGH_LC_L4 = 26;
    /** Audio data format: DTS UHD Profile-1 compressed (aka DTS:X Profile 1)
     * Has the same meaning and value as ENCODING_DTS_UHD_P1.
     * @deprecated Use {@link #ENCODING_DTS_UHD_P1} instead. */
    @Deprecated public static final int ENCODING_DTS_UHD = 27;
    /** Audio data format: DRA compressed */
    public static final int ENCODING_DRA = 28;
    /** Audio data format: DTS HD Master Audio compressed
     * DTS HD Master Audio stream is variable bit rate and contains lossless audio.
     * Use {@link #ENCODING_DTS_HD_MA} for lossless audio content (DTS-HD MA Lossless)
     * and use {@link #ENCODING_DTS_HD} for other DTS bitstreams with extension substream
     * (DTS 8Ch Discrete, DTS Hi Res, DTS Express). */
    public static final int ENCODING_DTS_HD_MA = 29;
    /** Audio data format: DTS UHD Profile-1 compressed (aka DTS:X Profile 1)
     * Has the same meaning and value as the deprecated {@link #ENCODING_DTS_UHD}.*/
    public static final int ENCODING_DTS_UHD_P1 = 27;
    /** Audio data format: DTS UHD Profile-2 compressed
     * DTS-UHD Profile-2 supports delivery of Channel-Based Audio, Object-Based Audio
     * and High Order Ambisonic presentations up to the fourth order.
     * Use {@link #ENCODING_DTS_UHD_P1} to transmit DTS UHD Profile 1 (aka DTS:X Profile 1)
     * bitstream.
     * Use {@link #ENCODING_DTS_UHD_P2} to transmit DTS UHD Profile 2 (aka DTS:X Profile 2)
     * bitstream. */
    public static final int ENCODING_DTS_UHD_P2 = 30;
    /** Audio data format: Direct Stream Digital */
    public static final int ENCODING_DSD = 31;
}
