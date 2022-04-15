public class LinkStore {
    private String link;
    private int depth;

    public LinkStore(String link, int depth){
        this.link = link;
        this.depth = depth;
    }

    public String getLink(){
        return link;
    }

    public int getDepth(){
        return depth;
    }
}
