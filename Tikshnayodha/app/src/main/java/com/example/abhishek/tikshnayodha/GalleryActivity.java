package com.example.abhishek.tikshnayodha;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.util.ArrayList;
import java.util.List;


public class GalleryActivity extends FragmentActivity {

    private TextView indicatorTv,name,gal_title;
    private ViewPager viewPager;
    public static int INDEX_NO;
    private List<CommonFragment> fragments = new ArrayList<>();
    private final String[] eNameArray={"Legend","Trophy","Kits"};
    private final String[] hNameArray={" किंवदंती "," ट्रॉफी ", "किट  "};
    private final String[] pNameArray={" Legenda ","Trofeum ","Kits "};
    private final String[] imageArray = {"assets://legend.jpg", "assets://trophy.jpg","assets://kits.jpg"};
    //private final String[] imageArray = {"", "",""};
    public final String[] gallery_title={" Gallery ","Galeria ", " गेलरी"};
    public final String[] click_here={" Click here to know details "," Kliknij tutaj, aby poznać szczegóły "," विवरण जानने के लिए यहां क्लिक करें "};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        gal_title=(TextView)findViewById(R.id.gallery_titl);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        INDEX_NO=0;
        //Toast.makeText(getApplicationContext(), "INDEX_NO:- "+INDEX_NO, Toast.LENGTH_LONG).show();
        initImageLoader();// 2. ImageLoader
        fillViewPager();// 3. ViewPager
    }


    @SuppressWarnings("deprecation")
    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800)      // default = device screen dimensions
                .threadPoolSize(3)      // default
                .threadPriority(Thread.NORM_PRIORITY - 1)      // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13) // default
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileCount(100)
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs().build();

        ImageLoader imageLoader = ImageLoader.getInstance();    // 2.ImageLoader
        imageLoader.init(config);
    }


    private void fillViewPager() {      //ViewPager
        indicatorTv = (TextView) findViewById(R.id.indicator_tv);
        name=(TextView)findViewById(R.id.name);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setPageTransformer(false, new CustPagerTransformer(this));// 1. viewPager parallax PageTransformer
        // 2. viewPageradapter
        for (int i = 0; i < 10; i++) {
            fragments.add(new CommonFragment());//fragment
        }

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                CommonFragment fragment = fragments.get(position % 10);
                fragment.bindData(imageArray[position % imageArray.length]);
                return fragment;
            }

            @Override
            public int getCount() {
                return 50;
            }
        });

        // 3. viewPager
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateIndicatorTv();
                INDEX_NO=position%3;
                //Toast.makeText(getApplicationContext(), "INDEX_NO:- "+INDEX_NO, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        updateIndicatorTv();
    }

    private void updateIndicatorTv() {
        int totalNum = viewPager.getAdapter().getCount();
        int currentItem = viewPager.getCurrentItem() + 1;
       // Toast.makeText(getApplicationContext(), "v "+LoginActivity.ENGLISH_FLAG+" "+LoginActivity.HINDI_FLAG, Toast.LENGTH_LONG).show();
        if(LoginActivity.ENGLISH_FLAG) {
            name.setText(eNameArray[(currentItem - 1) % 3]);
            gal_title.setText(gallery_title[0]);
        }
        else if(LoginActivity.POLISH_FLAG) {
            name.setText(pNameArray[(currentItem - 1) % 3]);
            gal_title.setText(gallery_title[1]);
        }
        else if(LoginActivity.HINDI_FLAG) {
            name.setText(hNameArray[(currentItem - 1) % 3]);
            gal_title.setText(gallery_title[2]);
        }
        indicatorTv.setText(Html.fromHtml("<font color='#12edf0'>" + currentItem + "</font>  /  " + totalNum));
    }

}


//redmi 3s path /storage/4389-0DFD/museum_data/