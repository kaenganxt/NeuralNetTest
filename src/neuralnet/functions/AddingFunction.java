package neuralnet.functions;

import java.util.List;

class AddingFunction implements INeuronFunction {

    @Override
    public double calc(List<Double> inputs) {
        double output = 0;
        for (double input : inputs) {
            output += input;
        }
        return output;
    }

}
