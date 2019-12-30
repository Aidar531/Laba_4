package Consumer;

public class Energy {
    public int energyToBuyLeft;
    public int installedPower;

    public int getInstalledPower() {
        return installedPower;
    }

    public void setInstalledPower(int installedPower) {
        this.installedPower = installedPower;
    }

    public int getEnergy() {
        return energyToBuyLeft;
    }

    public void setEnergy(int energy) {
        this.energyToBuyLeft = energy;
    }
}
