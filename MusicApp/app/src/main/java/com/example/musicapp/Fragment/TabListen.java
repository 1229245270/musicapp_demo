package com.example.musicapp.Fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.TabListenTopAdapter;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.TabListenTop;
import com.example.musicapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabListen extends MyFragment {
    private RecyclerView tabListen_rvTop;
    private int dragFlags,swipeFlags;
    private List<TabListenTop> listenTops = new ArrayList<>();
    private TabListenTopAdapter adapter;
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_listen,null);
        tabListen_rvTop = view.findViewById(R.id.tabListen_rvTop);
        tabListen_rvTop.setLayoutManager(new GridLayoutManager(getContext(), 5, RecyclerView.VERTICAL, false));
        for(int i = 0;i < 5;i++){
            listenTops.add(new TabListenTop(R.drawable.include_default,"wde"+i));
        }
        adapter = new TabListenTopAdapter(getContext(),listenTops);
        tabListen_rvTop.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                if(recyclerView.getLayoutManager() instanceof GridLayoutManager){
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    swipeFlags = 0;
                }else{
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    swipeFlags = 0;
                }
                return makeMovementFlags(dragFlags,swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                //拖拽的viewHolder的position
                int fromPosition = viewHolder.getAdapterPosition();
                //当前拖拽到的item的viewHolder的position
                int toPosition = target.getAdapterPosition();
                if(fromPosition < toPosition){
                    for(int i = fromPosition;i < toPosition;i++){
                        Collections.swap(listenTops,i,i + 1);
                    }
                }else{
                    for(int i = fromPosition;i > toPosition;i--){
                        Collections.swap(listenTops,i,i - 1);
                    }
                }
                adapter.notifyItemMoved(fromPosition,toPosition);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(getContext(), "拖拽方向" + direction, Toast.LENGTH_SHORT).show();
                int position = viewHolder.getAdapterPosition();
                listenTops.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(0);
            }
        });
        itemTouchHelper.attachToRecyclerView(tabListen_rvTop);
        return view;
    }

    @Override
    public void loadData() {

    }
}
