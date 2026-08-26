package dev.rodrigosambade.files;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
public final class FileExercises {
    public record Person(String name,int age) implements Serializable {}
    public static List<Path> listRegularFiles(Path dir)throws IOException{
        try(var s=Files.list(dir)){return s.filter(Files::isRegularFile).sorted().toList();}
    }
    public static void writeTextAtomically(Path target,List<String> lines)throws IOException{
        Path parent=Objects.requireNonNullElse(target.toAbsolutePath().getParent(),Path.of("."));
        Files.createDirectories(parent);
        Path tmp=Files.createTempFile(parent,target.getFileName().toString(),".tmp");
        try{
            Files.write(tmp,lines,StandardCharsets.UTF_8,StandardOpenOption.TRUNCATE_EXISTING);
            try{Files.move(tmp,target,StandardCopyOption.ATOMIC_MOVE,StandardCopyOption.REPLACE_EXISTING);}catch(AtomicMoveNotSupportedException e){Files.move(tmp,target,StandardCopyOption.REPLACE_EXISTING);}
        }finally{Files.deleteIfExists(tmp);}
    }
    public static void writeBinaryInts(Path p,int...values)throws IOException{
        try(var out=new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(p)))){for(int v:values)out.writeInt(v);}
    }
    public static int[] readBinaryInts(Path p)throws IOException{
        try(var in=new DataInputStream(new BufferedInputStream(Files.newInputStream(p)))){
            List<Integer>x=new ArrayList<>();try{while(true)x.add(in.readInt());}catch(EOFException ignored){}return x.stream().mapToInt(Integer::intValue).toArray();
        }
    }
    public static void writePeople(Path p,List<Person> people)throws IOException{
        try(var out=new ObjectOutputStream(Files.newOutputStream(p))){out.writeObject(new ArrayList<>(people));}
    }
    @SuppressWarnings("unchecked") public static List<Person> readPeople(Path p)throws IOException,ClassNotFoundException{
        try(var in=new ObjectInputStream(Files.newInputStream(p))){return List.copyOf((List<Person>)in.readObject());}
    }
    public static List<Double> scanNumbers(String text){
        try(var s=new Scanner(text).useLocale(Locale.US)){List<Double>x=new ArrayList<>();while(s.hasNext()){if(s.hasNextDouble())x.add(s.nextDouble());else s.next();}return List.copyOf(x);}
    }
}
