
import java.util.*;

public class SimulationTest {

    public static final String BASENAME = "Properties/MyBundle";

    public static final List<String> format = ResourceBundle.Control.FORMAT_PROPERTIES;


    public static final ClassLoader cl = ClassLoader.getSystemClassLoader();

    public static final ResourceBundle.Control rBControl = ResourceBundle.Control.getControl(format);

    public static void main(String[] args) {

        Locale requestLocale = new Locale("es","IT");
        Locale.setDefault(Locale.ROOT);

        ResourceBundle newBundle = ResourceBundle.getBundle(BASENAME,requestLocale);

        ResourceBundle result = Simulation.getResourceBundle(BASENAME, requestLocale, cl, rBControl, true, newBundle);


        String value1 = newBundle.getString("logInKey");
        System.out.println("es_IT varyantı icin var olmayan key'in degeri es dilinden getirildi: "+value1);

        Enumeration<String> keys = result.getKeys();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = result.getString(key);
            System.out.println("Keys: " + key + "\t\tValue:" + value);
        }
    }



}
