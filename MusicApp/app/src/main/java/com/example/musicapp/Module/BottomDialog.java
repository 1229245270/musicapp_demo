package com.example.musicapp.Module;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.musicapp.R;


public class BottomDialog extends Dialog {
    private Activity activity;
    private ListView botDig_lv;
    private ImageView botDig_iv;
    private TextView botDig_tv;
    public BottomDialog(Activity context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.activity = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_dialog);
        setViewLocation();
        setCanceledOnTouchOutside(true);
        botDig_lv = findViewById(R.id.botDig_lv);
        botDig_iv = findViewById(R.id.botDig_iv);
        botDig_tv = findViewById(R.id.botDig_tv);

    }

    private void setViewLocation(){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.x = 0;
        lp.y = height;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        onWindowAttributesChanged(lp);
    }
}
