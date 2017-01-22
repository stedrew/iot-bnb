package org.ancs.vnfbnb;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ancs.vnfbnb.fixture.DeployMode;
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

    }

    public HostNode deploy(Instance instance) {
        // check every host
        double minCost = Double.MAX_VALUE;
        HostNode destination = null;
        for (HostNode node : hostNodeMap.values()) {
            log.debug("Checking HostNode " + node + " for deploying instance " + instance);
            if (node.isResourceEnough(instance)) {
                double cost = node.getCost(instance);
                if (cost < minCost) {
                    destination = node;
                    minCost = cost;
                }
            }
        }
        if (destination != null) {
            destination.deployInstance(instance, minCost);
            log.info("Deployed instance " + instance + " to HostNode " + destination + ". Cost: [" + minCost + "]");
        }
        return destination;
    }

    public static void runScenario() {
        VnfBnbSystem sys = new VnfBnbSystem();
        sys.hostNodeMap = HostNodeFixture.generateHostNodeMap();
        log.info("There are [" + sys.hostNodeMap.size() + "] nodes generated.");
        sys.instanceToDeployList = InstanceFixture.generateInstanceListToDeploy(sys.hostNodeMap);
        log.info("There are [" + sys.instanceToDeployList.size() + "] instances generated to deploy.");
        int instancesDeployed = 0;
        int totalInstancesToDeploy = sys.getInstanceToDeployList().size();
        for (Instance instance : sys.getInstanceToDeployList()) {
            HostNode nodeChosen = sys.deploy(instance);
            if (nodeChosen == null) {
                log.warn("There is no host available for instance " + instance);
                break;
            }
            ++instancesDeployed;
        }
        log.info("Total number of instances to deploy: [" + totalInstancesToDeploy + "]. Actually deployed ["
                + instancesDeployed + "] instances.");
    }

    public static int runUntilDie(VnfBnbSystem sys, DeployMode deployMode, Flavor flavor) throws IOException {

        if (deployMode == DeployMode.LOCAL_CLOUD_BNB) {
            sys.hostNodeMap = HostNodeFixture.generateHostNodeMap();
            sys.instanceToDeployList = InstanceFixture.generateInstanceListToDeploy(sys.hostNodeMap);
            log.info("There are [" + sys.instanceToDeployList.size() + "] instances generated to deploy.");
        } else {
            sys.hostNodeMap = new HashMap<>();
            // add one bnb node
            String bnbName = HostNodeFixture.generateBnbNodeName(0, 0);
            BnbHostNode bnb = HostNodeFixture.createBnbHostNode(bnbName, Constants.NUM_RESOURCE_LEVELS, 1, 0, 0);
            sys.hostNodeMap.put(bnbName, bnb);
            if (deployMode == DeployMode.LOCAL_CLOUD) {
                // add cloud node at the end of the list
                String cloudName = "CLOUD_0_1";
                CloudHostNode cloud = HostNodeFixture.createCloudHostNode(cloudName, 0, 1);
                sys.hostNodeMap.put(cloudName, cloud);
            }
            log.info("There are [" + sys.hostNodeMap.size() + "] nodes generated.");
        }
        HostNode nodeChosen = null;
        int instancesDeployed = 0;
        String output = "";
        do {
            Instance instance = InstanceFixture.createInstance("INSTANCE_" + instancesDeployed, flavor.name());
            HostNode owner = sys.hostNodeMap.get(HostNodeFixture.generateBnbNodeName(0, 0));
            if (owner == null || !(owner instanceof BnbHostNode)) {
                throw new IOException("Cannot find owner BNB node with coordinates (" + 0 + "," + 0 + ")");
            }
            instance.setOwnerNode((BnbHostNode) owner);
            nodeChosen = sys.deploy(instance);
            if (nodeChosen != null) {
                ++instancesDeployed;
                output += "," + nodeChosen.getLastCost();
            }
        } while (nodeChosen != null);
        output = output.substring(1) + ";";
        // log.warn(output);
        log.info("Deployed [" + instancesDeployed + "] instances in total.");
        return instancesDeployed;
    }

    public static void runForAllFlavors(VnfBnbSystem sys) throws IOException {
        for (Flavor flavor : Flavor.values()) {
            // int num_local = runUntilDie(DeployMode.LOCAL, flavor);
            runUntilDie(sys, DeployMode.LOCAL_CLOUD, flavor);
            // int num_local_cloud_bnb = runUntilDie(DeployMode.LOCAL_CLOUD_BNB,
            // flavor);
        }
    }

    public static void main(String[] args) throws IOException {
        VnfBnbSystem sys = new VnfBnbSystem();
        runUntilDie(sys, DeployMode.LOCAL_CLOUD_BNB, Flavor.SMALL_HIGH_DELAY);
        for(int x =0; x < 10; x++) {
            String line = "";
            for(int y =0; y < 10; y++) {
                
                for (HostNode node : sys.hostNodeMap.values()) {
                    if (node.getX() == x && node.getY() == y) {
                        line += "," + node.getRemainingBandwidthCapacity();
                        break;
                    }
                }
            }
            log.warn(line.substring(1) + ";");
        }


    }
}
