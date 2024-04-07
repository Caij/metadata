package com.caij.puremusic.media.util.metadata;

import com.caij.puremusic.media.util.metadata.data.Format;
import com.caij.puremusic.media.util.metadata.data.MimeTypes;
import com.google.common.base.Ascii;
import com.google.common.base.Charsets;
import com.google.common.math.DoubleMath;
import com.google.common.math.LongMath;

import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Util {

    public static final long NANOS_PER_SECOND = 1_000_000_000L;

    @UnstableApi public static final int COLOR_RANGE_LIMITED = 2;

    @UnstableApi public static final int COLOR_RANGE_FULL = 1;

    public static final long TIME_END_OF_SOURCE = Long.MIN_VALUE;
    public static final int LENGTH_UNSET = -1;
    public static final int INDEX_UNSET = -1;
    public static final long TIME_UNSET = Long.MIN_VALUE + 1;

    public static final int RESULT_END_OF_INPUT = -1;

    public static final int BITS_PER_BYTE = 8;
    public static final long MICROS_PER_SECOND = 1_000_000L;

    public static final int CRYPTO_TYPE_NONE = 0;

    public static final String LANGUAGE_UNDETERMINED = "und";

    @UnstableApi public static final int DATA_TYPE_UNKNOWN = 0;

    /** A data type constant for media, typically containing media samples. */
    @UnstableApi public static final int DATA_TYPE_MEDIA = 1;

    /** A data type constant for media, typically containing only initialization data. */
    @UnstableApi public static final int DATA_TYPE_MEDIA_INITIALIZATION = 2;

    /** A data type constant for drm or encryption data. */
    @UnstableApi public static final int DATA_TYPE_DRM = 3;

    /** A data type constant for a manifest file. */
    @UnstableApi public static final int DATA_TYPE_MANIFEST = 4;

    /** A data type constant for time synchronization data. */
    @UnstableApi public static final int DATA_TYPE_TIME_SYNCHRONIZATION = 5;

    /** A data type constant for ads loader data. */
    @UnstableApi public static final int DATA_TYPE_AD = 6;


    /** See {@link AudioFormat#ENCODING_INVALID}. */
    @UnstableApi public static final int ENCODING_INVALID = AudioFormat.ENCODING_INVALID;

    /** See {@link AudioFormat#ENCODING_PCM_8BIT}. */
    @UnstableApi public static final int ENCODING_PCM_8BIT = AudioFormat.ENCODING_PCM_8BIT;

    /** See {@link AudioFormat#ENCODING_PCM_16BIT}. */
    @UnstableApi public static final int ENCODING_PCM_16BIT = AudioFormat.ENCODING_PCM_16BIT;

    /** Like {@link #ENCODING_PCM_16BIT}, but with the bytes in big endian order. */
    @UnstableApi public static final int ENCODING_PCM_16BIT_BIG_ENDIAN = 0x10000000;

    /** PCM encoding with 24 bits per sample. */
    @UnstableApi public static final int ENCODING_PCM_24BIT = AudioFormat.ENCODING_PCM_24BIT_PACKED;

    /** Like {@link #ENCODING_PCM_24BIT} but with the bytes in big endian order. */
    @UnstableApi public static final int ENCODING_PCM_24BIT_BIG_ENDIAN = 0x50000000;

    /** PCM encoding with 32 bits per sample. */
    @UnstableApi public static final int ENCODING_PCM_32BIT = AudioFormat.ENCODING_PCM_32BIT;

    /** Like {@link #ENCODING_PCM_32BIT} but with the bytes in big endian order. */
    @UnstableApi public static final int ENCODING_PCM_32BIT_BIG_ENDIAN = 0x60000000;

    /** See {@link AudioFormat#ENCODING_PCM_FLOAT}. */
    @UnstableApi public static final int ENCODING_PCM_FLOAT = AudioFormat.ENCODING_PCM_FLOAT;

    /** See {@link AudioFormat#ENCODING_MP3}. */
    @UnstableApi public static final int ENCODING_MP3 = AudioFormat.ENCODING_MP3;

    /** See {@link AudioFormat#ENCODING_AAC_LC}. */
    @UnstableApi public static final int ENCODING_AAC_LC = AudioFormat.ENCODING_AAC_LC;

    /** See {@link AudioFormat#ENCODING_AAC_HE_V1}. */
    @UnstableApi public static final int ENCODING_AAC_HE_V1 = AudioFormat.ENCODING_AAC_HE_V1;

    /** See {@link AudioFormat#ENCODING_AAC_HE_V2}. */
    @UnstableApi public static final int ENCODING_AAC_HE_V2 = AudioFormat.ENCODING_AAC_HE_V2;

    /** See {@link AudioFormat#ENCODING_AAC_XHE}. */
    @UnstableApi public static final int ENCODING_AAC_XHE = AudioFormat.ENCODING_AAC_XHE;

    /** See {@link AudioFormat#ENCODING_AAC_ELD}. */
    @UnstableApi public static final int ENCODING_AAC_ELD = AudioFormat.ENCODING_AAC_ELD;

    /** AAC Error Resilient Bit-Sliced Arithmetic Coding. */
    @UnstableApi public static final int ENCODING_AAC_ER_BSAC = 0x40000000;

    /** See {@link AudioFormat#ENCODING_AC3}. */
    @UnstableApi public static final int ENCODING_AC3 = AudioFormat.ENCODING_AC3;

    /** See {@link AudioFormat#ENCODING_E_AC3}. */
    @UnstableApi public static final int ENCODING_E_AC3 = AudioFormat.ENCODING_E_AC3;

    /** See {@link AudioFormat#ENCODING_E_AC3_JOC}. */
    @UnstableApi public static final int ENCODING_E_AC3_JOC = AudioFormat.ENCODING_E_AC3_JOC;

    /** See {@link AudioFormat#ENCODING_AC4}. */
    @UnstableApi public static final int ENCODING_AC4 = AudioFormat.ENCODING_AC4;

    /** See {@link AudioFormat#ENCODING_DTS}. */
    @UnstableApi public static final int ENCODING_DTS = AudioFormat.ENCODING_DTS;

    /** See {@link AudioFormat#ENCODING_DTS_HD}. */
    @UnstableApi public static final int ENCODING_DTS_HD = AudioFormat.ENCODING_DTS_HD;

    /** See {@link AudioFormat#ENCODING_DTS_UHD_P2}. */
    @UnstableApi public static final int ENCODING_DTS_UHD_P2 = AudioFormat.ENCODING_DTS_UHD_P2;

    /** See {@link AudioFormat#ENCODING_DOLBY_TRUEHD}. */
    @UnstableApi public static final int ENCODING_DOLBY_TRUEHD = AudioFormat.ENCODING_DOLBY_TRUEHD;

    /** See {@link AudioFormat#ENCODING_OPUS}. */
    @UnstableApi public static final int ENCODING_OPUS = AudioFormat.ENCODING_OPUS;


    public static final int TRACK_TYPE_UNKNOWN = -1;
    public static final int TRACK_TYPE_AUDIO = 1;
    public static final int TRACK_TYPE_VIDEO = 2;

    /** A type constant for text tracks. */
    public static final int TRACK_TYPE_TEXT = 3;

    /** A type constant for image tracks. */
    public static final int TRACK_TYPE_IMAGE = 4;

    /** A type constant for metadata tracks. */
    public static final int TRACK_TYPE_METADATA = 5;

    /** A type constant for camera motion tracks. */
    public static final int TRACK_TYPE_CAMERA_MOTION = 6;

    /**
     * Applications or extensions may define custom {@code TRACK_TYPE_*} constants greater than or
     * equal to this value.
     */
    public static final int TRACK_TYPE_CUSTOM_BASE = 10000;


    // LINT.IfChange(selection_flags)
    /** Indicates that the track should be selected if user preferences do not state otherwise. */
    public static final int SELECTION_FLAG_DEFAULT = 1;

    /**
     * Indicates that the track should be selected if its language matches the language of the
     * selected audio track and user preferences do not state otherwise. Only applies to text tracks.
     *
     * <p>Tracks with this flag generally provide translation for elements that don't match the
     * declared language of the selected audio track (e.g. speech in an alien language). See <a
     * href="https://partnerhelp.netflixstudios.com/hc/en-us/articles/217558918">Netflix's summary</a>
     * for more info.
     */
    public static final int SELECTION_FLAG_FORCED = 1 << 1; // 2

    /**
     * Indicates that the player may choose to play the track in absence of an explicit user
     * preference.
     */
    public static final int SELECTION_FLAG_AUTOSELECT = 1 << 2; // 4


    public static final int ROLE_FLAG_MAIN = 1;

    /**
     * Indicates an alternate track. For example a video track recorded from an different view point
     * than the main track(s).
     */
    public static final int ROLE_FLAG_ALTERNATE = 1 << 1;

    /**
     * Indicates a supplementary track, meaning the track has lower importance than the main track(s).
     * For example a video track that provides a visual accompaniment to a main audio track.
     */
    public static final int ROLE_FLAG_SUPPLEMENTARY = 1 << 2;

    /** Indicates the track contains commentary, for example from the director. */
    public static final int ROLE_FLAG_COMMENTARY = 1 << 3;

    /**
     * Indicates the track is in a different language from the original, for example dubbed audio or
     * translated captions.
     */
    public static final int ROLE_FLAG_DUB = 1 << 4;

    /** Indicates the track contains information about a current emergency. */
    public static final int ROLE_FLAG_EMERGENCY = 1 << 5;

    /**
     * Indicates the track contains captions. This flag may be set on video tracks to indicate the
     * presence of burned in captions.
     */
    public static final int ROLE_FLAG_CAPTION = 1 << 6;

    /**
     * Indicates the track contains subtitles. This flag may be set on video tracks to indicate the
     * presence of burned in subtitles.
     */
    public static final int ROLE_FLAG_SUBTITLE = 1 << 7;

    /** Indicates the track contains a visual sign-language interpretation of an audio track. */
    public static final int ROLE_FLAG_SIGN = 1 << 8;

    /** Indicates the track contains an audio or textual description of a video track. */
    public static final int ROLE_FLAG_DESCRIBES_VIDEO = 1 << 9;

    /** Indicates the track contains a textual description of music and sound. */
    public static final int ROLE_FLAG_DESCRIBES_MUSIC_AND_SOUND = 1 << 10;

    /** Indicates the track is designed for improved intelligibility of dialogue. */
    public static final int ROLE_FLAG_ENHANCED_DIALOG_INTELLIGIBILITY = 1 << 11;

    /** Indicates the track contains a transcription of spoken dialog. */
    public static final int ROLE_FLAG_TRANSCRIBES_DIALOG = 1 << 12;

    /** Indicates the track contains a text that has been edited for ease of reading. */
    public static final int ROLE_FLAG_EASY_TO_READ = 1 << 13;

    /** Indicates the track is intended for trick play. */
    public static final int ROLE_FLAG_TRICK_PLAY = 1 << 14;

    public static final int BUFFER_FLAG_LAST_SAMPLE = 1 << 29; // 0x20000000

    public static final int RATE_UNSET_INT = Integer.MIN_VALUE + 1;

    public static final int BUFFER_FLAG_KEY_FRAME = 1;


    /** Indicates Monoscopic stereo layout, used with 360/3D/VR videos. */
    @UnstableApi public static final int STEREO_MODE_MONO = 0;

    /** Indicates Top-Bottom stereo layout, used with 360/3D/VR videos. */
    @UnstableApi public static final int STEREO_MODE_TOP_BOTTOM = 1;

    /** Indicates Left-Right stereo layout, used with 360/3D/VR videos. */
    @UnstableApi public static final int STEREO_MODE_LEFT_RIGHT = 2;

    /**
     * Indicates a stereo layout where the left and right eyes have separate meshes, used with
     * 360/3D/VR videos.
     */
    @UnstableApi public static final int STEREO_MODE_STEREO_MESH = 3;



    // Additional mapping from ISO3 to ISO2 language codes.
    private static final String[] additionalIsoLanguageReplacements =
            new String[] {
                    // Bibliographical codes defined in ISO 639-2/B, replaced by terminological code defined in
                    // ISO 639-2/T. See https://en.wikipedia.org/wiki/List_of_ISO_639-2_codes.
                    "alb", "sq",
                    "arm", "hy",
                    "baq", "eu",
                    "bur", "my",
                    "tib", "bo",
                    "chi", "zh",
                    "cze", "cs",
                    "dut", "nl",
                    "ger", "de",
                    "gre", "el",
                    "fre", "fr",
                    "geo", "ka",
                    "ice", "is",
                    "mac", "mk",
                    "mao", "mi",
                    "may", "ms",
                    "per", "fa",
                    "rum", "ro",
                    "scc", "hbs-srp",
                    "slo", "sk",
                    "wel", "cy",
                    // Deprecated 2-letter codes, replaced by modern equivalent (including macrolanguage)
                    // See https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes, "ISO 639:1988"
                    "id", "ms-ind",
                    "iw", "he",
                    "heb", "he",
                    "ji", "yi",
                    // Individual macrolanguage codes mapped back to full macrolanguage code.
                    // See https://en.wikipedia.org/wiki/ISO_639_macrolanguage
                    "arb", "ar-arb",
                    "in", "ms-ind",
                    "ind", "ms-ind",
                    "nb", "no-nob",
                    "nob", "no-nob",
                    "nn", "no-nno",
                    "nno", "no-nno",
                    "tw", "ak-twi",
                    "twi", "ak-twi",
                    "bs", "hbs-bos",
                    "bos", "hbs-bos",
                    "hr", "hbs-hrv",
                    "hrv", "hbs-hrv",
                    "sr", "hbs-srp",
                    "srp", "hbs-srp",
                    "cmn", "zh-cmn",
                    "hak", "zh-hak",
                    "nan", "zh-nan",
                    "hsn", "zh-hsn"
            };

    private static final String[] isoLegacyTagReplacements =
            new String[] {
                    "i-lux", "lb",
                    "i-hak", "zh-hak",
                    "i-navajo", "nv",
                    "no-bok", "no-nob",
                    "no-nyn", "no-nno",
                    "zh-guoyu", "zh-cmn",
                    "zh-hakka", "zh-hak",
                    "zh-min-nan", "zh-nan",
                    "zh-xiang", "zh-hsn"
            };

    private static HashMap<String, String> languageTagReplacementMap;


    @UnstableApi
    public static final String CENC_TYPE_cenc = "cenc";

    /** "cbc1" scheme type name as defined in ISO/IEC 23001-7:2016. */
    @SuppressWarnings("ConstantField")
    @UnstableApi
    public static final String CENC_TYPE_cbc1 = "cbc1";

    /** "cens" scheme type name as defined in ISO/IEC 23001-7:2016. */
    @SuppressWarnings("ConstantField")
    @UnstableApi
    public static final String CENC_TYPE_cens = "cens";

    /** "cbcs" scheme type name as defined in ISO/IEC 23001-7:2016. */
    @SuppressWarnings("ConstantField")
    @UnstableApi
    public static final String CENC_TYPE_cbcs = "cbcs";

    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];


    public static boolean areEqual(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    public static String intToStringMaxRadix(int i) {
        return Integer.toString(i, Character.MAX_RADIX);
    }

    public static <T> T[] nullSafeArrayConcatenation(T[] first, T[] second) {
        T[] concatenation = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(
                /* src= */ second,
                /* srcPos= */ 0,
                /* dest= */ concatenation,
                /* destPos= */ first.length,
                /* length= */ second.length);
        return concatenation;
    }

    public static int hashCode(long value) {
        return (int) (value ^ (value >>> 32));
    }


    public static String normalizeLanguageCode(String language) {
        if (language == null) {
            return null;
        }
        // Locale data (especially for API < 21) may produce tags with '_' instead of the
        // standard-conformant '-'.
        String normalizedTag = language.replace('_', '-');
        if (normalizedTag.isEmpty() || normalizedTag.equals(LANGUAGE_UNDETERMINED)) {
            // Tag isn't valid, keep using the original.
            normalizedTag = language;
        }
        normalizedTag = Ascii.toLowerCase(normalizedTag);
        String mainLanguage = splitAtFirst(normalizedTag, "-")[0];
        if (languageTagReplacementMap == null) {
            languageTagReplacementMap = createIsoLanguageReplacementMap();
        }
        String replacedLanguage = languageTagReplacementMap.get(mainLanguage);
        if (replacedLanguage != null) {
            normalizedTag =
                    replacedLanguage + normalizedTag.substring(/* beginIndex= */ mainLanguage.length());
            mainLanguage = replacedLanguage;
        }
        if ("no".equals(mainLanguage) || "i".equals(mainLanguage) || "zh".equals(mainLanguage)) {
            normalizedTag = maybeReplaceLegacyLanguageTags(normalizedTag);
        }
        return normalizedTag;
    }

    private static String maybeReplaceLegacyLanguageTags(String languageTag) {
        for (int i = 0; i < isoLegacyTagReplacements.length; i += 2) {
            if (languageTag.startsWith(isoLegacyTagReplacements[i])) {
                return isoLegacyTagReplacements[i + 1]
                        + languageTag.substring(/* beginIndex= */ isoLegacyTagReplacements[i].length());
            }
        }
        return languageTag;
    }


    public static String[] splitAtFirst(String value, String regex) {
        return value.split(regex, /* limit= */ 2);
    }

    private static HashMap<String, String> createIsoLanguageReplacementMap() {
        String[] iso2Languages = Locale.getISOLanguages();
        HashMap<String, String> replacedLanguages =
                new HashMap<>(
                        /* initialCapacity= */ iso2Languages.length + additionalIsoLanguageReplacements.length);
        for (String iso2 : iso2Languages) {
            try {
                // This returns the ISO 639-2/T code for the language.
                String iso3 = new Locale(iso2).getISO3Language();
                if (!isEmpty(iso3)) {
                    replacedLanguages.put(iso3, iso2);
                }
            } catch (MissingResourceException e) {
                // Shouldn't happen for list of known languages, but we don't want to throw either.
            }
        }
        // Add additional replacement mappings.
        for (int i = 0; i < additionalIsoLanguageReplacements.length; i += 2) {
            replacedLanguages.put(
                    additionalIsoLanguageReplacements[i], additionalIsoLanguageReplacements[i + 1]);
        }
        return replacedLanguages;
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static String[] splitCodecs(String codecs) {
        if (isEmpty(codecs)) {
            return new String[0];
        }
        return split(codecs.trim(), "(\\s*,\\s*)");
    }

    public static String[] split(String value, String regex) {
        return value.split(regex, /* limit= */ -1);
    }

    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static String getCodecsOfType(String codecs, int trackType) {
        String[] codecArray = splitCodecs(codecs);
        if (codecArray.length == 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (String codec : codecArray) {
            if (trackType == MimeTypes.getTrackTypeOfCodec(codec)) {
                if (builder.length() > 0) {
                    builder.append(",");
                }
                builder.append(codec);
            }
        }
        return builder.length() > 0 ? builder.toString() : null;
    }

    public static List<String> getSelectionFlagStrings(int selectionFlags) {
        List<String> result = new ArrayList<>();
        // LINT.IfChange(selection_flags)
        if ((selectionFlags & Util.SELECTION_FLAG_AUTOSELECT) != 0) {
            result.add("auto");
        }
        if ((selectionFlags & Util.SELECTION_FLAG_DEFAULT) != 0) {
            result.add("default");
        }
        if ((selectionFlags & Util.SELECTION_FLAG_FORCED) != 0) {
            result.add("forced");
        }
        return result;
    }

    public static List<String> getRoleFlagStrings(int roleFlags) {
        List<String> result = new ArrayList<>();
        // LINT.IfChange(role_flags)
        if ((roleFlags & ROLE_FLAG_MAIN) != 0) {
            result.add("main");
        }
        if ((roleFlags & ROLE_FLAG_ALTERNATE) != 0) {
            result.add("alt");
        }
        if ((roleFlags & ROLE_FLAG_SUPPLEMENTARY) != 0) {
            result.add("supplementary");
        }
        if ((roleFlags & ROLE_FLAG_COMMENTARY) != 0) {
            result.add("commentary");
        }
        if ((roleFlags & ROLE_FLAG_DUB) != 0) {
            result.add("dub");
        }
        if ((roleFlags & ROLE_FLAG_EMERGENCY) != 0) {
            result.add("emergency");
        }
        if ((roleFlags & ROLE_FLAG_CAPTION) != 0) {
            result.add("caption");
        }
        if ((roleFlags & ROLE_FLAG_SUBTITLE) != 0) {
            result.add("subtitle");
        }
        if ((roleFlags & ROLE_FLAG_SIGN) != 0) {
            result.add("sign");
        }
        if ((roleFlags & ROLE_FLAG_DESCRIBES_VIDEO) != 0) {
            result.add("describes-video");
        }
        if ((roleFlags & ROLE_FLAG_DESCRIBES_MUSIC_AND_SOUND) != 0) {
            result.add("describes-music");
        }
        if ((roleFlags & ROLE_FLAG_ENHANCED_DIALOG_INTELLIGIBILITY) != 0) {
            result.add("enhanced-intelligibility");
        }
        if ((roleFlags & ROLE_FLAG_TRANSCRIBES_DIALOG) != 0) {
            result.add("transcribes-dialog");
        }
        if ((roleFlags & ROLE_FLAG_EASY_TO_READ) != 0) {
            result.add("easy-read");
        }
        if ((roleFlags & ROLE_FLAG_TRICK_PLAY) != 0) {
            result.add("trick-play");
        }
        return result;
    }

    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Throws {@link IllegalArgumentException} if {@code expression} evaluates to false.
     *
     * @param expression The expression to evaluate.
     * @param errorMessage The exception message if an exception is thrown. The message is converted
     *     to a {@link String} using {@link String#valueOf(Object)}.
     * @throws IllegalArgumentException If {@code expression} is false.
     */
    public static void checkArgument(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    public static String fromUtf8Bytes(byte[] bytes) {
        return new String(bytes, Charsets.UTF_8);
    }

    public static String fromUtf8Bytes(byte[] bytes, int offset, int length) {
        return new String(bytes, offset, length, Charsets.UTF_8);
    }

    public static boolean isLinebreak(int c) {
        return c == '\n' || c == '\r';
    }

    public static long toUnsignedLong(int x) {
        // x is implicitly casted to a long before the bit operation is executed but this does not
        // impact the method correctness.
        return x & 0xFFFFFFFFL;
    }

    public static long toLong(int mostSignificantBits, int leastSignificantBits) {
        return (toUnsignedLong(mostSignificantBits) << 32) | toUnsignedLong(leastSignificantBits);
    }

    public static void checkState(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalStateException(String.valueOf(errorMessage));
        }
    }

    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    public static <T> T castNonNull(T value) {
        return value;
    }

    public static long sampleCountToDurationUs(long sampleCount, int sampleRate) {
        return scaleLargeValue(sampleCount, MICROS_PER_SECOND, sampleRate, RoundingMode.FLOOR);
    }

    public static long scaleLargeValue(
            long value, long multiplier, long divisor, RoundingMode roundingMode) {
        if (value == 0 || multiplier == 0) {
            return 0;
        }
        if (divisor >= multiplier && (divisor % multiplier) == 0) {
            long divisionFactor = LongMath.divide(divisor, multiplier, RoundingMode.UNNECESSARY);
            return LongMath.divide(value, divisionFactor, roundingMode);
        } else if (divisor < multiplier && (multiplier % divisor) == 0) {
            long multiplicationFactor = LongMath.divide(multiplier, divisor, RoundingMode.UNNECESSARY);
            return LongMath.saturatedMultiply(value, multiplicationFactor);
        } else if (divisor >= value && (divisor % value) == 0) {
            long divisionFactor = LongMath.divide(divisor, value, RoundingMode.UNNECESSARY);
            return LongMath.divide(multiplier, divisionFactor, roundingMode);
        } else if (divisor < value && (value % divisor) == 0) {
            long multiplicationFactor = LongMath.divide(value, divisor, RoundingMode.UNNECESSARY);
            return LongMath.saturatedMultiply(multiplier, multiplicationFactor);
        } else {
            return scaleLargeValueFallback(value, multiplier, divisor, roundingMode);
        }
    }

    private static long scaleLargeValueFallback(
            long value, long multiplier, long divisor, RoundingMode roundingMode) {
        long numerator = LongMath.saturatedMultiply(value, multiplier);
        if (numerator != Long.MAX_VALUE && numerator != Long.MIN_VALUE) {
            return LongMath.divide(numerator, divisor, roundingMode);
        } else {
            // Directly multiplying value and multiplier will overflow a long, so we try and cancel
            // with GCD and try directly multiplying again below. If that still overflows we fall
            // through to floating point arithmetic.
            long gcdOfMultiplierAndDivisor = LongMath.gcd(Math.abs(multiplier), Math.abs(divisor));
            long simplifiedMultiplier =
                    LongMath.divide(multiplier, gcdOfMultiplierAndDivisor, RoundingMode.UNNECESSARY);
            long simplifiedDivisor =
                    LongMath.divide(divisor, gcdOfMultiplierAndDivisor, RoundingMode.UNNECESSARY);
            long gcdOfValueAndSimplifiedDivisor =
                    LongMath.gcd(Math.abs(value), Math.abs(simplifiedDivisor));
            long simplifiedValue =
                    LongMath.divide(value, gcdOfValueAndSimplifiedDivisor, RoundingMode.UNNECESSARY);
            simplifiedDivisor =
                    LongMath.divide(
                            simplifiedDivisor, gcdOfValueAndSimplifiedDivisor, RoundingMode.UNNECESSARY);
            long simplifiedNumerator = LongMath.saturatedMultiply(simplifiedValue, simplifiedMultiplier);
            if (simplifiedNumerator != Long.MAX_VALUE && simplifiedNumerator != Long.MIN_VALUE) {
                return LongMath.divide(simplifiedNumerator, simplifiedDivisor, roundingMode);
            } else {
                double multiplicationFactor = (double) simplifiedMultiplier / simplifiedDivisor;
                double result = simplifiedValue * multiplicationFactor;
                // Clamp values that are too large to be represented by 64-bit signed long. If we don't
                // explicitly clamp then DoubleMath.roundToLong will throw ArithmeticException.
                if (result > Long.MAX_VALUE) {
                    return Long.MAX_VALUE;
                } else if (result < Long.MIN_VALUE) {
                    return Long.MIN_VALUE;
                } else {
                    return DoubleMath.roundToLong(result, roundingMode);
                }
            }
        }
    }

    public static int constrainValue(int value, int min, int max) {
        return max(min, min(value, max));
    }

    public static long constrainValue(long value, long min, long max) {
        return max(min, min(value, max));
    }

    public static long scaleLargeTimestamp(long timestamp, long multiplier, long divisor) {
        return scaleLargeValue(timestamp, multiplier, divisor, RoundingMode.FLOOR);
    }

    public static long msToUs(long timeMs) {
        return (timeMs == TIME_UNSET || timeMs == TIME_END_OF_SOURCE) ? timeMs : (timeMs * 1000);
    }

    public static long usToMs(long timeUs) {
        return (timeUs == TIME_UNSET || timeUs == TIME_END_OF_SOURCE) ? timeUs : (timeUs / 1000);
    }

    public static int getPcmEncoding(int bitDepth) {
        switch (bitDepth) {
            case 8:
                return ENCODING_PCM_8BIT;
            case 16:
                return ENCODING_PCM_16BIT;
            case 24:
                return ENCODING_PCM_24BIT;
            case 32:
                return ENCODING_PCM_32BIT;
            default:
                return ENCODING_INVALID;
        }
    }

    public static int binarySearchFloor(
            int[] array, int value, boolean inclusive, boolean stayInBounds) {
        int index = Arrays.binarySearch(array, value);
        if (index < 0) {
            index = -(index + 2);
        } else {
            while (--index >= 0 && array[index] == value) {}
            if (inclusive) {
                index++;
            }
        }
        return stayInBounds ? max(0, index) : index;
    }

    public static int binarySearchFloor(
            long[] array, long value, boolean inclusive, boolean stayInBounds) {
        int index = Arrays.binarySearch(array, value);
        if (index < 0) {
            index = -(index + 2);
        } else {
            while (--index >= 0 && array[index] == value) {}
            if (inclusive) {
                index++;
            }
        }
        return stayInBounds ? max(0, index) : index;
    }

    public static <T extends Comparable<? super T>> int binarySearchFloor(
            List<? extends Comparable<? super T>> list,
            T value,
            boolean inclusive,
            boolean stayInBounds) {
        int index = Collections.binarySearch(list, value);
        if (index < 0) {
            index = -(index + 2);
        } else {
            while (--index >= 0 && list.get(index).compareTo(value) == 0) {}
            if (inclusive) {
                index++;
            }
        }
        return stayInBounds ? max(0, index) : index;
    }

    public static int binarySearchFloor(
            LongArray longArray, long value, boolean inclusive, boolean stayInBounds) {
        int lowIndex = 0;
        int highIndex = longArray.size() - 1;

        while (lowIndex <= highIndex) {
            int midIndex = (lowIndex + highIndex) >>> 1;
            if (longArray.get(midIndex) < value) {
                lowIndex = midIndex + 1;
            } else {
                highIndex = midIndex - 1;
            }
        }

        if (inclusive && highIndex + 1 < longArray.size() && longArray.get(highIndex + 1) == value) {
            highIndex++;
        } else if (stayInBounds && highIndex == -1) {
            highIndex = 0;
        }

        return highIndex;
    }

    public static int getBigEndianInt(ByteBuffer buffer, int index) {
        int value = buffer.getInt(index);
        return buffer.order() == ByteOrder.BIG_ENDIAN ? value : Integer.reverseBytes(value);
    }

    public static float toFloat(byte[] bytes) {
        checkArgument(bytes.length == 4);
        int intBits =
                bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
        return Float.intBitsToFloat(intBits);
    }

    public static int toInteger(byte[] bytes) {
        checkArgument(bytes.length == 4);
        return bytes[0] << 24 | bytes[1] << 16 | bytes[2] << 8 | bytes[3];
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder result = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            result
                    .append(Character.forDigit((bytes[i] >> 4) & 0xF, 16))
                    .append(Character.forDigit(bytes[i] & 0xF, 16));
        }
        return result.toString();
    }

    public static byte[] getUtf8Bytes(String value) {
        return value.getBytes(Charsets.UTF_8);
    }

    public static void scaleLargeTimestampsInPlace(long[] timestamps, long multiplier, long divisor) {
        scaleLargeValuesInPlace(timestamps, multiplier, divisor, RoundingMode.FLOOR);
    }

    public static void scaleLargeValuesInPlace(
            long[] values, long multiplier, long divisor, RoundingMode roundingMode) {
        if (multiplier == 0) {
            Arrays.fill(values, 0);
            return;
        }
        if (divisor >= multiplier && (divisor % multiplier) == 0) {
            long divisionFactor = LongMath.divide(divisor, multiplier, RoundingMode.UNNECESSARY);
            for (int i = 0; i < values.length; i++) {
                values[i] = LongMath.divide(values[i], divisionFactor, roundingMode);
            }
        } else if (divisor < multiplier && (multiplier % divisor) == 0) {
            long multiplicationFactor = LongMath.divide(multiplier, divisor, RoundingMode.UNNECESSARY);
            for (int i = 0; i < values.length; i++) {
                values[i] = LongMath.saturatedMultiply(values[i], multiplicationFactor);
            }
        } else {
            for (int i = 0; i < values.length; i++) {
                if (values[i] == 0) {
                    continue;
                }
                if (divisor >= values[i] && (divisor % values[i]) == 0) {
                    long divisionFactor = LongMath.divide(divisor, values[i], RoundingMode.UNNECESSARY);
                    values[i] = LongMath.divide(multiplier, divisionFactor, roundingMode);
                } else if (divisor < values[i] && (values[i] % divisor) == 0) {
                    long multiplicationFactor = LongMath.divide(values[i], divisor, RoundingMode.UNNECESSARY);
                    values[i] = LongMath.saturatedMultiply(multiplier, multiplicationFactor);
                } else {
                    values[i] = scaleLargeValueFallback(values[i], multiplier, divisor, roundingMode);
                }
            }
        }
    }

    public static int binarySearchCeil(
            int[] array, int value, boolean inclusive, boolean stayInBounds) {
        int index = Arrays.binarySearch(array, value);
        if (index < 0) {
            index = ~index;
        } else {
            while (++index < array.length && array[index] == value) {}
            if (inclusive) {
                index--;
            }
        }
        return stayInBounds ? min(array.length - 1, index) : index;
    }

    public static int binarySearchCeil(
            long[] array, long value, boolean inclusive, boolean stayInBounds) {
        int index = Arrays.binarySearch(array, value);
        if (index < 0) {
            index = ~index;
        } else {
            while (++index < array.length && array[index] == value) {}
            if (inclusive) {
                index--;
            }
        }
        return stayInBounds ? min(array.length - 1, index) : index;
    }

    public static int getPcmFrameSize(int pcmEncoding, int channelCount) {
        switch (pcmEncoding) {
            case ENCODING_PCM_8BIT:
                return channelCount;
            case ENCODING_PCM_16BIT:
            case ENCODING_PCM_16BIT_BIG_ENDIAN:
                return channelCount * 2;
            case ENCODING_PCM_24BIT:
            case ENCODING_PCM_24BIT_BIG_ENDIAN:
                return channelCount * 3;
            case ENCODING_PCM_32BIT:
            case ENCODING_PCM_32BIT_BIG_ENDIAN:
            case ENCODING_PCM_FLOAT:
                return channelCount * 4;
            case ENCODING_INVALID:
            case Format.NO_VALUE:
            default:
                throw new IllegalArgumentException();
        }
    }

    public static String formatInvariant(String format, Object... args) {
        return String.format(Locale.US, format, args);
    }

    public static int ceilDivide(int numerator, int denominator) {
        return (numerator + denominator - 1) / denominator;
    }

    public static <T> T checkStateNotNull(T reference) {
        if (reference == null) {
            throw new IllegalStateException();
        }
        return reference;
    }
}
