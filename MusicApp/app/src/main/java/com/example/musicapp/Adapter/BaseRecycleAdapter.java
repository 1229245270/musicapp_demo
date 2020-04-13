package com.example.musicapp.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.MainActivity;
import com.example.musicapp.Model.DrawableBottom;
import com.example.musicapp.Model.DrawableTop;
import com.example.musicapp.Model.Empty;
import com.example.musicapp.Model.Foot;
import com.example.musicapp.Model.GeDan;
import com.example.musicapp.Model.Head;
import com.example.musicapp.Model.PingLun;
import com.example.musicapp.Model.RvZhiBo;
import com.example.musicapp.Model.ShiPin;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.TabListenTop;
import com.example.musicapp.Model.XinGe;
import com.example.musicapp.Model.ZhiBo;
import com.example.musicapp.R;
import com.example.musicapp.Utils.SongListUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseRecycleViewHolder> {
    private Context context;
    private List<T> mData;
    private LayoutInflater inflater;
    private int itemLayoutId;
    public static final int TYPE_XINGE = 0;
    public static final int TYPE_GEDAN = 1;
    public static final int TYPE_ZHIBO = 2;
    public static final int TYPE_SHIPIN = 3;
    public static final int TYPE_PINGLUN = 4;
    public static final int TYPE_EMPTY = 5;
    public static final int TYPE_FOOT = 6;
    public static final int FLOG_TABLISTENALL = 0;
    public static final int FLOG_TABMEDANQU = 1;
    public static final int FLOG_TABSEARCH = 2;
    public static final int FLOG_TABDIALOG = 3;
    public static final int FLOG_TABSONGLIST = 4;
    private RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;
    private View view;
    //必须设置初始值
    private int flog = 99;
    public static int selectedIndex;

    public BaseRecycleAdapter(Context context, List<T> mData, int itemLayoutId){
        this.context = context;
        this.mData = mData;
        this.itemLayoutId = itemLayoutId;
        inflater = LayoutInflater.from(context);
    }
    public BaseRecycleAdapter(Context context, List<T> mData, int itemLayoutId,int flog){
        this.context = context;
        this.mData = mData;
        this.itemLayoutId = itemLayoutId;
        inflater = LayoutInflater.from(context);
        this.flog = flog;
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
    public interface OnItemClickListener{
        void onItemClick(Object object,int position);
        void onItemLongClick(Object object,int position);
    }
    @Override
    public int getItemViewType(int position) {
        if(!SongListUtil.lacksPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) && flog == FLOG_TABMEDANQU){
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

    public int getViewTypeCount(){
        return 7;
    }
    @NonNull
    @Override
    public BaseRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_EMPTY:
                return BaseRecycleViewHolder.getRecycleViewHolder(context,inflater.inflate(R.layout.item_recycleview_empty, parent, false));
            case TYPE_FOOT:
                return BaseRecycleViewHolder.getRecycleViewHolder(context,inflater.inflate(R.layout.item_recycleview_foot, parent, false));
        }
        if(flog == FLOG_TABLISTENALL){
            if(itemLayoutId != 0){
                view = inflater.inflate(itemLayoutId,parent,false);
            }else {
                switch (viewType) {
                    case TYPE_XINGE:
                        view = inflater.inflate(R.layout.item_tablisten_xinge, parent, false);
                        break;
                    case TYPE_GEDAN:
                        view = inflater.inflate(R.layout.item_tablisten_gedan, parent, false);
                        break;
                    case TYPE_ZHIBO:
                        view = inflater.inflate(R.layout.item_recycle, parent, false);
                        break;
                    case TYPE_SHIPIN:
                        view = inflater.inflate(R.layout.item_tablisten_shipin, parent, false);
                        break;
                    case TYPE_PINGLUN:
                        view = inflater.inflate(R.layout.item_tablisten_pinlun, parent, false);
                        break;
                    default:
                        view = null;
                }
            }
        }else{
            view = inflater.inflate(itemLayoutId,parent,false);
        }
        return BaseRecycleViewHolder.getRecycleViewHolder(context,view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseRecycleViewHolder holder, final int position) {
        //本地音乐没有授权时
        if(!SongListUtil.lacksPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) && flog == FLOG_TABMEDANQU){
            holder.getButton(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri packageURI = Uri.fromParts("package", MainActivity.getInstance().getPackageName(), null);
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,packageURI);
                    context.startActivity(intent);
                }
            });
        }else{
            if(onItemClickListener != null && (flog == FLOG_TABMEDANQU || flog == FLOG_TABSEARCH || flog == FLOG_TABDIALOG || flog == FLOG_TABSONGLIST) && getItemViewType(position) != TYPE_FOOT){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(mData.get(position),position);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        onItemClickListener.onItemLongClick(mData.get(position),position);
                        return false;
                    }
                });
            }else if(onItemClickListener != null && flog == FLOG_TABLISTENALL){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(mData.get(position),position);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        onItemClickListener.onItemLongClick(mData.get(position),position);
                        return false;
                    }
                });
            }
            convert(holder, mData.get(position), position);
        }

    }

    @Override
    public int getItemCount() {
        if(!SongListUtil.lacksPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) && flog == FLOG_TABMEDANQU){
            return 1;
        }
        return mData.size();
    }
    public abstract void convert(BaseRecycleViewHolder holder,T item,int position);
}
