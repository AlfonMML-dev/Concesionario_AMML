package home.amml.multi.concesionario_amml.model;

import android.util.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Cars {

    ArrayList<Car> cars = new ArrayList<>();

    public ArrayList<Car> getCars(){
        return cars;
    }
    public void getCarsFromResultSet(String sql) {
        cars = new ArrayList<>();
        if(sql == null || sql.isEmpty()){
            sql = "SELECT * FROM coches";
        }
        ResultSet rs = DBConnection.getResultSet(sql);
        try{
            //Mientras haya siguiente
            while(rs.next()){
                //Creamos un objeto car
                Car car = new Car();

                //Se asignan todos los campos a su valor correspondiente del ResultSet
                //Primer campo -> ref
                car.setRef(rs.getString(1));
                //Segundo campo -> title
                car.setTitle(rs.getString(2));
                //Tercer campo -> description
                car.setDescription(rs.getString(3));
                //Cuarto campo -> categories
                car.setCategoriesValue(getCategoriesStringArray(rs.getString(4)));
                //Quinto campo -> images
                car.setImages(getImagesStringArray(rs.getString(5)));
                //Sexto campo -> price
                car.setPrice(rs.getInt(6));
                //Séptimo campo -> localization
                car.setLocalization(rs.getString(7));
                //Octavo campo -> url
                car.setUrl(rs.getString(8));

                //Agregamos al array de car el car
                cars.add(car);

                //Lo mostramos con Log.v para comprobar que lo hemos hecho bien
                Log.v("XYZXYZ", car.toString());

                //Limpiamos el objeto despues de añadirlo al array por si el siguiente tiene algun
                //campo nulo salga nulo y no el valor del car anterior
                car = null;
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }

        //Lo mostramos con Log.v para comprobar que lo hemos hecho bien
        //Log.v("xyzyz", "Array de cars completo: " + cars.toString());
    }

    private String[] getCategoriesStringArray(String categories){
        //Convierto el String en un array
        String[] aux = categories.split(";");

        /**
         * Recorro el array y lo meto en un nuevo array que será el parámetro de
         * setCategoriesValues(String[] categories).
         */
        String[] categoriesStringArray = new String[aux.length];
        for (int i = 0; i < categoriesStringArray.length; i++) {
            String[] auxValues = aux[i].split(",");
            categoriesStringArray[i] = auxValues[1];
        }
        return categoriesStringArray;
    }

    private String[] getImagesStringArray(String imagenes) {
        //Creamos el array
        String[] imagenesSeparadas;

        //Si es nulo o longitud corta
        if(imagenes.isEmpty() || imagenes.length() <=7){
            return new String[]{"https://static.turbosquid.com/Preview/2014/12/25__23_33_08/01.jpgc343e688-6e75-4628-90e0-b3331493f667Zoom.jpg"};
        }

        //Separamos el String de todas las fotos separadas con ';'
        imagenesSeparadas = imagenes.split(";");

        return imagenesSeparadas;
    }

    public void setSqlCars(String query){
        for (Car car: cars) {
            car.setSql(query);
        }
    }

}
