/*
 * Copyright (C) 2016 The Android Open Source Project
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

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.annotation.ElementType.TYPE_USE;

/**
 * Represents a media format.
 *
 * <p>When building formats, populate all fields whose values are known and relevant to the type of
 * format being constructed. For information about different types of format, see ExoPlayer's <a
 * href="https://developer.android.com/media/media3/exoplayer/supported-formats">Supported formats
 * page</a>.
 *
 * <h2>Fields commonly relevant to all formats</h2>
 *
 * <ul>
 *   <li>{@link #id}
 *   <li>{@link #label}
 *   <li>{@link #language}
 *   <li>{@link #selectionFlags}
 *   <li>{@link #roleFlags}
 *   <li>{@link #averageBitrate}
 *   <li>{@link #peakBitrate}
 *   <li>{@link #codecs}
 *   <li>{@link #metadata}
 * </ul>
 *
 * <h2 id="container-formats">Fields relevant to container formats</h2>
 *
 * <ul>
 *   <li>{@link #containerMimeType}
 *   <li>If the container only contains a single media track, <a href="#sample-formats">fields
 *       relevant to sample formats</a> can are also be relevant and can be set to describe the
 *       sample format of that track.
 *   <li>If the container only contains one track of a given type (possibly alongside tracks of
 *       other types), then fields relevant to that track type can be set to describe the properties
 *       of the track. See the sections below for <a href="#video-formats">video</a>, <a
 *       href="#audio-formats">audio</a> and <a href="#text-formats">text</a> formats.
 * </ul>
 *
 * <h2 id="sample-formats">Fields relevant to sample formats</h2>
 *
 * <ul>
 *   <li>{@link #sampleMimeType}
 *   <li>{@link #maxInputSize}
 *   <li>{@link #initializationData}
 *   <li>{@link #subsampleOffsetUs}
 *   <li>Fields relevant to the sample format's track type are also relevant. See the sections below
 *       for <a href="#video-formats">video</a>, <a href="#audio-formats">audio</a> and <a
 *       href="#text-formats">text</a> formats.
 * </ul>
 *
 * <h2 id="video-formats">Fields relevant to video formats</h2>
 *
 * <ul>
 *   <li>{@link #width}
 *   <li>{@link #height}
 *   <li>{@link #frameRate}
 *   <li>{@link #rotationDegrees}
 *   <li>{@link #pixelWidthHeightRatio}
 *   <li>{@link #projectionData}
 *   <li>{@link #stereoMode}
 * </ul>
 *
 * <h2 id="audio-formats">Fields relevant to audio formats</h2>
 *
 * <ul>
 *   <li>{@link #channelCount}
 *   <li>{@link #sampleRate}
 *   <li>{@link #pcmEncoding}
 *   <li>{@link #encoderDelay}
 *   <li>{@link #encoderPadding}
 * </ul>
 *
 * <h2 id="text-formats">Fields relevant to text formats</h2>
 *
 * <ul>
 *   <li>{@link #accessibilityChannel}
 * </ul>
 *
 * <h2 id="image-formats">Fields relevant to image formats</h2>
 *
 * <ul>
 *   <li>{@link #tileCountHorizontal}
 *   <li>{@link #tileCountVertical}
 * </ul>
 */
public final class Format {

  /**
   * Builds {@link Format} instances.
   *
   * <p>Use Format#buildUpon() to obtain a builder representing an existing {@link Format}.
   *
   * <p>When building formats, populate all fields whose values are known and relevant to the type
   * of format being constructed. See the {@link Format} Javadoc for information about which fields
   * should be set for different types of format.
   */
  public static final class Builder {

    private String id;
    private String label;
    private String language;
    private int selectionFlags;
    private int roleFlags;
    private int averageBitrate;
    private int peakBitrate;
    private String codecs;
    private Metadata metadata;

    // Container specific.

    private String containerMimeType;

    // Sample specific.

    private String sampleMimeType;
    private int maxInputSize;
    private List<byte[]> initializationData;
//    private DrmInitData drmInitData;
    private long subsampleOffsetUs;

    // Video specific.

    private int width;
    private int height;
    private float frameRate;
    private int rotationDegrees;
    private float pixelWidthHeightRatio;
    private byte[] projectionData;
    private int stereoMode;
//    private ColorInfo colorInfo;

    // Audio specific.

    private int channelCount;
    private int sampleRate;
    private int pcmEncoding;
    private int encoderDelay;
    private int encoderPadding;

    // Text specific.

    private int accessibilityChannel;
    private @CueReplacementBehavior int cueReplacementBehavior;

    // Image specific

    private int tileCountHorizontal;
    private int tileCountVertical;

    // Provided by the source.

//    private int cryptoType;

    /** Creates a new instance with default values. */
    public Builder() {
      averageBitrate = NO_VALUE;
      peakBitrate = NO_VALUE;
      // Sample specific.
      maxInputSize = NO_VALUE;
      subsampleOffsetUs = OFFSET_SAMPLE_RELATIVE;
      // Video specific.
      width = NO_VALUE;
      height = NO_VALUE;
      frameRate = NO_VALUE;
      pixelWidthHeightRatio = 1.0f;
      stereoMode = NO_VALUE;
      // Audio specific.
      channelCount = NO_VALUE;
      sampleRate = NO_VALUE;
      pcmEncoding = NO_VALUE;
      // Text specific.
      accessibilityChannel = NO_VALUE;
      cueReplacementBehavior = CUE_REPLACEMENT_BEHAVIOR_MERGE;
      // Image specific.
      tileCountHorizontal = NO_VALUE;
      tileCountVertical = NO_VALUE;
      // Provided by the source.
//      cryptoType = Util.CRYPTO_TYPE_NONE;
    }

