package com.howfun.android.insect;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.widget.ImageView;

public class Footprint extends ImageView {

   public static final int STATE_CLEAR = 0;
   public static final int STATE_DIM = 1;
   public static final int STATE_DEAD = 2;

   public static long CLEAR_DIM_INTERVALS = 1000L;
   public static long CLEAR_DEAD_INTERVALS = 2000L;

   private final int FOOTPRINT_WIDTH = 16;
   private final int FOOTPRINT_HEIGHT = 16;

   private Point mCenter = null;

   private int mRectWidth = FOOTPRINT_WIDTH;
   private int mRectHeight = FOOTPRINT_HEIGHT;
   private Rect mRect = null;

   private int mState; // state 4 changing footprint res,there are 3 states

   private long mCreated; // created-time 4 changing footprint state

   public Footprint(Context context) {
      super(context);
   }

   public void setCenter(Point p) {
      mCenter = p;
      int x = p.x;
      int y = p.y;
      int left = x - mRectWidth / 2;
      int top = y - mRectHeight / 2;
      int right = x + mRectWidth / 2;
      int bottom = y + mRectHeight / 2;
      mRect = new Rect(left, top, right, bottom);
   }

   public int getX() {
      return mCenter.x;
   }

   public int getY() {
      return mCenter.y;
   }

   public Rect getRect() {
      return mRect;
   }

   public void setState(int state) {
      this.mState = state;
   }

   public int getState() {
      return mState;
   }

   public void setCreated(long created) {
      this.mCreated = created;
   }

   public long getIntervals() {
      return System.currentTimeMillis() - mCreated;
   }

}
