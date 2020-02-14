package com.example.musicapp.Fragment;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.Adapter.MyPagerAdapter;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.TabListenDate;
import com.example.musicapp.Model.TabListenTop;
import com.example.musicapp.Module.AutoViewPager;
import com.example.musicapp.R;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class TabListen extends MyFragment {
    private String TAG = "TabListen";
    private RecyclerView tabListen_rvTop,tabListen_rvGeDan;
    //private int dragFlags,swipeFlags;
    private List<TabListenTop> listenTops = new ArrayList<>();
    private List<TabListenDate> listenDates = new ArrayList<>();
    private BaseRecycleAdapter tabListenBaseAdapter;
    private Banner tabListen_banner;
    private TabLayout tabListen_tabLayout;
    private AutoViewPager tabListen_viewPager;
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_listen,null);

        tabListen_banner = view.findViewById(R.id.tabListen_banner);
        tabListen_banner.setImageLoader(new GlideImageLoader());
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.glideimg1);
        images.add(R.drawable.glideimg2);
        images.add(R.drawable.glideimg3);
        tabListen_banner.setImages(images);
        tabListen_banner.start();

        tabListen_rvTop = view.findViewById(R.id.tabListen_rvTop);
        listenTops.add(new TabListenTop(R.drawable.tablisten_yueku,"乐库"));
        listenTops.add(new TabListenTop(R.drawable.tablisten_guesslike,"猜你喜欢"));
        listenTops.add(new TabListenTop(R.drawable.tablisten_gedan,"歌单"));
        listenTops.add(new TabListenTop(R.drawable.tablisten_rank,"排行榜"));
        listenTops.add(new TabListenTop(R.drawable.tablisten_more,"更多"));
        tabListenBaseAdapter = new BaseRecycleAdapter<TabListenTop>(getContext(),listenTops,R.layout.item_tablisten_top) {
            @Override
            public void convert(BaseRecycleViewHolder holder, TabListenTop item, int position) {
                holder.setText(R.id.textView,item.getText());
                holder.setImageResource(R.id.imageView,item.getImage());
            }
        };
        tabListen_rvTop.setLayoutManager(new GridLayoutManager(getContext(), 5, RecyclerView.VERTICAL, false));
        tabListen_rvTop.setAdapter(tabListenBaseAdapter);
        listenDates.add(new TabListenDate(R.drawable.include_default,"111","根据你的口味推荐"));
        listenDates.add(new TabListenDate(R.drawable.include_default,"222","欧美电音：提神醒脑的神器"));
        listenDates.add(new TabListenDate(R.drawable.include_default,"333","列表循环！极致音效的欧美电音歌喉"));
        listenDates.add(new TabListenDate(R.drawable.include_default,"444","评论过万的爆火英文歌|好听到窒息"));
        listenDates.add(new TabListenDate(R.drawable.include_default,"555","超强出场BGM：死亡的气息"));
        listenDates.add(new TabListenDate(R.drawable.include_default,"666","游戏视频里的超燃BGM都在这里"));
        tabListenBaseAdapter = new BaseRecycleAdapter<TabListenDate>(getContext(), listenDates, R.layout.item_tablisten_date) {
            @Override
            public void convert(BaseRecycleViewHolder holder, TabListenDate item, int position) {
                holder.setImageResource(R.id.imageView,item.getImage());
                holder.setText(R.id.listenerNumber,item.getListenerNumber());
                holder.setText(R.id.textView,item.getText());
            }
        };
        tabListen_rvGeDan = view.findViewById(R.id.tabListen_rvGeDan);
        tabListen_rvGeDan.setLayoutManager(new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false));
        tabListen_rvGeDan.setAdapter(tabListenBaseAdapter);

        tabListen_tabLayout = view.findViewById(R.id.tabListen_tabLayout);
        tabListen_viewPager = view.findViewById(R.id.tabListen_viewPager);
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
        //拖拽功能
        /*ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
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
                tabListenTopAdapter.notifyItemMoved(fromPosition,toPosition);
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
        itemTouchHelper.attachToRecyclerView(tabListen_rvTop);*/
        return view;
    }

    @Override
    public void loadData() {

    }
    class GlideImageLoader extends ImageLoader{

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Picasso.get().load((int)path).into(imageView);
        }
    }
}
