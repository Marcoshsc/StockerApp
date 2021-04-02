package br.ufop.stocker.general;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PropertiesManager {

    /**
     * Singleton instance
     */
    private static PropertiesManager Instance;

    /**
     * Map of (key,value) properties that stores all the properties inserted in the file.
     */
    private final Map<String, String> properties;


    private PropertiesManager() throws PropertyError {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("app.properties");
        if(url == null)
            throw new PropertyError("No app.properties file found.");
        File file = new File(url.getFile());
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            this.properties = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("=");
                properties.put(values[0], values[1]);
            }
            reader.close();
        } catch(IOException exc) {
            throw new PropertyError(exc.getMessage());
        }
    }

    public static PropertiesManager GetInstance() throws PropertyError {
        if(Instance == null)
            Instance = new PropertiesManager();
        return Instance;
    }

    /**
     * Returns a property value, given it's key.
     * @param key the property name.
     * @return a property value, given it's key, null if not present.
     */
    public String getProperty(String key) {
        return properties.get(key);
    }
}
