package neuralnet;

import neuralnet.functions.Functions;

public class NeuralNetTest {

    public static void main(String[] args) {
        NetInput n1 = new NetInput();
        NetInput n2 = new NetInput();
        NetOutput out = new NetOutput();
        NeuralNet net = new NetBuilder().start(n1).start(n2).addConnPrev(Functions.MULTIPLY, 2).addConnPrev(Functions.MULTIPLY, 2).endConnPrev(Functions.ADD, out).build();
        n1.setInput(2);
        n2.setInput(1);
        net.run();
        System.out.println("Result: " + out.getResult());
        System.out.println("Learning...");
        net.learn(new double[] {2}, new double[] {2, 1}, 0.1, 0.01);
        net.learn(new double[] {15}, new double[] {5, 3}, 0.1, 0.01);
        n1.setInput(2);
        n2.setInput(1);
        net.run();
        System.out.println("Result after learning: " + out.getResult());
        n1.setInput(5);
        n2.setInput(3);
        net.run();
        System.out.println("Control value: " + out.getResult());
        n1.setInput(10);
        n2.setInput(7);
        net.run();
        System.out.println("Control value 2: " + out.getResult());
    }
}
