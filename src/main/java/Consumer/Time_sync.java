package Consumer;

public class Time_sync {

    public static long initTime = System.currentTimeMillis();
    public static long hourDuration = 20000;

    public static long getCurrentHour() {
        return (System.currentTimeMillis()-initTime)/hourDuration;
    }

    public static long calcMillisTillNextHour() {
        return hourDuration - (System.currentTimeMillis()-initTime)%hourDuration;
    }
}