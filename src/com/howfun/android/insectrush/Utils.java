package com.howfun.android.insectrush;

import android.util.Log;

public class Utils {
   
   public static final int MSG_UPDATE_SCREEN = 1;
   public static final int MSG_KILL_INSECT = 2;

   public static void log(String tag, String info) {
      Log.e("Insect Rush>>>>>>>>>" + tag, "-------->" + info);
   }
}
