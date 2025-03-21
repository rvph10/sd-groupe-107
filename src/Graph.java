import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class Graph {

    private Map<Integer, Artist> artists;
    private Map<String,Integer> artistsByName;


    public Graph(String fichierArtistes, String fichierMentions) {
        artists = new HashMap<>();
        artistsByName = new HashMap<>();
        initArtistes(fichierArtistes);
    }

    private void initArtistes(String fichierArtistes){
        String linkfile = "data/" + fichierArtistes;
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
        String linkfile = "data/" + fichierMentions;
        try (BufferedReader br = new BufferedReader(new FileReader(linkfile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Link link = new Link(artists.get(parts[0]),artists.get(parts[1]),Integer.parseInt(parts[2]));
                artists.get(parts[0]).addLinks(link);
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
        // TODO
        return;
    }
}
