// Copyright 2012 Google Inc. All Rights Reserved.
// Author: frkoenig@google.com (Fritz Koenig)
package com.google.libvpx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.util.Arrays;

/**
 * Return raw bitstream from IVF file.
 *
 * Pts is thrown away currently.
 */
public class IVFReader {
  private FileInputStream is;
  private int fourcc;
  private int width;
  private int height;
  private int fpsNum;
  private int fpsDen;
  private int frameCount;
  private int currentFrame;
  private static byte[] cDKIF = {'D', 'K', 'I', 'F'};

  public IVFReader(File file) throws IOException {
    is = new FileInputStream(file);
    readHeader();
  }

  public byte[] readNextFrame() {
    try {
      byte[] headerBuffer = readBuffer(12);
      if (headerBuffer == null) {
        return null;
      }
      ByteBuffer buf = ByteBuffer.wrap(headerBuffer);
      buf.order(ByteOrder.LITTLE_ENDIAN);

      int frameLength = buf.getInt();
      long pts = buf.getLong(4);

      currentFrame++;

      return readBuffer(frameLength);
    } catch (IOException e) {
      return null;
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  private byte[] readHeader() throws IOException {
    byte[] headerBuffer = readBuffer(32);

    ByteBuffer buf = ByteBuffer.wrap(headerBuffer);
    buf.order(ByteOrder.LITTLE_ENDIAN);

    if (!Arrays.equals(Arrays.copyOfRange(headerBuffer, 0, 4), cDKIF)) {
      throw new IOException("Incomplete magic for IVF file.");
    }

    int version = buf.getShort(4);
    if (version != 0) {
      throw new IOException("Unrecognized IVF version! This file may not" +
                            " decode properly.");
    }

    fourcc = buf.getInt(8);
    width = buf.getShort(12);
    height = buf.getShort(14);
    fpsNum = buf.getInt(16);
    fpsDen = buf.getInt(20);
    frameCount = buf.getInt(24);

    return headerBuffer;
  }

  private byte[] readBuffer(int size) throws IOException {
    byte[] bytes = new byte[size];

    // Read in the bytes
    int offset = 0;
    int numRead = 0;
    while (offset < bytes.length
            && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
      offset += numRead;
    }

    if (numRead != size) {
      return null;
    }

    return bytes;
  }
}
