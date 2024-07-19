package org.jaudiotagger.x;

import org.mozilla.universalchardet.UniversalDetector;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CharsetDetectorCompat {

    private List<byte[]> caches = new ArrayList<>();

    UniversalDetector detector = new UniversalDetector();

    public void handleData(byte[] buf) {
        this.handleData(buf, 0, buf.length);
    }

    public void handleData(byte[] buf, int offset, int length) {
        if (length == 0) return;
        if (buf.length == 0) return;

        byte[] cache = new byte[length];
        System.arraycopy(buf, offset, cache, 0, length);
        caches.add(cache);

        detector.handleData(buf, offset, length);
    }

    public void end() {
        int max = 10;
        if (!caches.isEmpty()) {
            while (!detector.isDone() && max > 0) {
                for (byte[] c : caches) {
                    detector.handleData(c);
                }
                max--;
            }
        }
        detector.dataEnd();
    }

    public Charset getDetectedCharset() {
        return CharsetDetectorUtil.format(detector.getDetectedCharset());
    }
}
