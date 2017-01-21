package org.ancs.vnfbnb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utilities {
    public static final int MIN_RESOURCE_LEVEL = 1;
    public static final int MAX_RESOURCE_LEVEL = 20;
    public static final int TOTAL_NUM_BNB_NODE = 100;

    private List<HostNode> hostNodeList;
    private Random random = new Random();

    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return random.nextInt((max - min) + 1) + min;
    }

    public void generateHostNodeList() {
        hostNodeList = new ArrayList<>();
        for (int i = 0; i < TOTAL_NUM_BNB_NODE; i++) {
            BnbHostNode bnb = createBnbHostNode("B&B-" + (i + 1));
            hostNodeList.add(bnb);
        }
    }

    public BnbHostNode createBnbHostNode(String name) {
        BnbHostNode bnb = new BnbHostNode();
        bnb.setName(name);
        int overallResourceLevelStart = MIN_RESOURCE_LEVEL;
        int overallResourceLevelEnd = MAX_RESOURCE_LEVEL;

        int overallResourceLevel = getRandomNumberInRange(overallResourceLevelStart, overallResourceLevelEnd);
        int cpuCapacity = overallResourceLevel;
        int memoryCapacity = overallResourceLevel;
        int bandwidthCapacity = overallResourceLevel;

        bnb.setCpuCapacity(cpuCapacity);
        bnb.setMemoryCapacity(memoryCapacity);
        bnb.setBandwidthCapacity(bandwidthCapacity);
        return bnb;
    }
}
