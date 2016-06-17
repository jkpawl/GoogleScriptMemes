package jkpawl.septimasoftware.com.googleappsmems;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jkpawl.septimasoftware.com.googleappsmems.datamodels.RowItem;

public class RowItemAdapter extends RecyclerView.Adapter<RowItemAdapter.ViewHolder> {


    private List<RowItem> mCategoriesMain;
    private Context mContext;

    public RowItemAdapter(List<RowItem> itemsData, Context context) {
        this.mCategoriesMain = itemsData;
        this.mContext = context;
    }

    @Override
    public RowItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_layout, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        if (position < mCategoriesMain.size()) {
            viewHolder.txtViewTitle.setText(mCategoriesMain.get(position).name);
            //viewHolder.imgViewIcon.setImageResource(mCategoriesMain.get(position).imagePlaceholder);

            Picasso.with(mContext)
                    .load(mCategoriesMain.get(position).img)
                    .placeholder(R.drawable.progress_animation)
                    .fit()
//                    .resizeDimen(R.dimen.category_main_image_width, R.dimen.category_main_image_height)
                    .error(R.drawable.error_animation)
//                    .tag(mContext)
                    .into(viewHolder.imgViewIcon);
        }
    }

    @Override
    public int getItemCount() {
        return null != mCategoriesMain ? mCategoriesMain.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_name);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);

        }
    }


}

