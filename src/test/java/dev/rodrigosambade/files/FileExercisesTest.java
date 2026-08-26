package dev.rodrigosambade.files;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class FileExercisesTest{
    @TempDir Path d;
    @Test void textBinaryObjects()throws Exception{
        Path t=d.resolve("a.txt");FileExercises.writeTextAtomically(t,List.of("uno","dos"));assertEquals(List.of("uno","dos"),Files.readAllLines(t));
        Path b=d.resolve("b.bin");FileExercises.writeBinaryInts(b,1,2,3);assertArrayEquals(new int[]{1,2,3},FileExercises.readBinaryInts(b));
        Path o=d.resolve("p.bin");var people=List.of(new FileExercises.Person("Ana",30));FileExercises.writePeople(o,people);assertEquals(people,FileExercises.readPeople(o));
    }
}
