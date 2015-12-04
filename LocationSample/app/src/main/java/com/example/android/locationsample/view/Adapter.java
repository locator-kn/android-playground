package com.example.android.locationsample.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.locationsample.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@EBean
public class Adapter extends BaseAdapter {

    @RootContext
    Context context;

    List<? extends Viewable> viewables;
    List<Bitmap> thumbnails;

    static class ViewHolder {
        TextView title;
        TextView description;
        ImageView icon;
    }

    public Adapter(Context context) {
        super();
        viewables = new LinkedList<>();
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .threadPoolSize(1)
                    .build();
            ImageLoader.getInstance().init(config);
        }
    }

    public void clear() {
        viewables.clear();
        notifyDataSetChanged();
    }

    public void setViewables(List<? extends Viewable> viewables) {
        thumbnails = Arrays.asList(new Bitmap[viewables.size()]);
        this.viewables = viewables;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return viewables.size();
    }

    @Override
    public Object getItem(int position) {
        return viewables.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rowlayout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        }

        final Viewable viewable = viewables.get(position);
        if (thumbnails.get(position) == null) {
            loadImage(viewable.image(), position);
        }
        viewHolder = (ViewHolder)convertView.getTag();
        viewHolder.title.setText(viewable.text());
        viewHolder.description.setText(viewable.description());
        viewHolder.icon.setImageBitmap(thumbnails.get(position));
        return convertView;
    }

    private void loadImage(final String image, final int position) {
        ImageLoader.getInstance().loadImage(image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }
            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                Bitmap fallback = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
                thumbnails.set(position, fallback);
                notifyDataSetChanged();
            }
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                thumbnails.set(position, loadedImage);
                notifyDataSetChanged();
            }
            @Override
            public void onLoadingCancelled(String imageUri, View view) {
            }
        });
    }
}
