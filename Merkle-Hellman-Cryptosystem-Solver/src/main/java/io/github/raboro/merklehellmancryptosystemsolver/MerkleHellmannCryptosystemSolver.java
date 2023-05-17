package io.github.raboro.merklehellmancryptosystemsolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Raboro
 */
public class MerkleHellmannCryptosystemSolver {

    private final SolverInput input;
    private int e; // multiplier
    private int d; // mod inverse of multiplier
    private Knapsack knapsack;
    private int[] decryptedMessage;

    public MerkleHellmannCryptosystemSolver(SolverInput input) {
        this.input = input;
    }

    /**
     * solve the encryption by decrypting the message
     */
    public void solve() {
        constructPrivateKey();
        decrypt();
    }

    /**
     * constructs the "secret" private key by iteration over e,
     * then check that gcd(e,C) = 1,
     * then determines the mod inverse of e, called d,
     * then tries to construct a valid super increasing knapsack
     */
    private void constructPrivateKey() {
        for (e = 1; e < input.c(); e++) {
            if (isPartUnrelated(e)) {
                determineD();
                knapsack = new Knapsack(input.publicKey(), input.c(), d);
                if (knapsack.isSuperIncreasingKnapsack()) {
                    break;
                }
            }
        }
    }

    /**
     * @param e multiplier
     * @return gcd(e, C) = 1
     */
    private boolean isPartUnrelated(int e) {
        return gcd(e, input.c()) == 1;
    }

    /**
     * @param x
     * @param y
     * @return greatest common divisor (german: ggT)
     */
    private int gcd(int x, int y) {
        while (y != 0) {
            if (x > y) {
                x -= y;
            } else {
                y -= x;
            }
        }
        return x;
    }

    private void determineD() {
        ExtendedEuclidAlgorithm extendedEuclidAlgorithm = new ExtendedEuclidAlgorithm(e, input.c());
        extendedEuclidAlgorithm.solve();
        d = extendedEuclidAlgorithm.determineD();
    }

    private void decrypt() {
        decryptedMessage = new int[input.encryptedMessage().length];
        for (int i = 0; i < decryptedMessage.length; i++) {
            int element = (input.encryptedMessage()[i] * d) % input.c();
            decryptedMessage[i] = toInt(decryptElementToBinary(element));
        }
    }

    /**
     * uses horner scheme for the sum
     * with the elements of the super increasing knapsack
     *
     * @param element (encrypted element * d) % c
     * @return binary representation of sum
     */
    private List<Boolean> decryptElementToBinary(int element) {
        List<Boolean> bits = new ArrayList<>();
        for (int j = knapsack.size() - 1; j >= 0; j--) {
            if (element - knapsack.getElementAt(j) < 0) {
                bits.add(false);
                continue;
            }
            element -= knapsack.getElementAt(j);
            bits.add(true);
        }
        return bits;
    }

    /**
     * @param bits binary representation of the int
     * @return constructed int out of bits
     */
    private int toInt(List<Boolean> bits) {
        int result = 0;
        for (int j = 0; j < bits.size(); j++) {
            if (Boolean.TRUE.equals(bits.get(j)))
                result |= 1 << j;
        }
        return result;
    }

    /**
     * print decrypted message as String to console
     */
    public void print() {
        System.out.println(convertMessageToString());
    }

    private String convertMessageToString() {
        return Arrays.stream(decryptedMessage)
                .mapToObj(j -> String.valueOf((char) j))
                .collect(Collectors.joining());
    }
}
