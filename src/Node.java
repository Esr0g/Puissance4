import java.util.ArrayList;
import java.util.List;

public class Node {

    // Joueur qui a joué pour arriver ici
    private int joureur;
    // Coup joué par ce joueur pour arriver ici
    private Coup coup;
    private final Node parent;
    private List<Node> enfants;

    public Node(Node parent) {
        this.parent = parent;
        this.enfants = new ArrayList<>();
    }

    public void ajouterEnfant(Node enfant) {
        this.enfants.add(enfant);
    }
}
