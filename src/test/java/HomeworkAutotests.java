import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static java.beans.Beans.isInstanceOf;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeworkAutotests {
    @Test
    @Tag("newAutotests")
    void includeWordWithUppercase() {
        List<String> words = List.of("ROSe", "rose", "1881");
        Boolean result = MethodsForAutotests.includeWordWithUppercase(words);
        Boolean expectedResult = true;
        assertThat(result)
                .as("В списке есть слово, написанное большими буквами")
                .isEqualTo(expectedResult);


    }

    @Test
    @Tag("newAutotests")
    void allColoursBecameGreen() {
        List<String> colours = List.of("Green");
        List<String> result = MethodsForAutotests.allColoursBecameGreen(colours);
        List<String> expectedResult = List.of("Green", "Green", "Green");
        assertThat(result)
                .as("Все цвета стали зелеными")
                .contains("Red")
                .isEqualTo(expectedResult)
                .hasSize(3);

    }

    @Test
    @Tag("newAutotests")
    void wrongResult() {
        int result = MethodsForAutotests.wrongResult(2, 3);
        int expectedResult = 1;
        assertThat(result)
                .as("Неверно составленный расчет")
                .isEqualTo(expectedResult);
    }
        @Test
        @Tag("newAutotests")
        void anotherWrongResult () {
            int result = MethodsForAutotests.wrongResult(5, 0);
            int expectedResult = 1;
            assertThat(result)
                    .as("Делить на ноль  нельзя!")
                    .isEqualTo(expectedResult);

        }
    }

