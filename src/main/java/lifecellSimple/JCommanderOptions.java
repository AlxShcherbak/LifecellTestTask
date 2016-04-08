package lifecellSimple;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.internal.Lists;

import java.util.List;

/**
 * Created by AlxEx on 25.12.2015.
 *
 * @author AlxEx - Alex Shcherbak
 */
public class JCommanderOptions {
    /**
     * лист неопознаных аргументов
     */
    @Parameter
    public List<String> parameters = Lists.newArrayList();

    /**
     * аргумент запуска программмы, путь к файлу db.properties
     */
    @Parameter(names = "-DBproperties", description = "BD properties address")
    public String properties = "db.properties"; // default value db.properties

    /**
     * аргумент запуска программы, путь к файлу с результатами result.html
     */
    @Parameter(names = "-result", description = "result.html address")
    public String result = "result.html"; // default value result.html

}
