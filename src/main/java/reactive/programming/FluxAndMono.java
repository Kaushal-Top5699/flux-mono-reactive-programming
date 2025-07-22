package reactive.programming;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class FluxAndMono {
    public static void main(String[] args) {
        System.out.println("Flux Example");
        namesFlux().subscribe(System.out::println);
        System.out.println();
        System.out.println("Mono Example");
        nameMono().subscribe(System.out::println);
        System.out.println("\n");
        System.out.println("Flux with map() operator:");
        namesFluxMap().subscribe(System.out::println);
        System.out.println("\n");
        System.out.println("Reactive Streams are immutable, the elements are not converted to upper case.");
        immutableReactiveStreamsExample().subscribe(System.out::println);
        System.out.println("\n");
        System.out.println("Flux with filter() operator:");
        namesFluxFilter().subscribe(System.out::println);

        System.out.println("\n");
        System.out.println("Assignment 2:");
        namesMono_map_filter(5).subscribe(System.out::println);

        System.out.println("\n");
        System.out.println("Operator flatMap() example: ");
        flatMapExample().subscribe(System.out::println);

//        System.out.println("\n");
//        System.out.println("Asynchronous nature of flatmap() operator:");
//        flatMapAsync().subscribe(System.out::println);
//
//        System.out.println("\n");
//        System.out.println("Flux with concatMap() operator:");
//        concatMapExample().subscribe(System.out::println);

        System.out.println("\n");
        System.out.println("Mono with flatMapMany() operator: ");
        flatMapMany().subscribe(System.out::println);

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
}
