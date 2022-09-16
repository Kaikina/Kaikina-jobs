package com.tgirou.skilled.progression;

import java.util.HashMap;
import java.util.Map;

public class Progression {
    /**
     * The list of every skill and its progress.
     */
    protected HashMap<String, Integer> skillsProgression = new HashMap<>();

    /**
     * Set all skills to 0 xp.
     */
    public Progression() {
        skillsProgression.put("miner", 0);
    }

    /**
     * Returns the progression of every skill.
     * @return the progression of every skill.
     */
    public Map<String, Integer> getSkillsProgression() {
        return this.skillsProgression;
    }

}
