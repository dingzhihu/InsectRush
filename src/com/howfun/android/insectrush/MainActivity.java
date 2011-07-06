package com.howfun.android.insectrush;

import com.howfun.android.insect.Bug;
import com.howfun.android.insect.Insect;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;

public class MainActivity extends Activity {

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
   }

   public void init() {
      mContext = this;
      mInspectManager = new InsectManager();

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
      mInspectManager.addInsect(insect);
   }
}