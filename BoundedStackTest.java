public class BoundedStackTest {

    static int pass = 0;
    static int fail = 0;

    public static void main(String[] args) {
        testAdd();
        testSubtract();
        testMultiply();
        testDivide();
        testDivideByZero();
        testClear();
        testHistoryIsCopy();

        System.out.println("\nPASS = " + pass + ", FAIL = " + fail);
    }

    static void check(String name, boolean condition) {
        if (condition) {
            pass++;
            System.out.println("[PASS] " + name);
        } else {
            fail++;
            System.out.println("[FAIL] " + name);
        }
    }

    static void testAdd() {
        BoundedStack c = new BoundedStack();
        c.add(5);
        check("0 + 5 = 5", c.getResult() == 5);

        c.add(-2);
        check("5 + (-2) = 3", c.getResult() == 3);
    }

    static void testSubtract() {
        BoundedStack c = new BoundedStack();
        c.add(10);
        c.subtract(15);
        check("10 - 15 = -5", c.getResult() == -5);
    }

    static void testMultiply() {
        BoundedStack c = new BoundedStack();
        c.add(4);
        c.multiply(3);
        check("4 * 3 = 12", c.getResult() == 12);

        c.multiply(0);
        check("12 * 0 = 0", c.getResult() == 0);
    }

    static void testDivide() {
        BoundedStack c = new BoundedStack();
        c.add(10);
        c.divide(2);
        check("10 / 2 = 5", c.getResult() == 5);
    }

    static void testDivideByZero() {
        BoundedStack c = new BoundedStack();
        c.add(10);

        boolean threw = false;
        try {
            c.divide(0);
        } catch (ArithmeticException e) {
            threw = true;
        }
        check("หาร 0 ต้อง throw", threw);
    }

    static void testClear() {
        BoundedStack c = new BoundedStack();
        c.add(100);
        c.clear();
        check("clear() แล้วค่าเป็น 0", c.getResult() == 0);
        check("clear() แล้วประวัติว่าง", c.getHistory().size() == 0);
    }

    static void testHistoryIsCopy() {
        BoundedStack c = new BoundedStack();
        c.add(1);
        c.getHistory().clear();
        check("แก้ history ที่ได้มา ไม่กระทบของจริง", c.getHistory().size() == 1);
    }
}
