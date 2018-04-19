package com.lc.base.recycle;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lc.base.R;
import com.lc.base.pickphoto.MultiplexImage;

import java.util.List;

/**
 *  图片列表适配器
 * Created by Luocheng on 2016/9/3.
 */
public class ImageAdapter extends BaseQuickAdapter<MultiplexImage, BaseViewHolder> {

    /**
     *
     例子：
     recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
     recyclerView.setAdapter(adapter = new ImageAdapter(images));

     adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    PickPhotoActivity.startActivity(getActivity(), view, images, position);
    }
    });

     */
    public ImageAdapter(List<MultiplexImage> data) {
        super(R.layout.image_recycle_item, data);
    }


    @Override
    protected void convert(BaseViewHolder holder, MultiplexImage multiplexImage) {
        Glide.with(mContext).load(multiplexImage.getImagePath())
                .crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into((ImageView) holder.getView(R.id.iv_));
    }

}
