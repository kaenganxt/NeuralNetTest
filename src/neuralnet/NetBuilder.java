package neuralnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import neuralnet.connections.Connection;
import neuralnet.connections.InputConnection;
import neuralnet.connections.OutputConnection;
import neuralnet.functions.INeuronFunction;
import neuralnet.neurons.InputNeuron;
import neuralnet.neurons.Neuron;
import neuralnet.neurons.OutputNeuron;

public class NetBuilder {

    private final List<InputNeuron> inputs = new ArrayList<>();
    private final List<OutputNeuron> outputs = new ArrayList<>();
    private final HashMap<Integer, List<Neuron>> hidden = new HashMap<>();
    private final List<NetInput> netIn = new ArrayList<>();
    private final List<NetOutput> netOut = new ArrayList<>();

    public NetBuilder start(NetInput input) {
        InputNeuron n = new InputNeuron();
        InputConnection conn = new InputConnection(n);
        inputs.add(n);
        input.setConnection(conn);
        netIn.add(input);
        return this;
    }

    public NetBuilder addConnAll(INeuronFunction func) {
        List<Neuron> conns = new ArrayList<>(inputs);
        for (List<Neuron> layer : hidden.values()) {
            conns.addAll(layer);
        }
        return add(func, conns);
    }

    public NetBuilder addConnAll(INeuronFunction func, int count) {
        List<Neuron> conns = new ArrayList<>(inputs);
        for (List<Neuron> layer : hidden.values()) {
            conns.addAll(layer);
        }
        for (int i = 0; i < count; i++) {
            add(func, conns);
        }
        return this;
    }

    public NetBuilder addConnPrev(INeuronFunction func, int count) {
        int highest = 0;
        for (int layer : hidden.keySet()) {
            if (layer > highest) highest = layer;
        }
        List<? extends Neuron> conns = getLayerNeurons(highest);
        for (int i = 0; i < count; i++) {
            add(func, conns);
        }
        return this;
    }

    public NetBuilder addConnPrev(INeuronFunction func) {
        int highest = 0;
        for (int layer : hidden.keySet()) {
            if (layer > highest) highest = layer;
        }
        return add(func, getLayerNeurons(highest));
    }

    public NetBuilder add(INeuronFunction func, List<? extends Neuron> conns) {
        if (conns == null || conns.isEmpty()) return this;
        int layer = getHighestLayer(conns) + 1;
        Neuron n = new Neuron(func, layer);

        for (Neuron oldN : conns) {
            new Connection(oldN, n);
        }
        if (!hidden.containsKey(layer)) {
            hidden.put(layer, new ArrayList<>());
        }
        hidden.get(layer).add(n);
        return this;
    }

    public NetBuilder end(INeuronFunction func, NetOutput output, List<? extends Neuron> conns) {
        if (conns == null || conns.isEmpty()) return this;
        int layer = getHighestLayer(conns) + 1;
        OutputNeuron n = new OutputNeuron(func, layer);
        for (Neuron oldN : conns) {
            new Connection(oldN, n);
        }
        outputs.add(n);
        OutputConnection conn = new OutputConnection(n);
        output.setConnection(conn);
        netOut.add(output);
        return this;
    }

    public NetBuilder endConnPrev(INeuronFunction func, NetOutput output) {
        int highest = 0;
        for (int layer : hidden.keySet()) {
            if (layer > highest) highest = layer;
        }
        return end(func, output, getLayerNeurons(highest));
    }

    public NetBuilder endConnPrev(INeuronFunction func, NetOutput... output) {
        int highest = 0;
        for (int layer : hidden.keySet()) {
            if (layer > highest) highest = layer;
        }
        List<? extends Neuron> n = getLayerNeurons(highest);
        for (NetOutput out : output) {
            end(func, out, n);
        }
        return this;
    }

    public NeuralNet build() {
        HashMap<Integer, List<? extends Neuron>> neurons = new HashMap<>();
        neurons.put(0, inputs);
        int highest = 0;
        for (Entry<Integer, List<Neuron>> hidd : hidden.entrySet()) {
            neurons.put(hidd.getKey(), hidd.getValue());
            if (hidd.getKey() > highest) highest = hidd.getKey();
        }
        neurons.put(highest + 1, outputs);
        return new NeuralNet(neurons, netIn, netOut);
    }

    private List<? extends Neuron> getLayerNeurons(int layer) {
        if (layer == 0) {
            return inputs;
        } else {
            return hidden.containsKey(layer) ? hidden.get(layer) : new ArrayList<>();
        }
    }

    private int getHighestLayer(List<? extends Neuron> neurons) {
        int highest = 0;
        for (Neuron n : neurons) {
            if (n.getLayer() > highest) highest = n.getLayer();
        }
        return highest;
    }
}
