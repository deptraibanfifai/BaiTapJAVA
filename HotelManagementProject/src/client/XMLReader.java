package client;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;

import java.io.File;

public class XMLReader {
    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;

    public static void main(String[] args) {
        readConfig("config.xml");
        System.out.println(" Database URL: " + dbUrl);
        System.out.println(" User: " + dbUser);
        System.out.println(" Password: " + dbPassword);
    }

    public static void readConfig(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println(" File not found: " + filePath);
                return;
            }

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            dbUrl = doc.getElementsByTagName("database").item(0).getTextContent();
            dbUser = doc.getElementsByTagName("user").item(0).getTextContent();
            dbPassword = doc.getElementsByTagName("password").item(0).getTextContent();

            System.out.println(" XML Config Loaded:");
            System.out.println(" Database: " + dbUrl);
            System.out.println(" User: " + dbUser);
            System.out.println(" Password: " + dbPassword);

            LogClient.send(" Config file read at " + new java.util.Date());
        } catch (Exception e) {
            System.out.println(" Error reading XML config:");
            e.printStackTrace();
        }
    }

    // Getter methods
    public static String getDbUrl() {
        return dbUrl;
    }

    public static String getDbUser() {
        return dbUser;
    }

    public static String getDbPassword() {
        return dbPassword;
    }
}
