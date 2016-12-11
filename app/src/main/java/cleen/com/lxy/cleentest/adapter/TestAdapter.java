package cleen.com.lxy.cleentest.adapter;

import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import cleen.com.lxy.cleentest.R;
import cleen.com.lxy.cleentest.moduel.RowsBean;
import cleen.com.lxy.cleentest.util.BaseActivity;

/**
 * Created by lxy on 16-12-9.
 */
public class TestAdapter extends BaseAdapter {

    private List<RowsBean> rowsBeanList;
    private BaseActivity mActivity;
    private int totalItem; //listview'显示总条目

    // 是否允许上拉加载数据。如果上拉加载的接口返回的列表是空的，说明没有更多的数据可以加载，这时需要禁用上拉加载。
    private boolean isPullUpEnable;


    //0: 上拉加载更多
    private final static int LOAD_MORE = 0;
    //1: 普通listview
    private final static int NORMAL = 1;


    public TestAdapter(BaseActivity mActivity) {
        this.mActivity = mActivity;
        rowsBeanList = new ArrayList<>();
        isPullUpEnable = false;
    }

    public void setRowsBeans(List<RowsBean> rowsBeanList) {
        this.rowsBeanList = rowsBeanList;
        notifyDataSetChanged();
    }

    //设置总的list数目
    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public void setIsPullUpEnable(boolean isPullUpEnable) {
        this.isPullUpEnable = isPullUpEnable;
    }

    @Override
    public int getCount() {
        if (isPullUpEnable)
            return rowsBeanList.size() + 1;
        return rowsBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPullUpEnable && position == rowsBeanList.size()) {
            return LOAD_MORE;
        }
        return NORMAL;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (getItemViewType(position) == LOAD_MORE) {
            boolean isShowLoadmore = true;
            if (!isPullUpEnable || rowsBeanList.size() >= totalItem) {
                isShowLoadmore = false;
            }
            return getLoadmoreFooterView(parent, isShowLoadmore);
        }

        ViewHolder viewHolder = null;

        if (view == null) {
            view = mActivity.getLayoutInflater().inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder(view);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        RowsBean rowsBean = rowsBeanList.get(position);
        viewHolder.title.setText(rowsBean.getTitle());
        viewHolder.description.setText(rowsBean.getDescription());

        if (TextUtils.isEmpty(rowsBean.getImageHref())) {
            viewHolder.image.setVisibility(View.GONE);
        } else {
            viewHolder.image.setVisibility(View.VISIBLE);
            setImage(viewHolder.image, rowsBean.getImageHref());
//            setImage(viewHolder.image, "http://7xu7a8.com1.z0.glb.clouddn.com/aa.png");
        }

        return view;
    }

    static class ViewHolder {
        public TextView title;
        public TextView description;
        public SimpleDraweeView image;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            image = (SimpleDraweeView) view.findViewById(R.id.image);
        }
    }

    private void setImage(SimpleDraweeView userAvatar, String avatarUrl) {
        Uri uri = Uri.parse(avatarUrl);
        userAvatar.setImageURI(uri);

        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setTapToRetryEnabled(true)
                .build();
        userAvatar.setController(draweeController);
    }

    private View getLoadmoreFooterView(ViewGroup parent, boolean isShowLoadmore) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.loadmore, parent, false);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
        if (isShowLoadmore)
            layout.setVisibility(View.VISIBLE);
        else layout.setVisibility(View.GONE);
        return view;
    }
}
