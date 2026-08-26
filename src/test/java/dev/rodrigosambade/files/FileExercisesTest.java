package dev.rodrigosambade.files;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileExercisesTest {

    @TempDir
    Path temporaryDirectory;

    @Test
    void writesAndReadsTextAtomically() throws Exception {
        Path textFile = temporaryDirectory.resolve("a.txt");

        FileExercises.writeTextAtomically(textFile, List.of("uno", "dos"));

        assertEquals(List.of("uno", "dos"), Files.readAllLines(textFile));
    }

    @Test
    void writesAndReadsBinaryIntegers() throws Exception {
        Path binaryFile = temporaryDirectory.resolve("b.bin");

        FileExercises.writeBinaryInts(binaryFile, 1, 2, 3);

        assertArrayEquals(
                new int[]{1, 2, 3},
                FileExercises.readBinaryInts(binaryFile));
    }

    @Test
    void writesAndReadsSerializablePeople() throws Exception {
        Path objectFile = temporaryDirectory.resolve("p.bin");
        List<FileExercises.Person> people = List.of(
                new FileExercises.Person("Ana", 30));

        FileExercises.writePeople(objectFile, people);

        assertEquals(people, FileExercises.readPeople(objectFile));
    }
}
