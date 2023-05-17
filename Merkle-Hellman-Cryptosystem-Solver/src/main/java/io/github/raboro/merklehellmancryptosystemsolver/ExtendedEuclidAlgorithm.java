package io.github.raboro.merklehellmancryptosystemsolver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raboro
 */
public class ExtendedEuclidAlgorithm {

    private final List<Equation> equations;
    private final int e;
    private final int c;
    private int result;

    public ExtendedEuclidAlgorithm(int e, int c) {
        this.e = e;
        this.c = c;
        equations = new ArrayList<>();
    }

    public void solve() {
        euclidAlgorithm();
        result = 1;
        int currentX = 0;
        int oldX;
        for (int i = equations.size() - 2; i >= 0; i--) {
            oldX = currentX;
            currentX = result;
            result = oldX - equations.get(i).multiplier * result;
        }
    }

    private void euclidAlgorithm() {
        equations.add(Equation.construct(e, c));
        int equationResult;
        do {
            equations.add(Equation.construct(equations.get(equations.size() - 1)));
            equationResult = equations.get(equations.size() - 1).b;
        } while (equationResult > 1);
    }

    public int determineD() {
        // handle variable switch leading to negative y, if a < b
        return (result < 0) ? result + c : result;
    }

    /**
     * a = b * multiplier with remainder
     *
     * @author Raboro
     */
    private static class Equation {

        int a;
        int b;
        int multiplier;
        int remainder;

        private Equation(int a, int b, int multiplier, int remainder) {
            this.a = a;
            this.b = b;
            this.multiplier = multiplier;
            this.remainder = remainder;
        }

        /**
         * @param a
         * @param b
         * @return constructs equation with given a and b
         * Is the first equation of the algorithm
         */
        static Equation construct(int a, int b) {
            return new Equation(a, b, a / b, a % b);
        }

        /**
         * @param previousEquation
         * @return constructs equation with previous equation as data
         */
        static Equation construct(Equation previousEquation) {
            final int a = previousEquation.b;
            final int b = previousEquation.remainder;
            final int multiplier = a / b;
            final int remainder = a % b;
            return new Equation(a, b, multiplier, remainder);
        }

        @Override
        public String toString() {
            return "%d =  %d * %d R%d".formatted(a, b, multiplier, remainder);
        }
    }
}
