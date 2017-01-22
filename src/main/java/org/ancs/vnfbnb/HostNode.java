package org.ancs.vnfbnb;

import java.util.ArrayList;
import java.util.List;

public abstract class HostNode {
    protected String name;
    protected double cpuCapacity;
    protected double memoryCapacity;
    protected double bandwidthCapacity;
    protected double cpuUnitPrice;
    protected double memoryUnitPrice;
    protected double bandwidthUnitPrice;
    protected int x;
    protected int y;
    protected List<Instance> instanceList = new ArrayList<>();
    protected double lastCost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCpuCapacity() {
        return cpuCapacity;
    }

    public void setCpuCapacity(double cpuCapacity) {
        this.cpuCapacity = cpuCapacity;
    }

    public double getMemoryCapacity() {
        return memoryCapacity;
    }

    public void setMemoryCapacity(double memoryCapacity) {
        this.memoryCapacity = memoryCapacity;
    }

    public double getBandwidthCapacity() {
        return bandwidthCapacity;
    }

    public void setBandwidthCapacity(double bandwidthCapacity) {
        this.bandwidthCapacity = bandwidthCapacity;
    }

    public double getCpuUnitPrice() {
        return cpuUnitPrice;
    }

    public void setCpuUnitPrice(double cpuUnitPrice) {
        this.cpuUnitPrice = cpuUnitPrice;
    }

    public double getMemoryUnitPrice() {
        return memoryUnitPrice;
    }

    public void setMemoryUnitPrice(double memoryUnitPrice) {
        this.memoryUnitPrice = memoryUnitPrice;
    }

    public double getBandwidthUnitPrice() {
        return bandwidthUnitPrice;
    }

    public void setBandwidthUnitPrice(double bandwidthUnitPrice) {
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

    public void deployInstance(Instance instance, double cost) {
        this.instanceList.add(instance);
        this.lastCost = cost;
    }

    public double getLastCost() {
        return lastCost;
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
    public abstract double getDelay(Instance instance);

    public abstract boolean isResourceEnough(Instance instance);

    public double getRemainingCpuCapacity() {
        double remainingCpuCapacity = this.getCpuCapacity();
        for (Instance instance : instanceList) {
            remainingCpuCapacity -= instance.getFlavor().getCpuNeed();
        }
        return remainingCpuCapacity;
    }

    public double getRemainingMemoryCapacity() {
        double remainingMemoryCapacity = this.getMemoryCapacity();
        for (Instance instance : instanceList) {
            remainingMemoryCapacity -= instance.getFlavor().getMemoryNeed();
        }
        return remainingMemoryCapacity;
    }

    public double getRemainingBandwidthCapacity() {
        double remainingBandwidthCapacity = this.getBandwidthCapacity();
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
