import java.util.ArrayList;

/**
 * Calculator — เครื่องคิดเลขอย่างง่าย เก็บค่าปัจจุบันและประวัติการคำนวณ
 */
public class BoundedStack {

    private double result;
    private ArrayList<String> history;

    // ---------- Constructor ----------

    public BoundedStack() {
        result = 0;
        history = new ArrayList<String>();
        checkRep();
    }

    // ---------- การคำนวณพื้นฐาน ----------

    public void add(double x) {
        double before = result;
        result = result + x;
        history.add(before + " + " + x + " = " + result);
        checkRep();
    }

    public void subtract(double x) {
        double before = result;
        result = result - x;
        history.add(before + " - " + x + " = " + result);
        checkRep();
    }

    public void multiply(double x) {
        double before = result;
        result = result * x;
        history.add(before + " * " + x + " = " + result);
        checkRep();
    }

    /**
     * หารค่าปัจจุบันด้วย x
     * ถ้า x เป็น 0 ให้โยน exception ออกไป เพราะเป็นความผิดของ input
     * ไม่ใช่ bug ของโปรแกรมเมอร์ (จึงไม่ใช้ assert ตรงนี้)
     */
    public void divide(double x) {
        if (x == 0) {
            throw new ArithmeticException("หารด้วยศูนย์ไม่ได้");
        }
        double before = result;
        result = result / x;
        history.add(before + " / " + x + " = " + result);
        checkRep();
    }

    /** ล้างค่าและประวัติทั้งหมด กลับไปเริ่มที่ 0 */
    public void clear() {
        result = 0;
        history.clear();
        checkRep();
    }

    // ---------- อ่านค่า (ไม่แก้ไข object) ----------

    public double getResult() {
        return result;
    }

    /**
     * คืนสำเนาของประวัติ ไม่ใช่ตัวจริง
     * ป้องกันไม่ให้คนภายนอกเอา list ตัวจริงไปแก้ไข (rep exposure)
     */
    public ArrayList<String> getHistory() {
        return new ArrayList<String>(history);
    }

    public void printHistory() {
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i));
        }
    }

    // ---------- ตรวจสอบภายใน ----------

    /**
     * checkRep: ตรวจว่าสถานะภายในยังถูกต้องอยู่เสมอ
     * เรียกท้าย constructor และท้ายทุกเมธอดที่แก้ค่า
     */
    private void checkRep() {
        assert history != null : "history ห้ามเป็น null";
        for (int i = 0; i < history.size(); i++) {
            assert history.get(i) != null : "สมาชิกใน history ห้ามเป็น null";
        }
        assert !Double.isNaN(result) : "result ห้ามเป็น NaN";
    }
}
