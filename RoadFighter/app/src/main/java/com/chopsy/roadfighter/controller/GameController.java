package com.chopsy.roadfighter.controller;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.chopsy.roadfighter.R;


public class GameController extends ActionBarActivity {

//    private CarController mCarController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameContext.registerGameController(this);
        setContentView(R.layout.activity_road_fighter_main);
//        mCarController = new CarController(this);
        // Todo: Initialize all controllers

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
        GameContext.getPlayerCarController().startSensorManager();
    }

    @Override
    protected void onStop() {
        GameContext.getPlayerCarController().stopSensorManager();
        super.onStop();
    }
}
