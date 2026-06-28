package Ex2_FactoryMethod;

public class WordDocument implements Document {
    public void open()  { System.out.println("Opening Word document (.docx)"); }
    public void save()  { System.out.println("Saving Word document (.docx)"); }
    public String getType() { return "Word"; }
}
