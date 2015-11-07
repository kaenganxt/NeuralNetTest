package neuralnet.functions;

import java.util.List;

public interface INeuronFunction {

    public abstract double calc(List<Double> inputs);
}
