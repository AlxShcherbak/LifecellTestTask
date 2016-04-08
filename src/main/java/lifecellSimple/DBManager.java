package lifecellSimple;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Properties;

/**
 * Created by Akexi on 07.04.2016.
 */
public class DBManager {

    static Logger logger = Logger.getLogger(DBManager.class);
    /**
     * properties of db
     */
    private static Properties properties = null;
    /**
     * DBManager instance
     */
    private static DBManager instance = null;
    /**
     * db connection
     */
    private static Connection connection = null;

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
    }

    public static Connection getConnection() {
        if (connection == null && instance != null && properties != null) {
            connectToDB();
        }
        return connection;
    }

    /**
     * connecting to DB by properties in DBManager
     */
    private static void connectToDB() {
        if (properties != null) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            String driver = "com.mysql.jdbc.Driver";
            try {
                Class.forName(driver);
                logger.info("driver found");
            } catch (ClassNotFoundException e) {
                logger.error(e.getMessage());
            }


            String host = properties.getProperty("jdbc.host"),
                    userlogin = properties.getProperty("jdbc.userlogin"),
                    userpassword = properties.getProperty("jdbc.userpassword");

            try {
                connection = DriverManager.getConnection("jdbc:mysql:" + host, userlogin, userpassword);
                logger.info("connection establish, \thost = " + host + "\tlogin = "
                        + userlogin + "\tpassword = " + userpassword);
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * connecting to db by properties
     *
     * @param inputProperties - properties
     */
    public static void setProperties(Properties inputProperties) {
        if (instance == null) {
            getInstance();
        }
        instance.properties = inputProperties;
        connectToDB();
    }

    /**
     * initing db with start values by properties
     *
     * @param inputProperties - init properties
     */
    public static void init(Properties inputProperties) {
        setProperties(inputProperties);

        String[] scripts = FileWorker.readFileByAddress(properties.getProperty("sql.scripts")).split(";");
        Statement statement = null;
        try {
            statement = connection.createStatement();
            for (String s : scripts) {
                try {
                    statement.execute(s);
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
