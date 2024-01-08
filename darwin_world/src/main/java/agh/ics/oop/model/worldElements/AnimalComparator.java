package agh.ics.oop.model.worldElements;

import java.util.Comparator;

public class AnimalComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal o1, Animal o2) {
        int energyDiff = o2.getEnergyLevel() - o1.getEnergyLevel();
        if (energyDiff != 0) {
            return energyDiff;
        }
        int ageDiff = o2.getStats().getAge() - o1.getStats().getAge();
        if (ageDiff != 0) {
            return ageDiff;
        }
        int childrenDiff = o2.getStats().getChildrenCount() - o1.getStats().getChildrenCount();
        if (childrenDiff != 0) {
            return childrenDiff;
        }
        return o1.hashCode() - o2.hashCode();
    }
}
