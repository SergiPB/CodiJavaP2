public class Telefon {
    private String telefon;

    public Telefon() {
    }

    public Telefon(String telefon) {
        this.telefon = telefon;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public boolean validarTelefon(String tel) {

        if (!(tel.length() == 9)) {
            System.out.println("El telefon no te 9 numeros");
            return false;
        }

        for (int i = 0; i < tel.length(); i++) {
            if (!Character.isDigit(tel.charAt(i))) {
                System.out.println("Algun caracter no es un numero");
                return false;
            }
        }

        return true;
    }
}
