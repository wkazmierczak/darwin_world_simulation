package agh.ics.oop.model.genotype;

import agh.ics.oop.model.MoveDirection;

import java.util.List;

public interface Genotype {
    MoveDirection next();
    List<MoveDirection> getGenotypeList();

}
