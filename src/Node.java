import java.util.*;

public class Node implements Iterable<Node> {

    // Joueur qui a joué pour arriver ici
    private int joureur;
    // Coup joué par ce joueur pour arriver ici
    private Puissance4 etat;
    private Node parent;
    private List<Node> enfants;
    private double nbPassage = 0;
    private double nbVictoire = 0;
    // Par défaut un nœud est explorable, mais si toutes ses branches sont une fin de partie
    // il n'est alors pas nécessaire d'aller dedans.
    private boolean explorable = true;

    public Node(Node parent, Puissance4 etat) {
        this.parent = parent;
        this.enfants = new ArrayList<>();
        this.etat = etat;
    }

    public void ajouterEnfant(Node enfant) {
        this.enfants.add(enfant);
    }

    public double getNbPassage() {
        return nbPassage;
    }

    public double getNbVictoire() {
        return nbVictoire;
    }

    public void setNbVictoire(double nm) {
        nbVictoire = nm;
    }

    public void increaseNbPassage() {
        this.nbPassage += 1;
    }

    public Node getParent() {
        return parent;
    }

    @Override
    public Iterator<Node> iterator() {
        return enfants.iterator();
    }

    public Puissance4 getEtat() {
        return etat;
    }

    public boolean isFeuille() {
        return this.enfants.size() == 0;
    }

    public void sortMax() {
        this.enfants.sort(new Comparator<Node>() {
            @Override
            public int compare(Node node, Node t1) {
                double B1 = node.getMu() + node.getValRacine();
                double B2 = t1.getMu() + t1.getValRacine();

                return -Double.compare(B1, B2);
            }
        });
    }

    public void sortMini() {
        this.enfants.sort(new Comparator<Node>() {
            @Override
            public int compare(Node node, Node t1) {
                double B1 = -node.getMu() + node.getValRacine();
                double B2 = -t1.getMu() + t1.getValRacine();

                return -Double.compare(B1, B2);
            }
        });
    }

    public void sortMeilleurChoix() {
        this.enfants.sort(new Comparator<Node>() {
            @Override
            public int compare(Node node, Node t1) {
                double B1 = node.getMu();
                double B2 = t1.getMu();

                if (B1 > B2) {
                    return -1;
                } else if (B1 < B2) {
                    return 1;
                } else {
                    double p1 = node.getNbPassage();
                    double p2 = t1.getNbPassage();

                    return -Double.compare(p1, p2);
                }
            }
        });
    }

    public Node getEnfant(int i) {
        return this.enfants.get(i);
    }

    public void setNbPassage(double x) {
        this.nbPassage = x;
    }

    public boolean estExplorable() {
        return this.explorable;
    }

    public void finExploration() {
        this.explorable = false;
    }

    // Permet de récupéré la partie droite de l'équation dans le cours
    public double getValRacine() {
        if (this.getNbPassage() == 0) {
            return Double.MAX_VALUE;
        } else {
            return Math.sqrt(2) * Math.sqrt(Math.log(this.getParent().getNbPassage()) / this.getNbPassage());
        }
    }

    public double getMu() {
        if (this.getNbPassage() == 0) {
            return 0;
        } else {
            return (this.getNbVictoire() / this.getNbPassage());
        }
    }

    public Node find(Coup cp) {

        for (Node n : enfants) {
            if (n.getEtat().getCoupJoue().equals(cp)) {
                return n;
            }
        }
        return null;
    }

    public void setParent(Node n) {
        this.parent = n;
    }
}



