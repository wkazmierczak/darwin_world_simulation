package agh.ics.oop.model.genotype;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractGenotype implements Genotype {

    protected List<MoveDirection> genotypeList;

    private static final String[] sites = {"left", "right"};


    AbstractGenotype(int length) {
        genotypeList = new Random().ints(0, 7).mapToObj(MoveDirection::valueOf).filter(Optional::isPresent).map(Optional::get).limit(length).toList();
    }

    AbstractGenotype(Animal stronger, Animal weaker) {
        List<MoveDirection> strongerGenotypeList = stronger.getGenotype().getGenotypeList();
        List<MoveDirection> weakerGenotypeList = weaker.getGenotype().getGenotypeList();
        int size = strongerGenotypeList.size();

        int strongerEnergy = stronger.getEnergyLevel();
        int weakerEnergy = weaker.getEnergyLevel();

        String siteForStronger = sites[new Random().nextInt(2)];

        if (siteForStronger.equals("left")){
            int splitPoint = (strongerEnergy/(strongerEnergy+weakerEnergy) * size);
            genotypeList = strongerGenotypeList.subList(0, splitPoint);
            genotypeList.addAll(weakerGenotypeList.subList(splitPoint, size));
        }
        else if (siteForStronger.equals("right")){
            int splitPoint = (weakerEnergy/(strongerEnergy+weakerEnergy) * size);
            genotypeList = weakerGenotypeList.subList(0, splitPoint);
            genotypeList.addAll(strongerGenotypeList.subList(splitPoint, size));
        }
    }

    @Override
    abstract public MoveDirection next();

    @Override
    public List<MoveDirection> getGenotypeList() {
        return genotypeList;
    }
}
