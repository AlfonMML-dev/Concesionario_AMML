package home.amml.multi.concesionario_amml.view.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import home.amml.multi.concesionario_amml.R;

public class SliderImageViewHolder extends SliderViewAdapter.ViewHolder {
    public ImageView iV_Slide_Item;

    public SliderImageViewHolder(View itemView) {
        super(itemView);
        iV_Slide_Item = itemView.findViewById(R.id.iV_Slide_Item);
    }
}
