package kiosk;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class Settings {

    private int timeoutMillis;

    /**
     * Default Constructor. This is used whenever
     * there is an error in the settings' xml
     * file. The reason that "weird" values are
     * used is because if these default values are
     * what are actually used, they will not appear
     * to be written out in the xml file.
     */
    public Settings() {
        timeoutMillis = 30001;
    }

    public int getTimeoutMillis() {
        return timeoutMillis;
    }

    public void setTimeoutMillis(int millis) {
        this.timeoutMillis = millis;
    }

    //TODO I think this only gets used in the editor
    public boolean writeSettings() {
        try (XMLEncoder encoder = new XMLEncoder(
                new BufferedOutputStream(new FileOutputStream(new File("settings.xml"))))) {
            encoder.writeObject(this);
            return true;
        } catch (FileNotFoundException exc) {
            return false;
        }
    }

    /**
     * Reads the settings as XML from a
     * hardcoded location.
     * @return a valid settings object (i.e. never null)
     */
    public static Settings readSettings() {
        //TODO should the pathname be hardcoded?
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(new File("settings.xml"))))) {
            Object settingsObject = decoder.readObject();
            if (!(settingsObject instanceof Settings)) {
                Settings settings = new Settings(); //TODO I think this should be the fallback
                return settings;
            }
            return (Settings) settingsObject;
        } catch (FileNotFoundException exc) {
            //TODO for the survey, an error creates an errorScene. What
            //TODO should be done here?
            Settings settings = new Settings(); //TODO I think this should be the fallback
            return settings;
        }
    }
}
