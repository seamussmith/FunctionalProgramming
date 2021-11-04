package main;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class App
{
    public static void main(String[] args) 
    {
        var x = System.currentTimeMillis();
        System.out.println(primeSieve(1000));
        System.out.println(System.currentTimeMillis() - x);
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
}
