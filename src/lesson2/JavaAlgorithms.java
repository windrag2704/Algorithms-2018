package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.*;
import java.util.*;
import java.util.function.BiFunction;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     * <p>
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     * <p>
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     * <p>
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        throw new NotImplementedError();
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     * <p>
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 5
     * <p>
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 х
     * <p>
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 х 3
     * 8   4
     * 7 6 Х
     * <p>
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     * <p>
     * 1 Х 3
     * х   4
     * 7 6 Х
     * <p>
     * 1 Х 3
     * Х   4
     * х 6 Х
     * <p>
     * х Х 3
     * Х   4
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   х
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   Х
     * Х х Х
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     * <p>
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    static public String longestCommonSubstring(String firs, String second) {
        int[][] arr = new int[firs.length() + 1][second.length() + 1];
        if (firs.length() == 0 || second.length() == 0) return "";
        int max = 0;
        int x = 0;
        int y = 0;
        for (int i = 0; i < firs.length(); i++) {
            for (int j = 0; j < second.length(); j++) {
                if (firs.charAt(i) == second.charAt(j)) {
                    arr[i + 1][j + 1] = ++arr[i][j];
                    if (arr[i + 1][j + 1] > max) {
                        max = arr[i + 1][j + 1];
                        y = i + 1;
                    }
                }
            }
        }
        return firs.substring(y - max, y);
        // T = O(N*M)
        // R = O(N*M)
    }


    /**
     * Число простых чисел в интервале
     * Простая
     * <p>
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     * <p>
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    static public int calcPrimesNumber(int limit) {
        throw new NotImplementedError();
    }

    /**
     * Балда
     * Сложная
     * <p>
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     * <p>
     * И Т Ы Н
     * К Р А Н
     * А К В А
     * <p>
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     * <p>
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     * <p>
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     * <p>
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) throws IOException {
        Set<String> result = new HashSet<>();
        Map<Character, ArrayList<Integer>> field = new HashMap<>();
        InputStreamReader reader = new InputStreamReader(new FileInputStream(inputName));
        int c;
        int count = 0;
        int width = -1;
        while ((c = reader.read()) != -1) {
            if (width == -1 && c == '\n') width = count;
            if (c == ' ' || c == '\n') continue;
            if (field.containsKey((char) c)) {
                field.get((char) c).add(count);
            } else {
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(count);
                field.put((char) c, temp);
            }
            count++;
        }
        if (width == -1) width = count;
        if (words.size() == 0 || field.size() == 0) return result;
        int[] num;
        int[] indexes;
        for (String word : words) {
            if (word.length() == 0) continue;
            num = new int[word.length() + 1];
            indexes = new int[word.length()];
            boolean isAllLeters = true;
            while (num[0] == 0 && isAllLeters) {
                for (int i = word.length(); i > 0; i--) {
                    ArrayList<Integer> temp = field.get(word.charAt(i - 1));
                    if (temp == null) {
                        isAllLeters = false;
                        break;
                    }
                    num[i - 1] += num[i] / temp.size();
                    num[i] %= field.get(word.charAt(i - 1)).size();
                    indexes[i - 1] = temp.get(num[i]);
                }
                if (isAllLeters && checkWord(indexes, width)) {
                    result.add(word);
                }
                num[num.length - 1]++;
            }
        }
        return result;
        // R = O(1)
        // T = O(N^M)
    }

    static boolean checkWord(int[] indexes, int width) {
        for (int i = 0; i < indexes.length - 1; i++) {
            int n = indexes[i + 1] - indexes[i];
            if (!(n == -1 || n == 1 || n == width || n == -width)) {
                return false;
            }
            for (int j = i + 1; j < indexes.length; j++) {
                if (indexes[i] == indexes[j]) return false;
            }
        }
        return true;
    }
}

