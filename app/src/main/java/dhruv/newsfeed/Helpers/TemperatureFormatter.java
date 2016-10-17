package dhruv.newsfeed.Helpers;

/**
 * Created by dhruv on 6/9/16.
 */
public class TemperatureFormatter {
    public static String format(float temperature) {
        return String.valueOf(Math.round(temperature)) + "°";
    }
}
