import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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


    @RepeatedTest(value = 10, name = "Повтор {currentRepetition} из {totalRepetitions} ")
    @Tag("NewTest")
    void testRandomNumber() {
        Random random = new Random();
        int n = random.nextInt(1, 100);
        boolean result = MyHomework.isEven(n);
        System.out.println("Тест. Определение четности числа." + "\nСлучайное число - " + n + "\nЯвляется ли ожидаемое  число четным?  " + result);
        boolean actual = (n % 2 == 1);
        Assertions.assertEquals(actual, result);
    }


    @RepeatedTest(value = 10, name = "Повтор {currentRepetition} из {totalRepetitions} ")
    @DisplayName("Тест допуска по возрасту (повторяемый)")
    @Tag("SpecialTest")
    void testCheckAccessStatus() {
        Random random = new Random();
        int age = random.nextInt(0, 18);
        String result = MyHomework.checkAccess(age);
        String actual = "Allowed";
        assertEquals(result, actual, "TEST FAILED");
    }


    @Tag("SpecialTest")
    @ParameterizedTest
    @CsvSource({
            "0, Ф",
            "21, D",
            "41, C",
            "61, B",
            "81, A",
            "101, Error"
    })
    void testGetGrade(int scores, String expected) {
        String result = MyHomework.getGrade(scores);
        Assertions.assertEquals(expected, result);
    }

    @RepeatedTest(value = 10, name = "Повтор {currentRepetition} из {totalRepetitions} ")
    @Tag("SpecialTest")
    void testIsPositive() {
        Random random = new Random();
        int n = random.nextInt(-50, 100);
        boolean result = MyHomework.isPositive(n);
        boolean actual = (n >= 0);
        Assertions.assertEquals(result, actual);

    }

    @RepeatedTest(value = 10, name = "Повтор {currentRepetition} из {totalRepetitions} ")
    @Tag("SpecialTest")
    void testBlastOff() {
        int start = 5;
        String result = MyHomework.blastOff(start);
        String expected = "5 4 3 2 1 Поехали!";
        System.out.println("Специальный тест. Обратный отсчет!");
        System.out.println("Ожидаемый результат:" + result);
        System.out.println("Фактический результат:" + expected);
        Assertions.assertEquals(result, expected, "TEST PASSED");
    }

    @Tag("SpecialTest")
    @ParameterizedTest
    @CsvSource({"5,  4 3 2 1 Поехали!",
            "4,  3 2 1 Поехали!",
            "3,  2 1 Поехали!",
            "2,   1 Поехали!",
            "1,   Поехали!"})
    void testAnotherOfBlastOff(int start, String result) {
        String expectedResult = MyHomework.blastOff(start);
        Assertions.assertEquals(result, expectedResult, "TEST PASSED");
    }

    @RepeatedTest(value = 10, name = "Повтор {currentRepetition} из {totalRepetitions} ")
    @Tag("SpecialTest")
    void testSumToN() {
        System.out.println("Тест. Сумма всех чисел!");
        int result = MyHomework.sumToN(5);
        int expected = 5;
        System.out.println("Фактическое значение:" + result);
        System.out.println("Ожидаемое значение:" + expected);
        Assertions.assertEquals(result, expected, "TEST FAILED");
    }

    @RepeatedTest(value = 10, name = "Повтор {currentRepetition} из {totalRepetitions} ")
    @Tag("SpecialTest")
    void testHasBug() {
        System.out.println("Тест.Проверка на наличие слова 'BUG'!");

        String[] list = {"BUG", "DAY", "BUN", "FUN"};
        Boolean result = MyHomework.hasBug(list);
        Boolean expected = false;
        System.out.println("Фактическое значение:" + result);
        System.out.println("Ожидаемое значение:" + expected);
        assertEquals(expected, result, "TEST FAILED!");
    }

    @RepeatedTest(value = 10, name = "Повтор {currentRepetition} из {totalRepetitions} ")
    @Tag("SpecialTest")
    void testGetInRange() {
        System.out.println("Тест!Чётные числа внутри диапазона!");
        String result = MyHomework.getEventInRange(2, 5);
        String expected = "2 5";
        System.out.println("Фактическое значение:" + result);
        System.out.println("Ожидаемое значение:" + expected);
        Assertions.assertEquals(result, expected, "TEST FAILED!");
    }

    @RepeatedTest(value = 10, name = "Повтор {currentRepetition} из {totalRepetitions} ")
    @Tag("SpecialTest")
    void testFindMax() {
        System.out.println("Тест! Максимальное  число внутри диапазона!");
        int[] numbers = {5, 6, 22, 55, 111};
        Integer result = MyHomework.findMax(numbers);
        Integer expected = 11;
        System.out.println("Фактическое значение:" + result);
        System.out.println("Ожидаемое значение:" + expected);
        Assertions.assertEquals(result, expected, "TEST FAILED!");

    }

    @RepeatedTest(value = 10, name = "Повтор {currentRepetition} из {totalRepetitions} ")
    @Tag("SpecialTest")
    void testReverse() {
        System.out.println("Тест! Обратный порядок в массиве!");
        String[] arr = {"One", "Two", "Zero"};
        String[] result = MyHomework.reverse(arr);
        String[] expected = {"Zero", "Two", "One"};
        System.out.println("Фактическое значение:" + Arrays.toString(result));
        System.out.println("Ожидаемое значение:" + Arrays.toString(expected));
        assertArrayEquals(expected, result);

    }

    @RepeatedTest(value = 10, name = "Повтор {currentRepetition} из {totalRepetitions} ")
    @Tag("SpecialTest")
    void testCalcAverage() {
        System.out.println("Тест! Среднее значение суммы всех чисел  в массиве!");
        List<Integer> list = Arrays.asList(5, 6, 7, 5, 8, 3);
        int result = MyHomework.calcAverage(list);
        int expected = 6;
        System.out.println("Фактическое значение:" + result);
        System.out.println("Ожидаемое значение:" + expected);
        assertEquals(expected, result, "TEST FAILED!");
    }

    @RepeatedTest(value = 10, name = "Повтор {currentRepetition} из {totalRepetitions} ")
    @Tag("SpecialTest")
    void testRemoveSpecificName() {
        System.out.println("Тест! Исключение имени из списка!");
        List<String> list = Arrays.asList("РОЗА", "Мимоза", "Ромашка", "АСТРА");
        List result = MyHomework.removeSpecificName(list, "РОЗА");
        List<String> expected = Arrays.asList("Химоза", "Ромашка", "АСТРА");
        System.out.println("Фактическое значение:" + result);
        System.out.println("Ожидаемое значение:" + expected);
        assertEquals(expected, result, "TEST FAILED!");


    }
}