package Generation;

import jade.core.AID;

public class Data {
    public Data(double myPrice) {
        this.myPrice = myPrice;
    }
    public AID topic;

    public double myPrice;

    public boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public AID getTopic() {
        return topic;
    }

    public void setTopic(AID topic) {
        this.topic = topic;
    }

    public double getMyPrice() {
        return myPrice;
    }

    public void setMyPrice(double myPrice) {
        this.myPrice = myPrice;
    }
}
