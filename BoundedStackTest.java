import java.util.*;
/**
 * Test runner 
 */
public class BoundedStackTest {

    private static int passed = 0;
    private static int failed = 0;

    /** helper กลาง — พิมพ์ PASS/FAIL และนับผลให้เอง */
    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            failed++;
            System.out.println("[FAIL] " + name);
        }
    }

    public static void main(String[] args) {
        boolean assertsOn = false;
        assert assertsOn = true;
        if (!assertsOn) {
            System.out.println("WARNING: assertions disabled"
                    + " - re-run with: java -ea BoundedStackTest\n");
        }

        System.out.println("=== Book Storage Test Suite ===\n");

        testCreators();
        testAdd();
        testRemove();
        testObservers();
        testProducer();
        testExposure();

        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);
        }
    }

    // --- Partition: ว่าง / มีหนังสือ / input ที่ผิดเงื่อนไข ---
    private static void testCreators() {
        System.out.println("-- Creators --");

        BoundedStack empty = new BoundedStack(0);
        check("new() -> empty", empty.size() == 0);
        check("new() -> contains nothing", !empty.contains("anything"));

        BoundedStack p = new BoundedStack(Arrays.asList("A", "B", "C"));
        check("new(list) -> size 3", p.size() == 3);
        check("new(list) -> contains B", p.contains("B"));
        check("new(list) -> preserves order",
                p.book().equals(Arrays.asList("A", "B", "C")));

        // boundary: list ว่างคือขอบล่างที่ถูกต้อง
        BoundedStack fromEmpty = new BoundedStack(new ArrayList<String>());
        check("new(empty list) -> empty", fromEmpty.size() == 0);

        // input ที่ผิดเงื่อนไขต้องโยน exception ไม่ใช่ปล่อยผ่าน
        boolean threwDup = false;
        try {
            new BoundedStack(Arrays.asList("A", "A"));
        } catch (IllegalArgumentException e) {
            threwDup = true;
        }
        check("new(duplicates) -> throws IllegalArgumentException", threwDup);

        boolean threwNull = false;
        try {
            new BoundedStack(Arrays.asList("A", null));
        } catch (IllegalArgumentException e) {
            threwNull = true;
        }
        check("new(list with null) -> throws IllegalArgumentException", threwNull);

        boolean threwNullList = false;
        try {
            new BoundedStack(null);
        } catch (IllegalArgumentException e) {
            threwNullList = true;
        }
        check("new(null) -> throws IllegalArgumentException", threwNullList);
    }

    // --- Mutator: add ต้องรักษาลำดับและกันหนังสือซ้ำ ---
    private static void testAdd() {
        System.out.println("\n-- Add --");

        BoundedStack s = new BoundedStack(0);
        check("add(A) -> returns true", s.add("A"));
        check("add(A) -> size 1", s.size() == 1);
        check("add(A) -> found by contains", s.contains("A"));

        s.add("B");
        s.add("C");
        check("add preserves insertion order",
                s.book().equals(Arrays.asList("A", "B", "C")));

        // หนังสือซ้ำไม่ใช่ error — คืน false เฉย ๆ
        check("add duplicate -> returns false", !s.add("A"));
        check("failed add leaves size unchanged", s.size() == 3);

        // input ที่ผิดเงื่อนไขต้องโยน exception
        boolean threwEmpty = false;
        try {
            s.add("");
        } catch (IllegalArgumentException e) {
            threwEmpty = true;
        }
        check("add(empty string) -> throws IllegalArgumentException", threwEmpty);

        boolean threwNull = false;
        try {
            s.add(null);
        } catch (IllegalArgumentException e) {
            threwNull = true;
        }
        check("add(null) -> throws IllegalArgumentException", threwNull);

        // boundary: เติมจนเต็มพอดีแล้วเติมเพิ่ม
        BoundedStack full = new BoundedStack(0);
        for (int i = 0; i < BoundedStack.MAX_BOOKS; i++) {
            full.add("book" + i);
        }
        check("can fill up to MAX_BOOKS", full.size() == BoundedStack.MAX_BOOKS);
        check("add when full -> returns false", !full.add("one more"));
    }

    // --- Mutator: remove ทั้งกรณีพบและไม่พบ ---
    private static void testRemove() {
        System.out.println("\n-- Remove --");

        BoundedStack s = new BoundedStack(Arrays.asList("A", "B", "C"));
        check("remove(B) -> returns true", s.remove("B"));
        check("remove -> size decreases", s.size() == 2);
        check("remove -> book is gone", !s.contains("B"));
        check("remove keeps the others in order",
                s.book().equals(Arrays.asList("A", "C")));

        // ลบหนังสือที่ไม่มีไม่ใช่ error — คืน false เฉย ๆ
        check("remove missing book -> returns false", !s.remove("nope"));
        check("failed remove leaves size unchanged", s.size() == 2);

        // boundary: ลบจนหมด
        s.remove("A");
        s.remove("C");
        check("remove all -> empty", s.size() == 0);
        check("remove on empty book storage -> returns false", !s.remove("A"));
    }

    // --- Observer ต้องไม่มี side effect ---
    private static void testObservers() {
        System.out.println("\n-- Observers --");

        BoundedStack s = new BoundedStack(Arrays.asList("A", "B"));
        check("size reports 2", s.size() == 2);
        check("contains finds an existing book", s.contains("A"));
        check("contains rejects a missing book", !s.contains("Z"));
        check("book() returns the full list in order",
                s.book().equals(Arrays.asList("A", "B")));

        int before = s.size();
        s.size();
        s.contains("A");
        s.book();
        check("observers have no side effects", s.size() == before);
    }

    // --- Producer ต้องคืนตัวใหม่ ไม่แก้ตัวเดิม ---
    private static void testProducer() {
        System.out.println("\n-- Producer (shuffled) --");

        BoundedStack original = new BoundedStack(Arrays.asList("A", "B", "C", "D"));
        BoundedStack shuffled = original.shuffled();

        check("shuffled has the same size", shuffled.size() == original.size());

        List<String> a = new ArrayList<String>(original.book());
        List<String> b = new ArrayList<String>(shuffled.book());
        Collections.sort(a);
        Collections.sort(b);
        check("shuffled contains exactly the same books", a.equals(b));

        check("shuffled does not mutate the original",
                original.book().equals(Arrays.asList("A", "B", "C", "D")));

        // mutate ตัวใหม่ต้องไม่กระทบตัวเดิม
        shuffled.add("E");
        check("mutating the result does not affect the original",
                original.size() == 4);

        // boundary: shuffle ระบบจัดเก็บที่ว่างต้องไม่พัง
        BoundedStack emptyShuffled = new BoundedStack(0).shuffled();
        check("shuffling an empty book storage is safe", emptyShuffled.size() == 0);
    }

    // --- ทดสอบว่าไม่เกิด representation exposure ---
    private static void testExposure() {
        System.out.println("\n-- Representation Exposure --");

        // ขาออก: แก้ list ที่ได้จาก book() ต้องไม่กระทบ rep
        BoundedStack s = new BoundedStack(0);
        s.add("A");

        List<String> got = s.book();
        got.clear();
        check("clearing result of book() does not affect book storage",
                s.size() == 1);

        got = s.book();
        got.add("injected");
        check("adding to result of book() does not affect book storage",
                s.size() == 1 && !s.contains("injected"));

        // ขาเข้า: แก้ list ที่ส่งให้ constructor ต้องไม่กระทบ rep
        List<String> input = new ArrayList<String>(Arrays.asList("A", "B"));
        BoundedStack p = new BoundedStack(input);

        input.clear();
        check("clearing constructor argument does not affect book storage",
                p.size() == 2);

        input.add("injected");
        check("adding to constructor argument does not affect book storage",
                !p.contains("injected"));
    }
}