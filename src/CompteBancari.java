import java.math.BigInteger;

public class CompteBancari {
    private String CCC;

    public CompteBancari(String cCC) {
        CCC = cCC;
    }

    public CompteBancari() {
    }

    public String getCCC() {
        return CCC;
    }

    public void setCCC(String cCC) {
        CCC = cCC;
    }

    public boolean validarCompte(String ccc) {
        boolean esValido = false;
        int i = 2;
        int caracterASCII = 0;
        int resto = 0;
        int dc = 0;
        String cadenaDc = "";
        BigInteger cuentaNumero = new BigInteger("0");
        BigInteger modo = new BigInteger("97");

        if (ccc.length() == 24 && ccc.substring(0, 1).toUpperCase().equals("E")
                && ccc.substring(1, 2).toUpperCase().equals("S")) {

            do {
                caracterASCII = ccc.codePointAt(i);
                esValido = (caracterASCII > 47 && caracterASCII < 58);
                i++;
            } while (i < ccc.length() && esValido);

            if (esValido) {
                cuentaNumero = new BigInteger(ccc.substring(4, 24) + "142800");
                resto = cuentaNumero.mod(modo).intValue();
                dc = 98 - resto;
                cadenaDc = String.valueOf(dc);
            }

            if (dc < 10) {
                cadenaDc = "0" + cadenaDc;
            }

            // Comparamos los caracteres 2 y 3 de la cuenta (dígito de control IBAN) con
            // cadenaDc
            if (ccc.substring(2, 4).equals(cadenaDc)) {
                esValido = true;
            } else {
                esValido = false;
            }
        }

        return esValido;

    }
}