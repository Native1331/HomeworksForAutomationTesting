import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



    public class MyHomework {

        public static void main(String[] args) {
            System.out.println(isEven(6));
            System.out.println(isEvenAnotherWay(6));
            System.out.println(checkAccess(18));
            System.out.println(isPositive(-18));
            System.out.println(getGrade(21));
            System.out.println(blastOff(10));
            System.out.println(sumToN(10));
            System.out.println(hasBug(new String[]{"start", "Bug1", "finish"}));
            System.out.println(hasBugAnotherWay(new String[]{"start", "Bug", "finish"}));
            System.out.println(getEventInRange(2, 5));
            System.out.println(findMax(new int[]{22, 22, 32}));
            System.out.println(Arrays.toString(reverse(new String[]{"ghgh", "gghjgjg", "hjhj"})));
            System.out.println(calcAverage(Arrays.asList(1, 1, 2, 2)));
            System.out.println(removeSpecificName(Arrays.asList("Cicilia", "Katya", "Nasty"), "Cicilia"));



        }

        public static boolean isEven(int n) {
            //Метод возвращает true, если число чётное, и false — если нечётное.
            if (n % 2 == 0) {
                return true;
            } else {
                return false;
            }
        }

        public static boolean isEvenAnotherWay(int n) {
            //Метод возвращает true, если число чётное, и false — если нечётное.
            Boolean IsEvenResult = (n % 2 == 0) ? true : false;
            return IsEvenResult;

        }

        public static String checkAccess(int age) {
            //Метод возвращает Allowed, если число строго больше 18, и Denied — если меньше.
            String checkAccessStatus = (age > 18) ? "Allowed" : "Denied";
            return checkAccessStatus;
        }

        public static boolean isPositive(int n) {
    /*    Метод должен возвращать true, если переданное число больше или равно нулю,
         и false, если переданное число меньше нуля. Проверка внутри метода
         должна происходить с помощью тернарного оператора.*/
            Boolean positiveStatus = (n >= 0) ? true : false;
            return positiveStatus;
        }

        public static String getGrade(int score) {
       /* Задача 4: разработать метод с сигнатурой public static String getGrade(int score).
            Метод возвращает строку, соответствующую строгому вхождению в границы:
            0–20: E;
            21–40: D;
            41–60: C;
            61–80: B;
            81–100: A.
    Если переданное число не входит в границы — вернуть строку Error. */
            if (score >= 0 && score <= 20) {
                return ("E");
            } else if ((score > 20) && (score <= 40)) {
                return ("D");
            } else if ((score > 40) && (score <= 60)) {
                return ("C");
            } else if ((score > 60) && (score <= 80)) {
                return ("B");
            } else if ((score > 80) && (score <= 100)) {
                return ("A");
            } else {
                return ("Error");

            }
        }

        public static String blastOff(int start) {
            if (start < 1) return "Поехали!";

            StringBuilder sb = new StringBuilder();
            for (int i = start; i >= 1; i--) {
                sb.append(i).append(" ");
            }
            sb.append("Поехали!");
            return sb.toString();
        }

        public static int sumToN(int n) {
     /*Задача 6: разработать метод с сигнатурой publiс static int sumToN(int n).
Метод возвращает сумму всех целых чисел от 1 до n.*/
            int sum = 0;
            for (int i = 1; i <= n; i++) {
                sum = sum + i;
            }
            return sum;
        }

        public static boolean hasBug(String[] messages) {
    /*Задача 7: разработать метод с сигнатурой publiс static boolean hasBug(String[] messages).
     Метод принимает массив строк и возвращает true, если хотя бы одна строка в массиве равна Bug.
     Сравнение можно выполнять без учёта регистра.
*/
            for (String message : messages) {
                if ("Bug".equalsIgnoreCase(message)) {
                    return true;
                }
            }
            return false;
        }

        public static boolean hasBugAnotherWay(String[] messages) {
    /*Задача 7: разработать метод с сигнатурой publiс static boolean hasBug(String[] messages).
     Метод принимает массив строк и возвращает true, если хотя бы одна строка в массиве равна Bug.
     Сравнение можно выполнять без учёта регистра.
*/
            if (messages == null) {
                return false;
            }

            for (String message : messages) {

                if ("bug".equals(message.toLowerCase())) {
                    return true;
                }
            }
            return false;
        }

        public static String getEventInRange(int start, int end) {
    /* Задача 8: разработать метод с сигнатурой publiс static getEvenInRange(int start, int end).
    Метод принимает границы диапазона и возвращает строку,
            состоящую только из чётных чисел внутри этого промежутка (включая границы),
            разделённых пробелом.
            Перед первым и после последнего числа пробел не ставится. Например: (2, 5) -> “2 4”
*/
            String result = "";
            boolean first = true;

            for (int i = start; i <= end; i++) {
                if (i % 2 == 0) {
                    if (first) {
                        result = String.valueOf(i);
                        first = false;
                    } else {
                        result += " " + i;
                    }

                }

            }


            return result;
        }

        public static int findMax(int[] arr) {
     /*Задача 9: разработать метод с сигнатурой publiс static public int findMax(int[] arr).
          Метод находит и возвращает самое большое число в переданном массиве.
*/
            int max = arr[0];
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] > max) {
                    max = arr[i];
                }
            }

            return max;
        }

        public static String[] reverse(String[] arr) {
       /*    Задача 10: разработать метод с сигнатурой publiс static String[] reverse(String[] arr).
        Метод возвращает новый массив, в котором элементы исходного массива расположены в обратном порядке.
        Например, {“One”, “Two”, “Zero”} -> {“Zero”, “Two”, “One}.
*/
            if (arr == null) {
                return null;
            }

            String[] reversed = new String[arr.length];
            for (int i = 0; i < arr.length; i++) {
                reversed[i] = arr[arr.length - 1 - i];
            }
            return reversed;
        }

        public static Integer calcAverage(List<Integer> list) {
   /* Задача 11: разработать метод с сигнатурой publiс static calcAverage(List<Integer> list).
            Метод вычисляет и возвращает среднее арифметическое всех чисел в списке.
  */
            if (list == null) {
                return null;
            }
            int summ = 0;
            for (int i = 0; i < list.size(); i++) {
                summ += list.get(i);
            }
            return summ / list.size();

        }

        public static List<String> removeSpecificName(List<String> list, String nameToRemove) {
   /* Задача 12: разработать метод с сигнатурой publiс static List<String> removeSpecificName
            (List<String> list, String nameToRemove).
             Метод принимает список и имя, которое нужно исключить.
    Возвращает новый список, не содержащий указанного имени.
*/
            if (list.size() == 0) {
                System.out.println("Список имен пуст");
            }
            List<String> result = new ArrayList<>();
            for (String str : list) {
                if (str == nameToRemove) {
                    continue;
                } else {
                    result.add(str);
                }
            }
            return result;
        }

    }





