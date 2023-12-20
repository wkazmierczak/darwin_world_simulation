package agh.ics.oop.model.stats;

import agh.ics.oop.model.worldElements.Animal;

import java.util.*;

public class AnimalStats {

    private final int reproduceEnergyMin;

    private final int energySpendToReproduce;

    private int age = 0;

    private int plantsEaten = 0;

    private int descendants = 0;

//    TODO linked-list
    private final List<Animal> children = new ArrayList<>();

    private Integer dayOfDeath = null;


    public AnimalStats(int reproduceEnergyMin, int energySpendToReproduce){
        this.reproduceEnergyMin = reproduceEnergyMin;
        this.energySpendToReproduce = energySpendToReproduce;

    }

    public void incrementAge(){
        age++;
    }

    public void incrementPlantsEaten(){
        plantsEaten++;
    }

    public void addChildren(Animal child){
        children.add(child);
        updateDescendants(child);
    }

    private void updateDescendants(Animal animal) {
        Queue<Animal> queue = new LinkedList<>();
        Set<Animal> visited = new HashSet<>();

        queue.add(animal);
        visited.add(animal);

        while (!queue.isEmpty()) {
            Animal currAnimal = queue.remove();
            if ((currAnimal.getParent1() != null) && (!visited.contains(currAnimal.getParent1()))) {
                currAnimal.getParent1().getStats().incrementDescendants();
                queue.add(currAnimal.getParent1());
            }
            if ((currAnimal.getParent2() != null) && (!visited.contains(currAnimal.getParent2()))) {
                currAnimal.getParent2().getStats().incrementDescendants();
                queue.add(currAnimal.getParent1());
            }
        }
    }

    private void incrementDescendants(){
        this.descendants++;
    }

    public void setDayOfDeath(int dayOfSimulation){
        this.dayOfDeath = dayOfSimulation;
    }

    public int getChildrenCount(){
        return children.size();
    }

    public int getAge() {
        return age;
    }

    public int getEnergySpendToReproduce() {
        return energySpendToReproduce;
    }

    public int getPlantsEaten() {
        return plantsEaten;
    }

    public int getReproduceEnergyMin() {
        return reproduceEnergyMin;
    }

    public Integer getDayOfDeath() {
        return dayOfDeath;
    }

    public List<Animal> getChildren() {
        return children;
    }

    public int getDescendantsCount(){
        return descendants;
    }

}
