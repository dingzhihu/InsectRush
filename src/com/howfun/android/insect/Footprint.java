package com.howfun.android.insect;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.widget.ImageView;
import com.howfun.android.insectrush.R;

public class Footprint extends ImageView {

   private final int FOOTPRINT_WIDTH = 20;
   private final int FOOTPRINT_HEIGHT = 20;

   private Point mCenter = null;
   
   private int mRectWidth = FOOTPRINT_WIDTH;
   private int mRectHeight = FOOTPRINT_HEIGHT;
   private Rect mRect = null;

   public Footprint(Context context) {
      super(context);
      setImageResource(R.drawable.bug_footprint0);
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

}
