package agh.ics.oop.model;

import java.util.Iterator;
import java.util.Random;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final int maxWidth;
    private final int maxHeight;
    private final int elemCount;

    public RandomPositionGenerator(int maxWidth, int maxHeight, int elemsCount) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.elemCount = elemsCount;

    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new RandomPositionIterator(maxWidth, maxHeight, elemCount);
    }

    private class RandomPositionIterator implements Iterator<Vector2d> {

        private final int maxWidth;
        private final int maxHeight;
        private int elemCount;
        private final Random rand = new Random();

        private int generatedCount;
//        private final List<Vector2d> positions;

        int step;

        public RandomPositionIterator(int maxWidth, int maxHeight, int elemCount) {
            this.maxWidth = maxWidth;
            this.maxHeight = maxHeight;
            this.elemCount = elemCount;
            this.generatedCount = 0;
            calculateStep();
        }

        private void calculateStep() {
            int range = maxWidth * maxHeight;
            this.step = Math.max(1, range / elemCount);
        }

        private Vector2d generateNext(int i) {
            int next = step * i + rand.nextInt(0, step);
            return new Vector2d((next / maxWidth), (next % maxHeight));
        }

        @Override
        public boolean hasNext() {
            if (generatedCount == elemCount) {
                return false;
            }
            if (generatedCount == maxWidth * maxHeight) {
                elemCount -= generatedCount;
                generatedCount = 0;
                calculateStep();
            }
            return true;
        }

        @Override
        public Vector2d next() {
            if (hasNext()) {
                return generateNext(generatedCount++);
//                return positions.get(generatedCount++);
            }
            return null;
        }
    }
}
