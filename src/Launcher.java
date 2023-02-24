import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) {

        // Initialisation début de partie
        System.out.println("Qui commence (0 : humain, 1 : ordinateur)");
        Scanner scan = new Scanner(System.in);
        int joueur;
        joueur = scan.nextInt();
        while (joueur < 0 || joueur > 1) {
            System.out.println("Choisissez un nombre entre 0 et 1 pour savoir qui commence\n" +
                    "(0 : humain, 1 : ordinateur)");
            joueur = scan.nextInt();
        }
        Puissance4 puissance4 = new Puissance4(joueur);
        boolean debut = true;

        do {
            if (debut) {
                System.out.println(puissance4.afficher());
                debut = false;
            }
            if (puissance4.joueurJoue()) {
                Coup cp = puissance4.demanderCoup();
                puissance4.jouerCoup(cp);
            } else {
                puissance4.ordiJoue();
            }
            System.out.println(puissance4.afficher());
            puissance4.testFin();

            if (puissance4.estTermine()) {
                System.out.println(puissance4.messageFin());
            }
        } while (!puissance4.estTermine());
    }
}