package Ex2_FactoryMethod;

// Abstract Factory
abstract class DocumentFactory {
    public abstract Document createDocument();

    // Template method – uses factory method
    public void openDocument() {
        Document doc = createDocument();
        System.out.println("Factory creating " + doc.getType() + " document...");
        doc.open();
        doc.save();
    }
}

class WordDocumentFactory extends DocumentFactory {
    public Document createDocument() { return new WordDocument(); }
}

class PdfDocumentFactory extends DocumentFactory {
    public Document createDocument() { return new PdfDocument(); }
}

class ExcelDocumentFactory extends DocumentFactory {
    public Document createDocument() { return new ExcelDocument(); }
}
