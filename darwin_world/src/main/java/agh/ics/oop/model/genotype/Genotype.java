package agh.ics.oop.model.genotype;


import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.worldElements.Animal;

import java.util.List;

public interface Genotype {
    Genotype createNewFrom(Animal animal1, Animal animal2);
    MoveDirection next();
    List<MoveDirection> getGenotypeList();


}
