package org.ancs.vnfbnb.fixture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ancs.vnfbnb.BnbHostNode;
import org.ancs.vnfbnb.CloudHostNode;
import org.ancs.vnfbnb.Constants;
import org.ancs.vnfbnb.HostNode;
import org.apache.log4j.Logger;

public class HostNodeFixture {
    private static Logger log = Logger.getLogger(HostNodeFixture.class);
    public static final String HOST_NODE_SCENARIO_FILE = "hostnode-scenario.csv";

    public static String generateBnbNodeName(int x, int y) {
        return "BNB_" + x + "_" + y;
    }


    public static Map<String, HostNode> generateHostNodeMap() {
        HashMap<String, HostNode> hostNodeMap = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(
                    Paths.get(HostNodeFixture.class.getClassLoader().getResource(HOST_NODE_SCENARIO_FILE).getFile()));
            int x = 0, y = 0;
            for (x = 0; x < lines.size(); x++) {
                String line = lines.get(x);
                String[] cells = line.split(",");
                for (y = 0; y < cells.length; y++) {
                    int resourceLevel = Integer.parseInt(cells[y]);
                    String nodeName = generateBnbNodeName(x, y);
                    int unitPrice = Constants.NUM_RESOURCE_LEVELS + 1 - resourceLevel;
                    BnbHostNode node = createBnbHostNode(nodeName, resourceLevel, unitPrice, x, y);
                    hostNodeMap.put(nodeName, node);
                    log.debug("BnB node added: " + node);
                }
            }
            x -= 1;
            // add cloud node at the end of the list
            String cloudName = "CLOUD_" + x + "_" + y;
            CloudHostNode cloud = createCloudHostNode(cloudName, x, y);
            hostNodeMap.put(cloudName, cloud);
            log.debug("Cloud node added: " + cloud);
        } catch (IOException e) {
            log.error("Error reading host node scenario file: " + HOST_NODE_SCENARIO_FILE, e);
            return null;
        }
        return hostNodeMap;
    }

    public static BnbHostNode createBnbHostNode(String name, int resourceLevel, int unitPrice, int x, int y) {
        BnbHostNode bnb = new BnbHostNode();
        bnb.setName(name);
        bnb.setCpuCapacity(resourceLevel);
        bnb.setMemoryCapacity(resourceLevel);
        bnb.setBandwidthCapacity(resourceLevel);
        bnb.setCpuUnitPrice(unitPrice);
        bnb.setMemoryUnitPrice(unitPrice);
        bnb.setBandwidthUnitPrice(unitPrice);
        bnb.setX(x);
        bnb.setY(y);
        return bnb;
    }

    public static CloudHostNode createCloudHostNode(String name, int x, int y) {
        CloudHostNode cloud = new CloudHostNode();
        cloud.setName(name);
        cloud.setCpuCapacity(2000);
        cloud.setMemoryCapacity(2000);
        cloud.setBandwidthCapacity(200);
        cloud.setX(x);
        cloud.setY(y);
        return cloud;
    }
}

