import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClientLog {

    ArrayList<String> journal = new ArrayList<>(); //массив строк
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");


    public void log(int productNum, int amount) {  //покупатель добавил покупку, то это действие должно быть там сохранено
        journal.add("Пользователь добавил продукт под номером " + productNum + " в количестве " + amount + " в " + df.format(new Date()));
    }
    public void exportAsCSV(File txtFile) {  // для сохранения всего журнала действия в файл в формате csv
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(txtFile, true))
        ) {
            csvWriter.writeNext(journal.toArray(new String[]{})); //преобразуем строки(список) в массив
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
