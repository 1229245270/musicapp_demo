package com.example.musicapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicapp.Model.DrawableBottom;
import com.example.musicapp.Model.DrawableTop;
import com.example.musicapp.R;

import java.util.ArrayList;

public class DrawableAdapter extends BaseAdapter {
    private static final int TYPE_DRAWABLE_TOP = 0;
    private static final int TYPE_DRAWABLE_BOTTOM = 1;
    private Context context;
    private ArrayList<Object> mData = null;
    public DrawableAdapter(Context context,ArrayList<Object> mData){
        this.context = context;
        this.mData = mData;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public int getItemViewType(int position){
        if(mData.get(position) instanceof DrawableTop){
            return TYPE_DRAWABLE_TOP;
        }else if(mData.get(position) instanceof DrawableBottom){
            return TYPE_DRAWABLE_BOTTOM;
        }else{
            return super.getItemViewType(position);
        }
    }
    public int getViewTypeCount(){
        return 2;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int type = getItemViewType(i);
        if(view == null){
            switch (type){
                case TYPE_DRAWABLE_TOP:
                    view = LayoutInflater.from(context).inflate(R.layout.item_drawable_top,viewGroup,false);
                    new ViewHolder1(view);
                    break;
                case TYPE_DRAWABLE_BOTTOM:
                    view =LayoutInflater.from(context).inflate(R.layout.item_drawable_bottom,viewGroup,false);
                     new ViewHolder2(view);
                    break;
            }
        }
        ViewHolder1 viewHolder1 = null;
        ViewHolder2 viewHolder2 = null;
        Object obj = mData.get(i);
        switch (type){
            case TYPE_DRAWABLE_TOP:
                DrawableTop drawableTop = (DrawableTop) obj;
                if(drawableTop != null){
                    viewHolder1 = (ViewHolder1) view.getTag();
                    viewHolder1.image.setImageResource(drawableTop.getImage());
                    viewHolder1.text.setText(drawableTop.getText());
                }
                break;
            case TYPE_DRAWABLE_BOTTOM:
                DrawableBottom drawableBottom = (DrawableBottom) obj;
                if(drawableBottom != null){
                    viewHolder2 = (ViewHolder2) view.getTag();
                    viewHolder2.icon.setImageResource(drawableBottom.getIcon());
                    viewHolder2.title.setText(drawableBottom.getTitle());
                    if(drawableBottom.isHava()){
                        viewHolder2.isHave.setVisibility(View.VISIBLE);
                    }else{
                        viewHolder2.isHave.setVisibility(View.GONE);
                    }
                    viewHolder2.hint.setText(drawableBottom.getHint());
                    if(drawableBottom.getHintImage() != 0){
                        viewHolder2.hintImage.setVisibility(View.VISIBLE);
                        viewHolder2.hintImage.setImageResource(drawableBottom.getHintImage());
                    }else{
                        viewHolder2.hintImage.setVisibility(View.GONE);
                    }
                }
        }
        return view;
    }

    class ViewHolder1{
        ImageView image;
        TextView text;
        ViewHolder1(View view){
            image = view.findViewById(R.id.image);
            text = view.findViewById(R.id.text);
            view.setTag(this);
        }
    }
    class ViewHolder2{
        ImageView icon,hintImage,isHave;
        TextView title,hint;
        ViewHolder2(View view){
            icon = view.findViewById(R.id.icon);
            hintImage = view.findViewById(R.id.hintImage);
            isHave = view.findViewById(R.id.isHave);
            title = view.findViewById(R.id.title);
            hint = view.findViewById(R.id.hint);
            view.setTag(this);
        }
    }
}