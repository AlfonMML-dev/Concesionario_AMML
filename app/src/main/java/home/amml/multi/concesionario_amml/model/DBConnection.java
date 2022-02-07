package home.amml.multi.concesionario_amml.model;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    //    String sql = "SELECT * FROM coches";
    static String driver = "org.gjt.mm.mysql.Driver";
    static String url = "jdbc:mysql://146.59.237.189:3306/dam208_ammlconcesionario";
    static String user = "dam208_amml";
    static String password = "dam208_amml";

    public static Connection establecerConexionBD() {
        Connection cnn = null;
        StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(politica);
        try {
            Class.forName(driver).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Asignamos los datos de la BD
        try {
            cnn = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cnn;
    }

    public static ResultSet getRS(String sql) {
        //Objeto que va a devolver el método
        ResultSet rs = null;
        //Hacemos la conexion
        Statement stm = null;
        try {
            stm = establecerConexionBD().createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //Obtenemos todos los datos de todos los cars con la consulta SQL
        try {
            rs = stm.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs;
    }
}
