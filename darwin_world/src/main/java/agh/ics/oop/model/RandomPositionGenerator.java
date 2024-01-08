package agh.ics.oop.model;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final int maxWidth;
    private final int maxHeight;
    private final int elemsCount;

    public RandomPositionGenerator(int maxWidth, int maxHeight, int elemsCount) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.elemsCount = elemsCount;

    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new RandomPositionIterator(maxWidth, maxHeight, elemsCount);
    }

    private class RandomPositionIterator implements Iterator<Vector2d> {

        private final int maxWidth;
        private final int maxHeight;
        private final int elemsCount;
        private final Random rand = new Random();
        ;
        private int generatedCount;
        private final List<Vector2d> positions;

        //new imlementation
        int step;

        public RandomPositionIterator(int maxWidth, int maxHeight, int elemsCount) {
            this.maxWidth = maxWidth;
            this.maxHeight = maxHeight;
            this.elemsCount = elemsCount;
            this.generatedCount = 0;

            int range = maxWidth * maxHeight;
            this.step = range / elemsCount;

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

        private Vector2d generateNext(int i) {
            int next = step * i + rand.nextInt(0, step);
            return new Vector2d((next / maxWidth), (next % maxHeight));
        }

        @Override
        public boolean hasNext() {
            return generatedCount < elemsCount;
        }

        @Override
        public Vector2d next() {
            if (hasNext()) {
                return generateNext(generatedCount++);
            }
            return null;
        }
    }
}
