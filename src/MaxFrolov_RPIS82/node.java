package MaxFrolov_RPIS82;

public class node {
    public Service service;
    public node next;
    public node previous;


    public node() {
    }

    public node(node previous)
    {
        this.previous=previous;
    }

}