package org.ancs.vnfbnb;

import org.apache.log4j.Logger;

public class CloudHostNode extends HostNode {

    private static Logger log = Logger.getLogger(CloudHostNode.class);

    @Override
    public double getCost(Instance instance) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getDelay(Instance instance) {
        return Constants.CLOUD_DELAY_FROM_OUT_OF_EDGE 
                / (getRemainingBandwidthCapacity() + Constants.CLOUD_DELAY_FACTOR);
    }

    @Override
    public boolean isResourceEnough(Instance instance) {
        Flavor instanceFlavor = instance.getFlavor();
        boolean bandwidthEnough = instanceFlavor.getBandwidthNeed() < getRemainingBandwidthCapacity();
        if(!bandwidthEnough) {
            log.error("Cloud Bandwidth not enough for instance: " + instance);
        }
        boolean delayAllowed = getDelay(instance) < instanceFlavor.getMaxDelay();
        if (!delayAllowed) {
            log.error("Cloud delay too long for instance: " + instance);
        }
        return bandwidthEnough && delayAllowed;
    }

}
