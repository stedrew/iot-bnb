package org.ancs.vnfbnb;

public class Instance {

    private String name;
    private Flavor flavor;

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

    public String toString() {
        return "[Instance name: " + name + ", flavor: " + flavor + "]";
    }
}
