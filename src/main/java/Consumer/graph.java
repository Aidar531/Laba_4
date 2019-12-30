package Consumer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class graph {

    @XmlAttribute
    private int hour;
    @XmlAttribute
    private double power;

    public graph(int hour, double power) {
        this.hour = hour;
        this.power = power;
    }
    public graph(){}
    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public double getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
