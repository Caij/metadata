/*
 * Copyright (C) 2017 The Android Open Source Project
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


import com.caij.puremusic.media.util.metadata.Util;

import java.util.Arrays;

/** Chapter information ID3 frame. */
public final class ChapterFrame extends Id3Frame {

  public static final String ID = "CHAP";

  public final String chapterId;
  public final int startTimeMs;
  public final int endTimeMs;

  public final long startOffset;

  public final long endOffset;

  private final Id3Frame[] subFrames;

  public ChapterFrame(
      String chapterId,
      int startTimeMs,
      int endTimeMs,
      long startOffset,
      long endOffset,
      Id3Frame[] subFrames) {
    super(ID);
    this.chapterId = chapterId;
    this.startTimeMs = startTimeMs;
    this.endTimeMs = endTimeMs;
    this.startOffset = startOffset;
    this.endOffset = endOffset;
    this.subFrames = subFrames;
  }


  /** Returns the number of sub-frames. */
  public int getSubFrameCount() {
    return subFrames.length;
  }

  /** Returns the sub-frame at {@code index}. */
  public Id3Frame getSubFrame(int index) {
    return subFrames[index];
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    ChapterFrame other = (ChapterFrame) obj;
    return startTimeMs == other.startTimeMs
        && endTimeMs == other.endTimeMs
        && startOffset == other.startOffset
        && endOffset == other.endOffset
        && Util.areEqual(chapterId, other.chapterId)
        && Arrays.equals(subFrames, other.subFrames);
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + startTimeMs;
    result = 31 * result + endTimeMs;
    result = 31 * result + (int) startOffset;
    result = 31 * result + (int) endOffset;
    result = 31 * result + (chapterId != null ? chapterId.hashCode() : 0);
    return result;
  }

}
