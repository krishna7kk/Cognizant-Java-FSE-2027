package com.example.mockito.advanced;

/**
 * Exercise 3 – Service that reads a file, transforms the content, and writes
 * the result back. Both I/O dependencies are injected so they can be mocked.
 */
public class FileService {

    private final FileReader fileReader;
    private final FileWriter fileWriter;

    public FileService(FileReader fileReader, FileWriter fileWriter) {
        this.fileReader = fileReader;
        this.fileWriter = fileWriter;
    }

    /**
     * Reads the file, applies a transformation, returns the processed text.
     * Returns "Processed <content>" so tests can assert on the prefix.
     */
    public String processFile() {
        String content = fileReader.read();
        return "Processed " + content;
    }

    /**
     * Reads the file, transforms it, and writes the result back.
     *
     * @return the transformed content that was written
     */
    public String transformAndWrite() {
        String content  = fileReader.read();
        String processed = "Processed " + content;
        fileWriter.write(processed);
        return processed;
    }

    /**
     * Reads a specific line from the file.
     */
    public String readLine(int lineNumber) {
        return fileReader.readLine(lineNumber);
    }

    /**
     * Appends a processed version of the given text to the file.
     */
    public void appendProcessed(String text) {
        fileWriter.appendLine("Processed " + text);
    }
}
