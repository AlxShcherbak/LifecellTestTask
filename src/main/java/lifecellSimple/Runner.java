package lifecellSimple;

import com.beust.jcommander.JCommander;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by Akexi on 07.04.2016.
 */
public class Runner {
    /**
     * logger Log4j
     */
    static Logger logger = Logger.getLogger(Runner.class);
    /**
     * propertiesURL - address of db.properties file
     */
    static private String propertiesURL = null;
    /**
     * resultURL - address of result.html file
     */
    static private String resultURL = null;

    public static void main(String... args) {
        //logger settings
        logger.setLevel(Level.INFO);
        BasicConfigurator.configure();

        // Парсинг аргументов командной строки.
        JCommanderOptions options = new JCommanderOptions();
        new JCommander(options, args);

        //init port and numb threads
        propertiesURL = options.properties;
        resultURL = options.result;

        logger.info("BD properties address : " + propertiesURL);
        logger.info("result.html address : " + resultURL);

        init();
        doTestQueries();
    }

    /**
     * initing db with start values by properties
     */
    private static void init() {
        Properties properties = FileWorker.getProperties(propertiesURL);
        DBManager.init(properties);
    }

    /**
     * executing of test query and writing result.html file by resultURL that wrote in start attributes
     */
    private static void doTestQueries() {
        /*String that will be writing in file result.html*/
        StringBuilder result = new StringBuilder("<html>\n<head>\n<title>Result page</title>\n<meta charset=\"UTF-8\">\n</head>\n<body>");
        Statement statement = null;
        ResultSet rs = null;


        try {
            statement = DBManager.getConnection().createStatement();

            /* first query */
            /* Script 1 : Запрос, который возвращает данные по всем сладам со следующими информацией: название склада,
            Тип оборудования, Модель оборудования, Характеристики, Количество, Название Фирма владельца оборудования,
            номер договора, контактное лицо;*/

            /*reading and parsing of query script*/
            String[] scripts = FileWorker.readFileFromResources("lifecellSimple/testQuery1.sql").split(";");
            result.append("\n<table style=\"width:100%\" border=\"1\">");
            result.append("\n<tr>" +
                    "\n<th>Название склада</th>" +
                    "\n<th>Тип оборудования</th>" +
                    "\n<th>Модель оборудования</th>" +
                    "\n<th>Характеристики</th>" +
                    "\n<th>Количество</th>" +
                    "\n<th>Название Фирма владельца</th>" +
                    "\n<th>номер договора</th>" +
                    "\n<th>контактное лицо</th>" +
                    "\n</tr>");

            for (String st : scripts) {
                try {
                    rs = statement.executeQuery(st);
                    while (rs != null && rs.next()) {
                        result.append("\n<tr>" +
                                "\n<td>" + rs.getString("Warehouse.title") + "</td>" +
                                "\n<td>" + rs.getString("Stuff.type") + "</td>" +
                                "\n<td>" + rs.getString("Stuff.model") + "</td>" +
                                "\n<td>" + rs.getString("Stuff.specification") + "</td>" +
                                "\n<td>" + rs.getString("Records.counter") + "</td>" +
                                "\n<td>" + rs.getString("Customer.title") + "</td>" +
                                "\n<td>" + rs.getString("Customer.license") + "</td>" +
                                "\n<td>" + rs.getString("Customer.contact") + "</td>" +
                                "\n</tr>");
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                }
            }
            result.append("</table>\n");

            /* second query*/
            /* Script 2 : Запрос, который возвращает данные о там какие фирмы разместили оборудования на наших сладах и
            в каком количестве (название фирмы, количество оборудования);*/

            /*reading and parsing of query script*/
            scripts = FileWorker.readFileFromResources("lifecellSimple/testQuery2.sql").split(";");
            result.append("\n<table style=\"width:100%\" border=\"1\">");
            result.append("\n<tr>" +
                    "\n<th>Название Фирма владельца</th>" +
                    "\n<th>количество оборудования</th>" +
                    "\n</tr>");

            for (String st : scripts) {
                try {
                    rs = statement.executeQuery(st);
                    while (rs != null && rs.next()) {
                        result.append("\n<tr>" +
                                "\n<td>" + rs.getString("Customer.title") + "</td>" +
                                "\n<td>" + rs.getString("counter") + "</td>" +
                                "\n</tr>");
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                }
            }

            /* third query*/
            /* Script 3 : Запрос, который возвращает данные по всем сладам со следующими информацией: название склада,
            адрес склада, телефон склада, количество товара которое может быть размещено на складе,
            количество товара которое размещено на складе, количество товара которое можно еще разместить;*/

            /*reading and parsing of query script*/
            scripts = FileWorker.readFileFromResources("lifecellSimple/testQuery3.sql").split(";");
            result.append("\n<table style=\"width:100%\" border=\"1\">");
            result.append("\n<tr>" +
                    "\n<th>Название склада</th>" +
                    "\n<th>адрес склада</th>" +
                    "\n<th>телефон склада</th>" +
                    "\n<th>вместимость скала</th>" +
                    "\n<th>кол-во на складе</th>" +
                    "\n<th>свободного места</th>" +
                    "\n</tr>");

            for (String st : scripts) {
                try {
                    rs = statement.executeQuery(st);
                    while (rs != null && rs.next()) {
                        result.append("\n<tr>" +
                                "\n<td>" + rs.getString("Warehouse.title") + "</td>" +
                                "\n<td>" + rs.getString("Warehouse.address") + "</td>" +
                                "\n<td>" + rs.getString("Warehouse.phone") + "</td>" +
                                "\n<td>" + rs.getString("Warehouse.stuffLimit") + "</td>" +
                                "\n<td>" + rs.getString("inCounter") + "</td>" +
                                "\n<td>" + rs.getString("available") + "</td>" +
                                "\n</tr>");
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        result.append("\n</body>\n</html>");
        FileWorker.writeResultFile(result.toString(), resultURL);

    }
}
