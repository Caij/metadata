package org.jaudiotagger.x;

import org.mozilla.universalchardet.UniversalDetector;

import java.nio.charset.Charset;

public class CharsetDetectorUtil {

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
            if (detectedCharset != null && Charset.isSupported(detectedCharset)) {
                return Charset.forName(detectedCharset);
            }
        } catch (Exception ignore) {

        }
        return null;
    }
}
