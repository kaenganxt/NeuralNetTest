package neuralnet.functions;

import java.util.List;

class MultiplyingFunction implements INeuronFunction {

    @Override
    public double calc(List<Double> inputs) {
        double output = 1;
        for (double input : inputs) {
            output *= input;
        }
        return output;
    }

}
