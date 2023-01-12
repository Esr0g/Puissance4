// Definition du type Etat (état/position du jeu)
public class EtatJeu {

    // Joueur qui doit jouer
    private int joueur;
    private final char plateau[][];

    public EtatJeu(int j) {
        this.joueur = j;
        this.plateau = new char[3][3];
        for (int i = 0; i < this.plateau.length; i++) {
            for (j = 0; j < this.plateau[i].length; j++) {
                this.plateau[i][j] = ' ';
            }
        }
    }

    public String afficher() {
        StringBuilder sb = new StringBuilder();
        sb.append("   |");
        for (int j = 0; j < 3; j++) {
            sb.append(" ").append(j).append(" |");
        }

        sb.append("\n----------------\n");

        for (int i = 0; i < 3; i++) {
            sb.append(" ").append(i).append(" |");
            for (int j = 0; j < 3; j++) {
                sb.append(" ").append(this.plateau[i][j]).append(" |");
            }
            sb.append("\n----------------\n");
        }

        return sb.toString();
    }
}
