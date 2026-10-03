import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodsForAutotests{
    /*1)метод должен вернуть булевое значение
    2)метод должен вернуть список
    3)метод должен вернуть неверное значение*/

    public static void main(String[] args) {

        System.out.println(MethodsForAutotests.allColoursBecameGreen(List.of("Green", "Blue", "Red")));
        System.out.println(MethodsForAutotests.includeWordWithUppercase(List.of()));
        System.out.println(MethodsForAutotests.wrongDivide(0, 2));
        System.out.println(MethodsForAutotests.wrongDivide(2, 2));


    }

    /*Первый метод-с использованием булевого значения*/
    public static boolean includeWordWithUppercase(List<String> words) {
        if (words == null) {
            throw new IllegalArgumentException("Список не должен быть null");
        }

        for (String word : words) {
            if (word != null && !word.isEmpty() && word.equals(word.toUpperCase())) {
                return true;
            }
        }
        return false;
    }


    /*Второй  метод-с использованием списка*/
    public static List<String> allColoursBecameGreen(List<String> list) {
            if (list == null) {
                throw new IllegalArgumentException("Список не должен быть null");
            }

            List<String> result = new ArrayList<>(list);
            result.replaceAll(colour -> "Green");
            return result;
        }


    public static List<Integer> parseColorCodes(List<String> colorNames) {
        List<Integer> result = new ArrayList<>();

        if (colorNames == null) {
            throw new IllegalArgumentException("Список не должен быть null");
        }

        for (String name : colorNames) {
            try {
                int code = Integer.parseInt(name);
                result.add(code);
            } catch (NumberFormatException e) {
                System.out.println("Не удалось распарсить цвет: '" + name + "' (будет 0)");
                result.add(0);
            }
        }

        return result;
    }

    /* Третий метод должен вернуть неверное значение*/
    public static int wrongDivide(int a, int b) {
        try {
            int c = b / a;
            System.out.println(c);
            return c;
        } catch (ArithmeticException e) {
            System.out.println ("Арифметическая ошибка! Расчет неверен");
            return -1;
        } finally {
            System.out.println ("Распечатать значение, независимо от результата расчета");
       }

    }
    public static int wrongResult(int c, int d){
        try{
            int r = c / d;
            System.out.println(r);
            return r;
        }catch (ArithmeticException e){
            System.out.println ("Арифметическая ошибка! Расчет неверен");
            return 0;
        }
    }
}

