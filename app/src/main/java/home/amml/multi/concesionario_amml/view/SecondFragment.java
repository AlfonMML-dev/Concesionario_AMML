package home.amml.multi.concesionario_amml.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import home.amml.multi.concesionario_amml.R;
import home.amml.multi.concesionario_amml.databinding.FragmentSecondBinding;
import home.amml.multi.concesionario_amml.model.Car;
import home.amml.multi.concesionario_amml.view.adapter.SliderImageAdapter;

public class SecondFragment extends Fragment implements View.OnClickListener {

    private FragmentSecondBinding binding;
    private Car car;
    //Hace referencia al coche obtenido al pulsar el componente Button bt_Anterior o bt_Siguiente
    private Car otherCar;

    private Button bt_Anterior, bt_Siguiente;
    SliderView sliderView;

    private boolean first_Time = true;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(SecondFragment.this)
//                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
//            }
//        });

        Bundle bundle = new Bundle();
        bundle = getArguments();
        car = bundle.getParcelable("car");
        initialize(car);
    }

    private void initialize(Car car) {

        cargarComponentes(car);
        cargarSlider(car);

        bt_Anterior = binding.btAnterior;
        bt_Siguiente = binding.btSiguiente;

        bt_Anterior.setOnClickListener(this);
        bt_Siguiente.setOnClickListener(this);
    }

    private void cargarComponentes(Car car) {
        binding.tvTitleSecond.setText(car.getTitle());
        binding.tvPriceAmount.setText(car.getPrice() + "€");
        binding.tvREFSecond.setText(binding.tvREFSecond.getText() + " " + car.getRef());
        binding.tvDateValueSecond.setText(car.getDate());
        binding.tvKilometersValueSecond.setText(car.getKilometers() + "km");
        binding.tvHorsepowerValueSecond.setText(car.getHorsepower() + "cv");
        binding.tvFuelValueSecond.setText(car.getFuel());
        binding.tvGearLeverValueSecond.setText(car.getGearLever());
        binding.tvDoorsValueSecond.setText(car.getDoors());
        binding.tvWarrantyValueSecond.setText(car.getGearWarranty());
        binding.tvColorValueSecond.setText(car.getColor());
        binding.tvDescriptionValueSecond.setText(car.getDescription());
    }

    private void cargarSlider(Car car) {
        String[] images = car.getImages();

        //Hacemos el Slider
        SliderImageAdapter sliderImageAdapter = new SliderImageAdapter(images, this.getContext());
        sliderView = binding.imageSlider;
        sliderView.setSliderAdapter(sliderImageAdapter);
        sliderView.startAutoCycle();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        Button btAux = (Button) v;

        if (btAux.getId() == bt_Anterior.getId()) {
            if(first_Time){
                otherCar = obtenerCoche(car, true);
                first_Time = false;
            } else{
                otherCar = obtenerCoche(otherCar, true);
            }
        } else {
            if(first_Time){
                otherCar = obtenerCoche(car, false);
                first_Time = false;
            } else{
                otherCar = obtenerCoche(otherCar, false);
            }
        }
        initialize(otherCar);
    }

    //True anterior, False siguiente
    public Car obtenerCoche(Car car, boolean anterior_o_Siguiente) {
        Car carReturn = null;
        ArrayList<Car> cars = MainActivity.cars.getCars();
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getRef().equals(car.getRef())) {
                if (anterior_o_Siguiente){
                    if(i == 0){
                        Toast.makeText(this.getContext(), "Es el primer coche",  Toast.LENGTH_SHORT).show();
                        carReturn = car;
                    } else{
                        carReturn = cars.get(i-1);
                    }
                } else{
                    if(i == cars.size()-1){
                        Toast.makeText(this.getContext(), "Es el último coche",  Toast.LENGTH_SHORT).show();
                        carReturn = car;
                    } else{
                        carReturn = cars.get(i+1);
                    }
                }
            }
        }
        return carReturn;
    }

}