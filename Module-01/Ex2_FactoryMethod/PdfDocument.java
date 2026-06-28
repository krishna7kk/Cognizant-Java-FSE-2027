package Ex2_FactoryMethod;

public class PdfDocument implements Document {
    public void open()  { System.out.println("Opening PDF document (.pdf)"); }
    public void save()  { System.out.println("Saving PDF document (.pdf)"); }
    public String getType() { return "PDF"; }
}
