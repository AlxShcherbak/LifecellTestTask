package lifecellSimple;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * Created by Akexi on 07.04.2016.
 */
public class FileWorker {
    static Logger logger = Logger.getLogger(FileWorker.class);

    /**
     * reading db.properties file and parsing it to properties map
     * @param url - address of db.properties file
     * @return properties map
     */
    public static Properties getProperties(String url) {
        Properties properties = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(url);
            logger.info("inputStream opened");

            // load a properties file
            properties.load(input);

            // get the property value and loget it out
            logger.info("jdbc.host = " + properties.getProperty("jdbc.host"));
            logger.info("jdbc.userlogin = " + properties.getProperty("jdbc.userlogin"));
            logger.info("jdbc.userpassword = " + properties.getProperty("jdbc.userpassword"));
            logger.info("sql.scripts = " + properties.getProperty("sql.scripts"));

        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                    logger.info("inputStream closed");
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return properties;
    }

    /**
     * reading file by it's address
     * @param url - address of file
     * @return - string representation of file
     */
    public static String readFileByAddress(String url) {
        String s = new String();
        StringBuffer sb = new StringBuffer();

        try {
            FileReader fr = new FileReader(new File(url));
            BufferedReader br = new BufferedReader(fr);

            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return sb.toString();
    }

    /**
     * reading file from resouces
     * @param url - url of file
     * @return - string representation of file
     */
    public static String readFileFromResources(String url) {
        String result = "";
        ClassLoader classLoader = FileWorker.class.getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * writing inputString into file by address
     * @param inputString - writing string (text)
     * @param url - address of file
     */
    public static void writeResultFile(String inputString, String url) {
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(url), "utf-8"));
            writer.write(inputString);
        } catch (IOException ex) {
            // report
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
    }
}
