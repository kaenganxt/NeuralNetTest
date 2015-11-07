package neuralnet;

import neuralnet.connections.OutputConnection;

public class NetOutput {

    private OutputConnection conn;
    private double value;

    public void setConnection(OutputConnection conn) {
        this.conn = conn;
    }

    public void pulse() {
        value = conn.take();
    }

    public double getResult() {
        return value;
    }

    public OutputConnection getConn() {
        return conn;
    }
}