    /**
     * Creates a new instance to build upon the provided {@link Format}.
     *
     * @param format The {@link Format} to build upon.
     */
    private Builder(Format format) {
      this.id = format.id;
      this.label = format.label;
      this.language = format.language;
      this.selectionFlags = format.selectionFlags;
      this.roleFlags = format.roleFlags;
      this.averageBitrate = format.averageBitrate;
      this.peakBitrate = format.peakBitrate;
      this.codecs = format.codecs;
      this.metadata = format.metadata;
      // Container specific.
      this.containerMimeType = format.containerMimeType;
      // Sample specific.
      this.sampleMimeType = format.sampleMimeType;
      this.maxInputSize = format.maxInputSize;
      this.initializationData = format.initializationData;
//      this.drmInitData = format.drmInitData;
      this.subsampleOffsetUs = format.subsampleOffsetUs;
      // Video specific.
      this.width = format.width;
      this.height = format.height;
      this.frameRate = format.frameRate;
      this.rotationDegrees = format.rotationDegrees;
      this.pixelWidthHeightRatio = format.pixelWidthHeightRatio;
      this.projectionData = format.projectionData;
      this.stereoMode = format.stereoMode;
//      this.colorInfo = format.colorInfo;
      // Audio specific.
      this.channelCount = format.channelCount;
      this.sampleRate = format.sampleRate;
      this.pcmEncoding = format.pcmEncoding;
      this.encoderDelay = format.encoderDelay;
      this.encoderPadding = format.encoderPadding;
      // Text specific.
      this.accessibilityChannel = format.accessibilityChannel;
      this.cueReplacementBehavior = format.cueReplacementBehavior;
      // Image specific.
      this.tileCountHorizontal = format.tileCountHorizontal;
      this.tileCountVertical = format.tileCountVertical;
      // Provided by the source.
//      this.cryptoType = format.cryptoType;
    }

    /**
     * Sets {@link Format#id}. The default value is {@code null}.
     *
     * @param id The {@link Format#id}.
     * @return The builder.
     */
    public Builder setId(String id) {
      this.id = id;
      return this;
    }

    /**
     * Sets {@link Format#id} to {@link Integer#toString() Integer.toString(id)}. The default value
     * is {@code null}.
     *
     * @param id The {@link Format#id}.
     * @return The builder.
     */
    public Builder setId(int id) {
      this.id = Integer.toString(id);
      return this;
    }

    /**
     * Sets {@link Format#label}. The default value is {@code null}.
     *
     * @param label The {@link Format#label}.
     * @return The builder.
     */
    public Builder setLabel(String label) {
      this.label = label;
      return this;
    }

    /**
     * Sets {@link Format#language}. The default value is {@code null}.
     *
     * @param language The {@link Format#language}.
     * @return The builder.
     */
    public Builder setLanguage(String language) {
      this.language = language;
      return this;
    }

    /**
     * Sets {@link Format#selectionFlags}. The default value is 0.
     *
     * @param selectionFlags The {@link Format#selectionFlags}.
     * @return The builder.
     */
    public Builder setSelectionFlags(int selectionFlags) {
      this.selectionFlags = selectionFlags;
      return this;
    }

    /**
     * Sets {@link Format#roleFlags}. The default value is 0.
     *
     * @param roleFlags The {@link Format#roleFlags}.
     * @return The builder.
     */
    public Builder setRoleFlags(int roleFlags) {
      this.roleFlags = roleFlags;
      return this;
    }

    /**
     * Sets {@link Format#averageBitrate}. The default value is {@link #NO_VALUE}.
     *
     * @param averageBitrate The {@link Format#averageBitrate}.
     * @return The builder.
     */
    public Builder setAverageBitrate(int averageBitrate) {
      this.averageBitrate = averageBitrate;
      return this;
    }

    /**
     * Sets {@link Format#peakBitrate}. The default value is {@link #NO_VALUE}.
     *
     * @param peakBitrate The {@link Format#peakBitrate}.
     * @return The builder.
     */
    public Builder setPeakBitrate(int peakBitrate) {
      this.peakBitrate = peakBitrate;
      return this;
    }

    /**
     * Sets {@link Format#codecs}. The default value is {@code null}.
     *
     * @param codecs The {@link Format#codecs}.
     * @return The builder.
     */
    public Builder setCodecs(String codecs) {
      this.codecs = codecs;
      return this;
    }

    /**
     * Sets {@link Format#metadata}. The default value is {@code null}.
     *
     * @param metadata The {@link Format#metadata}.
     * @return The builder.
     */
    public Builder setMetadata(Metadata metadata) {
      this.metadata = metadata;
      return this;
    }

    // Container specific.

    /**
     * Sets {@link Format#containerMimeType}. The default value is {@code null}.
     *
     * @param containerMimeType The {@link Format#containerMimeType}.
     * @return The builder.
     */
    public Builder setContainerMimeType(String containerMimeType) {
      this.containerMimeType = MimeTypes.normalizeMimeType(containerMimeType);
      return this;
    }

