package io.github.raboro.merklehellmancryptosystemsolver;

/***
 * @param c modulus
 * @param publicKey
 * @param encryptedMessage
 *
 * @author Raboro
 */
public record SolverInput(int c, int[] publicKey, int[] encryptedMessage) {
}
