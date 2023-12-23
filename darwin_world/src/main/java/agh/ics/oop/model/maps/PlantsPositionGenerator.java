package agh.ics.oop.model.maps;

import agh.ics.oop.model.Boundary.Boundary;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.worldElements.plants.Plant;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PlantsPositionGenerator {
    private final Set<Vector2d> plantsPlaced = new HashSet<>();
    private final Boundary boundary;

    public PlantsPositionGenerator(Map<Vector2d, Plant> plants, Boundary boundary) {
        this.plantsPlaced.addAll(plants.keySet());
        this.boundary = boundary;
    }

    public Stream<Vector2d> getAllPositionsShuffled() {

        final int n = boundary.getHeight();
        final int m = boundary.getWidth();
        List<Integer> allIt = IntStream.range(0, n * m).boxed().collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(allIt);
        return allIt.stream().map(i -> new Vector2d(i / n, i % n));
    }

    private Stream<Vector2d> getAllFreePositionsShuffled() {
        return getAllPositionsShuffled().filter(v -> !plantsPlaced.contains(v));
    }

    public List<Vector2d> getNFreePositions(int n) {
        List<Vector2d> result = getAllFreePositionsShuffled().limit(n).toList();
        plantsPlaced.addAll(result);
        return result;
    }

    public List<Vector2d> getNFreePositionsWithinBounds(int n, Boundary currentBoundary) {
        List<Vector2d> result = getAllFreePositionsShuffled().filter(v -> v.inBounds(currentBoundary)).limit(n).toList();
        plantsPlaced.addAll(result);
        return result;
    }

    public List<Vector2d> getFreePositionsNotInBounds(int n, Boundary currentBoundary) {
        List<Vector2d> result = getAllFreePositionsShuffled().filter(v -> !v.inBounds(currentBoundary)).limit(n).toList();
        plantsPlaced.addAll(result);
        return result;
    }
}
