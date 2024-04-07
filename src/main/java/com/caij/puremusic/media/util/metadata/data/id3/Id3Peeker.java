/*
 * Copyright (C) 2018 The Android Open Source Project
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

package com.caij.puremusic.media.util.metadata.data.id3;


import com.caij.puremusic.media.util.metadata.ExtractorInput;
import com.caij.puremusic.media.util.metadata.ParsableByteArray;
import com.caij.puremusic.media.util.metadata.data.Metadata;

import java.io.EOFException;
import java.io.IOException;

public final class Id3Peeker {

  private final ParsableByteArray scratch;

  public Id3Peeker() {
    scratch = new ParsableByteArray(Id3Decoder.ID3_HEADER_LENGTH);
  }

  public Metadata peekId3Data(
          ExtractorInput input, Id3Decoder.FramePredicate id3FramePredicate)
      throws IOException {
    int peekedId3Bytes = 0;
    Metadata metadata = null;
    while (true) {
      try {
        input.peekFully(scratch.getData(), /* offset= */ 0, Id3Decoder.ID3_HEADER_LENGTH);
      } catch (EOFException e) {
        // If input has less than ID3_HEADER_LENGTH, ignore the rest.
        break;
      }
      scratch.setPosition(0);
      if (scratch.readUnsignedInt24() != Id3Decoder.ID3_TAG) {
        // Not an ID3 tag.
        break;
      }
      scratch.skipBytes(3); // Skip major version, minor version and flags.
      int framesLength = scratch.readSynchSafeInt();
      int tagLength = Id3Decoder.ID3_HEADER_LENGTH + framesLength;

      if (metadata == null) {
        byte[] id3Data = new byte[tagLength];
        System.arraycopy(scratch.getData(), 0, id3Data, 0, Id3Decoder.ID3_HEADER_LENGTH);
        input.peekFully(id3Data, Id3Decoder.ID3_HEADER_LENGTH, framesLength);

        metadata = new Id3Decoder(id3FramePredicate).decode(id3Data, tagLength);
      } else {
        input.advancePeekPosition(framesLength);
      }

      peekedId3Bytes += tagLength;
    }

    input.resetPeekPosition();
    input.advancePeekPosition(peekedId3Bytes);
    return metadata;
  }
}
