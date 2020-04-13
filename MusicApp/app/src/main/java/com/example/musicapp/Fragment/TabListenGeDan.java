package com.example.musicapp.Fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.Factory.FragmentFactory;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.GeDan;
import com.example.musicapp.Model.GetListList.GetListList;
import com.example.musicapp.Model.GetListList.data;
import com.example.musicapp.Module.AutoLayoutManage;
import com.example.musicapp.Module.AutoViewPager;
import com.example.musicapp.R;
import com.example.musicapp.Utils.RetrofitUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.example.musicapp.Adapter.BaseRecycleAdapter.FLOG_TABLISTENALL;
import static com.example.musicapp.Utils.RetrofitUtil.GetListListForTime;

public class TabListenGeDan extends MyFragment {
    private RecyclerView recyclerView;
    private BaseRecycleAdapter<data> adapter;
    private Fragment Home_Fragment,TabSongList;

    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_listen_gedan,null);
        recyclerView = view.findViewById(R.id.recycleView);
        TabSongList = FragmentFactory.createFragment(116);
        Home_Fragment = FragmentFactory.createFragment(10);
        return view;
    }

    @Override
    public void loadData() {
        RetrofitUtil retrofitUtil = new RetrofitUtil(getContext(),GetListListForTime,null,new int[]{0,10});
        retrofitUtil.setAnInterface(new RetrofitUtil.AnInterface() {
            @Override
            public void setObject(Object object) {
                GetListList getListList = (GetListList) object;
                if(getListList != null){
                    List<data> listData = getListList.getData();
                    if(listData != null){
                        adapter = new BaseRecycleAdapter<data>(getContext(),listData,R.layout.item_tablisten_gedan,FLOG_TABLISTENALL) {
                            @Override
                            public void convert(BaseRecycleViewHolder holder, data item, int position) {
                                holder.setImageResource(R.id.imageView,item.getImg());
                                holder.setText(R.id.tag,item.getTag());
                                holder.setText(R.id.listener,String.valueOf(item.getListenNum()));
                                holder.setText(R.id.songListName,item.getListName());
                                holder.setText(R.id.songListIntro,item.getIntro());
                                holder.setText(R.id.getLike,item.getUserName());
                                holder.setText(R.id.comment,String.valueOf(item.getTotal()));
                            }
                        };
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Object object, int position) {
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                transaction.hide(Home_Fragment);
                                Bundle bundle = new Bundle();
                                bundle.putString("userName",((data) object).getUserName());
                                bundle.putString("listName",((data) object).getListName());
                                bundle.putString("listImage",((data) object).getImg());
                                TabSongList.setArguments(bundle);
                                if (!TabSongList.isAdded())
                                    transaction.add(R.id.frameLayout_main, TabSongList).show(TabSongList);
                                else transaction.show(TabSongList);
                                transaction
                                        .addToBackStack(null)
                                        .commit();
                            }
                            @Override
                            public void onItemLongClick(Object object, int position) {

                            }
                        });
                    }
                }
            }
        });
    }
}
