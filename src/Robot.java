public class Robot {
    private static int cont = 0;
    private int numDeSerie;

    public Robot() {
        numDeSerie = cont++;
    }

    public int getNumDeSerie() {
        return numDeSerie;
    }

    @Override
    public String toString() {
        return "Robot " + numDeSerie;
    }
}
