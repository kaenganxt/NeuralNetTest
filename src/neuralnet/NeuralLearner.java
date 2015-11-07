package neuralnet;

import java.util.ArrayList;
import java.util.List;
import neuralnet.connections.Connection;
import neuralnet.neurons.Neuron;

public class NeuralLearner {

    private final double[] expected;
    private final double[] inputs;
    private final List<NetOutput> output;
    private final List<NetInput> input;
    private final NeuralNet net;
    private final double allowedError;
    private final double learnRate;

    private int resumeCount = -1;

    public NeuralLearner(NeuralNet net, double[] expected, double[] inputs, List<NetInput> input, List<NetOutput> output, double learnRate, double allowedError) {
        this.expected = expected;
        this.inputs = inputs;
        this.input = input;
        this.output = output;
        this.net = net;
        this.learnRate = learnRate;
        this.allowedError = allowedError;
        doTry(0);
        while (resumeCount >= 0) { //Prevent stack overflow
            int count = resumeCount;
            resumeCount = -1;
            doTry(count);
        }
    }

    private void doTry(int count) {
        if (count == 3400) {
            resumeCount = count;
            return;
        }
        double beginError = getError();
        System.out.println("Current error at learning step " + count + ": " + beginError);
        if (beginError <= allowedError) return;
        for (NetOutput out : output) {
            double exp = expected[output.indexOf(out)];
            double localError = getError(out.getResult(), exp);
            bestModification(out.getConn().getFrom(), localError, exp);
        }
        doTry(count + 1);
    }

    private void bestModification(Neuron n, double currentError, double exp) {
        for (Connection conn : n.getInputs()) {
            if (isBetter(conn, currentError, n, conn.getWeight() + learnRate, exp)) {
                conn.setWeight(conn.getWeight() + learnRate);
            } else if (isBetter(conn, currentError, n, conn.getWeight() - learnRate, exp)) {
                conn.setWeight(conn.getWeight() - learnRate);
            }
        }
    }

    private boolean isBetter(Connection conn, double error, Neuron n, double newWeight, double expected) {
        double res = conn.simulateWeight(newWeight);
        List<Double> vals = new ArrayList<>();
        for (Connection co : n.getInputs()) {
            if (co == conn) vals.add(res);
            else vals.add(co.simulateWeight(co.getWeight()));
        }
        double c = n.getFunction().calc(vals);
        return getError(c, expected) < error;
    }

    private double getError(double val, double exp) {
        return Math.abs(exp - val);
    }

    private double getError() {
        double error = 0;
        double[] outputs = getOutputs();
        for (int i = 0; i < outputs.length; i++) {
            error += getError(outputs[i], expected[i]);
        }
        return error;
    }

    private double[] getOutputs() {
        test();
        double[] outputs = new double[output.size()];
        int i = 0;
        for (NetOutput out : output) {
            outputs[i] = out.getResult();
            i++;
        }
        return outputs;
    }

    private void test() {
        setInputs();
        net.run();
    }

    private void setInputs() {
        int i = 0;
        for (NetInput in : input) {
            in.setInput(inputs[i]);
            i++;
        }
    }
}
