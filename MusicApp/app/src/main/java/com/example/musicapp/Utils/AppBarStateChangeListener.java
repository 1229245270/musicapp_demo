package com.example.musicapp.Utils;

import android.util.Log;

import com.google.android.material.appbar.AppBarLayout;

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {
    public enum State{
        EXPANDED,
        COLLAPSED,
        IDLE
    }
    private State currentState = State.IDLE;
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if(verticalOffset == 0){
            if(currentState != State.EXPANDED){
                onStateChanged(appBarLayout,State.EXPANDED,verticalOffset);
            }
            currentState = State.EXPANDED;
        }else if(Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
            if(currentState != State.COLLAPSED){
                onStateChanged(appBarLayout,State.COLLAPSED,verticalOffset);
            }
            currentState = State.COLLAPSED;
        }else {
            onStateChanged(appBarLayout, State.IDLE,verticalOffset);
            /*
            if (currentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE);
            }
            currentState = State.IDLE;*/
        }
    }
    public abstract void onStateChanged(AppBarLayout appBarLayout,State state,int verticalOffset);
}
