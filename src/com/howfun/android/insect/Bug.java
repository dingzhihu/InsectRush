package com.howfun.android.insect;

import java.util.Random;
import java.util.Stack;

import com.howfun.android.insectrush.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;

public class Bug extends Insect {

   private final int RECT_WIDTH = 80;
   private final int RECT_HEIGHT = 80;

   public static final int[] FOOTPRINTS = { R.drawable.bug_footprint_north,
         R.drawable.bug_footprint_south, R.drawable.bug_footprint_west,
         R.drawable.bug_footprint_east };

   private static final Random RNG = new Random();

   private AnimationDrawable mAnimation;

   public Bug(Context context, int x, int y) {
      super(context);
      init(x, y);
   }

   private void init(int x, int y) {

      mRectWidth = RECT_WIDTH;
      mRectHeight = RECT_HEIGHT;
      setCenter(new Point(x, y));

      int direction = DIRECTIONS[RNG.nextInt(DIRECTIONS.length)];
      setDirection(direction);

      int step = MOVE_STEPS[RNG.nextInt(MOVE_STEPS.length)];
      setStep(step);

      long life = LIFES[RNG.nextInt(LIFES.length)];
      setLife(life);

      mFootprintStack = new Stack<Footprint>();

      setClickable(false);

      switch (direction) {
      case NORTH:
         this.setBackgroundResource(R.anim.bug_north_anim);
         break;
      case SOUTH:
         this.setBackgroundResource(R.anim.bug_south_anim);
         break;
      case WEST:
         this.setBackgroundResource(R.anim.bug_west_anim);
         break;
      case EAST:
         this.setBackgroundResource(R.anim.bug_east_anim);
         break;
      default:
         break;
      }
      mAnimation = (AnimationDrawable) this.getBackground();
   }

   @Override
   protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);
      if (mAnimation != null) {
         mAnimation.start();
      }
   }

}
