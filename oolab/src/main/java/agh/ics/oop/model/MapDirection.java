package agh.ics.oop.model;

public enum MapDirection {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public String toString(){
        return switch(this) {
            case NORTH -> "Północ";
            case SOUTH -> "Południe";
            case EAST -> "Wschód";
            case WEST -> "Zachód";
        };
    }
}
