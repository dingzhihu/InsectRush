package com.howfun.android.insectrush;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;

import com.howfun.android.insect.Footprint;
import com.howfun.android.insect.Insect;

public class InsectManager {

   private static final String TAG = "Insect Manager";

   private static final long DELAY_MILLIS = 200L;

   private static final int MSG_UPDATE_SCREEN = 1;
   private static final int MSG_KILL_INSECT = 2;

   private static final int MAX_INSECT_NUM = 20;

   private ScreenView mScreenView;

   private Queue<Insect> mInsectQueue = null;

   private Context mContext = null;
   private Handler mHandler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
         switch (msg.what) {
         case Utils.MSG_UPDATE_SCREEN:
            Insect insect2 = (Insect) msg.obj;
            update2(insect2);
            break;
         case MSG_KILL_INSECT:
            Insect insect = (Insect) msg.obj;
            if (mInsectQueue.contains(insect)) {
               mInsectQueue.remove(insect);
            }
            break;
         default:
            break;
         }
      }
   };

   private Thread mThread = new Thread() {

      public void run() {
         while (true) {
            try {
               sleep(DELAY_MILLIS);
            } catch (InterruptedException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
            mHandler.sendEmptyMessage(MSG_UPDATE_SCREEN);

         }
      }
   };

   public InsectManager(Context context) {
      mContext = context;
      mInsectQueue = new LinkedList<Insect>();
      // mThread.start();
   }

   public void addInsect(Insect insect) {

      if (insect == null) {
         return;
      }

      if (mInsectQueue.size() == MAX_INSECT_NUM) {
         mInsectQueue.remove();
      }

      mInsectQueue.add(insect);
      mScreenView.addView(insect);
      Rect rect = insect.getRect();
      insect.layout(rect.left, rect.top, rect.right, rect.bottom);

      Message msg = new Message();
      msg.what = MSG_KILL_INSECT;
      msg.obj = insect;
      mHandler.sendMessageDelayed(msg, insect.getLife());
   }

   public void addInsect2(Insect insect) {
      if (insect == null) {
         return;
      }

      mInsectQueue.add(insect);
      mScreenView.addView(insect);
      Rect rect = insect.getRect();
      insect.layout(rect.left, rect.top, rect.right, rect.bottom);
      new InsectThread(insect, mHandler, mContext).start();
   }

   private void setNextPosition() {

      if (mInsectQueue.size() == 0)
         return;

      Iterator<Insect> it = mInsectQueue.iterator();
      while (it.hasNext()) {
         Insect insect = it.next();
         int x = insect.getCenterX();
         int y = insect.getCenterY();
         int step = insect.getStep();
         int direction = insect.getDirection();
         switch (direction) {
         case Insect.NORTH:
            insect.setCenter(x, y - step);
            break;
         case Insect.SOUTH:
            insect.setCenter(x, y + step);
            break;
         case Insect.WEST:
            insect.setCenter(x - step, y);
            break;
         case Insect.EAST:
            insect.setCenter(x + step, y);
            break;
         default:
            break;
         }
      }

   }

   private void setNextPosition2(Insect insect) {

      if (insect == null) {
         return;
      }
      int x = insect.getCenterX();
      int y = insect.getCenterY();
      int step = insect.getStep();
      int direction = insect.getDirection();
      switch (direction) {
      case Insect.NORTH:
         insect.setCenter(x, y - step);
         break;
      case Insect.SOUTH:
         insect.setCenter(x, y + step);
         break;
      case Insect.WEST:
         insect.setCenter(x - step, y);
         break;
      case Insect.EAST:
         insect.setCenter(x + step, y);
         break;
      default:
         break;
      }

   }

   private void renderScreen() {

      if (mInsectQueue.size() == 0) {
         return;
      }

      mScreenView.removeAllViews();
      Iterator<Insect> it = mInsectQueue.iterator();
      while (it.hasNext()) {
         Insect insect = it.next();
         Rect rect = insect.getRect();
         mScreenView.addView(insect);
         insect.layout(rect.left, rect.top, rect.right, rect.bottom);
      }

   }

   private void renderScreen2(Insect insect) {
      if (insect == null) {
         return;
      }

      mScreenView.removeView(insect);

      Rect rect = insect.getRect();
      mScreenView.addView(insect);
      insect.layout(rect.left, rect.top, rect.right, rect.bottom);

      Footprint foot = insect.getFootprintStack().peek();

      Rect rect2 = foot.getRect();
      mScreenView.addView(foot);
      foot.layout(rect2.left, rect2.top, rect2.right, rect2.bottom);
      // Iterator<Footprint> it = insect.getFootprintStack().iterator();
      // while (it.hasNext()) {
      // Footprint footprint = it.next();
      // mScreenView.removeView(footprint);
      // Rect rect2 = footprint.getRect();
      // mScreenView.addView(footprint);
      // footprint.layout(rect2.left, rect2.top, rect2.right, rect2.bottom);
      // }
   }

   private void update() {
      setNextPosition();
      renderScreen();
   }

   private void update2(Insect insect) {
      setNextPosition2(insect);
      renderScreen2(insect);
   }

   public void clear() {
      mInsectQueue.clear();
      mScreenView.removeAllViews();
   }

   public void setScreenView(ScreenView screenView) {
      this.mScreenView = screenView;
   }

}
