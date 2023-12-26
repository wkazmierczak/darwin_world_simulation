package agh.ics.oop.model.genotype;

import agh.ics.oop.model.util.MyRange;
import agh.ics.oop.model.worldElements.Animal;

public class BasicGenotype extends AbstractGenotype {

    public BasicGenotype(int length) {
        super(length);
    }

    public BasicGenotype(Animal stronger, Animal weaker, MyRange mutationsRange) {
        super(stronger, weaker, mutationsRange);
    }

    @Override
    public int next() {
        var result = genotypeList.get(currentIdx);
        currentIdx = (currentIdx + 1) % genotypeList.size();
        return result;
    }

    @Override
    public BasicGenotype createNewFrom(Animal animal1, Animal animal2, MyRange mutationsRange) {
        return new BasicGenotype(animal1, animal2, mutationsRange);
    }
}
