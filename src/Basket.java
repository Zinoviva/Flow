import java.io.*;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

//import static java.lang.System.out;

public class Basket {
    private final String[] name;  //название
    private final int[] price;  //цена
    long[] longArrInField;  // количество в лонге условие

    public void setLongArrInField(long[] longArrInField) {
        this.longArrInField = longArrInField;
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
