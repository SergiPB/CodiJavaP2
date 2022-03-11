import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexioBD {

    private Connection conBD;

    private String servidor = "jdbc:mysql://localhost:3306/";
    private String bbdd = "projecte2";
    private String user = "root";
    private String password = "1234";

    public Connection conBD() {

        try {
            conBD = DriverManager.getConnection(servidor + bbdd, user, password);
        } catch (SQLException ex) {
            System.out.println("No sa pogut connectar");
            ex.printStackTrace();
        }
        return conBD;
    }

    public void desBD() {

        try {
            conBD.close();
        } catch (SQLException e) {

            System.out.println("No sa pogut tancar la conexio amb la base de dades");
            e.printStackTrace();
        }
    }

}
