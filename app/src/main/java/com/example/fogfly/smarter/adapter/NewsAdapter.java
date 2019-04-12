//package com.example.fogfly.smarter.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//
//import com.example.fogfly.smarter.R;
//
//import java.util.List;
//
///**
// * @author Zaki Xue
// * @time 2019/1/25 17:31
// * @description 新闻适配器
// */
//public class NewsAdapter extends BaseAdapter {
//    private LayoutInflater inflater;
//    private WindowManager wm;
//    private Context mcontext;
//    private int width;
//    private int height;
//    private List<String> mlist;
//    private View view;
//
//    public NewsAdapter(Context mcontext, List<String> mlist) {
//        this.mcontext = mcontext;
//        this.mlist = mlist;
//        inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        wm = (WindowManager) mcontext.getSystemService(Context.WINDOW_SERVICE);
//        width = wm.getDefaultDisplay().getWidth();
//        height = wm.getDefaultDisplay().getHeight();
//
//    }
//
//    @Override
//    public int getCount() {
//        return mlist.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return mlist.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        view = inflater.inflate(R.layout.list_item_news, null);
//        ImageView viewById = (ImageView) view.findViewById(R.id.iv_icon);
//        return view;
//    }
//}
