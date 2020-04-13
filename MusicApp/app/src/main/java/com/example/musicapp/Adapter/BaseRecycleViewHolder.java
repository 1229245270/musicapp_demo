package com.example.musicapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.example.musicapp.Module.CircularImageView;
import com.example.musicapp.Module.NumberImageView;
import com.example.musicapp.R;
import com.squareup.picasso.Picasso;


import java.security.MessageDigest;

import cn.jzvd.JzvdStd;

import static com.bumptech.glide.load.resource.bitmap.VideoDecoder.FRAME_OPTION;
import static com.example.musicapp.Adapter.BaseRecycleAdapter.selectedIndex;

public class BaseRecycleViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;
    private Context context;
    public static int SUPERVIEW_RELEASE = 0;
    public static int SUPERVIEW_PAUSE = 1;

    public interface OnItemClickListener {
        void onItemClick(View view ,int position);
        void onItemLongClick(View view ,int position);
    }

    public BaseRecycleViewHolder(Context context,@NonNull View itemView) {
        super(itemView);
        this.context = context;
        views = new SparseArray<>();
    }
    public static BaseRecycleViewHolder getRecycleViewHolder(Context context,View itemView){
        return new BaseRecycleViewHolder(context,itemView);
    }
    public SparseArray<View> getViews(){
        return views;
    }
    public <T extends View> T getView(int viewId){
        View view = views.get(viewId);
        if(view == null){
            view = itemView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T) view;
    }
    public BaseRecycleViewHolder setText(int viewId,String text){
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }
    public BaseRecycleViewHolder setImageResource(int viewId,Object drawableId){
        ImageView imageView = getView(viewId);
        if(drawableId != null){
            if(drawableId instanceof String && drawableId != ""){
                Picasso.get().load((String)drawableId).error(R.drawable.include_default).into(imageView);
            }else if(drawableId instanceof Integer){
                imageView.setImageResource((Integer) drawableId);
            }else{
                imageView.setImageResource(R.drawable.include_default);
            }
        }else{
            imageView.setImageResource(R.drawable.include_default);
        }
        return this;
    }
    public BaseRecycleViewHolder setImageNumber(int viewId,int num){
        NumberImageView numberImageView = getView(viewId);
        numberImageView.setNum(num);
        return this;
    }
    public RecyclerView getRecycleView(int viewId){
        RecyclerView recyclerView = getView(viewId);
        return recyclerView;
    }
    public Button getButton(int viewId){
        Button button = getView(viewId);
        return button;
    }
    public BaseRecycleViewHolder setVisibility(int viewId,boolean visibility){
        View view = getView(viewId);
        view.setVisibility(visibility ? View.VISIBLE : View.GONE);
        return this;
    }
    public BaseRecycleViewHolder setSelect(int showView,int hideView,boolean boo){
        if(hideView != 0){
            View hide =  getView(hideView);
            hide.setVisibility(boo ? View.VISIBLE : View.GONE);
        }
        if(showView != 0){
            View show = getView(showView);
            show.setVisibility(boo ? View.GONE : View.VISIBLE);
        }
        return this;
    }
    public BaseRecycleViewHolder setSelectColor(int tv,boolean boo,String newColor,String oldColor){
        TextView textView = getView(tv);
        if(boo){
            textView.setTextColor(Color.parseColor(newColor));
        }else{
            textView.setTextColor(Color.parseColor(oldColor));
        }
        return this;
    }

    public BaseRecycleViewHolder setHeightAndWidth(Activity activity, int id){
        CircularImageView circularImageView = getView(id);
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) circularImageView.getLayoutParams();
        layoutParams.height = display.getWidth()/2 - 40;
        layoutParams.width = display.getWidth()/2 - 40;
        circularImageView.setLayoutParams(layoutParams);
        return this;
    }

    public BaseRecycleViewHolder setTextSize(int tv,int textSize){
        TextView textView = getView(tv);
        textView.setTextSize(textSize);
        return this;
    }
    public BaseRecycleViewHolder setVideoURL(Context context,int videoView,String url,String title){
        JzvdStd jzvdStd = getView(videoView);
        jzvdStd.setUp(url,title);
        return this;
    }
    /*
    public BaseRecycleViewHolder setVideoPlay(int videoView,int play){
        if(getView(videoView) instanceof SuperPlayerView) {
            SuperPlayerView superPlayerView = getView(videoView);
            if(play == SUPERVIEW_RELEASE){
                superPlayerView.release();
                if (superPlayerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT){
                    superPlayerView.resetPlayer();
                }
            }else if(play == SUPERVIEW_PAUSE){
                if (superPlayerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
                    superPlayerView.onPause();
                }
            }
        }
        return this;
    }*/
}
