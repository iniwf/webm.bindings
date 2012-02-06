// Copyright 2012 Google Inc. All Rights Reserved.
// Author: frkoenig@google.com (Fritz Koenig)
package com.google.libvpx;

/**
 * Packet of data return from encoder.
 */
public class VpxCodecCxPkt {
  byte[] buffer;         // compressed data buffer
  long   sz;             // length of compressed data
  long   pts;            // time stamp to show frame (in timebase units)
  long   duration;       // duration to show frame (in timebase units)
  int    flags;          // flags for this frame
  int    partitionId;    // the partition id
                         // defines the decoding order
                         // of the partitions. Only
                         // applicable when "output partition"
                         // mode is enabled. First partition
                         // has id 0
  public VpxCodecCxPkt(long sz) {
    this.sz = sz;
    buffer = new byte[(int) this.sz];
  }
}
