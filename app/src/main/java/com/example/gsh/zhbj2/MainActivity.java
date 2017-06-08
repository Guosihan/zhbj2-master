package com.example.gsh.zhbj2;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.youth.banner.Banner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gsh on 2017/4/26.
 */

public class MainActivity extends FragmentActivity {
    private static final String iconPath = Environment.getExternalStorageDirectory()+"/Image";//图片的存储目录
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyPageAdapter mPageAdapter;
    private ArrayList<NewsFragment> mFragmentArrayList = new ArrayList<NewsFragment>();
    private ArrayList<String> mTitleList = new ArrayList<String>();;
    private ImageView imgHead;
    Bitmap myBitmap;
    private byte[] mContent;
    private Banner banner;
    //设置图片资源:url或本地资源
    String[] images= new String[] {
            "http://218.192.170.132/BS80.jpg",
            "http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg",
            "http://img.zcool.cn/community/018fdb56e1428632f875520f7b67cb.jpg",
            "http://img.zcool.cn/community/01c8dc56e1428e6ac72531cbaa5f2c.jpg",
            "http://img.zcool.cn/community/01fda356640b706ac725b2c8b99b08.jpg",
            "http://img.zcool.cn/community/01fd2756e142716ac72531cbf8bbbf.jpg",
            "http://img.zcool.cn/community/0114a856640b6d32f87545731c076a.jpg"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragmentArrayList();
        initTitleList();
        intiSlidingmenu();
        mTabLayout = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        mViewPager = (ViewPager) findViewById(R.id.vp_FindFragment_pager);
        imgHead= (ImageView) findViewById(R.id.img_head);
        mPageAdapter = new MyPageAdapter(getSupportFragmentManager(), mFragmentArrayList, mTitleList);
        mViewPager.setAdapter(mPageAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        banner = (Banner) findViewById(R.id.main_banner);

        banner.setBannerStyle(Banner.CIRCLE_INDICATOR_TITLE);
        banner.isAutoPlay(true)    ;
        banner.setDelayTime(5000);
        banner.setImages(images);
        imgHead.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                                /*
                                 * // TODO Auto-generated method stub Intent intent = new
                                 * Intent("android.media.action.IMAGE_CAPTURE");
                                 * startActivityForResult(intent,Activity.DEFAULT_KEYS_DIALER);
                                 */
                final AlertDialog.Builder builder = new AlertDialog.Builder(
                        MainActivity.this);
                builder.setTitle("选择照片");

                builder.setPositiveButton("相机",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface

                                                        dialog, int which) {
                                Intent intent = new Intent(
                                        "android.media.action.IMAGE_CAPTURE");
                                startActivityForResult(intent, 0);

                            }
                        });
                builder.setNegativeButton("相册",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface

                                                        dialog, int which) {
                                Intent intent = new Intent(
                                        Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, 1);

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        ContentResolver resolver = getContentResolver();
        /**
         * 如果不拍照 或者不选择图片返回 不执行任何操作
         */

        if (data != null) {
            /**
             * 因为两种方式都用到了startActivityForResult方法，这个方法执行完后都会执行onActivityResult方法
             * ， 所以为了区别到底选择了那个方式获取图片要进行判断
             * ，这里的requestCode跟startActivityForResult里面第二个参数对应 1== 相册 2 ==相机
             */
            if (requestCode == 1) {

                try {
                    // 获得图片的uri
                    Uri originalUri = data.getData();
                    // 将图片内容解析成字节数组
                    mContent = readStream(resolver.openInputStream(Uri
                            .parse(originalUri.toString())));
                    // 将字节数组转换为ImageView可调用的Bitmap对象
                    myBitmap = getPicFromBytes(mContent, null);
                    // //把得到的图片绑定在控件上显示
                    imgHead.setImageBitmap(myBitmap);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } else if (requestCode == 0) {

                String sdStatus = Environment.getExternalStorageState();
                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                    return;
                }
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
                FileOutputStream b = null;
                File file = new File("/sdcard/myImage/");
                file.mkdirs();// 创建文件夹，名称为myimage

                // 照片的命名，目标文件夹下，以当前时间数字串为名称，即可确保每张照片名称不相同。
                String str = null;
                Date date = null;
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");// 获取当前时间，进一步转化为字符串
                date = new Date();
                str = format.format(date);
                String fileName = "/sdcard/myImage/" + str + ".jpg";
                try {
                    b = new FileOutputStream(fileName);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        b.flush();
                        b.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (data != null) {
                        Bitmap cameraBitmap = (Bitmap) data.getExtras().get(
                                "data");
                        System.out.println("fdf================="
                                + data.getDataString());
                        imgHead.setImageBitmap(cameraBitmap);

                        System.out.println("成功======" + cameraBitmap.getWidth()
                                + cameraBitmap.getHeight());
                    }

                }
            }
        }
    }

    public static Bitmap getPicFromBytes(byte[] bytes,
                                         BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                        opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;

    }





    private void intiSlidingmenu() {
        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

//        menu.setShadowDrawable(R.drawable.shadow);

        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.layout_left_menu);
    }

    private void initTitleList() {

        mTitleList.add("头条");
        mTitleList.add("娱乐");
        mTitleList.add("体育");
        mTitleList.add("科技");
    }

    private void initFragmentArrayList() {
        NewsFragment fa = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("NEWSTYPE", 1);
        fa.setArguments(bundle);


        NewsFragment fb = new NewsFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("NEWSTYPE", 2);
        fb.setArguments(bundle2);

        NewsFragment fc = new NewsFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("NEWSTYPE", 3);
        fc.setArguments(bundle3);

        NewsFragment fd = new NewsFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putInt("NEWSTYPE", 4);
        fd.setArguments(bundle4);


        mFragmentArrayList.add(fa);
        mFragmentArrayList.add(fb);
        mFragmentArrayList.add(fc);
        mFragmentArrayList.add(fd);

    }

}


