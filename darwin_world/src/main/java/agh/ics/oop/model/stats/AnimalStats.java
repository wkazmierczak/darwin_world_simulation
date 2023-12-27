package agh.ics.oop.model.stats;

import agh.ics.oop.model.worldElements.Animal;

import java.util.*;


public class AnimalStats {

    private int age = 0;
    private int plantsEaten = 0;
    private int descendants = 0;

    private final List<Animal> children = new LinkedList<>();
    private Integer dayOfDeath = null;

    public void incrementAge() {
        age++;
    }

    public void incrementPlantsEaten() {
        plantsEaten++;
    }

    public void addChildren(Animal child) {
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
            for (var parent : currAnimal.getParents()) {
                if (parent != null && !visited.contains(parent)) {
                    parent.getStats().incrementDescendants();
                    queue.add(parent);
                }
            }
        }
    }


    private void incrementDescendants() {
        this.descendants++;
    }

    public void setDayOfDeath(int dayOfSimulation) {
        this.dayOfDeath = dayOfSimulation;
    }

    public int getChildrenCount() {
        return children.size();
    }

    public int getAge() {
        return age;
    }


    public int getPlantsEaten() {
        return plantsEaten;
    }


    public Integer getDayOfDeath() {
        return dayOfDeath;
    }

    public List<Animal> getChildren() {
        return children;
    }

    public int getDescendantsCount() {
        return descendants;
    }

}
