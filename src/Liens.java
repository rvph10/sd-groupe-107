public class Lien{

    private Artiste source, destination;
    private int occurrence;

    public Lien(Artiste source, Artiste destination, int occurrence){
        this.source = source;
        this.destination = destination;
        this.occurrence = occurrence;
    }

    public Artiste getSource(){
        return source;
    }

    public Artiste getDestination(){
        return destination;
    }

    public Artiste getOccurrence(){
        return occurrence;
    }
}