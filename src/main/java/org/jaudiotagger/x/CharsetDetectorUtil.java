package org.jaudiotagger.x;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

public class CharsetDetectorUtil {

    private static CharsetDetectorMapper sCharsetDetectorMapper;

    public static interface CharsetDetectorMapper {
        String map(String detectedCharset);
    }

    public static class DefaultCharsetDetectorMapper implements CharsetDetectorMapper {

        private List<String> userLanguages;

        public DefaultCharsetDetectorMapper(List<String> userLanguages) {
            this.userLanguages = userLanguages;
        }

        @Override
        public String map(String detectedCharset) {
            if (userLanguages == null) return detectedCharset;
            for (String language : userLanguages) {
                if (language.contains("zh") || language.contains("ZH")) {
                    if (Objects.equals(detectedCharset, "UTF-8") || Objects.equals(detectedCharset, "UTF-16")
                            || detectedCharset.contains("GB")
                            || detectedCharset.contains("ISO-8859")
                            || detectedCharset.toUpperCase().contains("BIG")) {
                        return detectedCharset;
                    } else {
                        return "GBK";
                    }
                }
            }
            return detectedCharset;
        }
    }

    public static void init(CharsetDetectorMapper charsetDetectorMapper) {
        sCharsetDetectorMapper = charsetDetectorMapper;
    }

    public static Charset detected(byte[] data) {
        return detected(data, 0, data.length);
    }

    public static Charset detected(byte[] data, int start, int length) {
        try {
            UniversalDetector detector = new UniversalDetector();
            int max = 10;
            while (!detector.isDone() && max > 0) {
                detector.handleData(data, start, length);
                max --;
            }
            detector.dataEnd();
            String detectedCharset = detector.getDetectedCharset();
            if (detectedCharset != null) {
                detectedCharset = mapOrDefault(detectedCharset);
            }
            if (detectedCharset != null && Charset.isSupported(detectedCharset)) {
                return Charset.forName(detectedCharset);
            }
        } catch (Throwable ignore) {

        }
        return null;
    }

    private static String mapOrDefault(String detectedCharset) {
        if (sCharsetDetectorMapper != null) {
            String mapValue = sCharsetDetectorMapper.map(detectedCharset);
            if (mapValue != null) return mapValue;
        }
        return detectedCharset;
    }

    public static Charset detected(File file) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        return detected(bufferedInputStream);
    }

    public static Charset detected(InputStream inputStream) throws FileNotFoundException {
        try {
            String detectedCharset = UniversalDetector.detectCharset(inputStream);
            detectedCharset = mapOrDefault(detectedCharset);
            if (detectedCharset != null && Charset.isSupported(detectedCharset)) {
                return Charset.forName(detectedCharset);
            }
        } catch (Throwable error) {

        }
        return null;
    }


}
