package com.howfun.android.insectrush;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;

import com.howfun.android.insect.Footprint;
import com.howfun.android.insect.Insect;

public class InsectManager {

   private static final String TAG = "Insect Manager";

   private static final int MAX_INSECT_NUM = 3;

   private ScreenView mScreenView;

   private Queue<InsectThread> mInsectThreadQueue = null;

   private Context mContext = null;
   private Handler mHandler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
         switch (msg.what) {
         case Utils.MSG_UPDATE_SCREEN:
            Insect insect = (Insect) msg.obj;
            update(insect);
            break;
         case Utils.MSG_KILL_INSECT:
            break;
         default:
            break;
         }
      }
   };

   public InsectManager(Context context) {
      mContext = context;
      mInsectThreadQueue = new LinkedList<InsectThread>();
   }

   public void addInsect(Insect insect) {
      if (insect == null) {
         return;
      }
      if (mInsectThreadQueue.size() == 1) {
         InsectThread insectThread = mInsectThreadQueue.remove();
         insectThread.kill();
         Insect oldInsect = insectThread.getInsect();
         Stack<Footprint> footprintStack = insectThread.getFootprintStack();
         Iterator<Footprint> it = footprintStack.iterator();
         while (it.hasNext()) {
            Footprint footprint = it.next();
            mScreenView.removeView(footprint);
         }
         footprintStack.clear();
         mScreenView.removeView(oldInsect);
      }

      mScreenView.addView(insect);
      Rect rect = insect.getRect();
      insect.layout(rect.left, rect.top, rect.right, rect.bottom);
      InsectThread thread = new InsectThread(insect, mHandler, mContext);
      mInsectThreadQueue.add(thread);
      thread.start();
   }

   private void setNextPosition(Insect insect) {

      if (insect == null) {
         return;
      }
      int x = insect.getX();
      int y = insect.getY();
      int step = insect.getStep();
      int direction = insect.getDirection();
      switch (direction) {
      case Insect.NORTH:
         insect.setCenter(new Point(x, y - step));
         break;
      case Insect.SOUTH:
         insect.setCenter(new Point(x, y + step));
         break;
      case Insect.WEST:
         insect.setCenter(new Point(x - step, y));
         break;
      case Insect.EAST:
         insect.setCenter(new Point(x + step, y));
         break;
      default:
         break;
      }

   }

   private void renderScreen(Insect insect) {
      if (insect == null) {
         return;
      }

      mScreenView.removeView(insect);

      Rect rect = insect.getRect();
      mScreenView.addView(insect);
      insect.layout(rect.left, rect.top, rect.right, rect.bottom);

      Stack<Footprint> stack = insect.getFootprintStack();
      Footprint foot = stack.peek();

      Rect rect2 = foot.getRect();
      mScreenView.addView(foot);
      foot.layout(rect2.left, rect2.top, rect2.right, rect2.bottom);
      Iterator<Footprint> it = stack.iterator();
      while (it.hasNext()) {
         Footprint print = it.next();
         int state = print.getState();
         long intervals = print.getIntervals();
         if (intervals > Footprint.CLEAR_DEAD_INTERVALS) {
            if (state != Footprint.STATE_DEAD) {
               print.setState(Footprint.STATE_DEAD);
               print.setImageResource(R.drawable.transparent);
            }
         } else if (intervals > Footprint.CLEAR_DIM_INTERVALS) {
            if (state != Footprint.STATE_DIM) {
               print.setState(Footprint.STATE_DIM);
               // print.setImageResource(R.drawable.bug_footprint1);
            }
         }
      }
   }

   private void update(Insect insect) {
      setNextPosition(insect);
      renderScreen(insect);
   }

   public void clear() {
      Iterator<InsectThread> it = mInsectThreadQueue.iterator();
      while (it.hasNext()) {
         InsectThread insectThread = it.next();
         insectThread.kill();
      }
      mInsectThreadQueue.clear();
      mScreenView.removeAllViews();
   }

   public void setScreenView(ScreenView screenView) {
      this.mScreenView = screenView;
   }

}
