package com.howfun.android.insectrush;

import com.howfun.android.insect.Bug;
import com.howfun.android.insect.Insect;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class MainActivity extends Activity {
   private static final String TAG = "MainActivity";

   private ScreenView mScreenView = null;

   private Context mContext = null;
   private InsectManager mInspectManager = null;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      init();
      findViews();
      setupListeners();
      mInspectManager.setScreenView(mScreenView);
      showWelcomeMessage(mContext, R.string.msg_welcome);
   }

   public void init() {
      mContext = this;
      mInspectManager = new InsectManager(mContext);

   }

   public void findViews() {
      mScreenView = (ScreenView) findViewById(R.id.screen);
   }

   public void setupListeners() {
      if (mScreenView != null) {
         mScreenView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
               // TODO
               int x = (int) event.getX();
               int y = (int) event.getY();
               addInsect(x, y);
               return false;
            }
         });
      }
   }

   private void addInsect(int x, int y) {
      Insect insect = new Bug(mContext, x, y);
      mInspectManager.addInsect2(insect);
   }

   private void showWelcomeMessage(Context context, int stringId) {
      new AlertDialog.Builder(context).setIcon(R.drawable.icon).setTitle(
            R.string.app_name).setMessage(stringId).setPositiveButton(
            android.R.string.ok, null).show();
   }

   public void onResume() {
      super.onResume();
      Toast.makeText(this, R.string.msg_start, Toast.LENGTH_LONG).show();
   }

   public void onPause() {
      super.onPause();
      mInspectManager.clear();
      Toast.makeText(this, R.string.msg_stop, Toast.LENGTH_LONG).show();
   }

}