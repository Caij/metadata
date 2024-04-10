package org.jaudiotagger.x;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.*;
import java.nio.charset.Charset;

public class CharsetDetectorUtil {

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
            if (detectedCharset != null && Charset.isSupported(detectedCharset)) {
                return Charset.forName(detectedCharset);
            }
        } catch (Throwable ignore) {

        }
        return null;
    }

    public static Charset detected(File file) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        return detected(bufferedInputStream);
    }

    public static Charset detected(InputStream inputStream) throws FileNotFoundException {
        try {
            String detectedCharset =  UniversalDetector.detectCharset(inputStream);
            if (detectedCharset != null && Charset.isSupported(detectedCharset)) {
                return Charset.forName(detectedCharset);
            }
        } catch (Throwable error) {

        }
        return null;
    }


}
