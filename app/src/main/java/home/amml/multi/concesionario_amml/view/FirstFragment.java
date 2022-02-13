package home.amml.multi.concesionario_amml.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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

    private final String sqlBase = "SELECT * FROM coches";
    private String sqlOrderBy = "";
    private String sqlASC_Or_DESC = "";
    private static String lastSQL = "";

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
//        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
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
        stringArrayAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, orderOptions);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_OrderOptions = binding.spinnerOrderOptions;
        spinner_OrderOptions.setAdapter(stringArrayAdapter);
        spinner_OrderOptions.setOnItemSelectedListener(this);

        stringArrayAdapter1 = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, orderOptions_ASC_Or_DESC);
        stringArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_OrderOptions_ASC_Or_DESC = binding.spinnerOrderOptionsASCOrDESC;
        spinner_OrderOptions_ASC_Or_DESC.setAdapter(stringArrayAdapter1);
        spinner_OrderOptions_ASC_Or_DESC.setOnItemSelectedListener(this);
    }

    private void initialize() {
        createSpinners();
    }

    private void getListCar(String sql) {
        MainActivity.cars.getCarsFromResultSet(sql);
        Log.v("FFLANZAR SQL", "getListCar");
        MainActivity.cars.setSqlCars(sql);
        lastSQL = MainActivity.cars.getSQLCars();
    }

    private String createSQL(String item) {
        switch (item) {
            case "ref":
                sqlOrderBy = " ORDER BY ref";
                break;
            case "nombre":
                sqlOrderBy = " ORDER BY title";
                break;
            case "precio":
                sqlOrderBy = " ORDER BY price";
                break;
            case "principio":
                sqlASC_Or_DESC = " ASC LIMIT 1000";
                break;
            case "final":
                sqlASC_Or_DESC = " DESC LIMIT 1000";
                break;
            default:
                break;
        }
        if(sqlASC_Or_DESC.isEmpty()){
            sqlASC_Or_DESC = " ASC LIMIT 1000";
        }
        String sqlFinalQuery = sqlBase + sqlOrderBy + sqlASC_Or_DESC;
        Log.v("FFCREAR SQL", sqlFinalQuery);
        return sqlFinalQuery;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        String sqlFinalQuery = createSQL(item.toLowerCase());
        if(!lastSQL.equals(sqlFinalQuery)){
            getListCar(sqlFinalQuery);
            createRVCars(MainActivity.cars.getCars());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static String getLastSQL(){
        return lastSQL;
    }

    public static void setLastSQL(String sql){
        lastSQL = sql;
    }
}