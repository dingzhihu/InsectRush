package com.howfun.android.insect;

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
   protected static final int[] MOVE_STEPS = { 32, 64 };
   protected static final long[] LIFES = { 10000L };
   
   protected static long count = 0;

   protected Point mCenter;
   protected int mRectWidth;
   protected int mRectHeight;
   protected Rect mRect;

   protected long mId;
   private int mStep;
   protected int mDirection;
   private long mLife;

   protected Stack<Footprint> mFootprintStack = null;

   public Insect(Context context) {
      super(context);
      mId = count ++;
   }

   public void setCenter(Point p) {
      int x = p.x;
      int y = p.y;
      mCenter = new Point(x, y);

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

   public long getInsectId(){
      return mId;
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
         x = mCenter.x;
         y = mCenter.y + mRectHeight / 2;
         break;
      case SOUTH:
         x = mCenter.x;
         y = mCenter.y - mRectHeight / 2;
         break;
      case WEST:
         x = mCenter.x + mRectWidth / 2;
         y = mCenter.y;
         break;
      case EAST:
         x = mCenter.x - mRectWidth / 2;
         y = mCenter.y;
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
