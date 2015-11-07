package neuralnet.neurons;

import neuralnet.connections.Connection;
import neuralnet.connections.OutputConnection;
import neuralnet.functions.INeuronFunction;

public class OutputNeuron extends Neuron {

    private OutputConnection out;

    public OutputNeuron(INeuronFunction func, int layer) {
        super(func, layer);
    }

    @Override
    public void addOutput(Connection conn) {
        assert(conn instanceof OutputConnection);
        out = (OutputConnection) conn;
    }

    @Override
    public void next(double input) {
        out.give(input);
    }
}
