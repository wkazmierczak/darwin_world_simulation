package agh.ics.oop.model.genotype;

import agh.ics.oop.model.MoveDirection;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class AbstractGenotype implements Genotype {

    protected List<MoveDirection> genotypeList;
    protected int genotypeLength;

    public int getGenotypeLength() {
        return genotypeLength;
    }

    AbstractGenotype(int length) {
        genotypeLength = length;
        genotypeList = new Random().ints(0, 7).mapToObj(MoveDirection::valueOf).filter(Optional::isPresent).map(Optional::get).limit(genotypeLength).toList();
    }

    AbstractGenotype(Genotype parent1, Genotype parent2) {
        genotypeLength = parent1.getGenotypeLength();
        //TODO
    }

    @Override
    abstract public MoveDirection next();
}
