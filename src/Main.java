import java.util.Scanner;

class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String[] products = {"Хлеб", "Яблоки", "Молоко"};  //продукт
        int[] prices = {100, 200, 300};  //цена
        int[] backet = new int[3];  //присвоение продукту ячейки
        int[] basket = {0, 0, 0};

        System.out.println("Список возможных товаров для покупки:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((products[i]).toString() + " " + prices[i] + " руб/шт");
        }

        int productNumber = 0;  //для номера продукта
        int productCount = 0;   //для количества
        int currentPrice = prices[productNumber];
        while (true) {
            System.out.println("Выберите товар(числом) и количество через пробел или введите `end`");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }

            String[] parts = input.split(" ");
            productNumber = Integer.parseInt(parts[0]) - 1;  //превращаем значение в число
            productCount = Integer.parseInt(parts[1]);    //количество в число
            backet[productNumber] = productCount;  //в каждую ячейку ложу количество товара
            basket[productNumber] += productCount;
        }

        int sumProducts = 0;
        System.out.println("Ваша корзина:");

        for (int i = 0; i < products.length; i++) {
            if (backet[i] != 0) {
                System.out.println(products[i] + " " + productCount + " " + prices[i] * backet[i] + "рублей");
            }
            int priceOfProduct = backet[i] * prices[i];
            sumProducts += priceOfProduct;
        }
        System.out.println("Итого" + " " + sumProducts);
    }

}
