public class Correu {
    private String correu;

    public Correu(String correu) {
        this.correu = correu;
    }

    public Correu() {
    }

    public String getCorreu() {
        return correu;
    }

    public void setCorreu(String correu) {
        this.correu = correu;
    }

    public boolean validarCorreu(String correu) {

        if (!correu.contains("@")) {
            System.out.println("Falet un @");
            return false;
        }

        if (!correu.contains(".")) {
            System.out.println("Falte un punt");
            return false;
        }

        return true;

    }
}