    // Sample specific.

    /**
     * Sets {@link Format#sampleMimeType}. The default value is {@code null}.
     *
     * @param sampleMimeType {@link Format#sampleMimeType}.
     * @return The builder.
     */
    public Builder setSampleMimeType(String sampleMimeType) {
      this.sampleMimeType = MimeTypes.normalizeMimeType(sampleMimeType);
      return this;
    }

    /**
     * Sets {@link Format#maxInputSize}. The default value is {@link #NO_VALUE}.
     *
     * @param maxInputSize The {@link Format#maxInputSize}.
     * @return The builder.
     */
    public Builder setMaxInputSize(int maxInputSize) {
      this.maxInputSize = maxInputSize;
      return this;
    }

    /**
     * Sets {@link Format#initializationData}. The default value is {@code null}.
     *
     * @param initializationData The {@link Format#initializationData}.
     * @return The builder.
     */
    public Builder setInitializationData(List<byte[]> initializationData) {
      this.initializationData = initializationData;
      return this;
    }

    /**
     * Sets {@link Format#drmInitData}. The default value is {@code null}.
     *
     * @param drmInitData The {@link Format#drmInitData}.
     * @return The builder.
     */
//    public Builder setDrmInitData(DrmInitData drmInitData) {
//      this.drmInitData = drmInitData;
//      return this;
//    }

    /**
     * Sets {@link Format#subsampleOffsetUs}. The default value is {@link #OFFSET_SAMPLE_RELATIVE}.
     *
     * @param subsampleOffsetUs The {@link Format#subsampleOffsetUs}.
     * @return The builder.
     */
    public Builder setSubsampleOffsetUs(long subsampleOffsetUs) {
      this.subsampleOffsetUs = subsampleOffsetUs;
      return this;
    }

    // Video specific.

    /**
     * Sets {@link Format#width}. The default value is {@link #NO_VALUE}.
     *
     * @param width The {@link Format#width}.
     * @return The builder.
     */
    public Builder setWidth(int width) {
      this.width = width;
      return this;
    }

    /**
     * Sets {@link Format#height}. The default value is {@link #NO_VALUE}.
     *
     * @param height The {@link Format#height}.
     * @return The builder.
     */
    public Builder setHeight(int height) {
      this.height = height;
      return this;
    }

    /**
     * Sets {@link Format#frameRate}. The default value is {@link #NO_VALUE}.
     *
     * @param frameRate The {@link Format#frameRate}.
     * @return The builder.
     */
    public Builder setFrameRate(float frameRate) {
      this.frameRate = frameRate;
      return this;
    }

    /**
     * Sets {@link Format#rotationDegrees}. The default value is 0.
     *
     * @param rotationDegrees The {@link Format#rotationDegrees}.
     * @return The builder.
     */
    public Builder setRotationDegrees(int rotationDegrees) {
      this.rotationDegrees = rotationDegrees;
      return this;
    }

    /**
     * Sets {@link Format#pixelWidthHeightRatio}. The default value is 1.0f.
     *
     * @param pixelWidthHeightRatio The {@link Format#pixelWidthHeightRatio}.
     * @return The builder.
     */
    public Builder setPixelWidthHeightRatio(float pixelWidthHeightRatio) {
      this.pixelWidthHeightRatio = pixelWidthHeightRatio;
      return this;
    }

    /**
     * Sets {@link Format#projectionData}. The default value is {@code null}.
     *
     * @param projectionData The {@link Format#projectionData}.
     * @return The builder.
     */
    public Builder setProjectionData(byte[] projectionData) {
      this.projectionData = projectionData;
      return this;
    }

    /**
     * Sets {@link Format#stereoMode}. The default value is {@link #NO_VALUE}.
     *
     * @param stereoMode The {@link Format#stereoMode}.
     * @return The builder.
     */
    public Builder setStereoMode(int stereoMode) {
      this.stereoMode = stereoMode;
      return this;
    }

    /**
     * Sets {@link Format#colorInfo}. The default value is {@code null}.
     *
     * @param colorInfo The {@link Format#colorInfo}.
     * @return The builder.
     */
//    public Builder setColorInfo(ColorInfo colorInfo) {
//      this.colorInfo = colorInfo;
//      return this;
//    }

    // Audio specific.

    /**
     * Sets {@link Format#channelCount}. The default value is {@link #NO_VALUE}.
     *
     * @param channelCount The {@link Format#channelCount}.
     * @return The builder.
     */
    public Builder setChannelCount(int channelCount) {
      this.channelCount = channelCount;
      return this;
    }

    /**
     * Sets {@link Format#sampleRate}. The default value is {@link #NO_VALUE}.
     *
     * @param sampleRate The {@link Format#sampleRate}.
     * @return The builder.
     */
    public Builder setSampleRate(int sampleRate) {
      this.sampleRate = sampleRate;
      return this;
    }

    /**
     * Sets {@link Format#pcmEncoding}. The default value is {@link #NO_VALUE}.
     *
     * @param pcmEncoding The {@link Format#pcmEncoding}.
     * @return The builder.
     */
    public Builder setPcmEncoding(int pcmEncoding) {
      this.pcmEncoding = pcmEncoding;
      return this;
    }

    /**
     * Sets {@link Format#encoderDelay}. The default value is 0.
     *
     * @param encoderDelay The {@link Format#encoderDelay}.
     * @return The builder.
     */
    public Builder setEncoderDelay(int encoderDelay) {
      this.encoderDelay = encoderDelay;
      return this;
    }

