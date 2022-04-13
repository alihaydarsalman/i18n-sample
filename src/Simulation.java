
import java.io.IOException;
import java.util.*;
public class Simulation {


    public static ResourceBundle getResourceBundle(String baseName, Locale targetLocale, ClassLoader classLoader,
                                                    ResourceBundle.Control rBControl, boolean expired, ResourceBundle resourceBundle) {

        List<Locale> candidateLocales = rBControl.getCandidateLocales(baseName, targetLocale);

        if (candidateLocales == null) {
            throw new IllegalArgumentException("Candidate locales have not be null.");
        }

        List<String> formats = rBControl.getFormats(baseName);

        if (ResourceBundle.Control.FORMAT_PROPERTIES != formats) {
            throw new IllegalArgumentException();
        }

        ResourceBundle ret = null;
        ResourceBundle currentBundle = null;
        ResourceBundle bundle = null;

        for (Locale locale : candidateLocales) {
            for (String format : formats) {
                try {
                    if (expired) {
                        bundle = rBControl.newBundle(baseName,locale,format,classLoader,true);
                    }
                    else {
                        try {
                            bundle = rBControl.newBundle(baseName, locale, format, classLoader, false);
                        }
                        catch (IllegalArgumentException e) {
                            // do nothing
                        }
                    }
                }
                catch (IllegalAccessException e) {
                    // do nothing
                }
                catch (InstantiationException e) {
                    // do nothing
                }
                catch (IOException e) {
                    // do nothing
                }

                if (bundle != null) {
                    if (currentBundle != null) {
                        currentBundle.setParent(bundle);
                        currentBundle = bundle;
                    }
                    else {
                        if (ret == null) {
                            ret = bundle;
                            currentBundle = ret;
                        }
                    }
                }
                if (bundle != null) {
                    break;
                     }
                }
            }
            if ((ret == null) || (Locale.ROOT.equals(ret.getLocale())) && (!(candidateLocales.size() == 1 && candidateLocales.contains(Locale.ROOT)))) {

                Locale nextLocale = rBControl.getFallbackLocale(baseName, targetLocale);

                if (nextLocale != null) {
                    ret = getResourceBundle(baseName, nextLocale, classLoader, rBControl, expired, resourceBundle);
                }
            }

        return ret;
    }


}