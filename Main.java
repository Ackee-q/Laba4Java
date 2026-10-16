package lab4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Головний клас для запуску та демонстрації Лабораторної роботи №4 (2-й рівень).
 *
 * Дисципліна: Кросплатформене програмування
 * Тема: Робота із файлами
 * Рівень: 2-й рівень
 */
public class Main {

    private static final String DEFAULT_FILE_NAME = "1.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileManager fileManager = new FileManager();

        System.out.println("======================================================================");
        System.out.println("       ЛАБОРАТОРНА РОБОТА №4: КРОСПЛАТФОРМЕНЕ ПРОГРАМУВАННЯ           ");
        System.out.println("                  ТЕМА: РОБОТА ІЗ ФАЙЛАМИ В JAVA                      ");
        System.out.println("                        ЗАВДАННЯ 2-Й РІВЕНЬ                           ");
        System.out.println("======================================================================\n");

        try {
            // -------------------------------------------------------------
            // ЕТАП 1: Генерація випадкових значень (символи та цифри)
            // -------------------------------------------------------------
            System.out.println(">>> [ПІДГОТОВКА] Генерація випадкових значень (символи та цифри):");
            int elementsCount = 10;
            int stringLength = 5;
            ArrayList<String> initialCollection = fileManager.generateRandomData(elementsCount, stringLength);
            System.out.printf("Згенеровано %d елементів у колекцію ArrayList<String>:%n", elementsCount);
            for (int i = 0; i < initialCollection.size(); i++) {
                System.out.printf("  [%2d] %s%n", (i + 1), initialCollection.get(i));
            }
            System.out.println();

            // -------------------------------------------------------------
            // ЗАВДАННЯ 1-й РІВЕНЬ:
            // -------------------------------------------------------------
            System.out.println("----------------------------------------------------------------------");
            System.out.println("            ВИКОНАННЯ ЗАВДАНЬ 1-ГО РІВНЯ (Пункти 1 - 4)               ");
            System.out.println("----------------------------------------------------------------------");

            // Пункт 1: Програма створює файл із назвою 1.txt
            System.out.println("\n>>> [1-й рівень, Пункт 1] Створення файлу '1.txt':");
            File file = fileManager.createFile(DEFAULT_FILE_NAME);

            // Пункт 2: Зберігає вміст колекції у створеному файлі, кожен новий елемент з нової стрічки
            System.out.println("\n>>> [1-й рівень, Пункт 2] Збереження колекції у файл '1.txt':");
            fileManager.saveCollectionToFile(file, initialCollection);

            // Пункт 3: Реалізувати метод читання із цього файлу
            System.out.println("\n>>> [1-й рівень, Пункт 3] Читання вмісту з файлу '1.txt' (FileReader + BufferedReader):");
            ArrayList<String> readList = fileManager.readFromFile(file);
            System.out.println("Прочитаний вміст файлу:");
            for (int i = 0; i < readList.size(); i++) {
                System.out.printf("  Рядок %2d: %s%n", (i + 1), readList.get(i));
            }

            // Пункт 4: Вивести у консоль кількість стрічок у файлі для читання
            System.out.println("\n>>> [1-й рівень, Пункт 4] Кількість стрічок у файлі:");
            fileManager.printLineCount(file);

            // -------------------------------------------------------------
            // ЗАВДАННЯ 2-й РІВЕНЬ:
            // -------------------------------------------------------------
            System.out.println("\n----------------------------------------------------------------------");
            System.out.println("            ВИКОНАННЯ ЗАВДАНЬ 2-ГО РІВНЯ (Пункти 1 - 4)               ");
            System.out.println("----------------------------------------------------------------------");
            System.out.println("Пункт 1: Усі завдання 1-го рівня виконано успішно (див. вище).\n");

            // Автоматична демонстрація двох випадків пошуку (успішний пошук та помилка):
            System.out.println(">>> [ДЕМОНСТРАЦІЯ] Автоматичний тест пошуку (Пункти 2 - 4):");

            // Випадок А: стрічка існує
            String existingTarget = initialCollection.get(2); // 3-й елемент
            System.out.printf("1. Тест пошуку наявної стрічки '%s':%n", existingTarget);
            fileManager.searchLineInFile(file, existingTarget);
            System.out.println();

            // Випадок Б: стрічка відсутня (повідомлення про помилку)
            String nonExistingTarget = "NOT_EXIST_XYZ";
            System.out.printf("2. Тест пошуку відсутньої стрічки '%s':%n", nonExistingTarget);
            fileManager.searchLineInFile(file, nonExistingTarget);
            System.out.println();

            // Пункти 2 - 4: Інтерактивний режим користувача
            System.out.println(">>> [2-й рівень, Пункти 2 - 4] Інтерактивний пошук користувачем:");
            boolean searchMore = true;
            while (searchMore) {
                System.out.print("\nВведіть стрічку для пошуку у файлі 1.txt (або 'exit' для виходу): ");
                String inputLine = scanner.nextLine().trim();

                if (inputLine.equalsIgnoreCase("exit") || inputLine.equalsIgnoreCase("вихід")) {
                    searchMore = false;
                    System.out.println("Пошук завершено.");
                } else if (inputLine.isEmpty()) {
                    System.out.println("Введено порожній рядок. Спробуйте ще раз.");
                } else {
                    fileManager.searchLineInFile(file, inputLine);
                }
            }

        } catch (IOException e) {
            System.err.println("Помилка роботи з файловою системою: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            System.out.println("\nРоботу програми завершено. Успіхів у захисті!");
        }
    }
}
