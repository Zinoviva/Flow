import java.io.*;
import java.util.Scanner;

import static java.lang.System.out;

public class Basket {
    protected String[] name;  //название
    protected int[] price;  //цена
    protected long[] longArrInField;  //лонговый массив нашей корзины
    File file = new File("basket.txt");

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public int[] getPrice() {
        return price;
    }

    public void setPrice(int[] price) {
        this.price = price;
    }

    public Basket(String[] name, int[] price) {
        this.name = name;
        this.price = price;
    }

    public void addToCart(int productNum, int amount) {  //метод добавления штук и номера продукта в корзину
        longArrInField[productNum] += amount;
    }

    public void printCart() {  //метод вывода на экран покупательской корзины
        out.println("Ваша корзина:" + longArrInField);
    }

    public void saveTxt(File textFile) throws FileNotFoundException {  //метод сохранения корзины в текстовый файл
        try (PrintWriter out = new PrintWriter(file);) {
            out.println(getName());
            out.println(getPrice());
            for (long e : longArrInField)
                out.print(e + " ");
        }
        out.close(); //закрываем поток
    }

    static Basket loadFromTxtFile(File textFile) {  //метод восстановления объекта корзины из текстового файла, в который ранее была она сохранена;
        InputStreamReader in = new FileInputStream(file);
        Scanner scanner = new Scanner(in);  //считать входной поток
        String [] box = scanner.nextLine().trim().split(" ");
        int price = Integer.parseInt(scanner.nextLine());
        int amounts = Integer.parseInt(scanner.nextLine());


        return null;
    }
}
