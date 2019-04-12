//package com.example.fogfly.smarter.adapter;
//
//import android.content.Context;
//import android.support.v4.view.PagerAdapter;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.example.fogfly.smarter.R;
//
///**
// * @author Zaki Xue
// * @time 2019/2/22 15:47
// * @description
// */
//public class WeatherAutoScrillAdapter extends PagerAdapter {
//    private LayoutInflater inflater;
//    private Context mContext;
//    private int[] imgs ={R.drawable.splash,R.drawable.splash1};
//
//    public WeatherAutoScrillAdapter(Context context) {
//        this.mContext = context;
//        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public int getCount() {
//        return imgs.length;
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view.equals(object);
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        View view = inflater.inflate(R.layout.weather_item_image, container, false);
//        assert container != null;
//        ImageView img = (ImageView) view.findViewById(R.id.img);
//        img.setBackgroundResource(imgs[position]);
//        container.addView(view);
//        return view;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View)object);
//    }
//}
