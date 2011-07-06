package com.howfun.android.insect;

import java.util.Queue;
import java.util.Stack;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.widget.ImageView;

public class Insect extends ImageView {
   public static final int NORTH = 1;
   public static final int SOUTH = 2;
   public static final int WEST = 3;
   public static final int EAST = 4;

   protected static final int[] DIRECTIONS = { NORTH, SOUTH, WEST, EAST };
   protected static final int[] MOVE_STEPS = { 5, 10, 15 };
   protected static final long[] LIFES = { 10000L };

   protected int mBirthX;
   protected int mBirthY;
   protected int mCenterX;
   protected int mCenterY;
   protected int mRectWidth;
   protected int mRectHeight;
   protected Rect mRect;

   private int mStep;
   protected int mDirection;
   private long mLife;

   protected Stack<Footprint> mFootprintStack = null;

   public Insect(Context context) {
      super(context);
   }

   public void setBirth(int x, int y) {
      this.mBirthX = x;
      this.mBirthY = y;
   }

   public int getBirthX() {
      return mBirthX;
   }

   public int getBirthY() {
      return mBirthY;
   }

   public void setCenter(int x, int y) {
      this.mCenterX = x;
      this.mCenterY = y;

      int left = mCenterX - mRectWidth / 2;
      int top = mCenterY - mRectHeight / 2;
      int right = mCenterX + mRectWidth / 2;
      int bottom = mCenterY + mRectHeight / 2;
      mRect = new Rect(left, top, right, bottom);
   }

   public int getCenterX() {
      return mCenterX;
   }

   public int getCenterY() {
      return mCenterY;
   }

   public Rect getRect() {
      return mRect;
   }

   public void setStep(int step) {
      mStep = step;
   }

   public int getStep() {
      return mStep;
   }

   public void setDirection(int direction) {
      this.mDirection = direction;
   }

   public int getDirection() {
      return this.mDirection;
   }

   public void setLife(long life) {
      this.mLife = life;
   }

   public long getLife() {
      return this.mLife;
   }

   public Point getFootprintPos() {
      int x = 0;
      int y = 0;
      switch (mDirection) {
      case NORTH:
         x = mCenterX;
         y = mCenterY + mRectHeight / 2;
         break;
      case SOUTH:
         x = mCenterX;
         y = mCenterY - mRectHeight / 2;
         break;
      case WEST:
         x = mCenterX + mRectWidth / 2;
         y = mCenterY;
         break;
      case EAST:
         x = mCenterX - mRectWidth / 2;
         y = mCenterY;
         break;
      default:
         break;
      }
      return new Point(x, y);
   }

   public Stack<Footprint> getFootprintStack() {
      return mFootprintStack;
   }

}
