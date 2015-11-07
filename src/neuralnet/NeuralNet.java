package neuralnet;

import java.util.HashMap;
import java.util.List;
import neuralnet.neurons.Neuron;

public class NeuralNet {

    private final HashMap<Integer, List<? extends Neuron>> neurons;
    private final List<NetInput> input;
    private final List<NetOutput> output;

    public NeuralNet(HashMap<Integer, List<? extends Neuron>> neurons, List<NetInput> in, List<NetOutput> out) {
        this.neurons = neurons;
        this.input = in;
        this.output = out;
    }

    public void run() {
        if (!checkInputs()) {
            throw new NeuralNetException("Not all NetInputs have been initialized! Aborting...");
        }
        for (Integer layer : neurons.keySet()) {
            System.out.println("Running layer " + layer);
            runLayer(layer);
        }
        for (NetOutput o : output) {
            o.pulse();
        }
        System.out.println("Output written to NetOutput objects!");
    }

    public void learn(double[] expected, double[] inputs, double allowedError, double learnRate) {
        assert(input.size() == inputs.length);
        assert(output.size() == expected.length);
        new NeuralLearner(this, expected, inputs, input, output, learnRate, allowedError);
    }

    private void runLayer(int layer) {
        for (Neuron n : neurons.get(layer)) {
            n.pulse();
        }
    }

    private boolean checkInputs() {
        for (NetInput i : input) {
            if (!i.hasInput()) return false;
        }
        return true;
    }
}
