package com.example.musicapp.Fragment;

import android.content.Context;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.musicapp.Adapter.MyPagerAdapter;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.MainActivity;
import com.example.musicapp.R;
import com.google.android.material.tabs.TabLayout;


public class TabHome extends MyFragment {
    private TabLayout fragHome_tabLayout;
    private ViewPager fragHome_viewPager;
    private Toolbar fragHome_toolBar;
    private ImageView fragHome_ivMenu,fragHome_ivSearch;
    private DrawerLayout drawerLayout;

    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_home,null);
        fragHome_toolBar = view.findViewById(R.id.fragHome_toolBar);
        setHasOptionsMenu(true);
        //需要先设置style为NoActionBar
        ((AppCompatActivity)getActivity()).setSupportActionBar(fragHome_toolBar);

        fragHome_viewPager = view.findViewById(R.id.fragHome_viewPager);
        fragHome_tabLayout = view.findViewById(R.id.fragHome_tabLayout);
        //TabLayout和ViewPager绑定
        fragHome_tabLayout.setupWithViewPager(fragHome_viewPager);
        //设置适配器
        int flog = getContext().getResources().getInteger(R.integer.tab_home);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager(),getContext(),11,flog);
        fragHome_viewPager.setAdapter(myPagerAdapter);
        for(int n = 0;n < fragHome_tabLayout.getTabCount();n++){
            TabLayout.Tab tab = fragHome_tabLayout.getTabAt(n);
            if(tab != null){
                //tab.setText(myPagerAdapter.getPageTitle(n));
                tab.setCustomView(myPagerAdapter.getTabView(n));
            }
        }
        //设置TabLayout选择事件
        fragHome_tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragHome_viewPager.setCurrentItem(tab.getPosition());
                View view = tab.getCustomView();
                if(view != null && view instanceof TextView){
                    ((TextView) view).setTextSize(22);
                    ((TextView) view).setTextColor(getResources().getColor(R.color.colorTabHomeSelect));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if(view != null && view instanceof TextView){
                    ((TextView) view).setTextSize(16);
                    ((TextView) view).setTextColor(getResources().getColor(R.color.colorTabHomeUnSelect));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        fragHome_viewPager.setCurrentItem(1);//坑！放到addOnTabSelectListener事件之后

        MainActivity mainActivity = (MainActivity) getActivity();//重点
        drawerLayout = mainActivity.findViewById(R.id.drawable_Main);
        fragHome_ivMenu = view.findViewById(R.id.fragHome_ivMenu);
        fragHome_ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
        //此方法用于初始化菜单，其中menu参数就是即将要显示的Menu实例。 返回true则显示该menu,false 则不显示;(只会在第一次初始化菜单时调用)
        fragHome_toolBar.inflateMenu(R.menu.home_menu);
        super.onCreateOptionsMenu(menu, inflater);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //菜单项被点击时调用，也就是菜单项的监听方法。通过这几个方法，可以得知，对于Activity，同一时间只能显示和监听一个Menu 对象。
        switch (item.getItemId()){
            case R.id.ting:
                Toast.makeText(getContext(), "听", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sao:
                Toast.makeText(getContext(), "扫", Toast.LENGTH_SHORT).show();
                /*Intent innerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Intent wrapperIntent = Intent.createChooser(innerIntent,"选择二维码图片");
                getActivity().startActivityForResult(wrapperIntent,1001);*/
                break;
            case R.id.add:
                Toast.makeText(getContext(), "加", Toast.LENGTH_SHORT).show();
                break;
            case R.id.me:
                Toast.makeText(getContext(), "我", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
    @Override
    public void loadData() {

    }
/*
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1001){
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(data.getData(),null,null,null);
            if(cursor.moveToFirst()){
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                String photoPath = cursor.getString(columnIndex);
                String result = parseQRcode(BitmapFactory.decodeFile(photoPath,null));
            }
        }
    }
    public static String parseQRcode(Bitmap bitmap){
        //bitmap = comp(bitmap);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels,0,width,0,0,width,height);
        QRCodeReader reader = new QRCodeReader();
        Map<DecodeHintType,Object> hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        hints.put(DecodeHintType.TRY_HARDER,Boolean.TRUE);
        hints.put(DecodeHintType.CHARACTER_SET,"utf-8");
        try{
            Result result = reader.decode(new BinaryBitmap(new HybridBinarizer(new RGBLuminanceSource(width,height,pixels))),hints);
            return result.getText();
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }*/
}
