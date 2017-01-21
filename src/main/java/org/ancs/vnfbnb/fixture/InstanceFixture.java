package org.ancs.vnfbnb.fixture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ancs.vnfbnb.BnbHostNode;
import org.ancs.vnfbnb.Flavor;
import org.ancs.vnfbnb.HostNode;
import org.ancs.vnfbnb.Instance;
import org.apache.log4j.Logger;

public class InstanceFixture {
    private static Logger log = Logger.getLogger(InstanceFixture.class);
    public static final String INSTANCES_TO_DEPLOY_FILE = "instances.csv";

    public static List<Instance> generateInstanceListToDeploy(Map<String, HostNode> hostNodeMap) {
        ArrayList<Instance> instanceList = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(
                    Paths.get(InstanceFixture.class.getClassLoader().getResource(INSTANCES_TO_DEPLOY_FILE).getFile()));
            for (String line : lines) {
                String[] cells = line.split(",");
                Instance instance = createInstance(cells[0], cells[1]);
                int x = Integer.parseInt(cells[2]);
                int y = Integer.parseInt(cells[3]);
                HostNode owner = hostNodeMap.get(HostNodeFixture.generateBnbNodeName(x, y));
                if (owner == null || !(owner instanceof BnbHostNode)) {
                    throw new IOException("Cannot find owner BNB node with coordinates (" + x + "," + y + ")");
                }
                instance.setOwnerNode((BnbHostNode) owner);
                instanceList.add(instance);
                log.info("Instance added: " + instance);
            }
        } catch (IOException e) {
            log.error("Error reading instances to deploy file: " + INSTANCES_TO_DEPLOY_FILE, e);
            return null;
        }
        return instanceList;
    }

    private static Instance createInstance(String name, String flavorName) {
        Instance instance = new Instance();
        instance.setName(name);
        instance.setFlavor(Flavor.valueOf(flavorName));
        return instance;
    }
}

