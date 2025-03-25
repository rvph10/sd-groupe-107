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
        Stack<Noeud> firstLayer = new Stack<>();
        Noeud first = new Noeud(artists.get(artistsByName.get(artiste1)), null, 1);
        firstLayer.push(first);
        visited.add(first.artist);

        Noeud noeud = trouverCheminLePlusCourt(firstLayer, visited, artists.get(artistsByName.get(artiste2)));

        if(noeud==null){
            throw new RuntimeException("aucun chemin entre "+artiste1+" et "+artiste2);
        }

        double cost = -1;
        List<Artist> path = new LinkedList<>();
        while(noeud != null){
            path.addFirst(noeud.artist);
            cost += ((double) 1) / noeud.occurrence;
            noeud = noeud.parent;
        }
        int distance = path.size()-1;

        System.out.println("Longueur du chemin : "+distance);
        System.out.println("Coût total du chemin : "+cost);
        System.out.println("Chemin:");
        for (Artist artist: path){
            System.out.print(artist.getNom()+" (");
            for (String categorie: artist.getCategories()){
                System.out.print(categorie+";");
            }
            System.out.println("\b)");
        }
    }

    private Noeud trouverCheminLePlusCourt(Stack<Noeud> currentLayer, Set<Artist> visited, Artist end){
        Stack<Noeud> nextLayer = new Stack<>();

        while(!currentLayer.empty()){
            Noeud current = currentLayer.pop();

            for (Link link: current.artist.getLinks()){
                Noeud next = new Noeud(link.getDestination(), current, link.getOccurrence());
                if(link.getDestination().equals(end)){
                    return next;
                }else if(!visited.contains(link.getDestination())){
                    visited.add(next.artist);
                    nextLayer.push(next);
                }
            }
        }

        if(nextLayer.empty()){
            return null;
        }
        return trouverCheminLePlusCourt(nextLayer, visited, end);
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
        // todo
        return;
    }
}
