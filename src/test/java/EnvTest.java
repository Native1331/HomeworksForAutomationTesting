import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

    public class EnvTest {
        @Test
        @Tag("Smoke")
        void envTest1(){
            System.out.println(System.getProperty("CIRCUIT"));

    }
}