    /**
     * Sets {@link Format#encoderPadding}. The default value is 0.
     *
     * @param encoderPadding The {@link Format#encoderPadding}.
     * @return The builder.
     */
    public Builder setEncoderPadding(int encoderPadding) {
      this.encoderPadding = encoderPadding;
      return this;
    }

    // Text specific.

    /**
     * Sets {@link Format#accessibilityChannel}. The default value is {@link #NO_VALUE}.
     *
     * @param accessibilityChannel The {@link Format#accessibilityChannel}.
     * @return The builder.
     */
    public Builder setAccessibilityChannel(int accessibilityChannel) {
      this.accessibilityChannel = accessibilityChannel;
      return this;
    }

    /**
     * Sets {@link Format#cueReplacementBehavior}. The default value is {@link
     * #CUE_REPLACEMENT_BEHAVIOR_MERGE}.
     *
     * @param cueReplacementBehavior The {@link CueReplacementBehavior}.
     * @return The builder.
     */
    public Builder setCueReplacementBehavior(@CueReplacementBehavior int cueReplacementBehavior) {
      this.cueReplacementBehavior = cueReplacementBehavior;
      return this;
    }

    // Image specific.

    /**
     * Sets {@link Format#tileCountHorizontal}. The default value is {@link #NO_VALUE}.
     *
     * @param tileCountHorizontal The {@link Format#accessibilityChannel}.
     * @return The builder.
     */
    public Builder setTileCountHorizontal(int tileCountHorizontal) {
      this.tileCountHorizontal = tileCountHorizontal;
      return this;
    }

    /**
     * Sets {@link Format#tileCountVertical}. The default value is {@link #NO_VALUE}.
     *
     * @param tileCountVertical The {@link Format#accessibilityChannel}.
     * @return The builder.
     */
    public Builder setTileCountVertical(int tileCountVertical) {
      this.tileCountVertical = tileCountVertical;
      return this;
    }

    // Provided by source.

    /**
     * @return The builder.
     */
//    public Builder setCryptoType(int cryptoType) {
//      this.cryptoType = cryptoType;
//      return this;
//    }

    // Build.

    public Format build() {
      return new Format(/* builder= */ this);
    }
  }

  /**
   */
  @Documented
  @Retention(RetentionPolicy.SOURCE)
  @Target(TYPE_USE)
  public @interface CueReplacementBehavior {}

  /**
   * Subsequent cues should be merged with any previous cues that should still be shown on screen.
   *
   * duration.
   */
  public static final int CUE_REPLACEMENT_BEHAVIOR_MERGE = 1;

  /**
   * Subsequent cues should replace all previous cues.
   *
   * duration (but the duration may also be set to a 'real' value).
   */
  public static final int CUE_REPLACEMENT_BEHAVIOR_REPLACE = 2;

  /** A value for various fields to indicate that the field's value is unknown or not applicable. */
  public static final int NO_VALUE = -1;

  /**
   * A value for {@link #subsampleOffsetUs} to indicate that subsample timestamps are relative to
   * the timestamps of their parent samples.
   */
  public static final long OFFSET_SAMPLE_RELATIVE = Long.MAX_VALUE;

  private static final Format DEFAULT = new Builder().build();

  /** An identifier for the format, or null if unknown or not applicable. */
  public final String id;

  /** The human readable label, or null if unknown or not applicable. */
  public final String label;

  /** The language as an IETF BCP 47 conformant tag, or null if unknown or not applicable. */
  public final String language;

  /** Track selection flags. */
  public final int selectionFlags;

  /** Track role flags. */
  public final int roleFlags;

  /**
   * The average bitrate in bits per second, or {@link #NO_VALUE} if unknown or not applicable. The
   * way in which this field is populated depends on the type of media to which the format
   * corresponds:
   *
   * <ul>
   *   <li>DASH representations: Always {@link Format#NO_VALUE}.
   *   <li>HLS variants: The {@code AVERAGE-BANDWIDTH} attribute defined on the corresponding {@code
   *       EXT-X-STREAM-INF} tag in the multivariant playlist, or {@link Format#NO_VALUE} if not
   *       present.
   *   <li>SmoothStreaming track elements: The {@code Bitrate} attribute defined on the
   *       corresponding {@code TrackElement} in the manifest, or {@link Format#NO_VALUE} if not
   *       present.
   *   <li>Progressive container formats: Often {@link Format#NO_VALUE}, but may be populated with
   *       the average bitrate of the container if known.
   *   <li>Sample formats: Often {@link Format#NO_VALUE}, but may be populated with the average
   *       bitrate of the stream of samples with type {@link #sampleMimeType} if known. Note that if
   *       {@link #sampleMimeType} is a compressed format (e.g., {@link MimeTypes#AUDIO_AAC}), then
   *       this bitrate is for the stream of still compressed samples.
   * </ul>
   */
  public final int averageBitrate;

