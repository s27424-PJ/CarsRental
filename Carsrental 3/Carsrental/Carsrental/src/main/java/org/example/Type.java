package org.example;

public enum Type {
    Classic(2), Sport(1.5);
    private final double multiplier;

    Type(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
