import java.util.HashSet;
import java.util.Objects;

public class Artist {

    private int id;
    private String nom;
    private HashSet<String> categories;
    private HashSet<Link> links;

    public Artist(int id, String nom, HashSet<String> categories) {
        this.id = id;
        this.nom = nom;
        this.categories = categories;
        this.links = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public HashSet<String> getCategories() {
        return categories;
    }

    public void setCategories(HashSet<String> categories) {
        this.categories = categories;
    }

    public HashSet<Link> getLinks() {
        return links;
    }

    public void addLinks(Link link) {
        this.links.add(link);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return id == artist.id && Objects.equals(nom, artist.nom) && Objects.equals(categories, artist.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, categories);
    }
}