  /**
   * The peak bitrate in bits per second, or {@link #NO_VALUE} if unknown or not applicable. The way
   * in which this field is populated depends on the type of media to which the format corresponds:
   *
   * <ul>
   *   <li>DASH representations: The {@code @bandwidth} attribute of the corresponding {@code
   *       Representation} element in the manifest.
   *   <li>HLS variants: The {@code BANDWIDTH} attribute defined on the corresponding {@code
   *       EXT-X-STREAM-INF} tag.
   *   <li>SmoothStreaming track elements: Always {@link Format#NO_VALUE}.
   *   <li>Progressive container formats: Often {@link Format#NO_VALUE}, but may be populated with
   *       the peak bitrate of the container if known.
   *   <li>Sample formats: Often {@link Format#NO_VALUE}, but may be populated with the peak bitrate
   *       of the stream of samples with type {@link #sampleMimeType} if known. Note that if {@link
   *       #sampleMimeType} is a compressed format (e.g., {@link MimeTypes#AUDIO_AAC}), then this
   *       bitrate is for the stream of still compressed samples.
   * </ul>
   */
  public final int peakBitrate;

  /**
   * The bitrate in bits per second. This is the peak bitrate if known, or else the average bitrate
   * if known, or else {@link Format#NO_VALUE}. Equivalent to: {@code peakBitrate != NO_VALUE ?
   * peakBitrate : averageBitrate}.
   */
  public final int bitrate;

  /** Codecs of the format as described in RFC 6381, or null if unknown or not applicable. */
  public final String codecs;

  /** Metadata, or null if unknown or not applicable. */
  public final Metadata metadata;

  // Container specific.

  /** The MIME type of the container, or null if unknown or not applicable. */
  public final String containerMimeType;

  // Sample specific.

  /** The sample MIME type, or null if unknown or not applicable. */
  public final String sampleMimeType;

  /**
   * The maximum size of a buffer of data (typically one sample), or {@link #NO_VALUE} if unknown or
   * not applicable.
   */
  public final int maxInputSize;

  /**
   * Initialization data that must be provided to the decoder. Will not be null, but may be empty if
   * initialization data is not required.
   */
  public final List<byte[]> initializationData;

  /** DRM initialization data if the stream is protected, or null otherwise. */
//  public final DrmInitData drmInitData;

  /**
   * For samples that contain subsamples, this is an offset that should be added to subsample
   * timestamps. A value of {@link #OFFSET_SAMPLE_RELATIVE} indicates that subsample timestamps are
   * relative to the timestamps of their parent samples.
   */
  public final long subsampleOffsetUs;

  // Video specific.

  /** The width of the video in pixels, or {@link #NO_VALUE} if unknown or not applicable. */
  public final int width;

  /** The height of the video in pixels, or {@link #NO_VALUE} if unknown or not applicable. */
  public final int height;

  /** The frame rate in frames per second, or {@link #NO_VALUE} if unknown or not applicable. */
  public final float frameRate;

  /**
   * The clockwise rotation that should be applied to the video for it to be rendered in the correct
   * orientation, or 0 if unknown or not applicable. Only 0, 90, 180 and 270 are supported.
   */
  public final int rotationDegrees;

  /** The width to height ratio of pixels in the video, or 1.0 if unknown or not applicable. */
  public final float pixelWidthHeightRatio;

  /** The projection data for 360/VR video, or null if not applicable. */
  public final byte[] projectionData;


  public final int stereoMode;

  /** The color metadata associated with the video, or null if not applicable. */
//  public final ColorInfo colorInfo;

  // Audio specific.

  /** The number of audio channels, or {@link #NO_VALUE} if unknown or not applicable. */
  public final int channelCount;

  /** The audio sampling rate in Hz, or {@link #NO_VALUE} if unknown or not applicable. */
  public final int sampleRate;

  public final int pcmEncoding;

  /**
   * The number of frames to trim from the start of the decoded audio stream, or 0 if not
   * applicable.
   */
  public final int encoderDelay;

  /**
   * The number of frames to trim from the end of the decoded audio stream, or 0 if not applicable.
   */
  public final int encoderPadding;

  // Text specific.

  /** The Accessibility channel, or {@link #NO_VALUE} if not known or applicable. */
  public final int accessibilityChannel;


  public final @CueReplacementBehavior int cueReplacementBehavior;

  // Image specific.

  /**
   * The number of horizontal tiles in an image, or {@link #NO_VALUE} if not known or applicable.
   */
  public final int tileCountHorizontal;

  /** The number of vertical tiles in an image, or {@link #NO_VALUE} if not known or applicable. */
  public final int tileCountVertical;

  // Provided by source.

//  public final int cryptoType;

  // Lazily initialized hashcode.
  private int hashCode;

