package agh.ics.oop.model.genotype;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AbstractGenotypeTest {

    @Test
    public void create() {
        int n = 100000;
        Genotype genotype = new PingPongGenotype(n);
        List<Integer> genotypeList = genotype.getGenotypeList();
        assertEquals(n, genotypeList.size());
        Set<Integer> moves = new HashSet<>(genotypeList);
        assertEquals(8, moves.size(), moves.toString());
    }

    @Test
    void mutate() {
        int mutationNum = 5;
        int n = 10;
        String methodName = "mutate";
        AbstractGenotype genotype = new PingPongGenotype(n);
        List<Integer> genotypeList = new ArrayList<>(genotype.getGenotypeList());
        assertDoesNotThrow(() -> AbstractGenotype.class.getDeclaredMethod(methodName, int.class));
        Method mutate;
        try {
            mutate = AbstractGenotype.class.getDeclaredMethod(methodName, int.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        mutate.setAccessible(true);
        try {
            mutate.invoke(genotype, mutationNum);
        } catch (IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        List<Integer> genotypeListAfterMutation = genotype.getGenotypeList();
        System.out.println(genotypeList);
        System.out.println(genotypeListAfterMutation);
        int diff = 0;
        for (int i = 0; i < n; i++) {
            if (genotypeListAfterMutation.get(i) != genotypeList.get(i))
                diff++;
        }
        assertEquals(mutationNum, diff);
    }
}