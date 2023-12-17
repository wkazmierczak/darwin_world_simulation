package agh.ics.oop.model.genotype;

import agh.ics.oop.model.MoveDirection;

public class BasicGenotype extends AbstractGenotype {
    private int currentIdx = 0;

    BasicGenotype(int length) {
        super(length);
    }

    BasicGenotype(Genotype parent1, Genotype parent2) {
        super(parent1, parent2);
    }

    @Override
    public MoveDirection next() {
        var result = genotypeList.get(currentIdx);
        currentIdx = (currentIdx + 1) % genotypeList.size();
        return result;
    }
}
