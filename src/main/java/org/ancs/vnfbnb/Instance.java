package org.ancs.vnfbnb;

public class Instance {

    private String name;
    private Flavor flavor;
    private BnbHostNode ownerNode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Flavor getFlavor() {
        return flavor;
    }

    public void setFlavor(Flavor flavor) {
        this.flavor = flavor;
    }

    public BnbHostNode getOwnerNode() {
        return ownerNode;
    }

    public void setOwnerNode(BnbHostNode ownerNode) {
        this.ownerNode = ownerNode;
    }

    public String toString() {
        return "[name: " + name + ", flavor: " + flavor + ", owner: " + ownerNode.getName() + "]";
    }
}
