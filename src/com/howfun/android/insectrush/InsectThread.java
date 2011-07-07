package com.howfun.android.insectrush;

import java.util.Random;
import java.util.Stack;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;

import com.howfun.android.insect.Bug;
import com.howfun.android.insect.Footprint;
import com.howfun.android.insect.Insect;

public class InsectThread extends Thread {

   private static final String TAG = "InsectThread";

   private static final long DELAY_MILLIS = 200L;

   private static final Random RNG = new Random();
   private static long count = 0;

   private Insect mInsect = null;
   private Stack<Footprint> mStack = null;
   private Handler mHandler = null;
   private Context mContext = null;

   private long mId;
   private boolean flag = true;

   public InsectThread(Insect insect, Handler handler, Context context) {
      mId = count++;
      mInsect = insect;
      mStack = insect.getFootprintStack();
      mHandler = handler;
      mContext = context;
   }

   @Override
   public void run() {
      while (flag) {
         try {
            sleep(DELAY_MILLIS);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         // TODO add a footprint
         Footprint footprint = new Footprint(mContext);
         footprint.setCreated(System.currentTimeMillis());
         footprint.setState(Footprint.STATE_CLEAR);
         int which = RNG.nextInt(Bug.FOOTPRINTS.length);
         footprint.setImageResource(R.drawable.bug_shit);
         Point p = mInsect.getFootprintPos();
         footprint.setCenter(p);
         mStack.push(footprint);

         Message msg = new Message();
         msg.what = Utils.MSG_UPDATE_SCREEN;
         msg.obj = mInsect;
         mHandler.sendMessage(msg);
      }
   }

   public Insect getInsect() {
      return mInsect;
   }

   public Stack<Footprint> getFootprintStack() {
      return mStack;
   }

   public void kill() {
      flag = false;
   }

}
