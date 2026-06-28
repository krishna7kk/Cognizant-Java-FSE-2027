package Ex2_FactoryMethod;

public class FactoryTest {
    public static void main(String[] args) {
        DocumentFactory[] factories = {
            new WordDocumentFactory(),
            new PdfDocumentFactory(),
            new ExcelDocumentFactory()
        };

        for (DocumentFactory factory : factories) {
            System.out.println("--- " + factory.createDocument().getType() + " ---");
            factory.openDocument();
            System.out.println();
        }
    }
}
