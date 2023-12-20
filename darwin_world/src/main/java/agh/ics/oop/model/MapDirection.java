package agh.ics.oop.model;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public String toString(){
        return switch(this){
            case NORTH -> "^";
            case EAST -> ">";
            case SOUTH -> "v";
            case WEST -> "<";
        };
    }

    public MapDirection next(){
        int nxt = (this.ordinal() + 1) % MapDirection.values().length;
        return MapDirection.values()[nxt];
    }

    public MapDirection previous(){
        int l = MapDirection.values().length;
        int prev = (this.ordinal() - 1 + l) % l;
        return MapDirection.values()[prev];
    }

    public Vector2d toUnitVector(){
        return switch(this) {
            case NORTH -> new Vector2d(0, 1);
            case SOUTH -> new Vector2d(0, -1);
            case EAST -> new Vector2d(1, 0);
            case WEST -> new Vector2d(-1, 0);
        };
    }

}
