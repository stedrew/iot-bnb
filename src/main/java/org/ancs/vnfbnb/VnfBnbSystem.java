package org.ancs.vnfbnb;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class VnfBnbSystem {
    private static Logger log = Logger.getLogger(VnfBnbSystem.class);

    private List<HostNode> hostNodeList;
    private List<Instance> instanceToDeployList;

    public VnfBnbSystem() {
        hostNodeList = new ArrayList<>();
        instanceToDeployList = new ArrayList<>();
    }

    public HostNode deploy(Instance instance) {
        // check every host
        double minCost = Double.MAX_VALUE;
        HostNode destination = null;
        for (HostNode node : hostNodeList) {
            log.info("Checking HostNode " + node +" for deploying instance " + instance);
            if (node.isResourceEnough(instance)) {
                double cost = node.getCost(instance);
                if (cost < minCost) {
                    destination = node;
                    minCost = cost;
                }
            }
        }
        if (destination != null) {
            destination.deployInstance(instance);
            log.info("Deployed instance " + instance + " to HostNode " + destination);
        }
        return destination;
    }

    public static void main(String[] args) {
        VnfBnbSystem sys = new VnfBnbSystem();
        log.info("There are [" + sys.hostNodeList.size() + "] host nodes for deployment.");
    }
}
