import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Homework2 {

    @BeforeEach
    void printBeforeTest() {
        System.out.println("=====================" + "\nTest method start");
    }

    @AfterEach
    void printAfterTest() {
        System.out.println("Test method end" + "\n=====================");
    }


    @Test
    @Tag("NewTest")
    void testRandomNumber() {
        Random random = new Random();
        int n = random.nextInt(1, 100);
        boolean result = MyHomework.isEven(n);
        System.out.println("Тест. Определение четности числа." + "\nСлучайное число - " + n + "\nЯвляется ли ожидаемое  число четным?  " + result);
        boolean actual = (n % 2 == 0);
        if (result == actual) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }


    @RepeatedTest(value = 20, name = "Повтор {currentRepetition} из {totalRepetitions} ")
    @DisplayName("Тест допуска по возрасту (повторяемый)")
    void testCheckAccessStatus() {
        Random random = new Random();
        int age = random.nextInt(1, 100);
        String result = MyHomework.checkAccess(age);
        System.out.println("Тест. Определение возможности допуска по возрасту(старше 18 лет)");
        String actual = (age > 18) ? "Allowed" : "Denied";
        System.out.println("Возраст: " + age);
        System.out.println("Ожидаемый результат: " + result);
        System.out.println("Фактический результат: " + actual);
        assertEquals(result, actual, "TEST PASSED");
    }


    @ParameterizedTest
    @ValueSource(ints = {0, 21, 41, 61, 81, 101})
    void testGetGrade(int scores) {
        String result = MyHomework.getGrade(scores);
        if (result == null) {
            System.out.println("Результат пустой");
        } else {
            System.out.println("Тест. Соотнесение цифры и буквы в зависимости от  диапазона цифр!\n" +
                    "Числа от 0 до 20 - Е, от 20 до 40 - D, от 40 до 60 - C, от 60 до 80-B, от 80 до 100-A.\nЧисло:" + scores + "\nРезультат == " + result + "\nTEST PASSED");
        }
    }

    @ParameterizedTest
    @CsvSource({"0,E", "21,D", "41,C", "61,B", "81,A", "101,A"})
    void anotherTestGetGrade(int scores, String result) {
        System.out.println("Альтернативный тест. Соотнесение цифры и буквы в зависимости от  диапазона цифр!");
        String expectedResult = MyHomework.getGrade(scores);
        if (expectedResult.equals(result)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
    @Tag("Special Test")
    void testIsPositive() {
        Random random = new Random();
        int n = random.nextInt(-50, 100);
        boolean result = MyHomework.isPositive(n);
        boolean actual = (n >= 0);
        System.out.println("Cпециальный тест.Проверка положительности числа.\nЧисло: " + n);
        if (n >= 0) {
            System.out.println("Число положительное!");
        } else {
            System.out.println("Число отрицательное!");
        }
        if (actual == result) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
    @Tag("Special Test")
    void testBlastOff() {
        int start = 5;
        String result = MyHomework.blastOff(start);
        String expected = "5 4 3 2 1 Поехали!";
        System.out.println("Специальный тест. Обратный отсчет!");
        System.out.println("Ожидаемый результат:" + result);
        System.out.println("Фактический результат:" + expected);
        if (result.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }


    @Tag("Special Test")
    @ParameterizedTest
    @CsvSource({"5, 5 4 3 2 1 Поехали!",
            "4, 4 3 2 1 Поехали!",
            "3, 3 2 1 Поехали!",
            "2,  2 1 Поехали!",
            "1,  1 Поехали!"})
    void testAnotherOfBlastOff(int start, String result) {
        System.out.println("Тест. Обратный отсчет!");
        String expectedResult = MyHomework.blastOff(start);
        System.out.println("Ожидаемый результат: " + result);
        System.out.println("Фактический результат: " + expectedResult);
        if (expectedResult.equals(result)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
    @Tag("Special Test")
    void testSumToN() {
        System.out.println("Тест. Сумма всех чисел!");
        int result = MyHomework.sumToN(5);
        int expected = 15;
        System.out.println("Фактическое значение:" + result);
        System.out.println("Ожидаемое значение:" + expected);

        if (result == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
    @Tag("Special Test")
    void testHasBug() {
        System.out.println("Тест.Проверка на наличие слова 'BUG'!");

        String[] list = {"BUG", "DAY", "BUN", "FUN"};
        Boolean result = MyHomework.hasBug(list);
        Boolean expected = true;
        System.out.println("Фактическое значение:" + result);
        System.out.println("Ожидаемое значение:" + expected);
        if (result == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
        assertEquals(expected, result, "TEST PASSED!");
    }

    @Test
    @Tag("Special Test")
    void testGetInRange() {
        System.out.println("Тест!Чётные числа внутри диапазона!");
        String result = MyHomework.getEventInRange(2, 5);
        String expected = "2 4";
        System.out.println("Фактическое значение:" + result);
        System.out.println("Ожидаемое значение:" + expected);
        if (result.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

    }

    @Test
    @Tag("Special Test")
    void testFindMax() {
        System.out.println("Тест! Максимальное  число внутри диапазона!");
        int[] numbers = {5, 6, 22, 55, 111};
        Integer result = MyHomework.findMax(numbers);
        Integer expected = 111;
        System.out.println("Фактическое значение:" + result);
        System.out.println("Ожидаемое значение:" + expected);
        if (result == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

    }

    @Test
    @Tag("Special Test")
    void testReverse() {
        System.out.println("Тест! Обратный порядок в массиве!");
        String[] arr = {"One", "Two", "Zero"};
        String[] result = MyHomework.reverse(arr);
        String[] expected = {"Zero", "Two", "One"};
        System.out.println("Фактическое значение:" + Arrays.toString(result));
        System.out.println("Ожидаемое значение:" + Arrays.toString(expected));
        assertArrayEquals(expected, result);

    }


    @Test
    @Tag("Special Test")
    void testCalcAverage() {
        System.out.println("Тест! Среднее значение суммы всех чисел  в массиве!");
        List<Integer> list = Arrays.asList(5, 6, 7, 5, 8, 3);
        int result = MyHomework.calcAverage(list);
        int expected = 5;
        System.out.println("Фактическое значение:" + result);
        System.out.println("Ожидаемое значение:" + expected);
        assertEquals(expected, result, "TEST PASSED");
        if (result == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
    @Tag("Special Test")
    void testRemoveSpecificName() {
        System.out.println("Тест! Исключение имени из списка!");
        List<String> list = Arrays.asList("РОЗА", "Мимоза", "Ромашка", "АСТРА");
        List result = MyHomework.removeSpecificName(list, "РОЗА");
        List<String> expected = Arrays.asList("Мимоза", "Ромашка", "АСТРА");
        System.out.println("Фактическое значение:" + result);
        System.out.println("Ожидаемое значение:" + expected);
        assertEquals(expected, result, "TEST PASSED");
        if (result.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }


    }
}