package de.illy.planCEvent.util;

public class LevelColor {
    public static String getColor(int level) {
        if (level < 40) {
            return "§7";
        } else if (level < 80) {
            return "§f";
        } else if (level < 120) {
            return "§e";
        } else if (level < 160) {
            return "§a";
        } else if (level < 200) {
            return "§2";
        } else if (level < 240) {
            return "§b";
        } else if (level < 280) {
            return "§3";
        } else if (level < 320) {
            return "§9";
        } else if (level < 360) {
            return "§d";
        } else if (level < 400) {
            return "§5";
        } else if (level < 440) {
            return "§6";
        } else if (level < 480) {
            return "§c";
        } else {
            return "§4";
        }
    }
}
