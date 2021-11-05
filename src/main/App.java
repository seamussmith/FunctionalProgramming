package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class App
{
    public static void main(String[] args) 
    {
        var primeUp = 1000;
        var x = System.currentTimeMillis();
        System.out.println("Sum of first " + primeUp + " prime numbers: " + primeSieve(primeUp));
        System.out.println("Time taken: " + (System.currentTimeMillis() - x) + "ms");
        System.out.println("-".repeat(30));
        var y = System.currentTimeMillis();
        printFiles("./TestFiles", "a");
        System.out.println("Time taken: " + (System.currentTimeMillis() - y) + "ms");
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
            System.out.println("Searching through " + Files.list(Paths.get(directory)).count() + " files...");
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
            System.out.println("Found " + matchingLines + " matching lines");
        }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}
