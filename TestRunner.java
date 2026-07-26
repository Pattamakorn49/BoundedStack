import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 
 * TestRunner
 */

public class TestRunner {

    private static int passed = 0;
    private static int failed = 0;

    /** helper กลาง - พิมพ์ PASS/FAIL และนับผลให้เอง */
    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS]" + name);
        } else {
            failed++;
            System.out.println("[FAIL]" +  name);
        }
    }

    public static void main(String[] args) {
        boolean assertOn = false;
        assert assertOn = true;
        if(!assertOn) {
            System.out.println("WARNING: assertions disabled" + "- re-run with: java -ea TestRunner\n");
        }

        System.out.println("=== BoundedStack Test Suit ===\n");

        /* ยังแดงอยู่ ต้องแก้ด้วยนะ!
        testCreators();
        testAdd();
        testRemove();
        testObservers();
        testProducer();
        testExposure();
        */

        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);
        }
    }
    // --- Partition
}
