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
    }

    public static Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("Kevin", "Cloe", "Andy", "Marcus", "Amanda", "Joe", "Alexander")).log();
    }

    public static Mono<String> nameMono() {
        return Mono.just("Kevin").log();
    }
}
