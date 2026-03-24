import MG2D.*;
import MG2D.geometrie.*;
import java.awt.Font;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

class HighScore {

    public static char suivant(char c) {
	if (c >= 'A' && c < 'Z') return (char)(c + 1);
	if (c == 'Z') return '.';
	if (c == '.') return ' ';
	return 'A';
    }

    public static char precedent(char c) {
	if (c > 'A' && c <= 'Z') return (char)(c - 1);
	if (c == 'A') return ' ';
	if (c == ' ') return '.';
	return 'Z';
    }

    /**
     * Demande au joueur de saisir son nom (3 caractères) avant le début de la partie.
     * Navigation : Gauche/Droite pour déplacer le curseur, Haut/Bas pour changer la lettre,
     * bouton F sur la case OK pour valider.
     */
    public static String demanderNom(Fenetre f, ClavierBorneArcade clavier) {
	f.effacer();

	char[] c = {'A', ' ', ' ', '#'};
	int indexSelec = 0;

	Font font = new Font("Arial", Font.BOLD, 60);
	Font fontPetit = new Font("Arial", Font.PLAIN, 25);

	Texte titre = new Texte(Couleur.JAUNE, "ENTREZ VOTRE NOM", font,
				new Point(640, 250));

	Texte[] caracteres = new Texte[4];
	caracteres[0] = new Texte(Couleur.BLANC, c[0]+"", font, new Point(500, 520));
	caracteres[1] = new Texte(Couleur.BLANC, c[1]+"", font, new Point(650, 520));
	caracteres[2] = new Texte(Couleur.BLANC, c[2]+"", font, new Point(800, 520));
	caracteres[3] = new Texte(Couleur.VERT,  "OK",    font, new Point(950, 520));

	Rectangle rect1 = new Rectangle(Couleur.BLANC, new Point(470, 460), new Point(540, 570), false);
	Rectangle rect2 = new Rectangle(Couleur.BLANC, new Point(620, 460), new Point(690, 570), false);
	Rectangle rect3 = new Rectangle(Couleur.BLANC, new Point(770, 460), new Point(840, 570), false);
	Rectangle rect4 = new Rectangle(Couleur.VERT,  new Point(920, 460), new Point(990, 570), false);

	// Flèche de sélection (triangle pointant vers le bas, au-dessus de la case active)
	Triangle select = new Triangle(Couleur.BLANC,
				       new Point(505, 450),
				       new Point(485, 410),
				       new Point(525, 410),
				       true);

	Texte aide = new Texte(Couleur.BLANC,
			       "Haut/Bas: lettre   Gauche/Droite: case   F: valider sur OK",
			       fontPetit, new Point(640, 680));

	f.ajouter(titre);
	f.ajouter(caracteres[0]);
	f.ajouter(caracteres[1]);
	f.ajouter(caracteres[2]);
	f.ajouter(caracteres[3]);
	f.ajouter(rect1);
	f.ajouter(rect2);
	f.ajouter(rect3);
	f.ajouter(rect4);
	f.ajouter(select);
	f.ajouter(aide);

	boolean fin = false;
	while (!fin) {
	    try { Thread.sleep(10); } catch (Exception e) {}

	    if (clavier.getJoyJ1DroiteTape()) {
		if (indexSelec < 3) {
		    indexSelec++;
		    select.translater(150, 0);
		}
	    }
	    if (clavier.getJoyJ1GaucheTape()) {
		if (indexSelec > 0) {
		    indexSelec--;
		    select.translater(-150, 0);
		}
	    }
	    if (clavier.getJoyJ1HautTape() && indexSelec != 3) {
		c[indexSelec] = suivant(c[indexSelec]);
		caracteres[indexSelec].setTexte(c[indexSelec]+"");
	    }
	    if (clavier.getJoyJ1BasTape() && indexSelec != 3) {
		c[indexSelec] = precedent(c[indexSelec]);
		caracteres[indexSelec].setTexte(c[indexSelec]+"");
	    }
	    if (clavier.getBoutonJ1ATape() && indexSelec == 3) {
		fin = true;
	    }

	    f.rafraichir();
	}

	return "" + c[0] + c[1] + c[2];
    }

    public static ArrayList<LigneHighScore> lireFichier(String fichier) {
	ArrayList<LigneHighScore> l = new ArrayList<LigneHighScore>();
	try {
	    BufferedReader reader = new BufferedReader(new FileReader(fichier));
	    String currentLine;
	    while ((currentLine = reader.readLine()) != null) {
		l.add(new LigneHighScore(currentLine));
	    }
	    reader.close();
	} catch (Exception e) {}
	return l;
    }

    public static void enregistrerFichier(String fichier, ArrayList<LigneHighScore> list, String nom, int score) {
	int position = 0;
	boolean fin = false;
	while (!fin) {
	    if (position == list.size()) fin = true;
	    else if (score <= list.get(position).getScore()) position++;
	    else fin = true;
	}

	list.add(position, new LigneHighScore(nom, score));
	while (list.size() > 10) list.remove(list.size() - 1);

	try {
	    BufferedWriter writer = new BufferedWriter(new FileWriter(fichier));
	    for (int i = 0; i < list.size(); i++) {
		writer.write(list.get(i).toString());
		if (i != (list.size() - 1)) writer.write("\n");
	    }
	    writer.close();
	} catch (Exception e) {}
    }
}
