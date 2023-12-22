package agh.ics.oop.model.genotype;

import agh.ics.oop.model.worldElements.Animal;
import agh.ics.oop.model.MoveDirection;

public class BasicGenotype extends AbstractGenotype {

    public BasicGenotype(int length) {
        super(length);
    }

    public BasicGenotype(Animal stronger, Animal weaker) {
        super(stronger, weaker);
    }

    @Override
    public int next() {
        var result = genotypeList.get(currentIdx);
        currentIdx = (currentIdx + 1) % genotypeList.size();
        return result;
    }

    @Override
    public BasicGenotype createNewFrom(Animal animal1, Animal animal2) {
        return new BasicGenotype(animal1, animal2);
    }
}
