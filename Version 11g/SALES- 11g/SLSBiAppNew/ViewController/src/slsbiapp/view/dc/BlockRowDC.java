package slsbiapp.view.dc;

public class BlockRowDC<A> {
    private Integer noOfCols;
    private A col1;
    private A col2;
    private A col3;
    private A col4;
    private A col5;
    
    public BlockRowDC(Integer noOfCols) {
        this.noOfCols = noOfCols;
    }
    public BlockRowDC(A col1,A col2,A col3,A col4,A col5){
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = col4;
        this.col5 = col5;
    }
    public void addBlock(A a){
        
    }

    public void setCol1(A col1) {
        this.col1 = col1;
    }

    public A getCol1() {
        return col1;
    }

    public void setCol2(A col2) {
        this.col2 = col2;
    }

    public A getCol2() {
        return col2;
    }

    public void setCol3(A col3) {
        this.col3 = col3;
    }

    public A getCol3() {
        return col3;
    }

    public void setCol4(A col4) {
        this.col4 = col4;
    }

    public A getCol4() {
        return col4;
    }

    public void setCol5(A col5) {
        this.col5 = col5;
    }

    public A getCol5() {
        return col5;
    }
}
