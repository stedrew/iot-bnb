package org.ancs.vnfbnb;

import org.apache.log4j.Logger;

public class CloudHostNode extends HostNode {

    private static Logger log = Logger.getLogger(CloudHostNode.class);

    @Override
    public double getCost(Instance instance) {
        double cost = 0;
        Flavor instanceFlavor = instance.getFlavor();
        cost += Constants.CLOUD_CPU_COST_COEFFICIENT * instanceFlavor.getCpuNeed() / getRemainingCpuCapacity();
        cost += Constants.CLOUD_MEMORY_COST_COEFFICIENT * instanceFlavor.getMemoryNeed() / getRemainingMemoryCapacity();
        cost += Constants.CLOUD_EDGE_LINK_BW_COST_COEFFICIENT * instanceFlavor.getBandwidthNeed()
                / getRemainingBandwidthCapacity();
        log.debug("Cost of [" + name + "] for instance " + instance + " is :" + cost);
        return cost;
    }

    @Override
    public double getDelay(Instance instance) {
        return Constants.CLOUD_DELAY_FROM_OUT_OF_EDGE 
                / (getRemainingBandwidthCapacity() + Constants.CLOUD_DELAY_FACTOR);
    }

    @Override
    public boolean isResourceEnough(Instance instance) {
        Flavor instanceFlavor = instance.getFlavor();
        boolean bandwidthEnough = instanceFlavor.getBandwidthNeed() < getRemainingBandwidthCapacity();
        if(!bandwidthEnough) {
            log.debug("Cloud Bandwidth not enough for instance: " + instance);
        }
        boolean delayAllowed = getDelay(instance) < instanceFlavor.getMaxDelay();
        if (!delayAllowed) {
            log.debug("Cloud delay too long for instance: " + instance);
        }
        return bandwidthEnough && delayAllowed;
    }

}
