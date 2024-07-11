package org.jaudiotagger.x;

import org.mozilla.universalchardet.UniversalDetector;

import java.nio.charset.Charset;

public class CharsetDetectorCompat {

    UniversalDetector detector = new UniversalDetector();

    public void handleData(byte[] buf) {
        this.handleData(buf, 0, buf.length);
    }

    public void handleData(byte[] buf, int offset, int length) {
        if (length == 0) return;
        if (buf.length == 0) return;
        detector.handleData(buf, offset, length);
    }

    public void end() {
        detector.dataEnd();
    }

    public Charset getDetectedCharset() {
        return CharsetDetectorUtil.format(detector.getDetectedCharset());
    }
}
