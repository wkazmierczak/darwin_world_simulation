package agh.ics.oop.model.genotype;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasicGenotypeTest {

    @Test
    public void next() {
        int n = 5;
        Genotype genotype = new BasicGenotype(n);
        List<MoveDirection> genotypeList = genotype.getGenotypeList();
        for (MoveDirection direction : genotypeList) {
            assertEquals(direction, genotype.next());
        }
        for (MoveDirection direction : genotypeList) {
            assertEquals(direction, genotype.next());
        }

    }
}