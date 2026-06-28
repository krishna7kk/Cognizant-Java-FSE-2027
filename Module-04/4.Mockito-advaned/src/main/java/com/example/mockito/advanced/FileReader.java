package com.example.mockito.advanced;

/**
 * Exercise 3 – Abstractions for file I/O.
 *
 * Using our own interfaces (not java.io.*) so Mockito can mock them simply
 * without needing PowerMock or byte-buddy tricks.
 */
public interface FileReader {

    /**
     * Reads the entire content of the target file and returns it as a String.
     *
     * @return file content
     * @throws FileOperationException if the file cannot be read
     */
    String read();

    /**
     * Reads a single line at the given line number (1-based).
     *
     * @param lineNumber the line to read
     * @return the line content
     */
    String readLine(int lineNumber);
}
