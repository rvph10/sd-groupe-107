public class Link{

    private Artist source, destination;
    private int occurrence;

    public Link(Artist source, Artist destination, int occurrence){
        this.source = source;
        this.destination = destination;
        this.occurrence = occurrence;
    }

    public Artist getSource(){
        return source;
    }

    public Artist getDestination(){
        return destination;
    }

    public int getOccurrence(){
        return occurrence;
    }
}