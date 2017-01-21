package org.ancs.vnfbnb;

import java.util.ArrayList;
import java.util.List;

public abstract class HostNode {
    protected String name;
    protected int cpuCapacity;
    protected int memoryCapacity;
    protected int bandwidthCapacity;
    protected int cpuUnitPrice;
    protected int memoryUnitPrice;
    protected int bandwidthUnitPrice;
    protected int x;
    protected int y;
    protected List<Instance> instanceList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCpuCapacity() {
        return cpuCapacity;
    }

    public void setCpuCapacity(int cpuCapacity) {
        this.cpuCapacity = cpuCapacity;
    }

    public int getMemoryCapacity() {
        return memoryCapacity;
    }

    public void setMemoryCapacity(int memoryCapacity) {
        this.memoryCapacity = memoryCapacity;
    }

    public int getBandwidthCapacity() {
        return bandwidthCapacity;
    }

    public void setBandwidthCapacity(int bandwidthCapacity) {
        this.bandwidthCapacity = bandwidthCapacity;
    }

    public int getCpuUnitPrice() {
        return cpuUnitPrice;
    }

    public void setCpuUnitPrice(int cpuUnitPrice) {
        this.cpuUnitPrice = cpuUnitPrice;
    }

    public int getMemoryUnitPrice() {
        return memoryUnitPrice;
    }

    public void setMemoryUnitPrice(int memoryUnitPrice) {
        this.memoryUnitPrice = memoryUnitPrice;
    }

    public int getBandwidthUnitPrice() {
        return bandwidthUnitPrice;
    }

    public void setBandwidthUnitPrice(int bandwidthUnitPrice) {
        this.bandwidthUnitPrice = bandwidthUnitPrice;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void deployInstance(Instance instance) {
        this.instanceList.add(instance);
    }

    /**
     * Get the cost for deploying the instance on this node
     * 
     * @param instance
     *            Instance to deploy
     * @return Cost
     */
    public abstract double getCost(Instance instance);

    /**
     * Get the delay for deploying the instance on this node
     * 
     * @param instance
     *            Instance to deploy
     * @return Delay
     */
    public abstract int getDelay(Instance instance);

    public abstract boolean isResourceEnough(Instance instance);

    public int getRemainingCpuCapacity() {
        int remainingCpuCapacity = this.getCpuCapacity();
        for (Instance instance : instanceList) {
            remainingCpuCapacity -= instance.getFlavor().getCpuNeed();
        }
        return remainingCpuCapacity;
    }

    public int getRemainingMemoryCapacity() {
        int remainingMemoryCapacity = this.getMemoryCapacity();
        for (Instance instance : instanceList) {
            remainingMemoryCapacity -= instance.getFlavor().getMemoryNeed();
        }
        return remainingMemoryCapacity;
    }

    public int getRemainingBandwidthCapacity() {
        int remainingBandwidthCapacity = this.getBandwidthCapacity();
        for (Instance instance : instanceList) {
            remainingBandwidthCapacity -= instance.getFlavor().getBandwidthNeed();
        }
        return remainingBandwidthCapacity;
    }

    @Override
    public String toString() {
        return "[" + getClass().getSimpleName() + " name: " + name + ". Resource capacities: " + cpuCapacity + ", "
                + memoryCapacity + ", " + bandwidthCapacity + ". Unit prices: " + cpuUnitPrice + ", " + memoryUnitPrice
                + ", " + bandwidthUnitPrice + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HostNode) {
            HostNode node = (HostNode) obj;
            return node.getName().equals(getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
