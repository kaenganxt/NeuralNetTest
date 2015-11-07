package neuralnet.connections;

import neuralnet.neurons.InputNeuron;

public class InputConnection extends Connection {

    public InputConnection(InputNeuron to) {
        super(null, to, 1);
    }

}
