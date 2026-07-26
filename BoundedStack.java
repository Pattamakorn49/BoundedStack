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

    //Abstaction Function(AF) : (book,capacity)
    // book คือ 
    // capacity คือ 
    
    //Representation Invariant(RI) :
    // - book ต้องไม่เป็น null 
    // - book ต้องมีรายการประวัติจริง 
    // - แต่ละรายการต้องไม่เป็น null
    // - แต่ละรายการต้องไม่เป็น String ว่าง
    // - จำนวนจัดเก็บต้องไม่เกิน MAX_BOOKS
    // - ชื่อหนังสือต้องไม่ซํ้ากัน


    private void checkRep() {
        assert book != null : "history is not null";
        assert book.size() <= MAX_BOOKS : "history exceeds maximum size";
        Set<String> seen = new HashSet<>();
        for(String b : book) {
            assert b != null : "history must not be null";
            assert !b.isEmpty() : "history most not be empty";
            assert b!="";
            assert seen.add(b):"ชื่อเพลงซ้ำ: "+b;
            
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
         if(b==null||b=="") throw new IllegalArgumentException();
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
    if(s==null||s =="")throw new IllegalArgumentException();
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
}