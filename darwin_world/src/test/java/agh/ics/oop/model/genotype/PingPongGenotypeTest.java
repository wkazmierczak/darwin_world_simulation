package agh.ics.oop.model.genotype;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PingPongGenotypeTest {

    @Test
    public void next() {
        int n = 5;
        Genotype genotype = new PingPongGenotype(n);
        List<Integer> genotypeList = genotype.getGenotypeList();
        for (var direction : genotypeList) {
            assertEquals(direction, genotype.next());
        }
        for (int i = genotypeList.size() - 2; i >= 0; i--) {
            assertEquals(genotypeList.get(i), genotype.next());
        }
    }

}