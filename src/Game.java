import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        System.out.println("Qui commence (0 : humain, 1 : ordinateur)");
        Scanner scan = new Scanner(System.in);
        int joueur = 0;
        joueur = scan.nextInt();
        EtatJeu jeu = new EtatJeu(joueur);

        do {
            if (jeu.joueurJoue()) {
                Coup cp = jeu.demanderCoup();
                jeu.jouerCoup(cp);
            } else {
                jeu.ordiJoue();
            }
            System.out.println(jeu.afficher());
            jeu.testFin();

            if (jeu.estTermine()) {
                System.out.println(jeu.messageFin());
            }
        } while (!jeu.estTermine());
    }
}