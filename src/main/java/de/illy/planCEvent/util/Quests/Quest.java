package de.illy.planCEvent.util.Quests;

import lombok.Getter;

public abstract class Quest {
    @Getter
    protected String name;
    @Getter
    protected int goal;
    @Getter
    protected int progress;
    @Getter
    protected boolean completed;

    public Quest(String name, int goal) {
        this.name = name;
        this.goal = goal;
        this.progress = 0;
        this.completed = false;
    }

    public void addProgress(int amount) {
        if (completed) return;
        progress += amount;
        if (progress >= goal) {
            progress = goal;
            completed = true;
        }
    }


    public abstract String getDisplay();

    protected String formatDisplay(String text) {
        return (completed ? "§a✔ " : "§e") + text;
    }

}
