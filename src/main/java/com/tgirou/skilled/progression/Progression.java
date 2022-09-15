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

    public void addExpFor(String skillName, Integer exp) {
        this.setExpFor(skillName, this.getExpFor(skillName) + exp);
    }

    /**
     * Returns the experience earned for a skill or null if that skill does not exist.
     * @param skillName the name of the skill
     * @return the amount of experience if the skill exist, null if it does not
     */
    public Integer getExpFor(String skillName) {
        return this.skillsProgression.get(skillName);
    }

    /**
     * Update the experience earned for a skill.
     * @param skillName the name of the skill
     * @param exp the amount for experience
     * @return the previous value if it has been updated or null if the skill does not exist
     */
    public Integer setExpFor(String skillName, Integer exp) {
        return this.skillsProgression.replace(skillName, exp);
    }
}
