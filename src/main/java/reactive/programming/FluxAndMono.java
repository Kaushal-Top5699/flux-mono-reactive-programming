package reactive.programming;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
}
