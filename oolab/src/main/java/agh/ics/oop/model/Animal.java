package agh.ics.oop.model;

public class Animal {
    private MapDirection orientation;
    private Vector2d position;

    private final static Vector2d LEFT_BOTTOM = new Vector2d(0, 0);
    private final static Vector2d RIGHT_TOP = new Vector2d(4, 4);

    public Animal(){
        this(new Vector2d(2, 2));
    }
    public Animal(Vector2d position){
        this.position = position;
        this.orientation = MapDirection.NORTH;
    }

    public String to_String(){
        return getPosition().toString() + ", " + getOrientation().toString();
    }

    public boolean isAt(Vector2d position){
        return getPosition().equals(position);
    }

    public void move(MoveDirection direction){
        Vector2d currPos= getPosition();
        MapDirection currOrient = getOrientation();

        switch (direction){
            case RIGHT -> this.orientation = currOrient.next();
            case LEFT -> this.orientation = currOrient.previous();
            case FORWARD -> currPos = currPos.add(currOrient.toUnitVector());
            case BACKWARD -> currPos = currPos.substract(currOrient.toUnitVector());
        };

        if (currPos.precedes(RIGHT_TOP) && currPos.follows(LEFT_BOTTOM)){
            this.position = currPos;
        }
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2d getPosition() {
        return position;
    }

    public static Vector2d getLeftBottom() {
        return LEFT_BOTTOM;
    }

    public static Vector2d getRightTop() {
        return RIGHT_TOP;
    }
}
