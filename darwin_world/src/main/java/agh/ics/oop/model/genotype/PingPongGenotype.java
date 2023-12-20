package agh.ics.oop.model.genotype;

import agh.ics.oop.model.worldElements.Animal;
import agh.ics.oop.model.MoveDirection;

public class PingPongGenotype extends AbstractGenotype {

    private int currentIdx = 0;

    private int genMove = 1;

    public PingPongGenotype(int length) {
        super(length);
    }

    public PingPongGenotype(Animal stronger, Animal weaker) {
        super(stronger, weaker);
    }

    @Override
    public MoveDirection next() {
        if (currentIdx == 0) {
            genMove = 1;
        }
        if (currentIdx == genotypeList.size() - 1) {
            genMove = -1;
        }

        MoveDirection result = genotypeList.get(currentIdx);
        currentIdx += genMove;

        return result;
    }

    @Override
    public PingPongGenotype createNewFrom(Animal animal1, Animal animal2) {
        return new PingPongGenotype(animal1, animal2);
    }

}
