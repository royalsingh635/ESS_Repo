package slsbiapp.view.dc;

import java.util.ArrayList;

public class BlockDC<A> {
    private A data;
    public BlockDC(A data) {
        this.data = data;
    }
    /**
     * @param data
     */
    public void setData(A data) {
        this.data = data;
    }

    public A getData() {
        return data;
    }
}
