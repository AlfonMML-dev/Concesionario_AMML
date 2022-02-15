package home.amml.multi.concesionario_amml.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import home.amml.multi.concesionario_amml.databinding.FragmentFirstBinding;
import home.amml.multi.concesionario_amml.model.Car;
import home.amml.multi.concesionario_amml.model.Cars;
import home.amml.multi.concesionario_amml.view.adapter.CarAdapter;

public class FirstFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentFirstBinding binding;

    private ArrayAdapter<String> stringArrayAdapter, stringArrayAdapter1;
    private Spinner spinner_OrderOptions;
    private Spinner spinner_OrderOptions_ASC_Or_DESC;
    private String[] orderOptions = {"REF", "Nombre", "Precio"};
    private String[] orderOptions_ASC_Or_DESC = {"Principio", "Final"};

    private String[] lastOrder = {"", ""};

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void createRVCars(ArrayList<Car> listCars) {
        binding.recyclerViewCarListMain.setLayoutManager(new LinearLayoutManager(getContext()));
        CarAdapter carAdapter = new CarAdapter(getContext());
        binding.recyclerViewCarListMain.setAdapter(carAdapter);
        carAdapter.setCarList(listCars);
    }

    private void createSpinners(){
        stringArrayAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, orderOptions);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_OrderOptions = binding.spinnerOrderOptions;
        spinner_OrderOptions.setAdapter(stringArrayAdapter);
        spinner_OrderOptions.setOnItemSelectedListener(this);

        stringArrayAdapter1 = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, orderOptions_ASC_Or_DESC);
        stringArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_OrderOptions_ASC_Or_DESC = binding.spinnerOrderOptionsASCOrDESC;
        spinner_OrderOptions_ASC_Or_DESC.setAdapter(stringArrayAdapter1);
        spinner_OrderOptions_ASC_Or_DESC.setOnItemSelectedListener(this);
    }

    private void initialize() {
        createSpinners();
    }

    public ArrayList<Car> ordernarLista(){
        ArrayList<Car> listaOrdenada = MainActivity.carArrayList;
        boolean asc_or_desc = (lastOrder[1].equalsIgnoreCase(orderOptions_ASC_Or_DESC[0])
                || lastOrder[1].isEmpty()) ? true : false;
        switch (lastOrder[0]){
            case "ref":
                listaOrdenada = new Cars().orderByREF(listaOrdenada, asc_or_desc);
                break;
            case "nombre":
                listaOrdenada = new Cars().orderByName(listaOrdenada, asc_or_desc);
                break;
            case "precio":
                listaOrdenada = new Cars().orderByPrice(listaOrdenada, asc_or_desc);
                break;
        }
        return listaOrdenada;
    }

    public void setOrder(String item){
        for (String orderOption: orderOptions) {
            if (item.equalsIgnoreCase(orderOption)) {
                lastOrder[0] = item;
                return;
            }
        }
        for (String orderOption_ASC_Or_DESC: orderOptions_ASC_Or_DESC) {
            if (item.equalsIgnoreCase(orderOption_ASC_Or_DESC)){
                lastOrder[1] = item;
                break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        setOrder(item.toLowerCase());
        createRVCars(ordernarLista());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}