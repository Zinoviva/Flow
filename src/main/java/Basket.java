import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.*;

public class Basket {
    private final String[] name;  //название
    private final int[] price;  //цена
    private long[] longArrInField;  // количество в лонг условие

    public void setLongArrInField(long[] longArrInField) {
        this.longArrInField = longArrInField;
    }

    public long[] getLongArrInField() {
        return longArrInField;
    }

    public Basket(String[] name, int[] price) {
        this.name = name;
        this.price = price;
        this.longArrInField = new long[name.length];  //массив делаем равный длинне наименований
        Arrays.fill(longArrInField, 0); //заполняем массив нулями
    }

    public void startWindow() {  //перенесли с мейна стартовую часть, должно быть в баскете
        System.out.println("Список возможных товаров для покупки:");
        for (int i = 0; i < name.length; i++) {
            System.out.println((name[i]) + " " + price[i] + " руб/шт");
        }
    }

    public void addToCart(int productNum, int amount) {    //метод добавления штук и номера в корзину
        longArrInField[productNum] += amount;
    }

    public void printCart() {  //метод вывода на экран покупательской корзины
        int sumProducts = 0;  //сумма продуктов
        System.out.println("Ваша корзина:");

        for (int i = 0; i < name.length; i++) {
            if (longArrInField[i] != 0) {
                System.out.println(name[i] + " " + longArrInField[i] + " " + price[i] * longArrInField[i] + " рублей");
            }
            long priceOfProduct = price[i] * longArrInField[i];
            sumProducts += priceOfProduct;
        }
        System.out.println("Итого " + sumProducts + " рублей");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length; i++) {
            sb.append(name[i]).append(" ").append(longArrInField[i]).append(" ").append(price[i] * longArrInField[i]).append("\n");
        }
        return sb.toString();
    }

    public JSONObject getJson(){
        JSONObject result = new JSONObject();  //создаем объект итога
        JSONArray names = new JSONArray();  //создаем три массива для цены,наименования и штук
        JSONArray prices = new JSONArray();
        JSONArray amounts = new JSONArray();

        //наименование
        names.addAll(Arrays.asList(name));  //пробегаемся по массиву имени с добавлением
        for(int p : price){
           prices.add(p);
        }
        for(long a : longArrInField){
            amounts.add(a);
        }
        result.put("name", "Basket");  //добавляем в объект
        result.put("names", names);
        result.put("prices", prices);
        result.put("amounts", amounts);

        return result; //возвращаем объект
    }


    public static Basket getFromJson(){
        JSONParser parser = new JSONParser();  //создаем объект для парсинга
        try {
            JSONObject object = (JSONObject) parser.parse(new FileReader("json_dir\\basket.json"));  //парсим файл

            JSONArray prices = (JSONArray) object.get("prices");  //берем массив по цене пробегаемся по нему и преобразуем в инты
            int[] pricesArray = new int[prices.size()];
            for (int i = 0; i < prices.size(); i++) {
                pricesArray[i] = Integer.parseInt(prices.get(i).toString());
            }
            JSONArray names = (JSONArray) object.get("names"); //берем массив по имени пробегаемся по нему и преобразуем в стринги
            String[] namesArray = new String[prices.size()];
            for (int i = 0; i < names.size(); i++) {
                namesArray[i] = names.get(i).toString();
            }
            JSONArray amounts = (JSONArray) object.get("amounts"); //берем массив по количеству пробегаемся по нему и преобразуем в лонги
            long[] amountsArray = new long[amounts.size()];
            for (int i = 0; i < amounts.size(); i++) {
                amountsArray[i] = Long.parseLong(amounts.get(i).toString());
            }

            Basket basket = new Basket(namesArray,pricesArray); //реализуем корзину
            basket.setLongArrInField(amountsArray); //устанавливаем количество
            return basket;  //вернем корзину
        } catch (IOException | ParseException e) {
            System.out.println("Не найден файл.");
            int[] prices = {100, 200, 300};  //если файл не найден создаем файл с пустыми данными с данным содержанием
            String[] products = {"Хлеб", "Яблоки", "Молоко"};
            return new Basket(products, prices);  //вернем корзину
        }
    }

    public void saveTxt(File textFile) throws FileNotFoundException { //метод сохранения корзины в текстовый файл
        try (PrintWriter out = new PrintWriter(textFile)) {  //заводим сохранялку
            //в условии сделана проверка каждой строки отдельно
            for (String s : name) {  //наименование
                out.print(s + " ");
            }
            out.print("\n");
            for (int i : price) {  //цена
                out.print(i + " ");
            }
            out.print("\n");
            for (long e : longArrInField) {  //количество
                out.print(e + " ");
            }

            //более красивый вариант с заменой скобок на пустоту
//            out.println(Arrays.toString(name).replace("[", "").replace("]", ""));
//            out.println(Arrays.toString(price).replace("[", "").replace("]", ""));
//            out.println(Arrays.toString(longArrInField).replace("[", "").replace("]", ""));+
        }
    }

    static Basket loadFromTxtFile(File textFile) {
        //метод восстановления объекта корзины из текстового файла, в который ранее была она сохранена;
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(textFile)); //считыватель
            Scanner scanner = new Scanner(in);  //считать входной поток
//считываем строки
            String[] names = scanner.nextLine().trim().split(" ");
            String[] sPrices = scanner.nextLine().trim().split(" ");
            String[] sAmounts = scanner.nextLine().trim().split(" ");  //+


            int[] prices = new int[sPrices.length];  //переводим строки в инты
            for (int i = 0; i < sPrices.length; i++) {
                prices[i] = Integer.parseInt(sPrices[i]);
            }

            long[] amounts = new long[sAmounts.length];  //+ //переводим строки в лонги
            for (int i = 0; i < sAmounts.length; i++) {
                amounts[i] = Long.parseLong(sAmounts[i]);
            }
            Basket b = new Basket(names, prices);  //+  //создаем новый объект корзины после считывания
            b.setLongArrInField(amounts);  //+

            return b;  //показали корзину
        } catch (FileNotFoundException | NoSuchElementException | NumberFormatException e) {  //ловим ошибки
            return null;
        }

    }

}
