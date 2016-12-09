package cleen.com.lxy.cleentest.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
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
import cleen.com.lxy.cleentest.util.Utils;
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
    private List<RowsBean> rowsBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        swipeLayout.setColorSchemeColors(android.R.color.holo_blue_dark, android.R.color.holo_blue_light,
                android.R.color.holo_green_light, android.R.color.holo_green_light);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                getData();
            }
        });

        initData();
        listView.setAdapter(adapter);
        getData();
    }

    private void initData() {
        adapter = new TestAdapter(mActivity);
        rowsBeanList = new ArrayList<>();
    }

    //获取用户列表
    private void getData() {
        String url = "http://thoughtworks-ios.herokuapp.com/facts.json";
        CleenHttpClient.get(mActivity, url, null, new CleenHttpHandler() {
            @Override
            public void onSuccess(String responseString) {
                mLog("responseString:" + responseString);

                swipeLayout.setRefreshing(false);

                CleenBean cleenbean = mActivity.decodeJson(CleenBean.class, responseString);
                if (cleenbean != null) {
                    setData(cleenbean);
                }
            }

            @Override
            public void onFailure(int response_status) {
                mLog("onFailure,response_status=" + response_status);
                swipeLayout.setRefreshing(false);

                String response = "{\n" +
                        "  \"title\": \"About Canada\",\n" +
                        "  \"rows\": [\n" +
                        "    {\n" +
                        "      \"title\": \"Beavers\",\n" +
                        "      \"description\": \"Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony\",\n" +
                        "      \"imageHref\": \"http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"title\": \"Flag\",\n" +
                        "      \"description\": null,\n" +
                        "      \"imageHref\": \"http://images.findicons.com/files/icons/662/world_flag/128/flag_of_canada.png\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"title\": \"Transportation\",\n" +
                        "      \"description\": \"It is a well known fact that polar bears are the main mode of transportation in Canada. They consume far less gas and have the added benefit of being difficult to steal.\",\n" +
                        "      \"imageHref\": \"http://1.bp.blogspot.com/_VZVOmYVm68Q/SMkzZzkGXKI/AAAAAAAAADQ/U89miaCkcyo/s400/the_golden_compass_still.jpg\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"title\": \"Hockey Night in Canada\",\n" +
                        "      \"description\": \"These Saturday night CBC broadcasts originally aired on radio in 1931. In 1952 they debuted on television and continue to unite (and divide) the nation each week.\",\n" +
                        "      \"imageHref\": \"http://fyimusic.ca/wp-content/uploads/2008/06/hockey-night-in-canada.thumbnail.jpg\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"title\": \"Eh\",\n" +
                        "      \"description\": \"A chiefly Canadian interrogative utterance, usually expressing surprise or doubt or seeking confirmation.\",\n" +
                        "      \"imageHref\": null\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"title\": \"Housing\",\n" +
                        "      \"description\": \"Warmer than you might think.\",\n" +
                        "      \"imageHref\": \"http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"title\": \"Public Shame\",\n" +
                        "      \"description\": \" Sadly it's true.\",\n" +
                        "      \"imageHref\": \"http://static.guim.co.uk/sys-images/Music/Pix/site_furniture/2007/04/19/avril_lavigne.jpg\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"title\": null,\n" +
                        "      \"description\": null,\n" +
                        "      \"imageHref\": null\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"title\": \"Space Program\",\n" +
                        "      \"description\": \"Canada hopes to soon launch a man to the moon.\",\n" +
                        "      \"imageHref\": \"http://files.turbosquid.com/Preview/Content_2009_07_14__10_25_15/trebucheta.jpgdf3f3bf4-935d-40ff-84b2-6ce718a327a9Larger.jpg\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"title\": \"Meese\",\n" +
                        "      \"description\": \"A moose is a common sight in Canada. Tall and majestic, they represent many of the values which Canadians imagine that they possess. They grow up to 2.7 metres long and can weigh over 700 kg. They swim at 10 km/h. Moose antlers weigh roughly 20 kg. The plural of moose is actually 'meese', despite what most dictionaries, encyclopedias, and experts will tell you.\",\n" +
                        "      \"imageHref\": \"http://caroldeckerwildlifeartstudio.net/wp-content/uploads/2011/04/IMG_2418%20majestic%20moose%201%20copy%20(Small)-96x96.jpg\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"title\": \"Geography\",\n" +
                        "      \"description\": \"It's really big.\",\n" +
                        "      \"imageHref\": null\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"title\": \"Kittens...\",\n" +
                        "      \"description\": \"�are illegal. Cats are fine.\",\n" +
                        "      \"imageHref\": \"http://www.donegalhimalayans.com/images/That%20fish%20was%20this%20big.jpg\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"title\": \"Mounties\",\n" +
                        "      \"description\": \"They are the law. They are also Canada's foreign espionage service. Subtle.\",\n" +
                        "      \"imageHref\": \"http://3.bp.blogspot.com/__mokxbTmuJM/RnWuJ6cE9cI/AAAAAAAAATw/6z3m3w9JDiU/s400/019843_31.jpg\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"title\": \"Language\",\n" +
                        "      \"description\": \"Nous parlons tous les langues importants.\",\n" +
                        "      \"imageHref\": null\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";
                CleenBean cleenBean = mActivity.decodeJson(CleenBean.class,response);
                if (cleenBean != null) {
                    setData(cleenBean);
                }
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
        rowsBeanList.clear();
        if (cleenbean != null) {
            rowsBeanList = cleenbean.getRows();
            adapter.setRowsBeans(rowsBeanList);
        }
    }
}
