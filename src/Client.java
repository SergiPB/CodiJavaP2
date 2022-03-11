import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    private String nom;
    private String cognom;
    private Dni DNI;
    private LocalDate dataNaixament;
    private Correu correu;
    private String sexe;
    private Telefon telefon;
    private CompteBancari compteBancari;
    private boolean comunicacio;
    private String condicioFisica;
    private int edat;

    public Client() {
        DNI = new Dni();
        dataNaixament = LocalDate.now();
        correu = new Correu();
        telefon = new Telefon();
        compteBancari = new CompteBancari();
    }

    @Override
    public String toString() {
        return "Client [DNI=" + DNI.getDni() + ", nom=" + nom + ", cognom=" + cognom + ", dataNaixament="
                + dataNaixament + ", edat=" + edat + ", sexe=" + sexe
                + ", correu=" + correu.getCorreu() + ", telefon=" + telefon.getTelefon()
                + ", compteBancari=" + compteBancari.getCCC() + ", comunicacio="
                + comunicacio + ", condicioFisica=" + condicioFisica + "]";
    }

    public void setDNI(Dni dNI) {
        DNI = dNI;
    }

    public void setCorreu(Correu correu) {
        this.correu = correu;
    }

    public void setTelefon(Telefon telefon) {
        this.telefon = telefon;
    }

    public void setCompteBancari(CompteBancari compteBancari) {
        this.compteBancari = compteBancari;
    }

    public void setDataNaixament(LocalDate dataNaixament) {
        this.dataNaixament = dataNaixament;
    }

    public ArrayList<Client> getClientOrCognom() throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();

        ConexioBD bd = new ConexioBD();
        Connection con = bd.conBD();

        String consulta = "SELECT * from clients order by Cognom;";

        PreparedStatement ps = con.prepareStatement(consulta);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Client c1 = new Client();
            c1.cargarDadesDeSentenciaEnClients(rs);

            clients.add(c1);
        }

        return clients;
    }

    public ArrayList<Client> getClientOrMesReserves() throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();

        ConexioBD bd = new ConexioBD();
        Connection con = bd.conBD();

        String consulta = "SELECT C.*,count(R.id_act) FROM reserva R right join clients C on R.DNI = C.DNI GROUP BY R.dni ORDER BY count(R.id_act) desc";

        PreparedStatement ps = con.prepareStatement(consulta);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Client c1 = new Client();
            c1.cargarDadesDeSentenciaEnClients(rs);

            clients.add(c1);
        }

        return clients;
    }

    public ArrayList<Client> getClientOrEdat() throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();

        ConexioBD bd = new ConexioBD();
        Connection con = bd.conBD();

        String consulta = "SELECT * from clients order by Data_naixement;";

        PreparedStatement ps = con.prepareStatement(consulta);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Client c1 = new Client();
            c1.cargarDadesDeSentenciaEnClients(rs);

            clients.add(c1);
        }

        return clients;

    }

    public void consultaClient() throws SQLException {

        Scanner teclat = new Scanner(System.in);

        System.out.println("Indicar el DNI del client");
        String dni = teclat.nextLine();

        Client client = consultaClientBD(dni);

        if (client != null) {

            System.out.println(client);

        } else {
            System.out.println("Client no trobat");
        }

    }

    private Client consultaClientBD(String dni) throws SQLException {

        ConexioBD bd = new ConexioBD();
        Connection con = bd.conBD();

        String consulta = "SELECT * FROM clients where DNI = ?";

        PreparedStatement sentencia = con.prepareStatement(consulta);

        sentencia.setString(1, dni);

        ResultSet rs = sentencia.executeQuery();

        if (rs.next()) {
            cargarDadesDeSentenciaEnClients(rs);

            return this;
        }

        return null;

    }

    public void altaClient() throws SQLException {

        Scanner teclat = new Scanner(System.in);

        Dni dniObj = new Dni();
        String dni;

        do {

            System.out.println("Introdueix el dni del client que vols donar d'alta");
            dni = teclat.next();

        } while (!dniObj.validarDni(dni));

        dniObj.setDni(dni);

        setDNI(dniObj);

        if (consultaClientBD(dniObj.getDni()) != null) {

            System.out.println("El client ja existeix");

        } else {
            // Nom
            System.out.println("Introdueix el nom del client");
            nom = teclat.next();

            // Cognom
            System.out.println("Introdueix el cognom");
            cognom = teclat.next();

            // Data de Naixement
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            boolean dataCorrecta;

            do {
                dataCorrecta = true;
                System.out.println("Introdueix Data Neixement(DD.MM.AAAA)");
                try {
                    this.dataNaixament = LocalDate.parse(teclat.next(), formatter);
                } catch (Exception ex) {
                    dataCorrecta = false;
                }

            } while (!dataCorrecta);

            // Sexe
            System.out.println("Introdueix el sexe (M / F)");
            sexe = teclat.next();

            // Correu
            Correu corObj = new Correu();
            String cor;

            do {

                System.out.println("Introdueix el correu");
                cor = teclat.next();

            } while (!corObj.validarCorreu(cor));

            corObj.setCorreu(cor);

            setCorreu(corObj);

            // Telefon
            Telefon telObj = new Telefon();
            String tel;

            do {

                System.out.println("Introdueix el Telefon");
                tel = teclat.next();

            } while (!telObj.validarTelefon(tel));

            telObj.setTelefon(tel);

            setTelefon(telObj);

            // Compte Bancari
            CompteBancari CCC = new CompteBancari();
            String ccc;

            do {

                System.out.println("Introdueix el CCC del comte bancari");
                ccc = teclat.next();

            } while (!CCC.validarCompte(ccc));

            CCC.setCCC(ccc);
            setCompteBancari(CCC);

            // Condicio Fisica
            System.out.println("Introdueix la condicio fisica");
            teclat.nextLine();
            condicioFisica = teclat.nextLine();

            // Comunicacio
            System.out.println("Indica si vol rebre informacio comercial");
            comunicacio = teclat.nextBoolean();

            altaClientBD();

        }

    }

    private void altaClientBD() throws SQLException {

        ConexioBD bd = new ConexioBD();
        Connection con = bd.conBD();

        String consulta = "INSERT INTO Clients VALUES (?, ?, ? , ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(consulta);

        cargarDadesDeClientsEnSentencia(ps);

        ps.execute();

    }

    public void baixaClient() throws SQLException {

        Scanner teclat = new Scanner(System.in);

        System.out.println("Indicar el DNI del client");
        String dni = teclat.nextLine();

        Client c = consultaClientBD(dni);

        if (c != null) {
            boolean baixa = comprovacioBaixa(dni);

            if (baixa) {
                baixaClientBD(dni);
                System.out.println("S'ha donat de baixa");
            } else {
                System.out.println("El client ja sa donat de baixa anteriorment");
            }

        } else {
            System.out.println("El client no existeix");
        }

    }

    private boolean comprovacioBaixa(String dni) throws SQLException {

        ConexioBD bd = new ConexioBD();
        Connection con = bd.conBD();

        String consulta = "SELECT * FROM Registres where DNI = ?";

        PreparedStatement sentencia = con.prepareStatement(consulta);

        sentencia.setString(1, dni);

        ResultSet rs = sentencia.executeQuery();

        while (rs.next()) {
            if (rs.getDate("Data_Baixa") == null) {
                return true;
            }
        }

        return false;
    }

    private void baixaClientBD(String dni) throws SQLException {

        ConexioBD bd = new ConexioBD();
        Connection con = bd.conBD();

        String consulta = "UPDATE registres set Data_Baixa = localtime() where DNI = ?";

        PreparedStatement ps = con.prepareStatement(consulta);

        ps.setString(1, dni);

        ps.execute();
    }

    public void modificarClients() throws SQLException {
        Scanner teclat = new Scanner(System.in);

        System.out.println("Indicar el DNI del client");
        String dni = teclat.nextLine();

        Client c = consultaClientBD(dni);

        if (c != null) {
            this.DNI.setDni(dni);
            modificarDadesClient();

            modificarClientBD(dni);

        } else {
            System.out.println("El client no existeix");
        }

    }

    private void modificarDadesClient() {

        Scanner teclat = new Scanner(System.in);

        System.out.println("Vols modificar nom (s/n)");
        String mod = teclat.next();
        if (mod.equalsIgnoreCase("s")) {
            // Nom
            System.out.println("Introdueix el nom del client");
            nom = teclat.next();
        }

        System.out.println("Vols modificar cognom (s/n)");
        mod = teclat.next();
        if (mod.equalsIgnoreCase("s")) {
            // Cognom
            System.out.println("Introdueix el cognom");
            cognom = teclat.next();
        }

        System.out.println("Vols modificar data de naixement (s/n)");
        mod = teclat.next();
        if (mod.equalsIgnoreCase("s")) {
            // Data de Naixement
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            boolean dataCorrecta;

            do {
                dataCorrecta = true;
                System.out.println("Introdueix Data Neixement(DD.MM.AAAA)");
                try {
                    this.dataNaixament = LocalDate.parse(teclat.next(), formatter);
                } catch (Exception ex) {
                    dataCorrecta = false;
                }

            } while (!dataCorrecta);
        }

        System.out.println("Vols modificar sexe (s/n)");
        mod = teclat.next();
        if (mod.equalsIgnoreCase("s")) {
            // Sexe
            System.out.println("Introdueix el sexe (M / F)");
            sexe = teclat.next();
        }

        System.out.println("Vols modificar correu (s/n)");
        mod = teclat.next();
        if (mod.equalsIgnoreCase("s")) {
            // Correu
            Correu corObj = new Correu();
            String cor;

            do {

                System.out.println("Introdueix el correu");
                cor = teclat.next();

            } while (!corObj.validarCorreu(cor));

            corObj.setCorreu(cor);

            setCorreu(corObj);
        }

        System.out.println("Vols modificar telefon (s/n)");
        mod = teclat.next();
        if (mod.equalsIgnoreCase("s")) {
            // Telefon
            Telefon telObj = new Telefon();
            String tel;

            do {

                System.out.println("Introdueix el Telefon");
                tel = teclat.next();

            } while (!telObj.validarTelefon(tel));

            telObj.setTelefon(tel);

            setTelefon(telObj);
        }

        System.out.println("Vols modificar compte bancari (s/n)");
        mod = teclat.next();
        if (mod.equalsIgnoreCase("s")) {
            // Compte Bancari
            CompteBancari CCC = new CompteBancari();
            String ccc;

            do {

                System.out.println("Introdueix el CCC del comte bancari");
                ccc = teclat.next();

            } while (!CCC.validarCompte(ccc));

            CCC.setCCC(ccc);
            setCompteBancari(CCC);
        }

        System.out.println("Vols modificar condicio fisica (s/n)");
        mod = teclat.next();
        if (mod.equalsIgnoreCase("s")) {
            // Condicio Fisica
            System.out.println("Introdueix la condicio fisica");
            teclat.nextLine();
            condicioFisica = teclat.nextLine();
        }

        System.out.println("Vols modificar informacio comercial (s/n)");
        mod = teclat.next();
        if (mod.equalsIgnoreCase("s")) {
            // Comunicacio
            System.out.println("Indica si vol rebre informacio comercial");
            comunicacio = teclat.nextBoolean();
        }

    }

    private void modificarClientBD(String dni) throws SQLException {

        ConexioBD bd = new ConexioBD();
        Connection con = bd.conBD();

        String consulta = "UPDATE clients set DNI = ?, Nom = ?, Cognom = ?, Data_naixement = ?, Tel = ?, Correu_e = ?, Sexe = ?, Condicio_Fisica = ?, Comunicació_comercial = ? where DNI = ?";

        PreparedStatement ps = con.prepareStatement(consulta);

        cargarDadesDeClientsEnSentencia(ps);

        ps.setString(10, dni);

        ps.execute();

    }

    public void cargarDadesDeClientsEnSentencia(PreparedStatement ps) throws SQLException {

        ps.setString(1, this.DNI.getDni());
        ps.setString(2, this.nom);
        ps.setString(3, this.cognom);
        ps.setString(4, this.dataNaixament.toString());
        ps.setString(5, this.telefon.getTelefon());
        ps.setString(6, this.correu.getCorreu());
        ps.setString(7, this.sexe);
        ps.setString(8, this.condicioFisica);
        ps.setBoolean(9, this.comunicacio);
        // ps.setString(10, this.compteBancari.getCCC());

    }

    private void cargarDadesDeSentenciaEnClients(ResultSet rs) throws SQLException {

        this.DNI = new Dni(rs.getString("DNI"));
        this.nom = rs.getString("Nom");
        this.cognom = rs.getString("Cognom");
        this.setDataNaixament(rs.getDate("Data_naixement").toLocalDate());
        this.correu = new Correu(rs.getString("Correu_e"));
        this.sexe = rs.getString("Sexe");
        this.telefon = new Telefon(rs.getString("Tel"));
        this.condicioFisica = rs.getString("Condicio_Fisica");
        this.comunicacio = rs.getBoolean("Comunicació_comercial");
        // this.compteBancari = rs.getString("compteBancari");

        calcularEdat();

    }

    private void calcularEdat() {

        LocalDate ara = LocalDate.now();
        this.edat = Period.between(dataNaixament, ara).getYears();

    }

}
