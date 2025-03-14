import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;


public class Graph {

    private Map<String, Artist> artistes;

    public Graph(String fichierArtistes, String fichierMentions) {
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
                artistes.put(parts[1], artist);
            }
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
