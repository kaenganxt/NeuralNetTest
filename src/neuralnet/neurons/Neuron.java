package neuralnet.neurons;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import neuralnet.connections.Connection;
import neuralnet.functions.INeuronFunction;

public class Neuron {

    protected final HashSet<Connection> outbound = new HashSet<>();
    protected final HashSet<Connection> inbound = new HashSet<>();
    private final INeuronFunction func;
    private final int layer;

    public Neuron(INeuronFunction func, int layer) {
        this.func = func;
        this.layer = layer;
    }

    public void pulse() {
        List<Double> input = inbound.stream().map((conn) -> {
            return conn.take();
        }).collect(Collectors.toList());
        next(func.calc(input));
    }

    public int getLayer() {
        return layer;
    }

    protected void next(double output) {
        outbound.forEach((conn) -> conn.give(output));
    }

    public void addInput(Connection conn) {
        inbound.add(conn);
    }

    public void addOutput(Connection conn) {
        outbound.add(conn);
    }

    public HashSet<Connection> getInputs() {
        return inbound;
    }

    public INeuronFunction getFunction() {
        return func;
    }
}
