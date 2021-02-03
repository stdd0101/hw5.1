import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);
        String json = listToJson(list);
        Type listType = new TypeToken<List<Employee>>() {}.getType();
    }

    private static List<Employee> parseCSV(String[] columnMapping, String fileName) throws FileNotFoundException {
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping("id", "firstName", "lastName", "country", "age");
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            List<Employee> file = csv.parse();
            file.forEach(System.out::println);
            return file;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String listToJson(List<Employee> list) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //System.out.println(gson.toJson(list));
        //gson.toJson(list);
        //gson.toJson(list, new FileWriter("C://Users//stdd0//IdeaProjects//hw51//result.json"));
        writeString(gson, list);
        return null;
    }

    private static void writeString(Gson gson, List<Employee> list) throws IOException {
        FileWriter filewriter = new FileWriter("C://Users//stdd0//IdeaProjects//hw51//result.json");
        filewriter.write(gson.toJson(list));
        filewriter.close();
    }

}
