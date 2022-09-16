package com.tgirou.skilled.skills;


import java.util.*;


public class AbstractSkill {
    protected Random random = new Random();
    protected HashMap<String, int[]> blocksTable = new HashMap<>();
    public static int[] expTable = new int[]{
            0,
            50,
            140,
            271,
            441,
            653,
            905,
            1199,
            1543,
            1911,
            1543,
            2792,
            3297,
            3840,
            4439,
            5078,
            5762,
            6493,
            7280,
            8097,
            8980,
            9898,
            10875,
            11903,
            12985,
            14122,
            15315,
            16564,
            17873,
            19242,
            20672,
            22166,
            23726,
            25353,
            27048,
            28815,
            30656,
            32572,
            34566,
            36641,
            38800,
            41044,
            43378,
            45804,
            48325,
            50946,
            53669,
            56498,
            59437,
            62491,
            65664,
            68960,
            72385,
            75948,
            79640,
            83482,
            87475,
            91624,
            95937,
            100421,
            105082,
            109930,
            114971,
            120215,
            125671,
            131348,
            137256,
            143407,
            149811,
            156481,
            163429,
            170669,
            178214,
            186080,
            194283,
            202839,
            211765,
            221082,
            230808,
            240964,
            251574,
            262660,
            274248,
            286364,
            299037,
            312297,
            326175,
            340705,
            355924,
            371870,
            388582,
            406106,
            424486,
            443772,
            464016,
            485274,
            507604,
            531071,
            555741,
            581687
    };



    /**
     * Returns exp multiplier depending on player level for this skill.
     * @param level of player for this skill
     * @return multiplier value
     */
    public float getMultiplier(int level) {
        if (level >= 80) {
            return 5f + random.nextFloat() * (50f - 5f);
        } else if (level >= 70) {
            return 4.5f + random.nextFloat() * (25f - 4.5f);
        } else if (level >= 60) {
            return 4f + random.nextFloat() * (25f - 4f);
        } else if (level >= 50) {
            return 3.5f + random.nextFloat() * (5f - 3.5f);
        } else if (level >= 40) {
            return 3f + random.nextFloat() * (5f - 3f);
        } else if (level >= 30) {
            return 2.5f + random.nextFloat() * (5f - 2.5f);
        } else if (level >= 20) {
            return 2f + random.nextFloat() * (2.5f - 2f);
        } else if (level >= 10) {
            return 1.5f;
        } else {
            return 1f;
        }
    }

    /**
     * Returns true if a skill has a block in its experience table or false if it has not.
     * @param blockName the name of the block to look for
     * @return true if it has it or false if it does not
     */
    public boolean hasBlock(String blockName) {
        return this.blocksTable.get(blockName) != null;
    }

    /**
     * Returns the experience range to earn for a block.
     * @param blockName triggering a experience gain
     * @return the experience range
     */
    public int[] getExpRangeForBlock(String blockName) {
        System.out.println(blockName);
        return this.blocksTable.get(blockName);
    }

}
