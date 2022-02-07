package home.amml.multi.concesionario_amml.view.adapter.viewholder;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import home.amml.multi.concesionario_amml.R;
import home.amml.multi.concesionario_amml.model.Car;

public class CarViewHolder extends RecyclerView.ViewHolder {

    public Car car;
    public Bundle bundle;
    public TextView tv_Title, tv_Price, tv_Date, tv_Kilometers, tv_Horsepower, tv_Fuel, tv_GearLever, tv_REF;
    public ImageView iV_Car_Item_Car;

    public CarViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_Title = itemView.findViewById(R.id.tv_Title);
        tv_Price = itemView.findViewById(R.id.tv_Price);
        tv_Date = itemView.findViewById(R.id.tv_Date);
        tv_Kilometers = itemView.findViewById(R.id.tv_Kilometers);
        tv_Horsepower = itemView.findViewById(R.id.tv_Horsepower);
        tv_Fuel = itemView.findViewById(R.id.tv_Fuel);
        tv_GearLever = itemView.findViewById(R.id.tv_GearLever);
        tv_REF = itemView.findViewById(R.id.tv_REF);
        iV_Car_Item_Car = itemView.findViewById(R.id.iV_Car_Item_Car);


        itemView.setOnClickListener(view -> {
            bundle = new Bundle();
            bundle.putParcelable("car", car);
            Navigation.findNavController(view).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
        });
    }
}
