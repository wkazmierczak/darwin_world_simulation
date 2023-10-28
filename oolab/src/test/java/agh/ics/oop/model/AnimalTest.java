package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnimalTest {

    @Test
    public void orientationTest(){
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(3, 4));
        Animal animal3 = new Animal();
        MapDirection sol1 = MapDirection.NORTH;
        MapDirection sol2 = MapDirection.SOUTH;
        MapDirection sol3 = MapDirection.WEST;



        animal1.move(MoveDirection.FORWARD);
        animal1.move(MoveDirection.BACKWARD);

        animal2.move(MoveDirection.LEFT);
        animal2.move(MoveDirection.LEFT);

        animal3.move(MoveDirection.RIGHT);
        animal3.move(MoveDirection.RIGHT);
        animal3.move(MoveDirection.RIGHT);



        Assertions.assertEquals(sol1, animal1.getOrientation());
        Assertions.assertEquals(sol2, animal2.getOrientation());
        Assertions.assertEquals(sol3, animal3.getOrientation());
    }


    @Test
    public void positionTest(){
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(3, 4));
        Animal animal3 = new Animal();
        Vector2d sol1 = new Vector2d(2, 2);
        Vector2d sol2 = new Vector2d(2, 3);
        Vector2d sol3 = new Vector2d(0, 1);



        animal1.move(MoveDirection.FORWARD);
        animal1.move(MoveDirection.BACKWARD);

        animal2.move(MoveDirection.LEFT);
        animal2.move(MoveDirection.FORWARD);
        animal2.move(MoveDirection.LEFT);
        animal2.move(MoveDirection.FORWARD);

        animal3.move(MoveDirection.LEFT);
        animal3.move(MoveDirection.FORWARD);
        animal3.move(MoveDirection.FORWARD);
        animal3.move(MoveDirection.RIGHT);
        animal3.move(MoveDirection.BACKWARD);




        Assertions.assertEquals(sol1, animal1.getPosition());
        Assertions.assertEquals(sol2, animal2.getPosition());
        Assertions.assertEquals(sol3, animal3.getPosition());

        Assertions.assertTrue(animal1.isAt(sol1));
        Assertions.assertTrue(animal2.isAt(sol2));
        Assertions.assertTrue(animal3.isAt(sol3));
        Assertions.assertFalse(animal2.isAt(sol1));
    }

    @Test
    public void offTheMapTest(){
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(3, 4));
        Animal animal3 = new Animal();
        Animal animal4 = new Animal();



        for (int i = 0; i<10; i++){
            animal1.move(MoveDirection.FORWARD);
        }

        animal2.move(MoveDirection.LEFT);
        for (int i = 0; i<10; i++){
            animal1.move(MoveDirection.FORWARD);
        }


        animal3.move(MoveDirection.RIGHT);
        for (int i = 0; i<4; i++){
            animal1.move(MoveDirection.FORWARD);
        }



        Assertions.assertTrue(animal1.getPosition().precedes(Animal.getRightTop()) &&
                animal1.getPosition().follows(Animal.getLeftBottom()));
        Assertions.assertTrue(animal2.getPosition().precedes(Animal.getRightTop()) &&
                animal2.getPosition().follows(Animal.getLeftBottom()));
        Assertions.assertTrue(animal3.getPosition().precedes(Animal.getRightTop()) &&
                animal3.getPosition().follows(Animal.getLeftBottom()));
        Assertions.assertTrue(animal4.getPosition().precedes(Animal.getRightTop()) &&
                animal4.getPosition().follows(Animal.getLeftBottom()));

    }
}
