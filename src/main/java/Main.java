

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Scanner;

class Main {

    public static void main(String[] args) throws FileNotFoundException {
        ClientLog cl = new ClientLog();
        Basket basket = Basket.getFromJson();  //баскет получаем из джейсона

        //при старте программы загружайте корзину десериализацией из json-а из файла basket.json
        Scanner scanner = new Scanner(System.in);
        basket.startWindow(); //вызываем стартовое окно Список возможных товаров для покупки

        while (true) {
            System.out.println("Выберите товар(числом) и количество через пробел или введите `end`");
            String input = scanner.nextLine();  //считываем ввод
            if ("end".equals(input)) {  //если введено энд выходим
                break;
            }

            String[] parts = input.split(" "); //разделем по пробелу

            int productNumber = Integer.parseInt(parts[0]) - 1;  //превращаем значение в число
            int productCount = Integer.parseInt(parts[1]);

            basket.addToCart(productNumber, productCount);
            cl.log(++productNumber, productCount);
        }
        //вместо вызова метода saveTxt сериализуйте корзину в json-формате в файл basket.json
        JSONObject obj = basket.getJson();

        try (FileWriter file = new FileWriter("json_dir\\basket.json")) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        cl.exportAsCSV(new File("log.csv"));

        basket.printCart();  //показываем корзину
    }

}
