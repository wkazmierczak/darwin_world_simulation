package agh.ics.oop.model.genotype;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import java.util.Collections;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractGenotype implements Genotype {

    protected List<MoveDirection> genotypeList;

    private static final String[] sites = {"left", "right"};

    AbstractGenotype(int length) {
        genotypeList = new Random().ints(0, 7).mapToObj(MoveDirection::valueOf).filter(Optional::isPresent).map(Optional::get).limit(length).toList();
    }

    AbstractGenotype(Animal animal1, Animal animal2){
        Animal stronger = animal1.getEnergyLevel() > animal2.getEnergyLevel() ? animal1: animal2;
        Animal weaker = animal1.getEnergyLevel() <= animal2.getEnergyLevel() ? animal1: animal2;

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

        int numberOfMutations = new Random().nextInt(size);
        mutate(numberOfMutations);
    }

    @Override
    abstract public MoveDirection next();

    @Override
    public List<MoveDirection> getGenotypeList() {
        return genotypeList;
    }

    public void mutate(int mutationNum){
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < genotypeList.size(); i++) {
            indexes.add(i);

        }

        Collections.shuffle(indexes);

        List<Integer> indexesToMutate = indexes.subList(0, mutationNum);

        for (Integer idx : indexesToMutate){
            genotypeList.set(idx, MoveDirection.valueOf(new Random().nextInt(0, 8)).get());
        }

    }
}
