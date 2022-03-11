public class Dni {

    private String dni;
    // private int numero;
    // private char lletra;

    public Dni(String dni) {
        this.dni = dni;
    }

    public Dni() {
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public boolean validarDni(String dni) {

        if (dni.length() != 9)
            return false;

        char ultPos = dni.charAt(8);

        if (!Character.isLetter(ultPos))
            return false;

        char lletraMayuscula = Character.toUpperCase(ultPos);

        String dniNum = dni.substring(0, 8);

        if (soloNumeros(dniNum) != true)
            return false;
        if (!(calcularlletraDNI(dniNum) == lletraMayuscula))
            return false;

        return true;

    }

    private boolean soloNumeros(String numero) {

        for (int i = 0; i < numero.length() - 1; i++) {

            if (!Character.isDigit(numero.charAt(i))) {

                System.out.println("Les primeres 8 xifres han de ser numeros");
                return false;

            }

        }
        return true;

    }

    private char calcularlletraDNI(String dniNum) {

        char[] assignacioLletra = { 'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'E', 'S', 'Q',
                'V', 'H', 'L', 'C' };
        int resto = 0;
        char dniLletra = ' ';

        resto = Integer.parseInt(dniNum) % 23;
        dniLletra = assignacioLletra[resto];

        return dniLletra;

    }

}
