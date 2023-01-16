public class Coup {

    private final int colonne;

    public Coup (int c) {
        this.colonne = c;
    }

    public int getColonne() {
        return this.colonne;
    }

    @Override
    public boolean equals(Object obj) {
        return this.colonne == ((Coup)obj).getColonne();
    }

    public Coup copy() {
        return new Coup(this.colonne);
    }
}
