package barcodeprofileapp.model.service;

public class Attribute implements Comparable<Attribute> {


    private int no;
    private int attId;
    private String attName;
    private int attLength;
    private int startPos;
    private int endPos;

    public Attribute() {
        super();
    }

    public Attribute(int attId) {
        this.attId = attId;
    }

    public Attribute(int attId, String attName, int attLength) {
        //     super();
        this.attId = attId;
        this.attName = attName;
        this.attLength = attLength;
    }

    public void setAttId(int attId) {
        this.attId = attId;
    }

    public int getAttId() {
        return attId;
    }

    public void setAttName(String attName) {
        this.attName = attName;
    }

    public String getAttName() {
        return attName;
    }

    public void setAttLength(int attLength) {
        this.attLength = attLength;
    }

    public int getAttLength() {
        return attLength;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    public int getStartPos() {
        return startPos;
    }

    public void setEndPos(int endPos) {
        this.endPos = endPos;
    }

    public int getEndPos() {
        return endPos;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Attribute) {
            Attribute ab = (Attribute) obj;
            /*  if (ab.getNo() == this.getNo())
                return true; */
            if (ab.getAttId() == this.getAttId())
                return true;
        } else if (obj instanceof Number) {
            int x = (Integer) obj;
            if (x == this.no) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(Attribute o) {
        return this.getNo() - o.getNo();
    }
}
