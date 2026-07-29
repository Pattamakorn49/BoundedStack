import java.util.*;

/**
 * 
 * BoundedStack - ADT คือ ระบบการจัดเก็บหนังสือ
 * From 6821601186 ปัตมากร เจริญใจ และ 6821601429 วัลย์วลี ยุติธรรม
 * 
*/
public class BoundedStack {

    public static final int MAX_BOOKS = 100;
    
    // ==== representation ====
    
    private final List<String> book;
    private final int capacity;

    //Abstraction Function(AF) : (book, capacity)
    // book     คือ ลำดับ (ตามลำดับที่เพิ่มเข้ามา) ของชื่อหนังสือที่ถูกจัดเก็บอยู่ในระบบ ณ ขณะนี้
    //          book.get(0) คือหนังสือที่ถูกเพิ่มเข้ามาก่อนสุด (ที่ยังไม่ถูกลบ)
    // capacity คือ ค่าที่รับมาตอนสร้างด้วย constructor แรก (ปัจจุบันยังไม่ถูกใช้บังคับขีดจำกัดใด ๆ
    //          ในโค้ดส่วน mutator เก็บไว้เผื่อขยายพฤติกรรมในอนาคต โดยขีดจำกัดจริงที่ถูกบังคับใช้คือ MAX_BOOKS)
    
    //Representation Invariant(RI) :
    // - book ต้องไม่เป็น null 
    // - book ต้องไม่มีสมาชิกที่เป็น null
    // - แต่ละรายการ (ชื่อหนังสือ) ต้องไม่เป็น String ว่าง ("")
    // - จำนวนหนังสือที่จัดเก็บ (book.size()) ต้องไม่เกิน MAX_BOOKS
    // - ชื่อหนังสือในระบบต้องไม่ซ้ำกัน (ห้ามมีชื่อเดียวกันสองรายการ)


    private void checkRep() {
        assert book != null : "book must not be null";
        assert book.size() <= MAX_BOOKS : "book exceeds MAX_BOOKS";
        Set<String> seen = new HashSet<>();
        for(String b : book) {
            assert b != null : "book item must not be null";
            assert !b.isEmpty() : "book item must not be empty string";
            assert seen.add(b) : "พบชื่อหนังสือซ้ำ: " + b;
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
    if(s==null||s.size()>MAX_BOOKS)throw new IllegalArgumentException();
    Set<String> seen = new HashSet<>();
    for(String b : s){
         if(b==null||b.isEmpty()) throw new IllegalArgumentException();
            if(!seen.add(b)) throw new IllegalArgumentException();}
        this.book = new ArrayList<>(s);
        this.capacity = 0;
        checkRep();
   }

   // ==== Mutators ====

   /**
    * เพิ่มหนังสือต่อท้ายระบบจัดเก็บ
    * 
    * @param s หนังสือต้องไม่เป็น null และไม่เป็นสตริงว่าง
    * @return true ถ้าเพิ่มสำเร็จ , false ถ้ามีหนังสือเล่มนี้อยู่แล้วหรือเต็มแล้ว
    * @throws IllegalArgumentException ถ้า s เป็น null หรือสตริงว่าง 
    */
   public boolean push(String s){
    if(s==null||s.isEmpty())throw new IllegalArgumentException();
    if(book.contains(s)||book.size()>=MAX_BOOKS)return false; 
        book.add(s);
        checkRep();
        return true;
   }

   /**
    * ลบหนังสือออกจากระบบจัดเก็บ
    * 
    * @param s ชื่อหนังสือที่ต้องการลบ
    * @return true ถ้าลบสำเร็จ , false ถ้าไม่พบชื่อหนังสือเล่มนี้
    */
   public boolean remove(String s) {
    if(!book.contains(s))return false;
       book.remove(s);
       checkRep();
       return true;
   }

   // ==== Observers ====

   /**
    * คืนจำนวนหนังสือในระบบจัดเก็บ
    */
   public int size() {
        return book.size();
   }

   /** 
    *  ตรวจว่ามีหนังสือเล่มนี้อยู่หรือไม่
    */
   public boolean contains(String s) {
        return book.contains(s);
   }

   /**
    * คืนรายชื่อหนังสือทั้งหมดตามลำดับ
    * 
    * ระวัง : ห้ามมคืน reference ของ book ตรงๆ (rep exposure!)
    */
   public List<String> book() {
        return new ArrayList<>(book);
   }

   // ==== Producer ====

   /**
    * คืนระบบจัดเก็บหนังสือใหม่ที่มีหนังสือเล่มเดียวกันแต่สลับลำดับ
    * 
    * @return ระบบจัดเก็บหนังสือใหม่ที่สลับลำดับแล้ว
    */
   public BoundedStack shuffled() {
        List<String> copy = new ArrayList<>(book);
        Collections.shuffle(copy);
        return new BoundedStack(copy);
   }

   @Override
   public String toString() {
        return book.toString();
   }

   /**
    * เพิ่มหนังสือต่อท้ายระบบจัดเก็บ (พฤติกรรมเหมือน push ทุกประการ
    * เก็บชื่อ add ไว้เพื่อให้สอดคล้องกับชื่อ operation ที่ใช้เรียกทั่วไป)
    *
    * @param string หนังสือต้องไม่เป็น null และไม่เป็นสตริงว่าง
    * @return true ถ้าเพิ่มสำเร็จ , false ถ้ามีหนังสือเล่มนี้อยู่แล้วหรือเต็มแล้ว
    * @throws IllegalArgumentException ถ้า string เป็น null หรือสตริงว่าง
    */
   public boolean add(String string) {
     return push(string);
   }
}