import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Node implements Iterable<Node> {

    // Joueur qui a joué pour arriver ici
    private int joureur;
    // Coup joué par ce joueur pour arriver ici
    private EtatJeu coup;
    private final Node parent;
    private List<Node> enfants;
    private int nbPassage = 0;
    private double mu = 0;

    public Node(Node parent) {
        this.parent = parent;
        this.enfants = new ArrayList<>();
    }

    public void ajouterEnfant(Node enfant) {
        this.enfants.add(enfant);
    }

    public int getNbPassage() {
        return nbPassage;
    }

    public double getMu() {
        return mu;
    }

    public void setMu(double nm) {
        mu = nm;
    }

    public void increaseNbPassage() {
        this.nbPassage++;
    }

    public Node getParent() {
        return parent;
    }

    @Override
    public Iterator<Node> iterator() {
        return enfants.iterator();
    }
}
