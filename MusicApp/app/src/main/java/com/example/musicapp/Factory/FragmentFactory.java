package com.example.musicapp.Factory;

import com.example.musicapp.Fragment.TabHome;
import com.example.musicapp.Fragment.TabListen;
import com.example.musicapp.Fragment.TabListenAll;
import com.example.musicapp.Fragment.TabListenGeDan;
import com.example.musicapp.Fragment.TabListenPingLun;
import com.example.musicapp.Fragment.TabListenShiPin;
import com.example.musicapp.Fragment.TabListenXinGe;
import com.example.musicapp.Fragment.TabListenZhiBo;
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
                    myFragment = new TabHome();
                    break;
                case 11:
                    myFragment = new TabMe();
                    break;
                case 12:
                    myFragment = new TabListen();
                    break;
                case 13:
                    myFragment = new TabLook();
                    break;
                case 121:
                    myFragment = new TabListenAll();
                    break;
                case 122:
                    myFragment = new TabListenXinGe();
                    break;
                case 123:
                    myFragment = new TabListenGeDan();
                    break;
                case 124:
                    myFragment = new TabListenZhiBo();
                    break;
                case 125:
                    myFragment = new TabListenShiPin();
                    break;
                case 126:
                    myFragment = new TabListenPingLun();
                    break;

            }
            hashMap.put(position,myFragment);
        }
        return myFragment;
    }
}
