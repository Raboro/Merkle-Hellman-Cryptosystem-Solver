package io.github.raboro.merklehellmancryptosystemsolver;

public class Main {

    public static void main(String[] args) {
        SolverInput input = new SolverInput(
                631, // modulus
                new int[]{ 211, 212, 424, 217, 13, 447, 473, 105 },
                new int[]{ 876, 1674, 853, 424, 1007, 1188, 1214, 1326, 1188, 1300, 529, 746, 746 }
        );
        MerkleHellmannCryptosystemSolver solver = new MerkleHellmannCryptosystemSolver(input);
        solver.solve();
        solver.print();
    }
}
