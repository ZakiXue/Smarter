package com.example.fogfly.smarter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.fogfly.smarter.R;
import com.example.fogfly.smarter.adapter.DiscoverAdapter;
import com.example.fogfly.smarter.entity.Discover;
import com.example.fogfly.smarter.entity.DiscoverSteps;
import com.example.fogfly.smarter.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zaki Xue
 * @time 2019/1/24 16:35
 * @description
 */
public class DiscoverFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<Discover> mList = new ArrayList<>();
    private List<DiscoverSteps> mList1 = new ArrayList<>();
//    private  List<ArrayList<DiscoverSteps>> mList1 = new ArrayList<ArrayList<DiscoverSteps>>();
    private DiscoverAdapter adapter;
    private String text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        TextView textView = new TextView(getActivity());
//        textView.setText("发现");
//        textView.setTextSize(100);
//        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        final View view = inflater.inflate(R.layout.fragment_discover, null);
        initview(view);
        return view;
    }

    /**
     * 解析接口并获取数据
     * @param view
     */
    private void initGet(View view) {
        String url = "http://apis.juhe.cn/cook/query?menu=" + text + "&dtype=&pn=&rn=&albums=&=&key=" + StaticClass.food_key;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                System.out.println("json:" + t);
                try {
                    parseJson(t);    //解析json数据
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }
        });

    }

    /**
     * 解析数据
     * @param t
     */
    private void parseJson(String t)  throws JSONException{
        //1、声明JSONObject 对象
        JSONObject jsonObject = new JSONObject(t);
        //2、获取JSONObject 数据
        JSONObject jsonResult = jsonObject.getJSONObject("result");
        JSONArray jsonArray = jsonResult.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            int id = object.optInt("id");
            String title = object.optString("title");
            String tags = object.optString("tags");
            String imtro = "        "+object.optString("imtro");
            String ingredients = object.optString("ingredients");
            String burden = object.optString("burden");
            //头图片
            JSONArray jsonArray1 = object.getJSONArray("albums");
            String albums = (String) jsonArray1.get(0);
            Discover discover = new Discover();
            discover.setId(id);
            discover.setTags(tags);
            discover.setImtro(imtro);
            discover.setTitle(title);
            discover.setIngredients(ingredients);
            discover.setBurden(burden);
            discover.setAlbums(albums);
            JSONArray steps = object.getJSONArray("steps");
            discover.setSteps(steps);
            mList.add(discover);
//
//            for (int j = 0; j < steps.length(); j++) {
//                JSONObject object2 = (JSONObject) steps.get(j);
//                String img = object2.getString("img");
//                String step = object2.getString("step");
//                DiscoverSteps discover1 = new DiscoverSteps();
//                discover1.setImg(img);
//                discover1.setStep(step);
////                mList1.add(discover1);
//                mList1.add(discover1);
//            }
        }
    }

    /**
     * 初始化
     * @param view
     */
    private void initview(final View view) {
        final SearchView search = (SearchView) view.findViewById(R.id.discover_search);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.discover_recyclerView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                //要跑到主线程中去更新UI
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //更新UI
                        mList.clear();
                        mList1.clear();
                        text = s;
                        System.out.println("text:"+text);
                        initGet(view);
                        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
                        mRecyclerView.setLayoutManager(layoutManager);
                        adapter = new DiscoverAdapter(mList,mList1);
                        mRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
}
