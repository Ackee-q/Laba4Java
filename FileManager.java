package lab4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Клас для роботи з файлами та колекціями.
 * Реалізує функціонал Лабораторної роботи №4 (1-й та 2-й рівні):
 * - Генерація випадкових значень (символи та цифри) у колекцію ArrayList;
 * - 1-й рівень:
 *   1. Створення файлу 1.txt;
 *   2. Збереження колекції у файл (по елементу на новий рядок);
 *   3. Читання даних із файлу;
 *   4. Підрахунок та виведення кількості рядків;
 * - 2-й рівень:
 *   1. Виконання завдань 1-го рівня;
 *   2. Зчитування шуканого рядка з консолі;
 *   3. Пошук відповідного рядка у файлі;
 *   4. Виведення номера знайденого рядка або повідомлення про помилку.
 */
public class FileManager {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final Random random = new Random();

    /**
     * Генерує колекцію випадкових рядків, що містять символи та цифри.
     *
     * @param count     кількість елементів для генерації
     * @param strLength довжина кожного випадкового рядка
     * @return колекція ArrayList<String> із згенерованими значеннями
     */
    public ArrayList<String> generateRandomData(int count, int strLength) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < strLength; j++) {
                int index = random.nextInt(ALPHABET.length());
                sb.append(ALPHABET.charAt(index));
            }
            list.add(sb.toString());
        }
        return list;
    }

    /**
     * Завдання 1.1: Створює файл із заданою назвою (наприклад, "1.txt").
     *
     * @param fileName ім'я або шлях до файлу
     * @return об'єкт File
     * @throws IOException у разі помилки створення файлу
     */
    public File createFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            // Очищаємо старий файл або підтверджуємо його наявність
            file.delete();
        }
        boolean created = file.createNewFile();
        if (created) {
            System.out.printf("[УСПІХ] Файл '%s' успішно створено за шляхом: %s%n", fileName, file.getAbsolutePath());
        } else {
            System.out.printf("[ІНФО] Файл '%s' уже існує.%n", fileName);
        }
        return file;
    }

    /**
     * Завдання 1.2: Зберігає вміст колекції у створеному файлі,
     * кожен новий елемент записується з нової стрічки.
     *
     * @param file       файл для запису
     * @param collection колекція даних для збереження
     * @throws IOException у разі помилки запису
     */
    public void saveCollectionToFile(File file, List<String> collection) throws IOException {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("Файл для запису не існує або передано null!");
        }

        // Використовуємо FileWriter (append: false для перезапису нового набору)
        FileWriter writer = new FileWriter(file.getAbsolutePath(), false);
        try {
            for (String item : collection) {
                writer.write(item + System.lineSeparator());
            }
        } finally {
            writer.close();
        }
        System.out.printf("[УСПІХ] Вміст колекції (%d елементів) успішно записано у файл '%s'.%n",
                collection.size(), file.getName());
    }

    /**
     * Завдання 1.3: Реалізувати метод читання із цього файлу.
     * Використовуються класи FileReader та BufferedReader.
     *
     * @param file файл для читання
     * @return колекція рядків, зчитаних із файлу
     * @throws IOException у разі помилки читання
     */
    public ArrayList<String> readFromFile(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("Файл для читання не знайдено!");
        }

        FileReader reader = new FileReader(file.getAbsolutePath());
        BufferedReader bufferedReader = new BufferedReader(reader);
        ArrayList<String> resultList = new ArrayList<>();

        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                resultList.add(line);
            }
        } finally {
            bufferedReader.close();
            reader.close();
        }

        return resultList;
    }

    /**
     * Завдання 1.4: Вивести у консоль кількість стрічок у файлі для читання.
     *
     * @param file файл для аналізу
     * @return кількість рядків
     * @throws IOException у разі помилки читання
     */
    public int printLineCount(File file) throws IOException {
        ArrayList<String> lines = readFromFile(file);
        int count = lines.size();
        System.out.printf("[РЕЗУЛЬТАТ] Кількість стрічок у файлі '%s': %d%n", file.getName(), count);
        return count;
    }

    /**
     * Завдання 2-й рівень (пункти 3 та 4):
     * Виконати пошук у текстовому файлі на наявність такої самої стрічки.
     * Якщо така ж стрічка знайдена – вивести її номер у консоль (нумерація з 1),
     * якщо не знайдено – вивести повідомлення про помилку.
     *
     * @param file       текстовий файл для пошуку
     * @param searchLine стрічка, яку ввів користувач
     * @return список знайдених номерів рядків (або порожній список, якщо не знайдено)
     * @throws IOException у разі помилки зчитування файлу
     */
    public List<Integer> searchLineInFile(File file, String searchLine) throws IOException {
        if (file == null || !file.exists()) {
            System.err.println("Помилка: файл для пошуку не існує!");
            return new ArrayList<>();
        }

        FileReader reader = new FileReader(file.getAbsolutePath());
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<Integer> foundLineNumbers = new ArrayList<>();

        try {
            String currentLine;
            int lineNumber = 1;

            while ((currentLine = bufferedReader.readLine()) != null) {
                // Точне порівняння вмісту стрічок
                if (currentLine.equals(searchLine)) {
                    foundLineNumbers.add(lineNumber);
                }
                lineNumber++;
            }
        } finally {
            bufferedReader.close();
            reader.close();
        }

        // Завдання 2.4: аналіз результату пошуку
        if (!foundLineNumbers.isEmpty()) {
            if (foundLineNumbers.size() == 1) {
                System.out.printf("[РЕЗУЛЬТАТ ПОШУКУ] Стрічку '%s' знайдено! Номер стрічки у файлі: %d%n",
                        searchLine, foundLineNumbers.get(0));
            } else {
                System.out.printf("[РЕЗУЛЬТАТ ПОШУКУ] Стрічку '%s' знайдено у кількох позиціях! Номери стрічок: %s%n",
                        searchLine, foundLineNumbers);
            }
        } else {
            // Повідомлення про помилку згідно з пунктом 4
            System.out.printf("[ПОМИЛКА] Стрічку '%s' НЕ знайдено у файлі '%s'!%n", searchLine, file.getName());
        }

        return foundLineNumbers;
    }
}
