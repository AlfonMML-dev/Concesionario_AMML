package home.amml.multi.concesionario_amml.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private Car car;

    SliderView sliderView;

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

        initialize();
    }

    private void initialize() {
        Bundle bundle = new Bundle();
        bundle = getArguments();
        car = bundle.getParcelable("car");
        binding.tvTitleSecond.setText(car.getTitle());
        binding.tvPriceAmount.setText(car.getPrice() + "€");
        binding.tvREFSecond.setText(binding.tvREFSecond.getText() + " " +car.getRef());
        binding.tvDateValueSecond.setText(car.getDate());
        binding.tvKilometersValueSecond.setText(car.getKilometers() + "km");
        binding.tvHorsepowerValueSecond.setText(car.getHorsepower() + "cv");
        binding.tvFuelValueSecond.setText(car.getFuel());
        binding.tvGearLeverValueSecond.setText(car.getGearLever());
        binding.tvDoorsValueSecond.setText(car.getDoors());
        binding.tvWarrantyValueSecond.setText(car.getGearWarranty());
        binding.tvColorValueSecond.setText(car.getColor());
        binding.tvDescriptionValueSecond.setText(car.getDescription());

        cargarSlider();
    }

    private void cargarSlider(){
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

}