package org.ancs.vnfbnb;

public enum Flavor {

    TINY(1, 1, 1, 1000),
    SMALL_LOW_DELAY(2, 2, 2, 100),
    SMALL_MODERATE_DELAY(2, 2, 2, 1000),
    SMALL_HIGH_DELAY(2, 2, 2, 10000),
    MEDIUM_LOW_DELAY(4, 4, 4, 100),
    MEDIUM_MODERATE_DELAY(4, 4, 4, 1000),
    MEDIUM_HIGH_DELAY(4, 4, 4, 10000),
    LARGE_LOW_DELAY(8, 8, 8, 100),
    LARGE_MODERATE_DELAY(8, 8, 8, 1000),
    LARGE_HIGH_DELAY(8, 8, 8, 10000);
    
    private Flavor(int cpuNeed, int memoryNeed, int bandwidthNeed, int maxDelay) {
        this.cpuNeed = cpuNeed;
        this.memoryNeed = memoryNeed;
        this.bandwidthNeed = bandwidthNeed;
        this.maxDelay = maxDelay;
    }

    private int cpuNeed;
    private int memoryNeed;
    private int bandwidthNeed;
    private int maxDelay;

    public int getCpuNeed() {
        return cpuNeed;
    }

    public void setCpuNeed(int cpuNeed) {
        this.cpuNeed = cpuNeed;
    }

    public int getMemoryNeed() {
        return memoryNeed;
    }

    public void setMemoryNeed(int memoryNeed) {
        this.memoryNeed = memoryNeed;
    }

    public int getBandwidthNeed() {
        return bandwidthNeed;
    }

    public void setBandwidthNeed(int bandwidthNeed) {
        this.bandwidthNeed = bandwidthNeed;
    }

    public int getMaxDelay() {
        return maxDelay;
    }

    public void setMaxDelay(int maxDelay) {
        this.maxDelay = maxDelay;
    }

    public String toString() {
        return "{Flavor name: " + name()
                + ", CPU need: " + cpuNeed 
                + ", memory need: " + memoryNeed 
                + ", bandwidthNeed: " + bandwidthNeed 
                + ", max delay allowed: " + maxDelay 
                + "}";
    }
}
