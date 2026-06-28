package Ex2_FactoryMethod;

public class ExcelDocument implements Document {
    public void open()  { System.out.println("Opening Excel document (.xlsx)"); }
    public void save()  { System.out.println("Saving Excel document (.xlsx)"); }
    public String getType() { return "Excel"; }
}
