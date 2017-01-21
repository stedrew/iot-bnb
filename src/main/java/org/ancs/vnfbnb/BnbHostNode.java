package org.ancs.vnfbnb;

import org.apache.log4j.Logger;

public class BnbHostNode extends HostNode {
    private static Logger log = Logger.getLogger(BnbHostNode.class);
    @Override
    public double getCost(Instance instance) {
        int cost = 0;
        if (instance.getOwnerNode().equals(this)) {
            Flavor instanceFlavor = instance.getFlavor();
            cost += this.getCpuUnitPrice() * instanceFlavor.getCpuNeed();
            cost += this.getMemoryUnitPrice() * instanceFlavor.getMemoryNeed();
            cost += this.getBandwidthUnitPrice() * instanceFlavor.getBandwidthNeed();
        }
        return cost;
    }

    @Override
    public int getDelay(Instance instance) {
        return 0;
    }

    @Override
    public boolean isResourceEnough(Instance instance) {
        Flavor instanceFlavor = instance.getFlavor();
        boolean cpuEnough = instanceFlavor.getCpuNeed() < getRemainingCpuCapacity();
        if (!cpuEnough) {
            log.error("Bnb node [" + name + "]: CPU not enough for instance: " + instance);
        }
        boolean memoryEnough = instanceFlavor.getMemoryNeed() < getRemainingMemoryCapacity();
        if (!memoryEnough) {
            log.error("Bnb node [" + name + "]: memory not enough for instance: " + instance);
        }
        boolean bandwidthEnough = instanceFlavor.getBandwidthNeed() < getRemainingBandwidthCapacity();
        if (!bandwidthEnough) {
            log.error("Bnb node [" + name + "]: bandwidth not enough for instance: " + instance);
        }
        return cpuEnough && memoryEnough && bandwidthEnough;
    }
}

