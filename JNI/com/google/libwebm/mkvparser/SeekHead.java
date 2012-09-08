// Author: mszal@google.com (Michael Szal)

package com.google.libwebm.mkvparser;

import com.google.libwebm.Common;

public class SeekHead extends Common {

  public SeekHead(Segment segment, long start, long size, long elementStart, long elementSize) {
    nativePointer = newSeekHead(segment.getNativePointer(), start, size, elementStart, elementSize);
  }

  public int getCount() {
    return GetCount(nativePointer);
  }

  public long getElementSize() {
    return getElementSize(nativePointer);
  }

  public long getElementStart() {
    return getElementStart(nativePointer);
  }

  public Entry getEntry(int index) {
    long pointer = GetEntry(nativePointer, index);
    return new Entry(pointer);
  }

  public Segment getSegment() {
    long pointer = getSegment(nativePointer);
    return new Segment(pointer);
  }

  public long getSize() {
    return getSize(nativePointer);
  }

  public long getStart() {
    return getStart(nativePointer);
  }

  public VoidElement getVoidElement(int index) {
    long pointer = GetVoidElement(nativePointer, index);
    return new VoidElement(pointer);
  }

  public int getVoidElementCount() {
    return GetVoidElementCount(nativePointer);
  }

  public long parse() {
    return Parse(nativePointer);
  }

  protected SeekHead(long nativePointer) {
    super(nativePointer);
  }

  protected void deleteObject() {
    deleteSeekHead(nativePointer);
  }

  private native void deleteSeekHead(long jSeekHead);
  private native int GetCount(long jSeekHead);
  private native long getElementSize(long jSeekHead);
  private native long getElementStart(long jSeekHead);
  private native long GetEntry(long jSeekHead, int idx);
  private native long getSegment(long jSeekHead);
  private native long getSize(long jSeekHead);
  private native long getStart(long jSeekHead);
  private native long GetVoidElement(long jSeekHead, int idx);
  private native int GetVoidElementCount(long jSeekHead);
  private native long newSeekHead(long jSegment, long start, long size, long element_start,
      long element_size);
  private native long Parse(long jSeekHead);
}
