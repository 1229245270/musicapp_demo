package com.example.musicapp.Factory;

import com.example.musicapp.Fragment.TabListen;
import com.example.musicapp.Fragment.TabLook;
import com.example.musicapp.Fragment.TabMe;

import java.util.HashMap;

public class FragmentFactory {
    private static HashMap<Integer,MyFragment> hashMap = new HashMap<>();
    public static MyFragment createFragment(int position){
        MyFragment myFragment = hashMap.get(position);
        if(myFragment == null) {
            switch (position) {
                case 10:
                    myFragment = new TabMe();
                    break;
                case 11:
                    myFragment = new TabListen();
                    break;
                case 12:
                    myFragment = new TabLook();
                    break;
            }
            hashMap.put(position,myFragment);
        }
        return myFragment;
    }
}
