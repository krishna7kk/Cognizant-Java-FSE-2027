package com.example.mockito.advanced;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Exercise 3: Mocking File I/O
 *
 * Key idea: real file I/O requires the file to exist on disk, introduces
 * path/permission issues, and pollutes the filesystem with test artefacts.
 * Mocking FileReader and FileWriter means:
 *   • No actual files are created or read
 *   • Tests run identically on every machine and in CI
 *   • We can simulate read errors / write failures without touching the OS
 *
 * Two mocks are injected here (FileReader + FileWriter), which shows how
 * @Mock fields map to constructor parameters when using @InjectMocks.
 * Because FileService takes (FileReader, FileWriter), Mockito matches by type.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Exercise 3: Mocking File I/O")
class FileServiceTest {

    @Mock
    FileReader mockFileReader;

    @Mock
    FileWriter mockFileWriter;

    // Manual construction so the parameter order is explicit
    // (alternatively use @InjectMocks if constructor param types are unambiguous)

    @Captor
    ArgumentCaptor<String> writtenContentCaptor;

    // ---------------------------------------------------------------
    // 3a. Core scenario from the exercise brief
    // ---------------------------------------------------------------

    @Test
    @DisplayName("processFile() reads from FileReader and prefixes with 'Processed '")
    void testServiceWithMockFileIO() {
        // Arrange
        when(mockFileReader.read()).thenReturn("Mock File Content");
        FileService fileService = new FileService(mockFileReader, mockFileWriter);

        // Act
        String result = fileService.processFile();

        // Assert
        assertEquals("Processed Mock File Content", result);
        verify(mockFileReader).read();            // reader was used
        verifyNoInteractions(mockFileWriter);     // writer was NOT touched
    }

    // ---------------------------------------------------------------
    // 3b. transformAndWrite – reader AND writer both involved
    // ---------------------------------------------------------------

    @Test
    @DisplayName("transformAndWrite() reads, processes, then writes the result")
    void transformAndWrite_ReadsAndWrites() {
        when(mockFileReader.read()).thenReturn("Raw Content");
        FileService fileService = new FileService(mockFileReader, mockFileWriter);

        String result = fileService.transformAndWrite();

        assertEquals("Processed Raw Content", result);
        // Capture what was written
        verify(mockFileWriter).write(writtenContentCaptor.capture());
        assertEquals("Processed Raw Content", writtenContentCaptor.getValue(),
            "The processed text must be what gets written to disk");
    }

    // ---------------------------------------------------------------
    // 3c. readLine – specific line from the file
    // ---------------------------------------------------------------

    @Test
    @DisplayName("readLine() delegates to FileReader.readLine() with correct line number")
    void readLine_DelegatesToReader() {
        when(mockFileReader.readLine(3)).thenReturn("Third line content");
        FileService fileService = new FileService(mockFileReader, mockFileWriter);

        String line = fileService.readLine(3);

        assertEquals("Third line content", line);
        verify(mockFileReader).readLine(3);
    }

    // ---------------------------------------------------------------
    // 3d. appendProcessed – writes to FileWriter
    // ---------------------------------------------------------------

    @Test
    @DisplayName("appendProcessed() writes 'Processed <text>' via FileWriter.appendLine()")
    void appendProcessed_CallsAppendLine() {
        FileService fileService = new FileService(mockFileReader, mockFileWriter);

        fileService.appendProcessed("new entry");

        verify(mockFileWriter).appendLine("Processed new entry");
        verifyNoInteractions(mockFileReader);  // reader not involved here
    }

    // ---------------------------------------------------------------
    // 3e. FileReader throws – service propagates the exception
    // ---------------------------------------------------------------

    @Test
    @DisplayName("processFile() propagates FileOperationException from FileReader")
    void processFile_PropagatesReadException() {
        when(mockFileReader.read())
                .thenThrow(new FileOperationException("File not found: data.txt"));
        FileService fileService = new FileService(mockFileReader, mockFileWriter);

        FileOperationException ex = assertThrows(
            FileOperationException.class,
            () -> fileService.processFile()
        );
        assertEquals("File not found: data.txt", ex.getMessage());
        verifyNoInteractions(mockFileWriter);  // writer must never be called on read failure
    }

    // ---------------------------------------------------------------
    // 3f. FileWriter throws – simulates a write permission error
    // ---------------------------------------------------------------

    @Test
    @DisplayName("transformAndWrite() propagates FileOperationException from FileWriter")
    void transformAndWrite_PropagatesWriteException() {
        when(mockFileReader.read()).thenReturn("Content");
        doThrow(new FileOperationException("Permission denied"))
                .when(mockFileWriter).write(anyString());
        FileService fileService = new FileService(mockFileReader, mockFileWriter);

        assertThrows(FileOperationException.class,
            () -> fileService.transformAndWrite());
    }
}
