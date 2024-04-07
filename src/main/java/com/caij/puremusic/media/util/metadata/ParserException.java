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
package com.caij.puremusic.media.util.metadata;

import java.io.IOException;

/** Thrown when an error occurs parsing media data and metadata. */
@UnstableApi
public class ParserException extends IOException {

  /**
   * Creates a new instance for which {@link #contentIsMalformed} is true and {@link #dataType} is
   *
   * @param message See {@link #getMessage()}.
   * @param cause See {@link #getCause()}.
   * @return The created instance.
   */
  public static ParserException createForMalformedDataOfUnknownType(String message, Throwable cause) {
    return new ParserException(message, cause, /* contentIsMalformed= */ true, Util.DATA_TYPE_UNKNOWN);
  }

  /**
   * Creates a new instance for which {@link #contentIsMalformed} is true and {@link #dataType} is
   *
   * @param message See {@link #getMessage()}.
   * @param cause See {@link #getCause()}.
   * @return The created instance.
   */
  public static ParserException createForMalformedContainer(
      String message, Throwable cause) {
    return new ParserException(message, cause, /* contentIsMalformed= */ true, Util.DATA_TYPE_MEDIA);
  }

  /**
   * Creates a new instance for which {@link #contentIsMalformed} is true and {@link #dataType} is
   *
   * @param message See {@link #getMessage()}.
   * @param cause See {@link #getCause()}.
   * @return The created instance.
   */
  public static ParserException createForMalformedManifest(String message, Throwable cause) {
    return new ParserException(
        message, cause, /* contentIsMalformed= */ true, Util.DATA_TYPE_MANIFEST);
  }

  /**
   * Creates a new instance for which {@link #contentIsMalformed} is false and {@link #dataType} is
   *
   * @param message See {@link #getMessage()}.
   * @param cause See {@link #getCause()}.
   * @return The created instance.
   */
  public static ParserException createForManifestWithUnsupportedFeature(
      String message, Throwable cause) {
    return new ParserException(
        message, cause, /* contentIsMalformed= */ false, Util.DATA_TYPE_MANIFEST);
  }

  /**
   * Creates a new instance for which {@link #contentIsMalformed} is false and {@link #dataType} is
   *
   * @param message See {@link #getMessage()}.
   * @return The created instance.
   */
  public static ParserException createForUnsupportedContainerFeature(String message) {
    return new ParserException(
        message, /* cause= */ null, /* contentIsMalformed= */ false, Util.DATA_TYPE_MEDIA);
  }

  /**
   * Whether the parsing error was caused by a bitstream not following the expected format. May be
   * false when a parser encounters a legal condition which it does not support.
   */
  public final boolean contentIsMalformed;

  public final int dataType;

  protected ParserException(
      String message,
      Throwable cause,
      boolean contentIsMalformed,
      int dataType) {
    super(message, cause);
    this.contentIsMalformed = contentIsMalformed;
    this.dataType = dataType;
  }

  @Override
  public String getMessage() {
    return super.getMessage()
        + "{contentIsMalformed="
        + contentIsMalformed
        + ", dataType="
        + dataType
        + "}";
  }
}
