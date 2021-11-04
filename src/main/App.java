package main;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.IntStream;

public class App
{
    public static void main(String[] args) 
    {
        // var x = System.currentTimeMillis();
        // System.out.println(primeSieve(1000));
        // System.out.println(System.currentTimeMillis() - x);
        var y = System.currentTimeMillis();
        printFiles("./TestFiles", "a");
        System.out.println(System.currentTimeMillis() - y);
    }
    public static int primeSieve(int upto)
    {
        return IntStream.iterate(2, x -> x + 1)
                .parallel()
                .filter(x -> x == 2 || x % 2 != 0)
                .filter(p -> IntStream.range(2, (int)Math.sqrt(p)).allMatch(x -> p % x != 0))
                .limit(upto)
                .sum();
    }
    public static void printFiles(String directory, String match)
    {
        try
        {
            var matchingLines = Files.list(Paths.get(directory))
                    .parallel()
                    .map(x -> x.toFile())
                    .filter(x -> x.isFile())
                    .map(x -> { // RIP Beautiful functional syntax
                        try
                        {
                            return Files.readAllLines(x.toPath());
                        }
                        catch (IOException e) { throw new RuntimeException(e); }
                    })
                    .mapToInt(x -> x.stream()
                            .parallel()
                            .mapToInt(y -> y.toLowerCase()
                                    .contains(match) ? 1 : 0)
                            .sum())
                    .sum();
            System.out.println(matchingLines);
        }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}
