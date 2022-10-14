import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Main {
    static File file = new File("basket.txt"); //создание файла

    public static void main(String[] args) throws FileNotFoundException {
        Basket basket;  //корзинка
        basket = Basket.loadFromTxtFile(file);   //восстанавливаем корзину

        if (basket == null) {  //если корзина равна 0
            int[] prices = {100, 200, 300};  //цена
            String[] products = {"Хлеб", "Яблоки", "Молоко"};  //товар
            basket = new Basket(products, prices);  //в корзину продукты и цену
        }

        Scanner scanner = new Scanner(System.in);
        basket.startWindow(); //вызываем стартовое окно Список возможных товаров для покупки

        while (true) {
            System.out.println("Выберите товар(числом) и количество через пробел или введите `end`");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }

            String[] parts = input.split(" ");

            int productNumber = Integer.parseInt(parts[0]) - 1;  //превращаем значение в число
            int productCount = Integer.parseInt(parts[1]);

            basket.addToCart(productNumber, productCount);
        }

        basket.saveTxt(file);  //метод сохранения корзины в текстовый файл

        basket.printCart();  //показываем корзину
    }

}
