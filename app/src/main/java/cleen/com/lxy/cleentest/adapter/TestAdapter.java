package cleen.com.lxy.cleentest.adapter;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
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

    public TestAdapter(BaseActivity mActivity) {
        this.mActivity = mActivity;
        rowsBeanList = new ArrayList<>();
    }

    public void setRowsBeans(List<RowsBean> rowsBeanList) {
        this.rowsBeanList = rowsBeanList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return rowsBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return rowsBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (view == null) {
            view = mActivity.getLayoutInflater().inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder(view);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        RowsBean rowsBean = rowsBeanList.get(i);
        viewHolder.title.setText(rowsBean.getTitle());
        viewHolder.description.setText(rowsBean.getDescription());

        if (TextUtils.isEmpty(rowsBean.getImageHref())) {
            viewHolder.image.setVisibility(View.GONE);
        } else {
            viewHolder.image.setVisibility(View.VISIBLE);
//            setImage(viewHolder.image, rowsBean.getImageHref());
            setImage(viewHolder.image, "http://7xu7a8.com1.z0.glb.clouddn.com/aa.png");
        }

        return view;
    }

    static class ViewHolder {
        public TextView title;
        public TextView description;
        public SimpleDraweeView image;

        public ViewHolder(View view){
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
}
