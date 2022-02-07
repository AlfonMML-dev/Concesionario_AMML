package home.amml.multi.concesionario_amml.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import home.amml.multi.concesionario_amml.R;
import home.amml.multi.concesionario_amml.view.adapter.viewholder.SliderImageViewHolder;

public class SliderImageAdapter extends SliderViewAdapter<SliderImageViewHolder> {

    String[] images;
    Context ctx;

    public SliderImageAdapter(String[] images, Context ctx) {
        this.images = images;
        this.ctx = ctx;
    }

    @Override
    public SliderImageViewHolder onCreateViewHolder(ViewGroup viewGroup) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.slider_item, viewGroup, false);

        return new SliderImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderImageViewHolder viewHolder, int position) {
        Glide.with(ctx).load(images[position]).into(viewHolder.iV_Slide_Item);
    }

    @Override
    public int getCount() {
        if (images == null)
            return 0;
        return images.length;
    }
}
