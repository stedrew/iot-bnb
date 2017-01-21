package org.ancs.vnfbnb;

import java.util.List;
import java.util.Map;

import org.ancs.vnfbnb.fixture.HostNodeFixture;
import org.ancs.vnfbnb.fixture.InstanceFixture;
import org.apache.log4j.Logger;

public class VnfBnbSystem {
    private static Logger log = Logger.getLogger(VnfBnbSystem.class);

    private Map<String, HostNode> hostNodeMap;
    private List<Instance> instanceToDeployList;

    public Map<String, HostNode> getHostNodeMap() {
        return hostNodeMap;
    }

    public List<Instance> getInstanceToDeployList() {
        return instanceToDeployList;
    }

    public VnfBnbSystem() {
        hostNodeMap = HostNodeFixture.generateHostNodeMap();
        log.info("There are [" + hostNodeMap.size() + "] nodes generated.");
        instanceToDeployList = InstanceFixture.generateInstanceListToDeploy(hostNodeMap);
        log.info("There are [" + instanceToDeployList.size() + "] instances generated to deploy.");
    }

    public HostNode deploy(Instance instance) {
        // check every host
        double minCost = Double.MAX_VALUE;
        HostNode destination = null;
        for (HostNode node : hostNodeMap.values()) {
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
        log.info("There are [" + sys.hostNodeMap.size() + "] host nodes for deployment.");
        int instancesDeployed = 0;
        int totalInstancesToDeploy = sys.getInstanceToDeployList().size();
        for (Instance instance : sys.getInstanceToDeployList()) {
            HostNode nodeChosen = sys.deploy(instance);
            if (nodeChosen == null) {
                log.warn("There is no host available for instance " + instance);
                break;
            }
            log.info("Host chosen: " + nodeChosen);
            ++instancesDeployed;
        }
        log.info("Total number of instances to deploy: [" + totalInstancesToDeploy + "]. Actually deployed ["
                + instancesDeployed + "] instances.");
    }
}
