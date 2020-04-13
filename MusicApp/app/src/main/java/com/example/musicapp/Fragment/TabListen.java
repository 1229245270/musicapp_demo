package com.example.musicapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.Adapter.MyPagerAdapter;
import com.example.musicapp.Factory.FragmentFactory;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.GeDan;
import com.example.musicapp.Model.GetListList.GetListList;
import com.example.musicapp.Model.GetListList.data;
import com.example.musicapp.Model.TabListenDate;
import com.example.musicapp.Model.TabListenTop;
import com.example.musicapp.Module.AutoLayoutManage;
import com.example.musicapp.Module.AutoViewPager;
import com.example.musicapp.R;
import com.example.musicapp.Utils.RetrofitUtil;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.example.musicapp.Adapter.BaseRecycleAdapter.FLOG_TABLISTENALL;
import static com.example.musicapp.Utils.RetrofitUtil.GetCommentListForGetLike;
import static com.example.musicapp.Utils.RetrofitUtil.GetListListForTime;

public class TabListen extends MyFragment {
    private String TAG = "TabListen";
    private RecyclerView tabListen_rvTop,tabListen_rvGeDan;
    //private int dragFlags,swipeFlags;
    private List<TabListenTop> listenTops;
    private BaseRecycleAdapter<TabListenTop> tabListenTopBaseRecycleAdapter;
    private BaseRecycleAdapter<data> dataBaseRecycleAdapter;
    private Banner tabListen_banner;
    private TabLayout tabListen_tabLayout;
    private ViewPager tabListen_viewPager;
    private Fragment Home_Fragment,TabSongList;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(String string) {

    }

    @Override
    public View initView() {
        EventBus.getDefault().register(this);
        View view = View.inflate(getContext(), R.layout.fragment_listen,null);
        tabListen_banner = view.findViewById(R.id.tabListen_banner);
        tabListen_rvGeDan = view.findViewById(R.id.tabListen_rvGeDan);
        tabListen_rvTop = view.findViewById(R.id.tabListen_rvTop);
        tabListen_tabLayout = view.findViewById(R.id.tabListen_tabLayout);
        tabListen_viewPager = view.findViewById(R.id.tabListen_viewPager);

        TabSongList = FragmentFactory.createFragment(116);
        Home_Fragment = FragmentFactory.createFragment(10);
        return view;
    }

    @Override
    public void loadData() {

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.glideimg1);
        images.add(R.drawable.glideimg2);
        images.add(R.drawable.glideimg3);
        tabListen_banner.setImages(images);
        tabListen_banner.setImageLoader(new GlideImageLoader());
        tabListen_banner.start();

        listenTops = new ArrayList<>();
        listenTops.add(new TabListenTop(R.drawable.tablisten_yueku,"乐库"));
        listenTops.add(new TabListenTop(R.drawable.tablisten_guesslike,"猜你喜欢"));
        listenTops.add(new TabListenTop(R.drawable.tablisten_gedan,"歌单"));
        listenTops.add(new TabListenTop(R.drawable.tablisten_rank,"排行榜"));
        listenTops.add(new TabListenTop(R.drawable.tablisten_more,"更多"));
        tabListenTopBaseRecycleAdapter = new BaseRecycleAdapter<TabListenTop>(getContext(),listenTops,R.layout.item_tablisten_top) {
            @Override
            public void convert(BaseRecycleViewHolder holder, TabListenTop item, int position) {
                holder.setText(R.id.textView,item.getText());
                holder.setImageResource(R.id.imageView,item.getImage());
            }
        };

        tabListen_rvTop.setLayoutManager(new GridLayoutManager(getContext(), 5, RecyclerView.VERTICAL, false));
        tabListen_rvTop.setAdapter(tabListenTopBaseRecycleAdapter);
        RetrofitUtil retrofitUtil = new RetrofitUtil(getContext(),GetListListForTime,null,new int[]{0,6});
        retrofitUtil.setAnInterface(new RetrofitUtil.AnInterface() {
            @Override
            public void setObject(Object object) {
                GetListList getListList = (GetListList) object;
                if(getListList != null){
                    List<data> dataList = getListList.getData();
                    if(dataList != null){
                        dataBaseRecycleAdapter = new BaseRecycleAdapter<data>(getContext(), dataList, R.layout.item_tablisten_date,FLOG_TABLISTENALL) {
                            @Override
                            public void convert(BaseRecycleViewHolder holder, data item, int position) {
                                holder.setImageResource(R.id.imageView,item.getImg());
                                holder.setText(R.id.listenerNumber,String.valueOf(item.getListenNum()));
                                holder.setText(R.id.textView,item.getListName());
                            }
                        };
                        tabListen_rvGeDan.setLayoutManager(new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false));
                        tabListen_rvGeDan.setAdapter(dataBaseRecycleAdapter);
                        dataBaseRecycleAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
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

        tabListen_tabLayout.setupWithViewPager(tabListen_viewPager);
        int flog = getContext().getResources().getInteger(R.integer.tab_listen);
        final MyPagerAdapter pagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager(),getContext(),121,flog);
        tabListen_viewPager.setAdapter(pagerAdapter);
        for(int n = 0;n < tabListen_tabLayout.getTabCount();n++){
            TabLayout.Tab tab = tabListen_tabLayout.getTabAt(n);
            if(tab != null){
                //tab.setText(myPagerAdapter.getPageTitle(n));
                tab.setCustomView(pagerAdapter.getTabView(n));
            }
        }
        tabListen_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabListen_viewPager.requestLayout();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabListen_viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        //设置TabLayout选择事件
        tabListen_tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabListen_viewPager.setCurrentItem(tab.getPosition());
                View view = tab.getCustomView();
                if(view != null && view instanceof TextView){
                    ((TextView) view).setTextColor(getResources().getColor(R.color.colorTabListenSelect));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if(view != null && view instanceof TextView){
                    ((TextView) view).setTextColor(getResources().getColor(R.color.colorTabListenUnSelect));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    class GlideImageLoader extends ImageLoader{

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Picasso.get().load((int)path).into(imageView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
