package home.amml.multi.concesionario_amml.view.adapter;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import home.amml.multi.concesionario_amml.R;
import home.amml.multi.concesionario_amml.model.Car;
import home.amml.multi.concesionario_amml.view.adapter.viewholder.CarViewHolder;

import java.util.ArrayList;

public class CarAdapter extends RecyclerView.Adapter<CarViewHolder> {

    private ArrayList<Car> carList;
    private Context context;

    public CarAdapter(Context context){ this.context = context; }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);

        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.car = car;
        holder.tv_Title.setText(car.getTitle());
//        Log.v("ADAPTER", holder.tv_Title.getText().toString());
        holder.tv_Price.setText(car.getPrice() + " €");
        holder.tv_Date.setText("Año " + car.getDate());
        String textKM = car.getKilometers();
        if(textKM.equals("NO ESPECIFICADO")){
            holder.tv_Kilometers.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimension(R.dimen.txSize_tV_NOESPECIFICADO));
            holder.tv_Kilometers.setText(textKM + "");
        } else{
            holder.tv_Kilometers.setText(textKM + " km");
        }
        holder.tv_Horsepower.setText(car.getHorsepower() + " cv");
        holder.tv_Fuel.setText(car.getFuel());
        holder.tv_GearLever.setText(car.getGearLever());
        holder.tv_REF.setText(String.valueOf("REF" + car.getRef()));
//        Log.v("ADAPTER", holder.tv_REF.getText().toString());
        Glide.with(context).load(car.getImagenPrincipal()).into(holder.iV_Car_Item_Car);

    }

    @Override
    public int getItemCount() {
        if(carList == null){
            return 0;
        }
        return carList.size();
    }

    public void setCarList(ArrayList<Car> carList){
        this.carList = carList;
    }
}

