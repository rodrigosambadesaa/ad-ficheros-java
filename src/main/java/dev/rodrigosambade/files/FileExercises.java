package dev.rodrigosambade.files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public final class FileExercises {

    private FileExercises() {
    }

    public record Person(String name, int age) implements Serializable {
    }

    public static List<Path> listRegularFiles(Path directory) throws IOException {
        try (var paths = Files.list(directory)) {
            return paths
                    .filter(Files::isRegularFile)
                    .sorted()
                    .toList();
        }
    }

    public static void writeTextAtomically(
            Path target,
            List<String> lines) throws IOException {
        Path parent = Objects.requireNonNullElse(
                target.toAbsolutePath().getParent(),
                Path.of("."));
        Files.createDirectories(parent);

        Path temporaryFile = Files.createTempFile(
                parent,
                target.getFileName().toString(),
                ".tmp");

        try {
            Files.write(
                    temporaryFile,
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.TRUNCATE_EXISTING);
            replaceAtomicallyWhenSupported(temporaryFile, target);
        } finally {
            Files.deleteIfExists(temporaryFile);
        }
    }

    public static void writeBinaryInts(Path path, int... values) throws IOException {
        try (DataOutputStream output = new DataOutputStream(
                new BufferedOutputStream(Files.newOutputStream(path)))) {
            for (int value : values) {
                output.writeInt(value);
            }
        }
    }

    public static int[] readBinaryInts(Path path) throws IOException {
        List<Integer> values = new ArrayList<>();

        try (DataInputStream input = new DataInputStream(
                new BufferedInputStream(Files.newInputStream(path)))) {
            while (true) {
                try {
                    values.add(input.readInt());
                } catch (EOFException endOfFile) {
                    break;
                }
            }
        }

        return values.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    public static void writePeople(Path path, List<Person> people) throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(
                Files.newOutputStream(path))) {
            output.writeObject(new ArrayList<>(people));
        }
    }

    public static List<Person> readPeople(Path path)
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream input = new ObjectInputStream(Files.newInputStream(path))) {
            Object value = input.readObject();
            if (!(value instanceof List<?> list)) {
                throw new IOException("El fichero no contiene una lista de personas");
            }

            List<Person> people = new ArrayList<>(list.size());
            for (Object item : list) {
                if (!(item instanceof Person person)) {
                    throw new IOException("El fichero contiene un objeto inesperado");
                }
                people.add(person);
            }
            return List.copyOf(people);
        }
    }

    public static List<Double> scanNumbers(String text) {
        List<Double> values = new ArrayList<>();

        try (Scanner scanner = new Scanner(text).useLocale(Locale.US)) {
            while (scanner.hasNext()) {
                if (scanner.hasNextDouble()) {
                    values.add(scanner.nextDouble());
                } else {
                    scanner.next();
                }
            }
        }

        return List.copyOf(values);
    }

    private static void replaceAtomicallyWhenSupported(
            Path source,
            Path target) throws IOException {
        try {
            Files.move(
                    source,
                    target,
                    StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (AtomicMoveNotSupportedException exception) {
            Files.move(
                    source,
                    target,
                    StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
