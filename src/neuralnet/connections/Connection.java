package neuralnet.connections;

import neuralnet.neurons.Neuron;

public class Connection {

    private final Neuron from;
    private final Neuron to;
    private double weight = 1;

    private double value = 0;

    public Connection(Neuron from, Neuron to) {
        this.from = from;
        this.to = to;
        if (from != null) from.addOutput(this);
        if (to != null) to.addInput(this);
    }

    public Connection(Neuron from, Neuron to, double defaultWeight) {
        this(from, to);
        weight = defaultWeight;
    }

    public void give(double in) {
        value = weight * in;
    }

    public double take() {
        return value;
    }

    public void setWeight(double w) {
        weight = w;
    }

    public Neuron getFrom() {
        return from;
    }

    public Neuron getTo() {
        return to;
    }

    public double getWeight() {
        return weight;
    }

    public double simulateWeight(double newWeight) {
        return (value / weight) * newWeight;
    }
}
