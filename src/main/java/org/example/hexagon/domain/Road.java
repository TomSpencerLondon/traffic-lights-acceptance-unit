package org.example.hexagon.domain;

public class Road {
    private final String name;

    public Road(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Road road = (Road) o;

        return name.equals(road.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}