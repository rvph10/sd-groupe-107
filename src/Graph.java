import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.ToDoubleBiFunction;


public class Graph {

    private Map<Integer, Artist> artists;
    private Map<String,Integer> artistsByName;


    public Graph(String fichierArtistes, String fichierMentions) {
        artists = new HashMap<>();
        artistsByName = new HashMap<>();
        initArtistes(fichierArtistes);
        initLinks(fichierMentions);
    }

    private void initArtistes(String fichierArtistes){
        String linkfile =  fichierArtistes;
        try (BufferedReader br = new BufferedReader(new FileReader(linkfile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                HashSet<String> categories = new HashSet<String>(Arrays.asList(parts[2].split(";")));
                Artist artist = new Artist(Integer.parseInt(parts[0]), parts[1], categories);
                artists.put(Integer.parseInt(parts[0]), artist);
                artistsByName.put(parts[1],Integer.parseInt(parts[0]) );
            }
            System.out.println("---------------- Initialisation des artistes fini ! ---------------------");

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initLinks(String fichierMentions){
        String linkfile =  fichierMentions;
        try (BufferedReader br = new BufferedReader(new FileReader(linkfile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Link link = new Link(artists.get(Integer.parseInt(parts[0])),artists.get(Integer.parseInt(parts[1])),Integer.parseInt(parts[2]));
                artists.get(Integer.parseInt(parts[0])).addLinks(link);
            }
            System.out.println("---------------- Initialisation des liens fini ! ---------------------");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void trouverCheminLePlusCourt(String artiste1, String artiste2) {
        if(!artistsByName.containsKey(artiste1) || !artistsByName.containsKey(artiste2)){
            throw new IllegalArgumentException();
        }

        Set<Artist> visited = new HashSet<>();
        Queue<Noeud> waiting = new ArrayDeque<>();
        Noeud noeud = new Noeud(artists.get(artistsByName.get(artiste1)), null, 1);
        waiting.add(noeud);
        visited.add(noeud.artist);
        Artist goal = artists.get(artistsByName.get(artiste2));

        searching:
        while(!waiting.isEmpty()){
            Noeud current = waiting.remove();

            for (Link link: current.artist.getLinks()){
                noeud = new Noeud(link.getDestination(), current, link.getOccurrence());
                if(link.getDestination().equals(goal)){
                    break searching;
                }else if(!visited.contains(link.getDestination())){
                    visited.add(noeud.artist);
                    waiting.add(noeud);
                }
            }
        }

        if(!noeud.artist.equals(goal)){
            throw new RuntimeException("aucun chemin entre "+artiste1+" et "+artiste2);
        }

        double cost = -1;
        List<Artist> path = new ArrayList<>();
        while(noeud != null){
            path.addFirst(noeud.artist);
            cost += ((double) 1) / noeud.occurrence;
            noeud = noeud.parent;
        }
        int distance = path.size()-1;

        System.out.println("Longueur du chemin : "+distance);
        System.out.println("Coût total du chemin : "+cost);
        System.out.println("Chemin :");
        for (Artist artist: path){
            System.out.print(artist.getNom()+" (");
            for (String categorie: artist.getCategories()){
                System.out.print(categorie+";");
            }
            System.out.println("\b)");
        }
    }

    private class Noeud{
        private Artist artist;
        private Noeud parent;
        private int occurrence;

        public Noeud(Artist artist, Noeud parent, int occurrence){
            this.artist = artist;
            this.parent = parent;
            this.occurrence = occurrence;
        }
    }

    public void trouverCheminMaxMentions(String artiste1, String artiste2) {
        if (!artistsByName.containsKey(artiste1)) {
            System.out.println("Artiste non trouvé: " + artiste1);
            return;
        }
        if (!artistsByName.containsKey(artiste2)) {
            System.out.println("Artiste non trouvé: " + artiste2);
            return;
        }

        int idArtiste1 = artistsByName.get(artiste1);
        int idArtiste2 = artistsByName.get(artiste2);
        Artist sourceArtist = artists.get(idArtiste1);
        Artist destinationArtist = artists.get(idArtiste2);


        // Map pour stocker la distance minimale de l'artiste source à chaque artiste
        Map<Integer, Double> distances = new HashMap<>();

        // Map pour stocker le prédécesseur de chaque artiste dans le chemin le plus court
        Map<Integer, Integer> predecesseurs = new HashMap<>();

        // Map pour marquer les artistes déjà visités
        Set<Integer> visites = new HashSet<>();

        // Initialisation des distances à l'infini sauf pour l'artiste source
        for (Integer id : artists.keySet()) {
            distances.put(id, Double.MAX_VALUE);
        }
        distances.put(idArtiste1, 0.0);

        // Algorithme de Dijkstra
        while (visites.size() < artists.size()) {
            // Trouver l'artiste non visité avec la distance minimale
            Integer artisteActuel = null;
            double minDistance = Double.MAX_VALUE;

            for (Integer id : artists.keySet()) {
                if (!visites.contains(id) && distances.get(id) < minDistance) {
                    minDistance = distances.get(id);
                    artisteActuel = id;
                }
            }

            // Si aucun artiste accessible n'est trouvé, sortir de la boucle
            if (artisteActuel == null) break;

            // Marquer l'artiste actuel comme visité
            visites.add(artisteActuel);

            // Si nous avons atteint l'artiste destination, nous pouvons arrêter
            if (artisteActuel.equals(idArtiste2)) break;

            // Mettre à jour les distances des artistes voisins
            Artist artiste = artists.get(artisteActuel);
            for (Link link : artiste.getLinks()) {
                Artist voisin = link.getDestination();
                int idVoisin = (int) voisin.getId();

                // Calculer la nouvelle distance en ajoutant 1/occurrence comme coût
                double nouveauCout = distances.get(artisteActuel) + (1.0 / link.getOccurrence());

                // Si la nouvelle distance est plus petite, mettre à jour
                if (nouveauCout < distances.get(idVoisin)) {
                    distances.put(idVoisin, nouveauCout);
                    predecesseurs.put(idVoisin, artisteActuel);
                }
            }
        }


        // Reconstruire le chemin
        if (!predecesseurs.containsKey(idArtiste2)) {
            System.out.println("Aucun chemin n'a été trouvé entre " + artiste1 + " et " + artiste2);
            return;
        }

        List<Integer> chemin = new ArrayList<>();
        Integer courant = idArtiste2;

        while (courant != null) {
            chemin.add(0, courant);
            courant = predecesseurs.get(courant);
        }

        // Afficher le résultat
        System.out.println("Longueur du chemin : " + (chemin.size() - 1));
        System.out.println("Coût total du chemin : " + distances.get(idArtiste2));
        System.out.println("Chemin :");

        for (Integer id : chemin) {
            Artist a = artists.get(id);
            System.out.println(a.getNom() + " (" + String.join(";", a.getCategories()) + ")");
        }

    }
}
