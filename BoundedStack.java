import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 
 * BoundedStack - ADT คือ ระบบการจัดเก็บหนังสือ
 * From 6821601186 ปัตมากร เจริญใจ และ 6821601429 วัลย์วลี ยุติธรรม
 * 
 * ค่านามธรรม (A) : ลำดับการจัดเก็บหนังสือ เช่น [BOOK1 , BOOK2 , BOOK3 ,..., BOOK100]
*/
public class BoundedStack {

    public static final int MAX_BOOKS = 100;
    
    // ==== representation ====
    
    private final List<String> book;
    private final int capacity;

    //Abstaction Function(AF) : (book,capacity)
    
    //Representation Invariant(RI) :
    // - book ต้องไม่เป็น null
    // - book ต้องมีรายการประวัติจริง
    // - แต่ละรายการต้องไม่เป็น null
    // - แต่ละรายการต้องไม่เป็น String ว่าง
    // - จำนวนประวัติต้องไม่เกิน MAX_BOOKS
    // - อนุญาตให้มีประวัติการตำนวณซ้ำกัน

    // Safety from rep exposre :
    // ...

    /** 
     * เขียน checkRep()
     * แปลง RI ทุกข้อเป็น assert หนึ่งบรรทัด พร้อมข้อความอธิบาย
     */

    private void checkRep() {
        assert book != null : "history is not null";
        assert book.size() <= MAX_BOOKS : "history exceeds maximum size";
        for(String b : book) {
            assert b != null : "history must not be null";
            assert !b.isEmpty() : "history most not be empty";
        }    
    }

    // ==== Creator ====

    /**
    * สร้างระบบจัดเก็บหนังสือว่าง
    * @param capacity
    */
   public BoundedStack(int capacity){
        this.book = new ArrayList<>();
        this.capacity = capacity;
        checkRep();
   }

   /** 
    * Creator ตัวที่สอง
    * สร้างระบบจัดเก็บหนังสือจากรายชื่อหนังสือที่ให้มา
    * 
    * ระวัง : ห้ามเก็บ reference ของ s ตรงๆ (rep exposure!)
    * 
    * @param s รายชื่อหนังสือเริ่มต้น ต้องไม่ซ้ำและไม่เกิน MAX_BOOKS
    * @throws IllegalArgumentException ถ้า s ผิดเงื่อนไข
    */

   public BoundedStack(List<String> s) {
        this.book = null;
        this.capacity = 0;
   }

   // ==== Mutators ====

   /**
    * เพิ่มหนังสือต่อท้ายระบบจัดเก็บ
    * 
    * @param s
    * @return 
    * @return true ถ้าเพิ่มสำเร็จ , false ถ้ามีหนังสือเล่มนี้อยู่แล้วหรือเต็มแล้ว
    * @throws IllegalArgumentException ถ้า book เป็น null หรือสตริงว่าง 
    */
   public boolean push(String s){
        return false;
   }

   /**
    * ลบหนังสือออกจากระบบจัดเก็บ
    * 
    * @param book ชื่อหนังสือที่ต้องการลบ
    * @return true ถ้าลบสำเร็จ , false ถ้าไม่พบชื่อหนังสือเล่มนี้
    */
   public boolean remove(String book) {
        return false;
   }

   // ==== Observers ====

   /**
    * คืนจำนวนหนังสือในระบบจัดเก็บ
    */
   public int size() {
        return -1;
   }

   /** 
    *  ตรวจว่ามีหนังสือเล่มนี้อยู่หรือไม่
    */
   public boolean contains(String book) {
        return false;
   }

   /**
    * คืนรายชื่อหนังสือทั้งหมดตามลำดับ
    * 
    * ระวัง : ห้ามมคืน reference ของ book ตรงๆ (rep exposure!)
    */
   public List<String> book() {
        return null;
   }

   // ==== Producer ====

   /**
    * คืนระบบจัดเก็บหนังสือใหม่ที่มีหนังสือเล่มเดียวกันแต่สลับลำดับ
    * 
    * @return ระบบจัดเก็บหนังสือใหม่ที่สลับลำดับแล้ว
    */
   public BoundedStack shuffled() {
    return null;
   }

   @Override
   public String toString() {
        return book.toString();
   }
}