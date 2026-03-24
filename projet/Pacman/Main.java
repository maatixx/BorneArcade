import MG2D.FenetrePleinEcran;
import MG2D.geometrie.*;
import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;

public class Main {
    static final int TAILLEX = 1280;
    static final int TAILLEY = 1024;
    static final int CASE_SIZE = 32;
    
    public static void main(String args[]) {
        FenetrePleinEcran f = new FenetrePleinEcran("Pac-Man");
        f.setVisible(true);
        f.setBackground(Color.BLACK);
        
        ClavierBorneArcade clavier = new ClavierBorneArcade();
        f.addKeyListener(clavier);
        f.getP().addKeyListener(clavier);
        
        // Création du labyrinthe (0=mur, 1=point, 2=vide)
        int[][] labyrinthe = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,1,0,0,1,0,0,0,1,0,0,1,0,1,0,0,1,0,0,0,1,0,0,1,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,1,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,1,0},
            {0,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,0},
            {0,0,0,0,1,0,0,0,1,0,0,2,0,2,0,0,1,0,0,0,1,0,0,0,0},
            {2,2,2,0,1,0,2,2,2,0,2,2,2,2,2,0,2,2,2,0,1,0,2,2,2},
            {0,0,0,0,1,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,1,0,0,0,0},
            {2,2,2,2,1,2,2,0,2,2,2,2,2,2,2,2,2,0,2,2,1,2,2,2,2},
            {0,0,0,0,1,0,2,0,0,0,0,0,0,0,0,0,0,0,2,0,1,0,0,0,0},
            {2,2,2,0,1,0,2,2,2,2,2,2,2,2,2,2,2,2,2,0,1,0,2,2,2},
            {0,0,0,0,1,0,2,0,0,0,0,0,0,0,0,0,0,0,2,0,1,0,0,0,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,1,0,0,1,0,0,0,1,0,0,1,0,1,0,0,1,0,0,0,1,0,0,1,0},
            {0,1,1,0,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,0,1,1,0},
            {0,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1,0,0},
            {0,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,0},
            {0,1,0,0,0,0,0,0,1,0,0,1,0,1,0,0,1,0,0,0,0,0,0,1,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
        };
        
        int offsetX = (TAILLEX - 25 * CASE_SIZE) / 2;
        int offsetY = (TAILLEY - 21 * CASE_SIZE) / 2;
        
        // Position initiale de Pac-Man
        int pacmanX = 12;
        int pacmanY = 15;
        int direction = 0; // 0=haut, 1=droite, 2=bas, 3=gauche
        
        // Fantômes
        ArrayList<int[]> fantomes = new ArrayList<>();
        fantomes.add(new int[]{11, 9, 1}); // x, y, direction
        fantomes.add(new int[]{12, 9, 2});
        fantomes.add(new int[]{13, 9, 3});
        
        int score = 0;
        int compteurMouvement = 0;
        boolean jeuEnCours = true;
        boolean victoire = false;
        
