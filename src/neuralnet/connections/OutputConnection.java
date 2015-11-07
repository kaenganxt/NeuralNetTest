package neuralnet.connections;

import neuralnet.neurons.OutputNeuron;

public class OutputConnection extends Connection {

    public OutputConnection(OutputNeuron from) {
        super(from, null, 1);
    }

}
