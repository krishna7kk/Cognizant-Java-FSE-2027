package com.example.mockito.advanced;

/**
 * Exercise 3 – Abstraction for writing to a file.
 */
public interface FileWriter {

    /**
     * Writes (or overwrites) the file with the given content.
     *
     * @param content text to write
     * @throws FileOperationException if the write fails
     */
    void write(String content);

    /**
     * Appends a line to an existing file.
     *
     * @param line text to append
     */
    void appendLine(String line);
}
