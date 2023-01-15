public class Coup {

    private final int column;

    public Coup (int c) {
        this.column = c;
    }

    public int getColumn() {
        return this.column;
    }

    @Override
    public boolean equals(Object obj) {
        return this.column == ((Coup)obj).getColumn();
    }
}
