package model;

import java.util.Random;

public class NeuralNetwork {

    private final int inputSize, hiddenSize, outputSize;
    private final double[][] W1, W2;
    private final double[] B1, B2;

    public NeuralNetwork(int inputSize, int hiddenSize, int outputSize) {
        this.inputSize = inputSize;
        this.hiddenSize = hiddenSize;
        this.outputSize = outputSize;

        W1 = new double[hiddenSize][inputSize];
        W2 = new double[outputSize][hiddenSize];
        B1 = new double[hiddenSize];
        B2 = new double[outputSize];

        initializeWeights();
    }

    private void initializeWeights() {
        Random rand = new Random();
        for (int i = 0; i < hiddenSize; i++) {
            for (int j = 0; j < inputSize; j++) {
                W1[i][j] = rand.nextGaussian() * 0.1;
            }
            B1[i] = 0;
        }

        for (int i = 0; i < outputSize; i++) {
            for (int j = 0; j < hiddenSize; j++) {
                W2[i][j] = rand.nextGaussian() * 0.1;
            }
            B2[i] = 0;
        }
    }

    public int predict(double[] input) {
        double[] hidden = new double[hiddenSize];
        for (int i = 0; i < hiddenSize; i++) {
            hidden[i] = B1[i];
            for (int j = 0; j < inputSize; j++) {
                hidden[i] += W1[i][j] * input[j];
            }
            hidden[i] = relu(hidden[i]);
        }

        double[] output = new double[outputSize];
        for (int i = 0; i < outputSize; i++) {
            output[i] = B2[i];
            for (int j = 0; j < hiddenSize; j++) {
                output[i] += W2[i][j] * hidden[j];
            }
        }

        return argMax(output);
    }

    private double relu(double x) {
        return Math.max(0, x);
    }

    private int argMax(double[] array) {
        int index = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[index]) index = i;
        }
        return index;
    }

    public void trainOnce(TrainingSample[] samples, double learningRate) {
        for (TrainingSample sample : samples) {
            double[] input = sample.getInput();
            double[] target = sample.getOutput();

            // FORWARD
            double[] hidden = new double[hiddenSize];
            double[] output = new double[outputSize];

            for (int i = 0; i < hiddenSize; i++) {
                hidden[i] = B1[i];
                for (int j = 0; j < inputSize; j++) {
                    hidden[i] += W1[i][j] * input[j];
                }
                hidden[i] = relu(hidden[i]);
            }

            for (int i = 0; i < outputSize; i++) {
                output[i] = B2[i];
                for (int j = 0; j < hiddenSize; j++) {
                    output[i] += W2[i][j] * hidden[j];
                }
                output[i] = sigmoid(output[i]);
            }

            // BACKWARD
            double[] deltaOutput = new double[outputSize];
            for (int i = 0; i < outputSize; i++) {
                double error = output[i] - target[i];
                deltaOutput[i] = error * sigmoidDerivative(output[i]);
            }

            double[] deltaHidden = new double[hiddenSize];
            for (int i = 0; i < hiddenSize; i++) {
                double sum = 0;
                for (int j = 0; j < outputSize; j++) {
                    sum += W2[j][i] * deltaOutput[j];
                }
                deltaHidden[i] = sum * reluDerivative(hidden[i]);
            }

            for (int i = 0; i < outputSize; i++) {
                for (int j = 0; j < hiddenSize; j++) {
                    W2[i][j] -= learningRate * deltaOutput[i] * hidden[j];
                }
                B2[i] -= learningRate * deltaOutput[i];
            }

            for (int i = 0; i < hiddenSize; i++) {
                for (int j = 0; j < inputSize; j++) {
                    W1[i][j] -= learningRate * deltaHidden[i] * input[j];
                }
                B1[i] -= learningRate * deltaHidden[i];
            }
        }
    }

    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    private double sigmoidDerivative(double y) {
        return y * (1 - y);
    }

    private double reluDerivative(double y) {
        return y > 0 ? 1 : 0;
    }

}
