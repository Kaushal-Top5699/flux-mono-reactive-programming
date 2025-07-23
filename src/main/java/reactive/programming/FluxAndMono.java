package reactive.programming;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.SQLOutput;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMono {
    public static void main(String[] args) {
//        System.out.println("Flux Example");
//        namesFlux().subscribe(System.out::println);
//        System.out.println();
//        System.out.println("Mono Example");
//        nameMono().subscribe(System.out::println);
//        System.out.println("\n");
//        System.out.println("Flux with map() operator:");
//        namesFluxMap().subscribe(System.out::println);
//        System.out.println("\n");
//        System.out.println("Reactive Streams are immutable, the elements are not converted to upper case.");
//        immutableReactiveStreamsExample().subscribe(System.out::println);
//        System.out.println("\n");
//        System.out.println("Flux with filter() operator:");
//        namesFluxFilter().subscribe(System.out::println);
//
//        System.out.println("\n");
//        System.out.println("Assignment 2:");
//        namesMono_map_filter(5).subscribe(System.out::println);
//
//        System.out.println("\n");
//        System.out.println("Operator flatMap() example: ");
//        flatMapExample().subscribe(System.out::println);

//        System.out.println("\n");
//        System.out.println("Asynchronous nature of flatmap() operator:");
//        flatMapAsync().subscribe(System.out::println);
//
//        System.out.println("\n");
//        System.out.println("Flux with concatMap() operator:");
//        concatMapExample().subscribe(System.out::println);

//        System.out.println("\n");
//        System.out.println("Mono with flatMapMany() operator: ");
//        flatMapMany().subscribe(System.out::println);

//        System.out.println("\n");
//        System.out.println("transform() with Flux example:");
//        fluxTransformExample(3).subscribe(System.out::println);

//        System.out.println("Operator concat() example: ");
//        exploreConcat().subscribe(System.out::println);

//        System.out.println("Operator concatWith() example: ");
//        exploreConcatWith().subscribe(System.out::println);

//        System.out.println("Operator concatWith() with Mono example: ");
//        exploreConcatWithMono().subscribe(System.out::println);

//        System.out.println("Operator merge() example: ");
//        exploreMerge().subscribe(System.out::println);

//        System.out.println("Operator mergeWith() with Mono:");
//        exploreMergeWith().subscribe(System.out::println);

        System.out.println("Operator zip() example:");
        exploreZip4().subscribe(System.out::println);

    }


    // Basic flux example.
    public static Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("Kevin", "Cloe", "Andy", "Marcus", "Amanda", "Joe", "Alexander")).log();
    }

    // Basic mono example.
    public static Mono<String> nameMono() {
        return Mono.just("Kevin").log();
    }

    // Flux with map() operator.
    public static Flux<String> namesFluxMap() {
        return Flux.fromIterable(List.of("Kevin", "Cloe", "Andy", "Marcus", "Amanda", "Joe", "Alexander"))
                .map(String::toUpperCase);
    }

    // Reactive streams are immutable.
    public static Flux<String> immutableReactiveStreamsExample() {
        var names = Flux.fromIterable(List.of("Kevin", "Cloe", "Andy", "Marcus", "Amanda", "Joe", "Alexander"));
        names.map(String::toUpperCase);
        return names;
    }

    // Flux with filter() operator.
    public static Flux<String> namesFluxFilter() {
        return Flux.fromIterable(List.of("Kevin", "Cloe", "Andy", "Marcus", "Amanda", "Joe", "Alexander"))
                .filter(name -> name.length() > 6);
    }

    // Mono with map() and filter() operators.
    public static Mono<String> namesMono_map_filter(int stringLength) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(name -> name.length() >= stringLength);
    }

    // Flux with flatMap() operator.
    public static Flux<String> flatMapExample() {
        return Flux.fromIterable(List.of("Kevin", "Cloe", "Andy", "Marcus", "Amanda", "Joe", "Alexander"))
                .flatMap(name -> Flux.fromArray(name.split("")));
    }

    // Async nature of flatMap() operator. Part-1.
    private static Flux<String> splitStringWithDelay(String name) {
        var charArray = name.split("");
        var delayRandom = new Random().nextInt(1000);
        var delay = 1000;
        System.out.println("Current delay: "+delay);
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(delay));
    }

    // Async nature of flatMap() operator. Part-2.
    public static Flux<String> flatMapAsync() {
        return Flux.fromIterable(List.of("Kevin", "Cloe", "Andy", "Marcus", "Amanda", "Joe", "Alexander"))
                .map(String::toUpperCase)
                .flatMap(FluxAndMono::splitStringWithDelay).log();
    }

    // Flux concatMap() operator - similar to flatMap() but it preserves the order. Part-1.
    public static Flux<String> concatMapExample() {
        return Flux.fromIterable(List.of("Kevin", "Cloe", "Andy", "Marcus", "Amanda", "Joe", "Alexander"))
                .concatMap(name -> Flux.fromArray(name.split("")));
    }

    // Flux concatMap() operator - similar to flatMap() but it preserves the order. Part-2.
    public static Flux<String> concatMapExampleWithDelay() {
        return Flux.fromIterable(List.of("Kevin", "Cloe", "Andy", "Marcus", "Amanda", "Joe", "Alexander"))
                .concatMap(FluxAndMono::splitStringWithDelay).log();
    }

    // The flatMap() operator with Mono.
    public Mono<List<String>> namesMono_flatMap(int stringLength) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(name -> name.length() >= stringLength)
                .flatMap(this::splitStringMono);
    }

    private Mono<List<String>> splitStringMono(String s) {
        var charArray = s.split("");
        var charList = List.of(charArray);
        return Mono.just(charList);
    }

    // flatMapMany() operator with Mono - Converts one thing to many things.
    public static Flux<String> flatMapMany() {
        Mono<String> nameMono = Mono.just("Kevin");
        return nameMono.flatMapMany(name -> Flux.fromArray(name.split("")));
    }

    // Takes a string and converts it into a Flux of Strings, Flux<String>
    private static Flux<String> splitStringToArray(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }

    // Operator transform() example.
    public static Flux<String> fluxTransformExample(int stringLenght) {
        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > stringLenght);

        return Flux.fromIterable(List.of("Alex", "Ben", "Chloe"))
                .transform(filterMap)
                .flatMap(s -> splitStringToArray(s))
                .log();
    }

    // Operator concat() example.
    public static Flux<String> exploreConcat() {
        var streamOne = Flux.just("A", "B", "C");
        var streamTwo = Flux.just("D", "E", "F");

        return Flux.concat(streamOne, streamTwo);
    }

    // Operator concatWith() example.
    public static Flux<String> exploreConcatWith() {
        var streamOne = Flux.just("A", "B", "C");
        var streamTwo = Flux.just("D", "E", "F");

        return streamOne.concatWith(streamTwo);
    }

    // Operator concatWith() with Mono example.
    public static Flux<String> exploreConcatWithMono() {
        var streamOne = Mono.just("A");
        var streamTwo = Flux.just("B", "C", "D", "E", "F", "G");

        return streamOne.concatWith(streamTwo);
    }

    // Operator merge() example.
    public static Flux<String> exploreMerge() {
        var streamOne = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
        var streamTwo = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(120));

        return Flux.merge(streamOne, streamTwo).log();
    }

    // Operator mergeWith() with Mono example.
    public static Flux<String> exploreMergeWith() {
        var streamOne = Mono.just("A");
        var streamTwo = Flux.just("B", "C", "D");
        return streamOne.mergeWith(streamTwo);
    }

    //Operator zip() example.
    public static Flux<String> exploreZip() {
        var streamOne = Flux.just("A", "B", "C", "D");
        var streamTwo = Flux.just("E", "F", "G", "H");
        var streamThree = Flux.just("1", "2", "3", "4");
        var streamFour = Flux.just("5", "6", "7", "8");

        return Flux.zip(streamOne, streamTwo, streamThree, streamFour)
                .map(t -> t.getT1() + t.getT2() + t.getT3() + t.getT4());
    }

    //Operator zip() example 2.
    public static Flux<String> exploreZip2() {
        var streamOne = Flux.just("A", "B", "C", "D");
        var streamTwo = Flux.just("E", "F", "G", "H");

        return Flux.zip(streamOne, streamTwo)
                .map(t -> t.getT1() + t.getT2());
    }

    //Operator zip() example 3.
    public static Flux<String> exploreZip3() {
        var streamOne = Flux.just("A", "B", "C", "D");
        var streamTwo = Flux.just("E", "F", "G", "H");

        return Flux.zip(streamOne, streamTwo, (first, second) -> first + second);
    }

    //Operator zip() example 4.
    public static Flux<String> exploreZip4() {
        var streamOne = Flux.just("A", "B", "C", "D");
        var streamTwo = Flux.just("E", "F", "G", "H");
        var streamThree = Flux.just("1", "2", "3", "4");
        var streamFour = Flux.just("9", "7", "6", "5");

        Flux<String> resultOne = Flux.zip(streamOne, streamTwo, (first, second) -> first + second);

        Flux<String> resultTwo = Flux.zip(streamThree, streamFour, (third, fourth) -> third + fourth);

        return Flux.zip(resultOne, resultTwo, (first, second) -> first + second);
    }
}
