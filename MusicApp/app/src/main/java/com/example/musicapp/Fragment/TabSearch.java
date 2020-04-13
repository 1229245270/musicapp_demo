package com.example.musicapp.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.DateBase.UsersTable;
import com.example.musicapp.Factory.FragmentFactory;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.HotSong;
import com.example.musicapp.Model.SearchSession;
import com.example.musicapp.Module.AutoLayoutManage;
import com.example.musicapp.R;

import java.util.ArrayList;
import java.util.List;

public class TabSearch extends MyFragment {
    private EditText tabSearch_eTSearch;
    private Button tabSearch_btnSearch;
    private ImageView tabSearch_ivBack, tabSearch_ivCha, tabSearch_ivYuyin, tabSearch_ivClean;
    private static LinearLayout tabSearch_lLHistory;
    private RecyclerView tabSearch_rvHot;
    private static Fragment tabSearchResult;
    private static FragmentTransaction transaction;
    private static Context context;
    private static Fragment tabSearch;
    public static MyHandler handler = new MyHandler();
    static class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            tabSearch_lLHistory.removeAllViews();
            List<SearchSession> sessionList = new UsersTable(context).getAllSearchSession();
            if (sessionList != null) {
                for (int n = 0; n < sessionList.size(); n++) {
                    TextView textView = new TextView(context);
                    final String str = sessionList.get(n).getSearchSong();
                    textView.setText(str);
                    textView.setBackgroundColor(Color.parseColor("#000000"));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        textView.setBackground(context.getDrawable(R.drawable.tabsearch_history));
                    }
                    textView.setPadding(20, 0, 20, 0);
                    textView.setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 60);
                    layoutParams.setMargins(20, 20, 20, 20);
                    layoutParams.gravity = Gravity.LEFT;
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putString("searchText", str);
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            transaction.hide(tabSearch);
                            tabSearchResult.setArguments(bundle);
                            if(!tabSearchResult.isAdded()) {
                                transaction.add(R.id.frameLayout_main, tabSearchResult).show(tabSearchResult);
                            }else{
                                transaction.show(tabSearchResult);
                            }
                            //存栈
                            transaction.addToBackStack("null");
                            transaction.commit();
                        }
                    });
                    tabSearch_lLHistory.addView(textView, layoutParams);
                }
            }
        }
    }
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_search, null);
        context = getContext();
        tabSearch = TabSearch.this;
        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        tabSearchResult = FragmentFactory.createFragment(22);
        tabSearch_eTSearch = view.findViewById(R.id.tabSearch_eTSearch);
        tabSearch_btnSearch = view.findViewById(R.id.tabSearch_btnSearch);
        tabSearch_ivBack = view.findViewById(R.id.tabSearch_ivBack);
        tabSearch_ivCha = view.findViewById(R.id.tabSearch_ivCha);
        tabSearch_ivYuyin = view.findViewById(R.id.tabSearch_ivYuyin);
        tabSearch_lLHistory = view.findViewById(R.id.tabSearch_lLHistory);
        tabSearch_ivClean = view.findViewById(R.id.tabSearch_ivClean);
        tabSearch_rvHot = view.findViewById(R.id.tabSearch_rvHot);
        return view;
    }
    @Override
    public void loadData() {
        List<HotSong> hotSongs = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            HotSong hotSong = new HotSong(i + 1, "下上" + i, "简介" + i);
            hotSongs.add(hotSong);
        }
        BaseRecycleAdapter<HotSong> adapter = new BaseRecycleAdapter<HotSong>(getContext(), hotSongs, R.layout.item_tabsearch_hotsong) {
            @Override
            public void convert(BaseRecycleViewHolder holder, HotSong item, int position) {
                holder.setText(R.id.ranking, String.valueOf(item.getRanking()));
                holder.setText(R.id.songName, String.valueOf(item.getSongName()));
                holder.setText(R.id.intro, String.valueOf(item.getIntro()));
            }
        };
        tabSearch_rvHot.setLayoutManager(new AutoLayoutManage(getContext(), LinearLayoutManager.VERTICAL, false));
        tabSearch_rvHot.setAdapter(adapter);

        tabSearch_ivClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UsersTable(getContext()).deleteSearchSession();
                handler.sendMessage(new Message());
            }
        });

        tabSearch_btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = tabSearch_eTSearch.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("searchText", searchText);

                SearchSession searchSession = new SearchSession(searchText);
                UsersTable usersTable = new UsersTable(getContext());
                SearchSession havaSearch = usersTable.getSearchSessionBySearchSong(searchText);
                if (havaSearch == null && !searchText.equals("")) {
                    new UsersTable(getContext()).insertSearchSession(searchSession);
                }
                handler.sendMessage(new Message());
                if (!searchText.equals("")) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.hide(tabSearch);
                    tabSearchResult.setArguments(bundle);
                    if (!tabSearchResult.isAdded()) {
                        transaction.add(R.id.frameLayout_main, tabSearchResult).show(tabSearchResult);
                    } else {
                        transaction.show(tabSearchResult);
                    }
                    transaction.addToBackStack("null");
                    transaction.commit();
                } else {
                    Toast.makeText(getContext(), "请输入搜索内容", Toast.LENGTH_SHORT).show();
                }

            }
        });
        tabSearch_ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        tabSearch_ivCha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabSearch_eTSearch.setText("");
            }
        });
        tabSearch_eTSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 > 0) {
                    tabSearch_ivCha.setVisibility(View.VISIBLE);
                    //
                } else {
                    tabSearch_ivCha.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