        while (jeuEnCours) {
            // Effacer l'écran
            f.effacer();
            
            // Gérer les entrées
            if (clavier.getJoyJ1HautEnfoncee()) {
                direction = 2;
            } else if (clavier.getJoyJ1DroiteEnfoncee()) {
                direction = 1;
            } else if (clavier.getJoyJ1BasEnfoncee()) {
                direction = 0;
            } else if (clavier.getJoyJ1GaucheEnfoncee()) {
                direction = 3;
            }
            
            if (clavier.getBoutonJ1ZEnfoncee()) { // Bouton Quitter
                jeuEnCours = false;
            }
            
            // Déplacer Pac-Man tous les 8 frames (plus lent)
            if (compteurMouvement % 8 == 0) {
                int newX = pacmanX;
                int newY = pacmanY;
                
                switch (direction) {
                    case 0: newY--; break; // Bas
                    case 1: newX++; break; // Droite
                    case 2: newY++; break; // Haut
                    case 3: newX--; break; // Gauche
                }
                
                // Vérifier collision avec murs
                if (newX >= 0 && newX < 25 && newY >= 0 && newY < 21) {
                    if (labyrinthe[newY][newX] != 0) {
                        pacmanX = newX;
                        pacmanY = newY;
                        
                        // Manger le point
                        if (labyrinthe[pacmanY][pacmanX] == 1) {
                            labyrinthe[pacmanY][pacmanX] = 2;
                            score += 10;
                        }
                    }
                }
                
                // Déplacer les fantômes
                for (int[] fantome : fantomes) {
                    int fx = fantome[0];
                    int fy = fantome[1];
                    int fdir = fantome[2];
                    
                    int newFx = fx;
                    int newFy = fy;
                    
                    switch (fdir) {
                        case 0: newFy--; break; // Bas
                        case 1: newFx++; break; // Droite
                        case 2: newFy++; break; // Haut
                        case 3: newFx--; break; // Gauche
                    }
                    
                    // Vérifier collision fantôme avec murs
                    if (newFx >= 0 && newFx < 25 && newFy >= 0 && newFy < 21 && 
                        labyrinthe[newFy][newFx] != 0) {
                        fantome[0] = newFx;
                        fantome[1] = newFy;
                    } else {
                        // Changer de direction aléatoirement
                        fantome[2] = (int)(Math.random() * 4);
                    }
                    
                    // Vérifier collision Pac-Man avec fantôme
                    if (fantome[0] == pacmanX && fantome[1] == pacmanY) {
                        jeuEnCours = false;
                    }
                }
            }
            
            compteurMouvement++;
            
            // Dessiner le labyrinthe
            for (int y = 0; y < 21; y++) {
                for (int x = 0; x < 25; x++) {
                    int px = offsetX + x * CASE_SIZE;
                    int py = offsetY + y * CASE_SIZE;
                    
                    if (labyrinthe[y][x] == 0) {
                        // Mur
                        Rectangle mur = new Rectangle(Couleur.BLEU, 
                                                      new Point(px, py), 
                                                      new Point(px + CASE_SIZE, py + CASE_SIZE), 
                                                      true);
                        f.ajouter(mur);
                    } else if (labyrinthe[y][x] == 1) {
                        // Point
                        Cercle point = new Cercle(Couleur.BLANC,
                                                  new Point(px + CASE_SIZE/2, py + CASE_SIZE/2), 
                                                  4, true);
                        f.ajouter(point);
                    }
                }
            }
            
            // Dessiner Pac-Man
            int pacPx = offsetX + pacmanX * CASE_SIZE + CASE_SIZE/2;
            int pacPy = offsetY + pacmanY * CASE_SIZE + CASE_SIZE/2;
            Cercle pacman = new Cercle(Couleur.JAUNE, new Point(pacPx, pacPy), CASE_SIZE/2 - 2, true);
            f.ajouter(pacman);
            
            // Dessiner les fantômes
            Couleur[] couleursFantomes = {Couleur.ROUGE, Couleur.ROSE, Couleur.ORANGE};
            for (int i = 0; i < fantomes.size(); i++) {
                int[] fantome = fantomes.get(i);
                int fPx = offsetX + fantome[0] * CASE_SIZE + CASE_SIZE/2;
                int fPy = offsetY + fantome[1] * CASE_SIZE + CASE_SIZE/2;
                Cercle f_cercle = new Cercle(couleursFantomes[i % 3],
                                             new Point(fPx, fPy), 
                                             CASE_SIZE/2 - 2, 
                                             true);
                f.ajouter(f_cercle);
            }
            
            // Afficher le score
            Texte scoreText = new Texte(Couleur.BLANC, "SCORE: " + score, 
                                        new Font("Arial", Font.PLAIN, 30), 
                                        new Point(50, 50));
            f.ajouter(scoreText);
            
            // Vérifier victoire
            boolean tousPointsManges = true;
            for (int y = 0; y < 21; y++) {
                for (int x = 0; x < 25; x++) {
                    if (labyrinthe[y][x] == 1) {
                        tousPointsManges = false;
                        break;
                    }
                }
            }
            
            if (tousPointsManges && !victoire) {
                victoire = true;
                jeuEnCours = false;
            }
            
            f.rafraichir();
            
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (Exception e) {}
        }
        
        // Écran de fin
        f.effacer();
        if (victoire) {
            Texte resultat = new Texte(Couleur.VERT, "VICTOIRE!", 
                                       new Font("Arial", Font.BOLD, 60),
                                       new Point(TAILLEX/2 - 150, TAILLEY/2));
            f.ajouter(resultat);
        } else {
            Texte resultat = new Texte(Couleur.ROUGE, "GAME OVER", 
                                       new Font("Arial", Font.BOLD, 60),
                                       new Point(TAILLEX/2 - 150, TAILLEY/2));
            f.ajouter(resultat);
        }
        
        Texte scoreText = new Texte(Couleur.BLANC, "Score: " + score,
                                    new Font("Arial", Font.PLAIN, 40), 
                                    new Point(TAILLEX/2 - 100, TAILLEY/2 + 100));
        f.ajouter(scoreText);
        f.rafraichir();
        
        try {
            Thread.sleep(3000);
        } catch (Exception e) {}
    }
}
