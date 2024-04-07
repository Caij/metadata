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

import com.caij.puremusic.media.util.metadata.Util;

/** Internal ID3 frame that is intended for use by the player. */
public final class InternalFrame extends Id3Frame {

  public static final String ID = "----";

  public final String domain;
  public final String description;
  public final String text;

  public InternalFrame(String domain, String description, String text) {
    super(ID);
    this.domain = domain;
    this.description = description;
    this.text = text;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    InternalFrame other = (InternalFrame) obj;
    return Util.areEqual(description, other.description)
            && Util.areEqual(domain, other.domain)
            && Util.areEqual(text, other.text);
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (domain != null ? domain.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (text != null ? text.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return id + ": domain=" + domain + ", description=" + description;
  }

}
