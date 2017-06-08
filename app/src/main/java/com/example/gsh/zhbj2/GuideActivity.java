package com.example.gsh.zhbj2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by gsh on 2017/4/26.
 */

public class GuideActivity extends AppCompatActivity {
    private Banner banner;


    //设置图片资源:url或本地资源
    Object[] images = new Object[]{
            R.drawable.guide_one,
            R.drawable.guide_two,
            R.drawable.guide_three


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setFullScreen();

        setContentView(R.layout.activity_guide);






        banner = (Banner) findViewById(R.id.banner);


        //设置样式,默认为:Banner.NOT_INDICATOR(不显示指示器和标题)
        //可选样式如下:
        //1. Banner.CIRCLE_INDICATOR    显示圆形指示器
        //2. Banner.NUM_INDICATOR    显示数字指示器
        //3. Banner.NUM_INDICATOR_TITLE    显示数字指示器和标题
        //4. Banner.CIRCLE_INDICATOR_TITLE    显示圆形指示器和标题
        banner.setBannerStyle(Banner.CIRCLE_INDICATOR_TITLE);

        //设置轮播样式（没有标题默认为右边,有标题时默认左边）
        //可选样式:
        //Banner.LEFT    指示器居左
        //Banner.CENTER    指示器居中
        //Banner.RIGHT    指示器居右
        banner.setIndicatorGravity(Banner.CENTER);

        //设置轮播要显示的标题和图片对应（如果不传默认不显示标题）


        //设置是否自动轮播（不设置则默认自动）
        banner.isAutoPlay(true)    ;

        //设置轮播图片间隔时间（不设置默认为2000）
        banner.setDelayTime(5000);

        //设置图片资源:可选图片网址/资源文件，默认用Glide加载,也可自定义图片的加载框架
        //所有设置参数方法都放在此方法之前执行
        //banner.setImages(images);

        //自定义图片加载框架
        banner.setImages(images, new Banner.OnLoadImageListener() {
            @Override
            public void OnLoadImage(ImageView view, Object url) {
                System.out.println("加载中");
                Glide.with(getApplicationContext()).load(url).into(view);
                System.out.println("加载完");
            }
        });
        //设置点击事件，下标是从1开始
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {//设置点击事件
            @Override
            public void OnBannerClick(View view, int position) {
               if(position==images.length){
                   Intent intent=new Intent(GuideActivity.this,MainActivity.class);
                   startActivity(intent);
               }
            }
        });


;

    }
    private void hideActionBar() {
        // Hide UI
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }


    /**
     * set the activity display in full screen
     */

    private void setFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
