package com.example.musicapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Model.DrawableBottom;
import com.example.musicapp.Model.DrawableTop;
import com.example.musicapp.Model.Empty;
import com.example.musicapp.Model.Foot;
import com.example.musicapp.Model.GeDan;
import com.example.musicapp.Model.Head;
import com.example.musicapp.Model.PingLun;
import com.example.musicapp.Model.RvZhiBo;
import com.example.musicapp.Model.ShiPin;
import com.example.musicapp.Model.TabListenTop;
import com.example.musicapp.Model.XinGe;
import com.example.musicapp.Model.ZhiBo;
import com.example.musicapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

import static com.example.musicapp.MainActivity.isPerMissRead;

public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseRecycleViewHolder> {
    private Context context;
    private List<T> mData;
    private LayoutInflater inflater;
    private int itemLayoutId;
    private boolean isTabListenAll = false;
    public static final int TYPE_XINGE = 0;
    public static final int TYPE_GEDAN = 1;
    public static final int TYPE_ZHIBO = 2;
    public static final int TYPE_SHIPIN = 3;
    public static final int TYPE_PINGLUN = 4;
    public static final int TYPE_EMPTY = 5;
    public static final int TYPE_FOOT = 6;
    private RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;
    private View view;
    private boolean isPerMiss = false;

    public BaseRecycleAdapter(Context context, List<T> mData, int itemLayoutId){
        this.context = context;
        this.mData = mData;
        this.itemLayoutId = itemLayoutId;
        inflater = LayoutInflater.from(context);
    }
    public BaseRecycleAdapter(Context context, List<T> mData){
        this.context = context;
        this.mData = mData;
        isTabListenAll = true;
        inflater = LayoutInflater.from(context);
    }
    public BaseRecycleAdapter(Context context, List<T> mData, int itemLayoutId,boolean isPerMiss){
        this.context = context;
        this.mData = mData;
        this.itemLayoutId = itemLayoutId;
        inflater = LayoutInflater.from(context);
        this.isPerMiss = isPerMiss;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public interface  OnItemClickListener{
        void onItemClick(int position);
        void onItemLongClick(int position);
    }
    @Override
    public int getItemViewType(int position) {
        if(!isPerMissRead && isPerMiss){
            return TYPE_EMPTY;
        }else if(mData.get(position) instanceof Foot){
            return TYPE_FOOT;
        }else if(mData.get(position) instanceof XinGe){
            return TYPE_XINGE;
        }else if(mData.get(position) instanceof GeDan){
            return TYPE_GEDAN;
        }else if(mData.get(position) instanceof RvZhiBo){
            return TYPE_ZHIBO;
        }else if(mData.get(position) instanceof ShiPin){
            return TYPE_SHIPIN;
        }else if(mData.get(position) instanceof PingLun){
            return TYPE_PINGLUN;
        }else{
            return super.getItemViewType(position);
        }
    }

    /*public int getViewTypeCount(){
        return 5;
    }*/
    @NonNull
    @Override
    public BaseRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_EMPTY:
                return BaseRecycleViewHolder.getRecycleViewHolder(context,inflater.inflate(R.layout.item_recycleview_empty, parent, false));
            case TYPE_FOOT:
                return BaseRecycleViewHolder.getRecycleViewHolder(context,inflater.inflate(R.layout.item_recycleview_foot, parent, false));
        }
        if(isTabListenAll){
            switch (viewType){
                case TYPE_XINGE:
                    view = inflater.inflate(R.layout.item_tablisten_xinge,parent,false);
                    break;
                case TYPE_GEDAN:
                    view = inflater.inflate(R.layout.item_tablisten_gedan,parent,false);
                    break;
                case TYPE_ZHIBO:
                    view = inflater.inflate(R.layout.item_recycle,parent,false);
                    break;
                case TYPE_SHIPIN:
                    view = inflater.inflate(R.layout.item_tablisten_shipin,parent,false);
                    break;
                case TYPE_PINGLUN:
                    view = inflater.inflate(R.layout.item_tablisten_pinlun,parent,false);
                    break;
                default:
                    view = null;
            }
        }else{
            view = inflater.inflate(itemLayoutId,parent,false);
        }
        return BaseRecycleViewHolder.getRecycleViewHolder(context,view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecycleViewHolder holder, final int position) {
        if(!isPerMissRead && isPerMiss){
            holder.getButton(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "点击了", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            if(onItemClickListener != null && (getItemViewType(position) != TYPE_FOOT)){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(position);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        onItemClickListener.onItemLongClick(position);
                        return false;
                    }
                });
            }
            convert(holder, mData.get(position), position);
        }

    }

    @Override
    public int getItemCount() {
        if(!isPerMissRead && isPerMiss){
            return 1;
        }
        return mData.size();
    }

    public abstract void convert(BaseRecycleViewHolder holder,T item,int position);
}
