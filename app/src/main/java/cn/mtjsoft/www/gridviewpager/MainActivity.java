package cn.mtjsoft.www.gridviewpager;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] titles = {"美食", "电影", "酒店住宿", "休闲娱乐", "外卖", "自助餐", "KTV", "机票/火车票", "周边游", "美甲美睫",
            "火锅", "生日蛋糕", "甜品饮品", "水上乐园", "汽车服务", "美发", "丽人", "景点", "足疗按摩", "运动健身", "健身", "超市", "买菜",
            "今日新单", "小吃快餐", "面膜", "洗浴/汗蒸", "母婴亲子", "生活服务", "婚纱摄影", "学习培训", "家装", "结婚", "全部分配"};

    private int[] iconS = new int[titles.length];

    private ViewPager viewPager;
    private LinearLayout idotLayout;//知识圆点
    private List<View> mPagerList;//页面集合
    private List<Da> mDataList;//数据集合；
    private LayoutInflater mInflater;

    /*总的页数*/
    private int pageCount;
    /*每一页显示的个数*/
    private int pageSize = 8;
    /*当前显示的是第几页*/
    private int curIndex = 0;
    private GridViewAdapter[] arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
//
//        final GridPager gridPager = findViewById(R.id.gridpager);
//
//        //
//        gridPager
//                // 设置数量总条数
//                .setDataAllCount(mDatas)
////                // 设置背景色，默认白色
////                .setGridPagerBackgroundColor(ContextCompat.getColor(getBaseContext(),R.color.colorBg))
////                // 设置item的纵向间距
////                .setVerticalSpacing(10)
////                // 设置图片宽度
////                .setImageWidth(50)
////                // 设置图片高度
////                .setImageHeight(50)
////                // 设置文字与图片的间距
////                .setTextImgMargin(5)
////                // 设置文字颜色
////                .setTextColor(ContextCompat.getColor(getBaseContext(),R.color.colorAccent))
////                // 设置文字大小
////                .setTextSize(16)
////                // 设置每页行数
////                .setRowCount(2)
////                // 设置每页列数
////                .setColumnCount(5)
////                // 设置是否显示指示器
////                .setPointIsShow(false)
////                // 设置指示器与page的间距
////                .setPointMarginPage(30)
////                // 设置指示器与底部的间距
////                .setPointMarginBottom(30)
////                // 设置指示器的item宽度
////                .setPointChildWidth(15)
////                // 设置指示器的item高度
////                .setPointChildHeight(2)
////                // 设置指示器的item的间距
////                .setPointChildMargin(2)
////                // 指示器的item是否为圆形，默认圆形直径取宽高的最小值
////                .setPointIsCircle(false)
////                // 指示器item未选中的颜色
////                .setPointNormalColor(ContextCompat.getColor(getBaseContext(),R.color.colorPrimary))
////                // 指示器item选中的颜色
////                .setPointSelectColor(ContextCompat.getColor(getBaseContext(),R.color.colorAccent))
//                // 数据绑定
//                .setItemBindDataListener(new GridPager.ItemBindDataListener() {
//                    @Override
//                    public void BindData(ImageView imageView, TextView textView, LinearLayout linearLayout, int position) {
//                        // 自己进行数据的绑定，灵活度更高，不受任何限制
//                        imageView.setImageResource(mDatas.get(position).logo);
//                        textView.setText(mDatas.get(position).Title);
//                        if (mDatas.get(position).isSelcet) {
//                            linearLayout.setBackgroundColor(getResources().getColor(R.color.color_99D81B));
//                        } else {
//                            linearLayout.setBackgroundColor(getResources().getColor(R.color.color_598EE4));
//                        }
//                    }
//                })
//                // Item点击
//                .setGridItemClickListener(new GridPager.GridItemClickListener() {
//                    @Override
//                    public void click(int position, GiftGridViewAdapter atMostGridViewAdapter) {
//                        Toast.makeText(getBaseContext(), "点击了" + titles[position], Toast.LENGTH_SHORT).show();
//                        for (int i = 0; i < mDatas.size(); i++) {
//                            if (position == i) {
//                                mDatas.get(position).setSelcet(true);
//                            } else {
//                                mDatas.get(i).setSelcet(false);
//                            }
//                        }
//                        atMostGridViewAdapter.notifyDataSetChanged();
//                    }
//                })
//                .show();
    }

    /**
     * 初始化数据源，这里使用本地图标作为示例
     */
    private void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
            int imageId = getResources().getIdentifier("ic_category_" + i, "mipmap", getPackageName());
            iconS[i] = imageId;
            Da da = new Da();
            da.setLogo(imageId);
            da.setTitle(titles[i]);
            mDataList.add(da);
        }

        mInflater = LayoutInflater.from(MainActivity.this);
        //总的页数=总数/每页数量，并取整
        pageCount = (int) Math.ceil(mDataList.size() * 1.0 / pageSize);
        arr = new GridViewAdapter[pageCount];
        //viewpager
        mPagerList = new ArrayList<View>();
        for (int j = 0; j < pageCount; j++) {
            final GridView gridview = (GridView) mInflater.inflate(R.layout.bottom_vp_gridview, viewPager, false);
            final GridViewAdapter gridAdapter = new GridViewAdapter(MainActivity.this, mDataList, j);
            gridview.setAdapter(gridAdapter);
            arr[j] = gridAdapter;
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(MainActivity.this, "点击位置position：" + position + "..id:" + id, Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < mDataList.size(); i++) {
                        Da model = mDataList.get(i);
                        if (i == id) {
                            if (model.isSelcet()) {
                                model.setSelcet(false);
                            } else {
                                model.setSelcet(true);
                            }
                            Log.i("tag", "==点击位置：" + i + "..id:" + id);
                        } else {
                            model.setSelcet(false);
//                            Log.i("tag","==位置2："+i+"..id:"+id);
                        }
                    }
                    Log.i("tag", "状态：" + mDataList.toString());
                    gridAdapter.notifyDataSetChanged();
                }
            });
            mPagerList.add(gridview);
        }

        viewPager.setAdapter(new ViewPagerAdapter(mPagerList));
        setOvalLayout();
    }
    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        idotLayout = (LinearLayout) findViewById(R.id.ll_dot);
    }

    /**
     * 设置圆点
     */
    public void setOvalLayout() {
        for (int i = 0; i < pageCount; i++) {
            idotLayout.addView(mInflater.inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        idotLayout.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                arr[0].notifyDataSetChanged();
                arr[1].notifyDataSetChanged();
                arr[2].notifyDataSetChanged();

                // 取消圆点选中
                idotLayout.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                idotLayout.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
}
