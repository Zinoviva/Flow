import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Main {

    public static void main(String[] args) throws FileNotFoundException {
        ClientLog cl = new ClientLog();  //создаем клиентлог
        Basket basket = Basket.getFromJson();  //баскет получаем из джсон

        //задача-при старте программы загружайте корзину десериализацией из json-а из файла basket.json
        Scanner scanner = new Scanner(System.in);  //заводим сканер
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

        //задача-вместо вызова метода saveTxt сериализуйте корзину в json-формате в файл basket.json
        JSONObject obj = basket.getJson();  //получаем объект джсон

        try (FileWriter file = new FileWriter("json_dir\\basket.json")) {
            file.write(obj.toJSONString());  //в стринги
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        cl.exportAsCSV(new File("log.csv"));

        basket.printCart();  //показываем корзину
    }

}