  private Format(Builder builder) {
    id = builder.id;
    label = builder.label;
    language = Util.normalizeLanguageCode(builder.language);
    selectionFlags = builder.selectionFlags;
    roleFlags = builder.roleFlags;
    averageBitrate = builder.averageBitrate;
    peakBitrate = builder.peakBitrate;
    bitrate = peakBitrate != NO_VALUE ? peakBitrate : averageBitrate;
    codecs = builder.codecs;
    metadata = builder.metadata;
    // Container specific.
    containerMimeType = builder.containerMimeType;
    // Sample specific.
    sampleMimeType = builder.sampleMimeType;
    maxInputSize = builder.maxInputSize;
    initializationData =
        builder.initializationData == null ? Collections.emptyList() : builder.initializationData;
//    drmInitData = builder.drmInitData;
    subsampleOffsetUs = builder.subsampleOffsetUs;
    // Video specific.
    width = builder.width;
    height = builder.height;
    frameRate = builder.frameRate;
    rotationDegrees = builder.rotationDegrees == NO_VALUE ? 0 : builder.rotationDegrees;
    pixelWidthHeightRatio =
        builder.pixelWidthHeightRatio == NO_VALUE ? 1 : builder.pixelWidthHeightRatio;
    projectionData = builder.projectionData;
    stereoMode = builder.stereoMode;
//    colorInfo = builder.colorInfo;
    // Audio specific.
    channelCount = builder.channelCount;
    sampleRate = builder.sampleRate;
    pcmEncoding = builder.pcmEncoding;
    encoderDelay = builder.encoderDelay == NO_VALUE ? 0 : builder.encoderDelay;
    encoderPadding = builder.encoderPadding == NO_VALUE ? 0 : builder.encoderPadding;
    // Text specific.
    accessibilityChannel = builder.accessibilityChannel;
    cueReplacementBehavior = builder.cueReplacementBehavior;
    // Image specific.
    tileCountHorizontal = builder.tileCountHorizontal;
    tileCountVertical = builder.tileCountVertical;
    // Provided by source.
//    if (builder.cryptoType == Util.CRYPTO_TYPE_NONE && drmInitData != null) {
//      // Encrypted content cannot use CRYPTO_TYPE_NONE.
//      cryptoType = Util.CRYPTO_TYPE_UNSUPPORTED;
//    } else {
//      cryptoType = builder.cryptoType;
//    }
  }

  /** Returns a {@link Builder} initialized with the values of this instance. */
  public Builder buildUpon() {
    return new Builder(this);
  }

  @SuppressWarnings("ReferenceEquality")
  public Format withManifestFormatInfo(Format manifestFormat) {
    if (this == manifestFormat) {
      // No need to copy from ourselves.
      return this;
    }

    int trackType = MimeTypes.getTrackType(sampleMimeType);

    // Use manifest value only.
    String id = manifestFormat.id;
    int tileCountHorizontal = manifestFormat.tileCountHorizontal;
    int tileCountVertical = manifestFormat.tileCountVertical;

    // Prefer manifest values, but fill in from sample format if missing.
    String label = manifestFormat.label != null ? manifestFormat.label : this.label;
    String language = this.language;
    if ((trackType == Util.TRACK_TYPE_TEXT || trackType == Util.TRACK_TYPE_AUDIO)
        && manifestFormat.language != null) {
      language = manifestFormat.language;
    }

    // Prefer sample format values, but fill in from manifest if missing.
    int averageBitrate =
        this.averageBitrate == NO_VALUE ? manifestFormat.averageBitrate : this.averageBitrate;
    int peakBitrate = this.peakBitrate == NO_VALUE ? manifestFormat.peakBitrate : this.peakBitrate;
    String codecs = this.codecs;
    if (codecs == null) {
      // The manifest format may be muxed, so filter only codecs of this format's type. If we still
      // have more than one codec then we're unable to uniquely identify which codec to fill in.
      String codecsOfType = Util.getCodecsOfType(manifestFormat.codecs, trackType);
      if (Util.splitCodecs(codecsOfType).length == 1) {
        codecs = codecsOfType;
      }
    }

    Metadata metadata =
        this.metadata == null
            ? manifestFormat.metadata
            : this.metadata.copyWithAppendedEntriesFrom(manifestFormat.metadata);

    float frameRate = this.frameRate;
    if (frameRate == NO_VALUE && trackType == Util.TRACK_TYPE_VIDEO) {
      frameRate = manifestFormat.frameRate;
    }

    // Merge manifest and sample format values.
    int selectionFlags = this.selectionFlags | manifestFormat.selectionFlags;
    int roleFlags = this.roleFlags | manifestFormat.roleFlags;
//    @Nullable
//    DrmInitData drmInitData =
//        DrmInitData.createSessionCreationData(manifestFormat.drmInitData, this.drmInitData);

    return buildUpon()
        .setId(id)
        .setLabel(label)
        .setLanguage(language)
        .setSelectionFlags(selectionFlags)
        .setRoleFlags(roleFlags)
        .setAverageBitrate(averageBitrate)
        .setPeakBitrate(peakBitrate)
        .setCodecs(codecs)
        .setMetadata(metadata)
//        .setDrmInitData(drmInitData)
        .setFrameRate(frameRate)
        .setTileCountHorizontal(tileCountHorizontal)
        .setTileCountVertical(tileCountVertical)
        .build();
  }

//  public Format copyWithCryptoType(int cryptoType) {
//    return buildUpon().setCryptoType(cryptoType).build();
//  }

  /**
   * Returns the number of pixels if this is a video format whose {@link #width} and {@link #height}
   * are known, or {@link #NO_VALUE} otherwise
   */
  public int getPixelCount() {
    return width == NO_VALUE || height == NO_VALUE ? NO_VALUE : (width * height);
  }

  @Override
  public String toString() {
    return "Format("
        + id
        + ", "
        + label
        + ", "
        + containerMimeType
        + ", "
        + sampleMimeType
        + ", "
        + codecs
        + ", "
        + bitrate
        + ", "
        + language
        + ", ["
        + width
        + ", "
        + height
        + ", "
        + frameRate
        + "]"
        + ", ["
        + channelCount
        + ", "
        + sampleRate
        + "])";
  }

