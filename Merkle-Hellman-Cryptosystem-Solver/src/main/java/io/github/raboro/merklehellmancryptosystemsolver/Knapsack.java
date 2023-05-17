package io.github.raboro.merklehellmancryptosystemsolver;

import java.util.Arrays;

/**
 * @author Raboro
 */
public class Knapsack {

    private final int[] data;
    private final int c;

    public Knapsack(int[] publicKey, int c, int d) {
        data = new int[publicKey.length];
        this.c = c;
        Arrays.setAll(data, i -> (publicKey[i] * d) % c);
    }

    /**
     * If the sum of all elements <= c and all elements are
     * greater than the sum of all elements before
     *
     * @return boolean
     */
    public boolean isSuperIncreasingKnapsack() {
        final int knapsackSum = Arrays.stream(data).sum();
        return knapsackSum <= c && isSuperIncreasingKnapsackSumNotSmallerC();
    }

    private boolean isSuperIncreasingKnapsackSumNotSmallerC() {
        for (int i = 1; i < data.length - 1; i++) {
            int sum = Arrays.stream(data, 0, i).sum();
            if (sum >= data[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return size of the knapsack
     */
    public int size() {
        return data.length;
    }

    /**
     * @param index
     * @return element at given index
     */
    public int getElementAt(int index) {
        return data[index];
    }
}