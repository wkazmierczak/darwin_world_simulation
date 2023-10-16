package agh.ics.oop.model;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public static String toString(int x, int y){
        return "(%d, %d)".formatted(x, y);
    }

    public boolean precedes(Vector2d other){
        return this.x<=other.x && this.y<=other.y;
    }

    public boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x+other.x, this.y+other.y);
    }
    public Vector2d substract(Vector2d other){
        return new Vector2d(this.x-other.x, this.y-other.y);
    }

    public Vector2d upperRight(Vector2d other){
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }
    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    public Vector2d opposite(){
        return new Vector2d(-this.x, -this.y);
    }

//    public boolean equals(Object other){
//        if (this == other)
//            return true;
//        if (!(other instanceof Vector2d))
//            return false;
//        Vector2d that = (Vector2d) other;
//        // tutaj przeprowadzane jest faktyczne porównanie
//    }


}