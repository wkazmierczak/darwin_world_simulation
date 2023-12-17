package agh.ics.oop.model.genotype;

import agh.ics.oop.model.MoveDirection;

public interface Genotype {
    MoveDirection next();
    int getGenotypeLength();
}
