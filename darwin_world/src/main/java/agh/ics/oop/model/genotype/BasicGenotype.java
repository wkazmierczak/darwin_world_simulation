package agh.ics.oop.model.genotype;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;

public class BasicGenotype extends AbstractGenotype {
    private int currentIdx = 0;

    public BasicGenotype(int length) {
        super(length);
    }

    public BasicGenotype(Animal stronger, Animal weaker) {
        super(stronger, weaker);
    }

    @Override
    public MoveDirection next() {
        var result = genotypeList.get(currentIdx);
        currentIdx = (currentIdx + 1) % genotypeList.size();
        return result;
    }
}