  @Override
  public int hashCode() {
    if (hashCode == 0) {
      // Some fields for which hashing is expensive are deliberately omitted.
      int result = 17;
      result = 31 * result + (id == null ? 0 : id.hashCode());
      result = 31 * result + (label != null ? label.hashCode() : 0);
      result = 31 * result + (language == null ? 0 : language.hashCode());
      result = 31 * result + selectionFlags;
      result = 31 * result + roleFlags;
      result = 31 * result + averageBitrate;
      result = 31 * result + peakBitrate;
      result = 31 * result + (codecs == null ? 0 : codecs.hashCode());
      result = 31 * result + (metadata == null ? 0 : metadata.hashCode());
      // Container specific.
      result = 31 * result + (containerMimeType == null ? 0 : containerMimeType.hashCode());
      // Sample specific.
      result = 31 * result + (sampleMimeType == null ? 0 : sampleMimeType.hashCode());
      result = 31 * result + maxInputSize;
      // [Omitted] initializationData.
      // [Omitted] drmInitData.
      result = 31 * result + (int) subsampleOffsetUs;
      // Video specific.
      result = 31 * result + width;
      result = 31 * result + height;
      result = 31 * result + Float.floatToIntBits(frameRate);
      result = 31 * result + rotationDegrees;
      result = 31 * result + Float.floatToIntBits(pixelWidthHeightRatio);
      // [Omitted] projectionData.
      result = 31 * result + stereoMode;
      // [Omitted] colorInfo.
      // Audio specific.
      result = 31 * result + channelCount;
      result = 31 * result + sampleRate;
      result = 31 * result + pcmEncoding;
      result = 31 * result + encoderDelay;
      result = 31 * result + encoderPadding;
      // Text specific.
      result = 31 * result + accessibilityChannel;
      // Image specific.
      result = 31 * result + tileCountHorizontal;
      result = 31 * result + tileCountVertical;
      // Provided by the source.
      hashCode = result;
    }
    return hashCode;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Format other = (Format) obj;
    if (hashCode != 0 && other.hashCode != 0 && hashCode != other.hashCode) {
      return false;
    }
    // Field equality checks ordered by type, with the cheapest checks first.
    return selectionFlags == other.selectionFlags
        && roleFlags == other.roleFlags
        && averageBitrate == other.averageBitrate
        && peakBitrate == other.peakBitrate
        && maxInputSize == other.maxInputSize
        && subsampleOffsetUs == other.subsampleOffsetUs
        && width == other.width
        && height == other.height
        && rotationDegrees == other.rotationDegrees
        && stereoMode == other.stereoMode
        && channelCount == other.channelCount
        && sampleRate == other.sampleRate
        && pcmEncoding == other.pcmEncoding
        && encoderDelay == other.encoderDelay
        && encoderPadding == other.encoderPadding
        && accessibilityChannel == other.accessibilityChannel
        && tileCountHorizontal == other.tileCountHorizontal
        && tileCountVertical == other.tileCountVertical
        && Float.compare(frameRate, other.frameRate) == 0
        && Float.compare(pixelWidthHeightRatio, other.pixelWidthHeightRatio) == 0
        && Util.areEqual(id, other.id)
        && Util.areEqual(label, other.label)
        && Util.areEqual(codecs, other.codecs)
        && Util.areEqual(containerMimeType, other.containerMimeType)
        && Util.areEqual(sampleMimeType, other.sampleMimeType)
        && Util.areEqual(language, other.language)
        && Arrays.equals(projectionData, other.projectionData)
        && Util.areEqual(metadata, other.metadata)
        && initializationDataEquals(other);
  }

  /**
   * Returns whether the {@link #initializationData}s belonging to this format and {@code other} are
   * equal.
   *
   * @param other The other format whose {@link #initializationData} is being compared.
   * @return Whether the {@link #initializationData}s belonging to this format and {@code other} are
   *     equal.
   */
  public boolean initializationDataEquals(Format other) {
    if (initializationData.size() != other.initializationData.size()) {
      return false;
    }
    for (int i = 0; i < initializationData.size(); i++) {
      if (!Arrays.equals(initializationData.get(i), other.initializationData.get(i))) {
        return false;
      }
    }
    return true;
  }

  // Utility methods

