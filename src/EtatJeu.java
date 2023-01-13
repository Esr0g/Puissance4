import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Definition du type Etat (état/position du jeu)
public class EtatJeu {

    // Joueur qui doit jouer
    private int joueur;
    private final char plateau[][];
    private boolean quitter = false;
    private FinDePartie etatFin = FinDePartie.NON;

    public EtatJeu(int j) {
        this.joueur = j;
        this.plateau = new char[6][7];
        for (int i = 0; i < this.plateau.length; i++) {
            for (j = 0; j < this.plateau[i].length; j++) {
                this.plateau[i][j] = ' ';
            }
        }
    }

    public String afficher() {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < 7; j++) {
            sb.append(" ").append(j).append(" |");
        }

        sb.append("\n----------------------------\n");

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (this.plateau[i][j] == 'X') {
                    sb.append(TexteCouleur.ANSI_YELLOW_BACKGROUND);
                    sb.append(" ").append(this.plateau[i][j]);
                    sb.append(" ").append(TexteCouleur.ANSI_RESET).append("|");
                } else if (this.plateau[i][j] == 'O') {
                    sb.append(TexteCouleur.ANSI_RED_BACKGROUND);
                    sb.append(" ").append(this.plateau[i][j]);
                    sb.append(" ").append(TexteCouleur.ANSI_RESET).append("|");
                } else {
                    sb.append(" ").append(this.plateau[i][j]).append(" |");
                }
            }
            sb.append("\n----------------------------\n");
        }

        return sb.toString();
    }

    public Coup demanderCoup() {
        System.out.println("Quelle colonne ?");
        Scanner scan = new Scanner(System.in);
        int c = -1;

        try {
            c = scan.nextInt();
        } catch (Exception e) {
            System.out.println("Veuillez entrer un nombre entre 0 et 6. (10 pour quitter).");
            this.demanderCoup();
        }
        return new Coup(c);
    }

    public void joueurSuivant() {
        if (this.joueur == 0) {
            this.joueur = 1;
        } else {
            this.joueur = 0;
        }
    }

    public boolean jouerCoup(Coup cp) {

        // 10 pour quitter
        if (cp.getColonne() == 10) {
            this.quitter = true;
            return false;
        } else if (cp.getColonne() < 0 || cp.getColonne() > 6) {
            return false;
        } else if (plateau[0][cp.getColonne()] != ' ') { // Si la colonne est pleine alors, le coups est impossible
            return false;
        } else {
            for (int i = 0 ; i < plateau.length; i++) {
                if ( i + 1 == plateau.length && plateau[i][cp.getColonne()] == ' ') {
                   plateau[i][cp.getColonne()] = (this.joueur == 1) ? 'O' : 'X';
                   joueurSuivant();
                   return true;
                } else if (plateau[i + 1][cp.getColonne()] != ' ') {
                    plateau[i][cp.getColonne()] = (this.joueur == 1) ? 'O' : 'X';
                    joueurSuivant();
                    return true;
                }
            }
        }
        return true;
    }

    public boolean doitQuitter() {
        return this.quitter;
    }

    public List<Coup> coupPossible() {
        List<Coup> cps = new ArrayList<>();

        for (int j = 0; j < plateau.length; j++) {
            if (plateau[0][j] == ' ') {
                cps.add(new Coup(j));
            }
        }

        return cps;
    }

    public boolean testFin() {
        int nbcp = 0; // nombre de coups joués

        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j] != ' ') {
                    nbcp++;

                    // lignes
                    int k = 0;
                    while( k < 4 && i + k < plateau.length && plateau[i + k][j] == plateau[i][j]) {
                        k++;
                    }
                    if (k == 4) {
                        this.etatFin = (plateau[i][j] == 'O') ? FinDePartie.ORDI_GAGNE : FinDePartie.HUMAIN_GAGNE;
                        return true;
                    }

                    // colonnes
                    k = 0;
                    while (k < 4 && j + k < plateau[i].length && plateau[i][j + k] == plateau[i][j]) {
                        k++;
                    }
                    if (k == 4) {
                        this.etatFin = (plateau[i][j] == 'O') ? FinDePartie.ORDI_GAGNE : FinDePartie.HUMAIN_GAGNE;
                        return true;
                    }

                    //diagonales
                    k = 0;
                    while ( k < 4 && i + k < plateau.length && j + k < plateau[i].length && plateau[i + k][j + k] == plateau[i][j] ) {
                        k++;
                    }
                    if ( k == 4 ) {
                        this.etatFin = plateau[i][j] == 'O'? FinDePartie.ORDI_GAGNE : FinDePartie.HUMAIN_GAGNE;
                        return true;
                    }

                    k = 0;
                    while ( k < 4 && i + k < plateau.length && j - k >= 0 && plateau[i + k][j - k] == plateau[i][j] ) {
                        k++;
                    }
                    if ( k == 4 ) {
                        this.etatFin = plateau[i][j] == 'O'? FinDePartie.ORDI_GAGNE : FinDePartie.HUMAIN_GAGNE;
                        return  true;
                    }

                }
            }
        }

        if (nbcp == 6 * 7) {
            this.etatFin = FinDePartie.MATCHNUL;
            return true;
        }

        this.etatFin = FinDePartie.NON;
        return false;
    }

    public boolean estTermine() {
        return (this.etatFin != FinDePartie.NON) || quitter;
    }

    public String messageFin() {
        if (quitter) {
            return "Vous avez quitter la partie.";
        } else {
            switch (this.etatFin) {
                case MATCHNUL:
                    return "Matche null !";
                case HUMAIN_GAGNE:
                    return "Vous avez gagné la partie !";
                case ORDI_GAGNE:
                    return "Vous avez perdu !";
            }
        }

        return "";
    }

    public void ordiJoue() {
        List<Coup> cps = coupPossible();
        Random rand = new Random();
        int choix = rand.nextInt(cps.size());
        jouerCoup(cps.get(choix));
    }

    public boolean joueurJoue() {
        return this.joueur == 0;
    }
}
