package com.chopsy.roadfighter.controller;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.chopsy.roadfighter.R;
import com.chopsy.roadfighter.model.RaceStatus;


public class GameController extends ActionBarActivity {

    private float mBackgroundOneTop = 0;
    private float mBackgroundTwoTop = 0;
    private ImageView backgroundOne;
    private ImageView backgroundTwo;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameContext.registerGameController(this);
        setContentView(R.layout.activity_road_fighter_main);
    }

    public void startController() {
        backgroundOne = (ImageView) findViewById(R.id.game_background1);
        backgroundTwo = (ImageView) findViewById(R.id.game_background2);
        height = backgroundOne.getHeight();
        mBackgroundOneTop = -1.0f * backgroundOne.getHeight();
        mBackgroundTwoTop = 0;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_road_fighter_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (GameContext.getCurrentRaceStatus() == RaceStatus.PLAYING) {
            GameContext.getPlayerCarController().startSensorManager();
        }
    }

    @Override
    protected void onStop() {
        if (GameContext.getCurrentRaceStatus() == RaceStatus.PLAYING) {
            GameContext.getPlayerCarController().stopSensorManager();
        }
        super.onStop();
    }

    public void updateBackground(int speed) {
        height = backgroundOne.getHeight();
        float translateY = height * speed * 0.01f;
        mBackgroundOneTop += translateY;
        mBackgroundTwoTop += translateY;
        if (mBackgroundOneTop > (float) height) {
            mBackgroundOneTop = -1.0f * backgroundOne.getHeight();
        }
        if (mBackgroundTwoTop > height) {
            mBackgroundTwoTop = -1.0f * backgroundOne.getHeight();
        }
        if (mBackgroundTwoTop < 0) {
            backgroundOne.setTranslationY(mBackgroundTwoTop + height);
        } else {
            backgroundOne.setTranslationY(mBackgroundTwoTop - height);
        }
        backgroundTwo.setTranslationY(mBackgroundTwoTop);
    }
}
