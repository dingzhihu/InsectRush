package com.howfun.android.insectrush;

import java.util.Queue;
import java.util.Stack;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;

import com.howfun.android.insect.Footprint;
import com.howfun.android.insect.Insect;

public class InsectThread extends Thread {

   private static final long DELAY_MILLIS = 200L;

   private static final int MSG_UPDATE_SCREEN = 1;

   private Insect mInsect = null;
   private Stack<Footprint> mStack = null;
   private Handler mHandler = null;
   private Context mContext = null;

   public InsectThread(Insect insect, Handler handler, Context context) {
      mInsect = insect;
      mStack = insect.getFootprintStack();
      mHandler = handler;
      mContext = context;
   }

   @Override
   public void run() {
      while (true) {
         try {
            sleep(DELAY_MILLIS);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         // TODO add a footprint
         Footprint footprint = new Footprint(mContext);
         Point p = mInsect.getFootprintPos();
         footprint.setCenter(p);
         mStack.push(footprint);
         Message msg = new Message();
         msg.what = Utils.MSG_UPDATE_SCREEN;
         msg.obj = mInsect;
         mHandler.sendMessage(msg);
      }
   }

}
