package cleen.com.lxy.cleentest.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cleen.com.lxy.cleentest.R;
import cleen.com.lxy.cleentest.adapter.TestAdapter;
import cleen.com.lxy.cleentest.moduel.CleenBean;
import cleen.com.lxy.cleentest.moduel.RowsBean;
import cleen.com.lxy.cleentest.util.BaseActivity;
import cleen.com.lxy.cleentest.util.http.CleenHttpClient;
import cleen.com.lxy.cleentest.util.http.CleenHttpHandler;

/**
 * Created by lxy on 16-12-9.
 */
public class MainActivity extends BaseActivity {

    @Bind((R.id.list_view))
    ListView listView;

    @Bind((R.id.swipeLayout))
    SwipeRefreshLayout swipeLayout;


    private TestAdapter adapter;

    private List<RowsBean> originalRowsBeanList; //全部数据
    private List<RowsBean> rowsBeanList; //初次加载5条,每刷新一次,增加5条

    private static final int PER_LOAD_COUNT = 2;//上拉加载,每次拉取的数据条目

    private int lastPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();

        setSwipeLayout();
        setListView();

        getData();
    }

    private void setSwipeLayout() {
        swipeLayout.setColorSchemeColors(android.R.color.holo_blue_dark, android.R.color.holo_blue_light,
                android.R.color.holo_green_light, android.R.color.holo_green_light);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                getData();
            }
        });
    }

    private void setListView() {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (absListView.getLastVisiblePosition() == absListView.getCount() - 1) {

                    if (lastPosition > originalRowsBeanList.size() - 1) {
                        adapter.setIsPullUpEnable(false);
                        adapter.notifyDataSetChanged();
                        return;
                    } else if (lastPosition == originalRowsBeanList.size() - 1) {
                        rowsBeanList.add(originalRowsBeanList.get(lastPosition));
                        adapter.setIsPullUpEnable(false);
                        adapter.setRowsBeans(rowsBeanList);
                        return;
                    } else {
                        int end = lastPosition - 1 + PER_LOAD_COUNT;
                        if (end <= originalRowsBeanList.size() - 1) {
                            mLog("[" + lastPosition + "," + end + "]");

                            List<RowsBean> list = getSubList(lastPosition, end);
                            rowsBeanList.addAll(list);
                            adapter.setIsPullUpEnable(true);
                            adapter.setRowsBeans(rowsBeanList);
                            lastPosition = end + 1;
                            return;
                        } else {
                            mLog("[" + lastPosition + "," + (originalRowsBeanList.size() - 1) + "]");

                            List<RowsBean> list = getSubList(lastPosition, originalRowsBeanList.size() - 1);
                            rowsBeanList.addAll(list);
                            adapter.setIsPullUpEnable(false);
                            adapter.setRowsBeans(rowsBeanList);
                            lastPosition = originalRowsBeanList.size();
                            return;
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

        listView.setAdapter(adapter);
    }

    private List<RowsBean> getSubList(int startPosition, int endPosition) {
        List<RowsBean> list = new ArrayList<>();
        for (int i = startPosition; i <= endPosition; i++)
            list.add(originalRowsBeanList.get(i));
        return list;
    }

    private void initData() {
        adapter = new TestAdapter(mActivity);
        rowsBeanList = new ArrayList<>();
        originalRowsBeanList = new ArrayList<>();
    }

    private void getData() {
        String url = "http://thoughtworks-ios.herokuapp.com/facts.json";
        CleenHttpClient.get(mActivity, url, null, new CleenHttpHandler() {
            @Override
            public void onSuccess(String responseString) {
                mLog("responseString:" + responseString);

                swipeLayout.setRefreshing(false);

                CleenBean cleenbean = mActivity.decodeJson(CleenBean.class, responseString);

                if (cleenbean == null) {
                    adapter.setIsPullUpEnable(false);
                    adapter.notifyDataSetChanged();
                    return;
                } else {
                    setData(cleenbean);
                }
            }

            @Override
            public void onFailure(int response_status) {
                mLog("onFailure,response_status=" + response_status);
                swipeLayout.setRefreshing(false);
            }
        });
    }

    private void setData(CleenBean cleenbean) {
        //设置actionbar
        if (!TextUtils.isEmpty(cleenbean.getTitle())) {
            setActionbarTitle(cleenbean.getTitle());
        } else {
            setActionbarTitle(R.string.actionbar_title);
        }

        //设置listview的数据
        originalRowsBeanList.clear();
        rowsBeanList.clear();
        if (cleenbean != null) {
            originalRowsBeanList = cleenbean.getRows();
            adapter.setTotalItem(originalRowsBeanList.size());
            if (originalRowsBeanList.size() > PER_LOAD_COUNT) {
                lastPosition = PER_LOAD_COUNT;
                rowsBeanList = getSubList(0, lastPosition - 1);
                adapter.setIsPullUpEnable(true);
                mLog("第一次" + "[0" + "," + (lastPosition - 1) + "]");
            } else {
                rowsBeanList = originalRowsBeanList;
                adapter.setIsPullUpEnable(false);
            }
        }
        adapter.setRowsBeans(rowsBeanList);
    }
}
