package agh.ics.oop.model;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public String toString(){
        return switch(this){
            case NORTH -> "N";
            case NORTHEAST -> "NE";
            case EAST -> "E";
            case SOUTHEAST -> "SE";
            case SOUTH -> "S";
            case SOUTHWEST -> "SW";
            case WEST -> "W";
            case NORTHWEST -> "NW";
        };
    }
//    public String toString(){
//        return switch(this){
//            case NORTH -> "^";
//            case NORTHEAST -> "";
//            case EAST -> ">";
//            case SOUTHEAST -> "";
//            case SOUTH -> "v";
//            case SOUTHWEST -> "";
//            case WEST -> "<";
//            case NORTHWEST -> "";
//        };
//    }
    public MapDirection rotateNTimes (int n){
        int nxt = (this.ordinal() + n) % MapDirection.values().length;
        return MapDirection.values()[nxt];
    }

    public Vector2d toUnitVector(){
        return switch(this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTHEAST -> new Vector2d(1, 1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTHEAST -> new Vector2d(1, -1);
            case EAST -> new Vector2d(1, 0);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTHWEST -> new Vector2d(-1, 1);
        };
    }

}
