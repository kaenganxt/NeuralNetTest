package neuralnet;

import neuralnet.connections.InputConnection;

public class NetInput {

    private InputConnection conn;
    private boolean hasInput = false;

    public void setConnection(InputConnection conn) {
        this.conn = conn;
    }

    public void setInput(double value) {
        if (conn != null) {
            conn.give(value);
            hasInput = true;
        }
    }

    public boolean hasInput() {
        boolean res = hasInput;
        hasInput = false;
        return res;
    }
}
