package alliness.zzone.core.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {


    private static final String path = Dir.RESOURCES + "/application.properties";
    private        Properties   properties;
    private static ConfigLoader instance;

    private ConfigLoader() {
        properties = new Properties();
        try {
            properties.load(new FileReader(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(Object property) {
        if (instance == null) {
            instance = new ConfigLoader();
        }
        return (String) instance.properties.get(property);
    }


}