  /** Returns a prettier {@link String} than {@link #toString()}, intended for logging. */
//  public static String toLogString(Format format) {
//    if (format == null) {
//      return "null";
//    }
//    StringBuilder builder = new StringBuilder();
//    builder.append("id=").append(format.id).append(", mimeType=").append(format.sampleMimeType);
//    if (format.containerMimeType != null) {
//      builder.append(", container=").append(format.containerMimeType);
//    }
//    if (format.bitrate != NO_VALUE) {
//      builder.append(", bitrate=").append(format.bitrate);
//    }
//    if (format.codecs != null) {
//      builder.append(", codecs=").append(format.codecs);
//    }
//    if (format.drmInitData != null) {
//      Set<String> schemes = new LinkedHashSet<>();
//      for (int i = 0; i < format.drmInitData.schemeDataCount; i++) {
//        UUID schemeUuid = format.drmInitData.get(i).uuid;
//        if (schemeUuid.equals(C.COMMON_PSSH_UUID)) {
//          schemes.add("cenc");
//        } else if (schemeUuid.equals(C.CLEARKEY_UUID)) {
//          schemes.add("clearkey");
//        } else if (schemeUuid.equals(C.PLAYREADY_UUID)) {
//          schemes.add("playready");
//        } else if (schemeUuid.equals(C.WIDEVINE_UUID)) {
//          schemes.add("widevine");
//        } else if (schemeUuid.equals(C.UUID_NIL)) {
//          schemes.add("universal");
//        } else {
//          schemes.add("unknown (" + schemeUuid + ")");
//        }
//      }
//      builder.append(", drm=[");
//      Joiner.on(',').appendTo(builder, schemes);
//      builder.append(']');
//    }
//    if (format.width != NO_VALUE && format.height != NO_VALUE) {
//      builder.append(", res=").append(format.width).append("x").append(format.height);
//    }
//    if (format.colorInfo != null && format.colorInfo.isValid()) {
//      builder.append(", color=").append(format.colorInfo.toLogString());
//    }
//    if (format.frameRate != NO_VALUE) {
//      builder.append(", fps=").append(format.frameRate);
//    }
//    if (format.channelCount != NO_VALUE) {
//      builder.append(", channels=").append(format.channelCount);
//    }
//    if (format.sampleRate != NO_VALUE) {
//      builder.append(", sample_rate=").append(format.sampleRate);
//    }
//    if (format.language != null) {
//      builder.append(", language=").append(format.language);
//    }
//    if (format.label != null) {
//      builder.append(", label=").append(format.label);
//    }
//    if (format.selectionFlags != 0) {
//      builder.append(", selectionFlags=[");
//      Joiner.on(',').appendTo(builder, Util.getSelectionFlagStrings(format.selectionFlags));
//      builder.append("]");
//    }
//    if (format.roleFlags != 0) {
//      builder.append(", roleFlags=[");
//      Joiner.on(',').appendTo(builder, Util.getRoleFlagStrings(format.roleFlags));
//      builder.append("]");
//    }
//    return builder.toString();
//  }

  // Bundleable implementation.

  private static final String FIELD_ID = Util.intToStringMaxRadix(0);
  private static final String FIELD_LABEL = Util.intToStringMaxRadix(1);
  private static final String FIELD_LANGUAGE = Util.intToStringMaxRadix(2);
  private static final String FIELD_SELECTION_FLAGS = Util.intToStringMaxRadix(3);
  private static final String FIELD_ROLE_FLAGS = Util.intToStringMaxRadix(4);
  private static final String FIELD_AVERAGE_BITRATE = Util.intToStringMaxRadix(5);
  private static final String FIELD_PEAK_BITRATE = Util.intToStringMaxRadix(6);
  private static final String FIELD_CODECS = Util.intToStringMaxRadix(7);
  private static final String FIELD_METADATA = Util.intToStringMaxRadix(8);
  private static final String FIELD_CONTAINER_MIME_TYPE = Util.intToStringMaxRadix(9);
  private static final String FIELD_SAMPLE_MIME_TYPE = Util.intToStringMaxRadix(10);
  private static final String FIELD_MAX_INPUT_SIZE = Util.intToStringMaxRadix(11);
  private static final String FIELD_INITIALIZATION_DATA = Util.intToStringMaxRadix(12);
  private static final String FIELD_DRM_INIT_DATA = Util.intToStringMaxRadix(13);
  private static final String FIELD_SUBSAMPLE_OFFSET_US = Util.intToStringMaxRadix(14);
  private static final String FIELD_WIDTH = Util.intToStringMaxRadix(15);
  private static final String FIELD_HEIGHT = Util.intToStringMaxRadix(16);
  private static final String FIELD_FRAME_RATE = Util.intToStringMaxRadix(17);
  private static final String FIELD_ROTATION_DEGREES = Util.intToStringMaxRadix(18);
  private static final String FIELD_PIXEL_WIDTH_HEIGHT_RATIO = Util.intToStringMaxRadix(19);
  private static final String FIELD_PROJECTION_DATA = Util.intToStringMaxRadix(20);
  private static final String FIELD_STEREO_MODE = Util.intToStringMaxRadix(21);
  private static final String FIELD_COLOR_INFO = Util.intToStringMaxRadix(22);
  private static final String FIELD_CHANNEL_COUNT = Util.intToStringMaxRadix(23);
  private static final String FIELD_SAMPLE_RATE = Util.intToStringMaxRadix(24);
  private static final String FIELD_PCM_ENCODING = Util.intToStringMaxRadix(25);
  private static final String FIELD_ENCODER_DELAY = Util.intToStringMaxRadix(26);
  private static final String FIELD_ENCODER_PADDING = Util.intToStringMaxRadix(27);
  private static final String FIELD_ACCESSIBILITY_CHANNEL = Util.intToStringMaxRadix(28);
  private static final String FIELD_CRYPTO_TYPE = Util.intToStringMaxRadix(29);
  private static final String FIELD_TILE_COUNT_HORIZONTAL = Util.intToStringMaxRadix(30);
  private static final String FIELD_TILE_COUNT_VERTICAL = Util.intToStringMaxRadix(31);

  private static String keyForInitializationData(int initialisationDataIndex) {
    return FIELD_INITIALIZATION_DATA
        + "_"
        + Integer.toString(initialisationDataIndex, Character.MAX_RADIX);
  }

  /**
   * Utility method to get {@code defaultValue} if {@code value} is {@code null}. {@code
   * defaultValue} can be {@code null}.
   *
   */
  private static <T> T defaultIfNull(T value, T defaultValue) {
    return value != null ? value : defaultValue;
  }
}
