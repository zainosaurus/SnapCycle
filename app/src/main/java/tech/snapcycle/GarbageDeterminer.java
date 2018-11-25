package tech.snapcycle;

import java.util.List;

public class GarbageDeterminer {
    private static final String[] RECYCLABLES = {"plastic", "cardboard", "paper", "glass", "metal", "cup", "styrofoam"};

    // Determines if something is trash or recyclable
    public static boolean checkGarbage(List<String> possibleItems) {
        boolean trash = true;
        for (int i = 0; i < possibleItems.size(); i++) {
            for (int j = 0; j < RECYCLABLES.length; i++) {
                if (possibleItems.get(i).toUpperCase().equals(RECYCLABLES[j].toUpperCase())) {
                    trash = false;
                    break;
                }
            }
            if (!trash) {
                break;
            }
        }
        return trash;
    }
}
