import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Gimnas {

    private String nom;
    private String CIF;
    private Telefon telefon;

    ArrayList<Client> clients;
    ArrayList<Activitat> activitats;
    ArrayList<Reserva> reserves;

    public Gimnas() {
        clients = new ArrayList<>();
        activitats = new ArrayList<>();
        reserves = new ArrayList<>();
    }

    public void gestionarGimnas() throws SQLException {

        Scanner teclat = new Scanner(System.in);
        boolean sortirmenu = false;

        do {

            System.out.println("Introdueix el numero de l'opció");
            System.out.println("");
            System.out.println("********Menu Gestio Gimnas********");
            System.out.println("");
            System.out.println("1.- Gestio de Clients");
            System.out.println("2.- Visualitzar els Clients");
            System.out.println("3.- Activitats diaries");
            System.out.println("4.- Sortir");

            int opcio = teclat.nextInt();

            switch (opcio) {

                case 1:
                    gestioClients();
                    break;

                case 2:
                    menuVisuaCli();
                    break;

                case 3:
                    activitatsDiaries();
                    break;

                case 4:
                    sortirmenu = true;
                    break;

                default:
                    System.out.println("Opció no valida");

            }

        } while (!sortirmenu);

    }

    private void activitatsDiaries() throws SQLException {

        Activitat a = new Activitat();

        this.activitats = a.visalitzaActivitats();
        visualitzarActivitats();

    }

    private void visualitzarActivitats() {
        for (int i = 0; i < activitats.size(); i++) {
            System.out.println(activitats.get(i));
        }
    }

    private void menuVisuaCli() throws SQLException {
        Scanner teclat = new Scanner(System.in);

        Client c = new Client();

        System.out.println("Introdueix el numero de l'opció");
        System.out.println("");
        System.out.println("********Menu Ordencio Clients********");
        System.out.println("");
        System.out.println("1.- Or Cognom");
        System.out.println("2.- Or Edat");
        System.out.println("3.- Or Mes Reserves");
        System.out.println("4.- Sortir");

        int opcio = teclat.nextInt();

        switch (opcio) {

            case 1:
                this.clients = c.getClientOrCognom();
                visualitzarClients();
                break;

            case 2:
                this.clients = c.getClientOrEdat();
                visualitzarClients();
                break;

            case 3:
                this.clients = c.getClientOrMesReserves();
                visualitzarClients();
                break;

            case 4:
                break;

            default:
                System.out.println("Opció no valida");
        }
    }

    private void visualitzarClients() {
        for (int i = 0; i < clients.size(); i++) {
            System.out.println(clients.get(i));
        }
    }

    static void gestioClients() throws SQLException {

        Scanner teclat = new Scanner(System.in);
        boolean sortirmenu = false;

        do {

            System.out.println("Introdueix el numero de l'opció");
            System.out.println("");
            System.out.println("********Menu Gestio Clients********");
            System.out.println("");
            System.out.println("1.- Consulta Client");
            System.out.println("2.- Alta de Clients");
            System.out.println("3.- Baixa de Clients");
            System.out.println("4.- Modificacio de Clients");
            System.out.println("5.- Sortir");

            System.out.println("");

            int opcio = teclat.nextInt();

            switch (opcio) {

                case 1:
                    consultaClients();
                    break;

                case 2:
                    altaClients();
                    break;

                case 3:
                    baixaClients();
                    break;

                case 4:
                    modificarClients();
                    break;

                case 5:
                    sortirmenu = true;
                    break;

                default:
                    System.out.println("Opció no valida");

            }

        } while (!sortirmenu);

    }

    private static void modificarClients() {
        Client c = new Client();

        try {
            c.modificarClients();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Hi ha algun error al modificar el client");
            e.printStackTrace();
        }
    }

    private static void baixaClients() {
        Client c = new Client();

        try {
            c.baixaClient();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Hi ha un error al donar de baixa el client");
            e.printStackTrace();
        }

    }

    private static void consultaClients() {

        Client c = new Client();
        try {
            c.consultaClient();
        } catch (SQLException e) {
            System.out.println("Error al Consultar el usuari");
            e.printStackTrace();
        }

    }

    private static void altaClients() {

        Client c = new Client();
        try {
            c.altaClient();
        } catch (SQLException e) {
            System.out.println("Error al donar d'Alta l'usuari");
            e.printStackTrace();
        }

    }

}
