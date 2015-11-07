package neuralnet.neurons;

import neuralnet.connections.Connection;
import neuralnet.connections.InputConnection;

public class InputNeuron extends Neuron {

    private InputConnection in;

    public InputNeuron() {
        super(null, 0);
    }

    @Override
    public void addInput(Connection in) {
        assert(in instanceof InputConnection);
        this.in = (InputConnection) in;
    }

    @Override
    public void pulse() {
        next(in.take());
    }
}
