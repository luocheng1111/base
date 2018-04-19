package com.lc.base.pickphoto;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.lc.base.R;

import java.lang.ref.SoftReference;
import java.util.List;


/**
 * Mango PickPhotoAdapter
 * Created by Jelly on 2016/3/10.
 */
public class PickPhotoAdapter extends PagerAdapter {

    private static final String TAG = PickPhotoAdapter.class.getName();

    private Context context;
    /**
     * Image source
     */
    private List<MultiplexImage> images;
    private SparseArray<SoftReference<View>> cacheView;


    public PickPhotoAdapter(Context context, List<MultiplexImage> images) {
        this.context = context;
        this.images = images;
        cacheView = new SparseArray<>(images.size());
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        View view = cacheView.get(position) != null ? cacheView.get(position).get() : null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.pickview_viewpager_item,container,false);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.image = (PhotoView) view.findViewById(R.id.image);
            viewHolder.progressView = (ProgressBar) view.findViewById(R.id.progress);
            viewHolder.oImage = (PhotoView) view.findViewById(R.id.oImage);

            if(images.get(position).getThumImagePath()==null){
                viewHolder.progressView.setVisibility(View.GONE);
                Glide.with(context).load(images.get(position).getImagePath()).into(viewHolder.image);
            }else {
                viewHolder.progressView.setVisibility(View.VISIBLE);
                Glide.with(context).load(images.get(position).getThumImagePath()).into(viewHolder.image);
                Glide.with(context)
                        .load(images.get(position).getImagePath())
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                viewHolder.progressView.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(viewHolder.oImage);
            }

            viewHolder.image.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
                @Override
                public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                    ((Activity) context).finish();
                    return false;
                }

                @Override
                public boolean onDoubleTap(MotionEvent motionEvent) {
                    return false;
                }

                @Override
                public boolean onDoubleTapEvent(MotionEvent motionEvent) {
                    return false;
                }
            });

            viewHolder.oImage.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
                @Override
                public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                    ((Activity) context).finish();
                    return false;
                }

                @Override
                public boolean onDoubleTap(MotionEvent motionEvent) {
                    return false;
                }

                @Override
                public boolean onDoubleTapEvent(MotionEvent motionEvent) {
                    return false;
                }
            });

        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    /**
     * Cache ViewPager ViewHolder
     */
    private class ViewHolder{
        PhotoView image;
        PhotoView oImage;

        ProgressBar progressView;
        PhotoViewAttacher photoViewAttacher;
    }

}
