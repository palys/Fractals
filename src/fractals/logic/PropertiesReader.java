package fractals.logic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
	
	private final static String PROP_FILE_PATH = "config.properties";
	
	private final InputStream inStream = getClass().getClassLoader().getResourceAsStream(PROP_FILE_PATH);
	
	private Properties prop = new Properties();
	
	public PropertiesReader() {
		if (inStream != null) {
			try {
				prop.load(inStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getPropertyValue(String key) {
		
		String value =  prop.getProperty(key);
		
		if (value == null) {
			value = "";
		}
		
		return value;
	}
}
