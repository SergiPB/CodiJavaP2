import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

public class Activitat {
    int id_act;
    String nom;
    LocalTime durada;
    LocalTime horaFi;
    LocalTime horaInici;

    public Activitat() {

        durada = LocalTime.now();
        horaFi = LocalTime.now();
        horaInici = LocalTime.now();

    }

    @Override
    public String toString() {
        return "Activitat [id_act=" + id_act + ", nom=" + nom + ", horaInici=" + horaInici + ", horaFi=" + horaFi
                + ", durada=" + durada + "]";
    }

    public ArrayList<Activitat> visalitzaActivitats() throws SQLException {
        ArrayList<Activitat> activitats = new ArrayList<>();

        ConexioBD bd = new ConexioBD();
        Connection con = bd.conBD();

        String consulta = "SELECT A.*,count(R.dni) FROM reserva R right join activitats A on R.id_act = A.ID_Act where R.Data = curdate() GROUP BY R.ID_Act ORDER BY count(R.DNI) desc";

        PreparedStatement ps = con.prepareStatement(consulta);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Activitat a1 = new Activitat();
            a1.cargarDadesDeSentenciaEnActivitat(rs);

            activitats.add(a1);
        }

        return activitats;

    }

    private void cargarDadesDeSentenciaEnActivitat(ResultSet rs) throws SQLException {

        this.id_act = rs.getInt("ID_Act");
        this.nom = rs.getString("Descipció");
        this.durada = rs.getTime("Durada").toLocalTime();
        this.horaFi = rs.getTime("Hora_Fi").toLocalTime();
        this.horaInici = rs.getTime("Hora_i").toLocalTime();

    }
}
