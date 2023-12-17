package agh.ics.oop.model;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final int maxWidth;
    private final int maxHeight;
    private final int grassCount;

    public RandomPositionGenerator(int maxWidth, int maxHeight, int grassCount){
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.grassCount = grassCount;

    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new RandomPositionIterator(maxWidth, maxHeight, grassCount);
    }

    private class RandomPositionIterator implements Iterator<Vector2d>{

        private final int maxWidth;
        private final int maxHeight;
        private final int grassCount;
        private final Random rand = new Random();;
        private int generatedCount;
        private final List<Vector2d> positions;

        public RandomPositionIterator(int maxWidth, int maxHeight, int grassCount) {
            this.maxWidth = maxWidth;
            this.maxHeight = maxHeight;
            this.grassCount = grassCount;
            this.generatedCount = 0;

            this.positions = new ArrayList<>();
            for (int x = 0; x < maxWidth; x++) {
                for (int y = 0; y < maxHeight; y++) {
                    positions.add(new Vector2d(x, y));
                }
            }

            Random rand = new Random();
            for (int i = positions.size() - 1; i > 0; i--) {
                int j = rand.nextInt(i + 1);
                Collections.swap(positions, i, j);
            }

        }

        @Override
        public boolean hasNext() {
            return generatedCount < grassCount;
        }

        @Override
        public Vector2d next() {
            if (hasNext()) {
                return positions.get(generatedCount++);

            }
            return null;
        }
    }
}
