import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



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
        // TODO
        return;
    }

    public void trouverCheminMaxMentions(String artiste1, String artiste2) {
        System.out.println("Début de la recherche du chemin avec Max mentions...");
        if (!artistsByName.containsKey(artiste1) || !artistsByName.containsKey(artiste2)) {
            throw new IllegalArgumentException("Un des artistes n'existe pas dans le graphe.");
        }

        int idArtiste1 = artistsByName.get(artiste1);
        int idArtiste2 = artistsByName.get(artiste2);

        // Map pour stocker le "score" maximal de mentions pour chaque artiste
        Map<Integer, Double> scores = new HashMap<>();

        // Map pour stocker le prédécesseur de chaque artiste dans le chemin optimal
        Map<Integer, Integer> predecesseurs = new HashMap<>();

        // File de priorité pour visiter les artistes dans l'ordre décroissant de leur score
        PriorityQueue<Integer> file = new PriorityQueue<>((a, b) ->
                Double.compare(scores.get(b), scores.get(a)));

        // Initialisation des scores à 0 sauf pour l'artiste source
        for (Integer id : artists.keySet()) {
            scores.put(id, 0.0);
        }
        scores.put(idArtiste1, 1.0); // Commencer avec un score de 1 pour l'artiste source

        file.add(idArtiste1);

        // Ensemble pour marquer les artistes déjà traités définitivement
        Set<Integer> traites = new HashSet<>();

        while (!file.isEmpty()) {
            Integer artisteActuel = file.poll();

            // Si cet artiste a déjà été traité, passer au suivant
            if (traites.contains(artisteActuel)) continue;

            // Marquer l'artiste comme traité
            traites.add(artisteActuel);

            // Si nous avons atteint l'artiste destination, nous pouvons arrêter
            if (artisteActuel.equals(idArtiste2)) break;

            // Mettre à jour les scores des artistes voisins
            Artist artiste = artists.get(artisteActuel);
            for (Link link : artiste.getLinks()) {
                Artist voisin = link.getDestination();
                int idVoisin = (int) voisin.getId();

                // Calculer le nouveau score en multipliant par le nombre d'occurrences / (nbOccurrences + 1)
                double nouveauScore = scores.get(artisteActuel) * link.getOccurrence() / (link.getOccurrence() + 1.0);

                // Si le nouveau score est plus grand, mettre à jour
                if (nouveauScore > scores.get(idVoisin)) {
                    scores.put(idVoisin, nouveauScore);
                    predecesseurs.put(idVoisin, artisteActuel);
                    file.add(idVoisin); // Ajouter ou mettre à jour l'artiste dans la file de priorité
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
        System.out.println("Coût total du chemin : " + (1.0 / scores.get(idArtiste2)));
        System.out.println("Chemin :");

        for (Integer id : chemin) {
            Artist a = artists.get(id);
            System.out.println(a.getNom() + " (" + String.join(";", a.getCategories()) + ")");
        }
    }
}
