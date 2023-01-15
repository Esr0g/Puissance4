import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Definition du type Etat (état/position du jeu)
public class Puissance4 {

    // Joueur qui doit jouer
    private int joueur;
    private char[][] plateau;
    private boolean quitter = false;
    private GameState gameState = GameState.RUNNING;
    // Coup qui a été jouer pour arriver dans cet état
    private Coup coupJoue;

    public Puissance4(int j) {
        this.joueur = j;
        this.plateau = new char[6][7];
        for (int i = 0; i < this.plateau.length; i++) {
            for (j = 0; j < this.plateau[i].length; j++) {
                this.plateau[i][j] = ' ';
            }
        }
    }

    public Puissance4 copy() {
        Puissance4 copie = new Puissance4(this.joueur);

        for (int i = 0 ; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                copie.setCase(i, j, plateau[i][j]);
            }
        }

        copie.quitter = this.quitter;
        copie.gameState = this.gameState;
        return copie;
    }

    private void setCase(int l, int c, char p) {
        this.plateau[l][c] = p;
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
        System.out.println("Quelle colonne ? (Entrer un nombre entre 0 et 6, 10 pour quitter)");
        int c;
        Scanner scan = new Scanner(System.in);
        c = scan.nextInt();

        // Quitter
        if (c == 10) {
            return new Coup(c);
        }

        // c doit être compris entre 0 et 6 OU égale à 10
        while (!(c >= 0 && c <= 6)) {
            System.out.println("Quelle colonne ? (Entrer un nombre entre 0 et 6, 10 pour quitter)");
            scan = new Scanner(System.in);
            c = scan.nextInt();

            // Quitter
            if (c == 10) {
                return new Coup(c);
            }
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
        if (cp.getColumn() == 10) {
            this.quitter = true;
            return false;

            //TODO Faire les vérifications dans demanderCoup
        } else if (cp.getColumn() < 0 || cp.getColumn() > 6) {
            return false;

        // Si la colonne est pleine alors, le coup est impossible
        } else if (plateau[0][cp.getColumn()] != ' ') {
            return false;

        } else {

            for (int i = 0 ; i < plateau.length; i++) {

                if ( i + 1 == plateau.length && plateau[i][cp.getColumn()] == ' ') {
                   plateau[i][cp.getColumn()] = (this.joueur == 1) ? 'O' : 'X';
                   this.joueurSuivant();
                   return true;

                } else if (plateau[i + 1][cp.getColumn()] != ' ') {
                    plateau[i][cp.getColumn()] = (this.joueur == 1) ? 'O' : 'X';
                    this.joueurSuivant();
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

        if (!estTermine()) {
            for (int j = 0; j < plateau[0].length; j++) {
                if (plateau[0][j] == ' ') {
                    cps.add(new Coup(j));
                }
            }
        } else {
            throw new RuntimeException("Partie terminé coup impossible");
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
                    while( k < 4 && j + k < plateau[i].length && plateau[i][j + k] == plateau[i][j]) {
                        k++;
                    }
                    if (k == 4) {
                        this.gameState = (plateau[i][j] == 'O') ? GameState.CPU_WINS : GameState.PLAYER_WINS;
                        return true;
                    }

                    // colonnes
                    k = 0;
                    while (k < 4 && i + k < plateau.length && plateau[i + k][j] == plateau[i][j]) {
                        k++;
                    }
                    if (k == 4) {
                        this.gameState = (plateau[i][j] == 'O') ? GameState.CPU_WINS : GameState.PLAYER_WINS;
                        return true;
                    }

                    //diagonales
                    k = 0;
                    while ( k < 4 && i + k < plateau.length && j + k < plateau[i].length && plateau[i + k][j + k] == plateau[i][j] ) {
                        k++;
                    }
                    if ( k == 4 ) {
                        this.gameState = plateau[i][j] == 'O'? GameState.CPU_WINS : GameState.PLAYER_WINS;
                        return true;
                    }

                    k = 0;
                    while ( k < 4 && i - k >= 0 && j - k >= 0 && plateau[i - k][j - k] == plateau[i][j] ) {
                        k++;
                    }
                    if ( k == 4 ) {
                        this.gameState = plateau[i][j] == 'O'? GameState.CPU_WINS : GameState.PLAYER_WINS;
                        return  true;
                    }

                }
            }
        }

        if (nbcp == 6 * 7) {
            this.gameState = GameState.DRAW;
            return true;
        }

        this.gameState = GameState.RUNNING;
        return false;
    }

    public boolean estTermine() {
        return (this.gameState != GameState.RUNNING) || quitter;
    }

    public String messageFin() {
        if (quitter) {
            return "Vous avez quitter la partie.";
        } else {
            switch (this.gameState) {
                case DRAW:
                    return "Matche nul !";
                case PLAYER_WINS:
                    return "Vous avez gagné la partie !";
                case CPU_WINS:
                    return "Vous avez perdu !";
            }
        }

        return "";
    }

    public void ordiJoue() {
        long startTime = System.currentTimeMillis();
        long currenTime = startTime;

        Node racine = new Node(null, this);

        while (currenTime - startTime < 5000) {
            MCTS_UTC(racine);
            currenTime = System.currentTimeMillis();
        }

        System.out.println(racine.getNbPassage());
        racine.sortMax();

        this.plateau = racine.getEnfant(0).getEtat().plateau;
        this.joueurSuivant();
    }

    private int MCTS_UTC(Node n) {
        if (n.isFeuille()) {
            // Si il y'a 0 passage on lance une simulation
            if (n.getNbPassage() == 0 && n.getParent() != null) {
                int resSimul = 0;

                n.getEtat().testFin();

                if (!n.getEtat().estTermine()) {
                    resSimul = n.getEtat().simulate(n.getEtat());
                } else {
                    resSimul = n.getEtat().evaluate();
                    n.finExploration();
                }

                n.increaseNbPassage();
                n.setNbVictoire(n.getNbVictoire() + resSimul);

                return resSimul;
            } else {    // Sinon on rajoute un enfant et après on fait une simulation
                List<Coup> cps = n.getEtat().coupPossible();

                for (Coup cp: cps) {
                    Puissance4 et = n.getEtat().copy();
                    et.jouerCoup(cp);
                    n.ajouterEnfant(new Node(n, et));
                }

                int res = MCTS_UTC(n.getEnfant(0));
                n.setNbVictoire(n.getNbVictoire() + res);
                n.increaseNbPassage();

                return res;
            }
            // et simulation
        } else {
            if (n.getEtat().joueurJoue()) {
                // on fait mcts sur min des enfants
                n.sortMini();
                int res = -1;
                boolean finBranche = true;

                for (Node e: n) {
                    if (e.estExplorable()) {
                        res = MCTS_UTC(e);

                        if (res >= 0) {
                            n.increaseNbPassage();
                            n.setNbVictoire(n.getNbVictoire() + res);
                            finBranche = false;
                            break;
                        }
                    }
                }

                if (finBranche) {
                    n.finExploration();
                }

                return res;
            } else {
                // on fait mcts sur max des enfants
                n.sortMax();
                int res = -1;
                boolean finBranche = true;

                for (Node e: n) {
                    if (e.estExplorable()) {
                        res = MCTS_UTC(e);

                        if (res >= 0) {
                            n.increaseNbPassage();
                            n.setNbVictoire(n.getNbVictoire() + res);
                            finBranche = false;
                            break;
                        }
                    }
                }

                if (finBranche) {
                    n.finExploration();
                }

                return res;
            }
        }
    }

    public boolean joueurJoue() {
        return this.joueur == 0;
    }

    private int evaluate() {
        switch (this.gameState) {
            case CPU_WINS:
                return 1;
            case DRAW:
            case PLAYER_WINS:
                return 0;
        }

        return 0;
    }

    public int simulate(Puissance4 et) {
        Puissance4 simul = et.copy();
        int i = 0;

         do {
            List<Coup> cps = simul.coupPossible();
            Random rand = new Random();
            int choix = rand.nextInt(cps.size());
            simul.jouerCoup(cps.get(choix));
            simul.testFin();
        } while(!simul.estTermine());

        return simul.evaluate();
    }
}
