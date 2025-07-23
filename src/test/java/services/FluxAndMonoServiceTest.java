package services;

import org.junit.jupiter.api.Test;
import reactive.programming.FluxAndMono;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

public class FluxAndMonoServiceTest {

    @Test
    void namesFlux() {
        var names = FluxAndMono.namesFlux();
        StepVerifier.create(names)
                .expectNext("Kevin", "Cloe", "Andy", "Marcus", "Amanda", "Joe", "Alexander")
                .verifyComplete();
    }

    @Test
    void nameMono() {
        var name = FluxAndMono.nameMono();
        StepVerifier.create(name)
                .expectNext("Kevin")
                .verifyComplete();
    }

    @Test
    void namesFluxMap() {
        var names = FluxAndMono.namesFluxMap();
        StepVerifier.create(names)
                .expectNext("KEVIN", "CLOE", "ANDY", "MARCUS", "AMANDA", "JOE", "ALEXANDER")
                .verifyComplete();
    }

    @Test
    void namesFluxFilter() {
        var names = FluxAndMono.namesFluxFilter();
        StepVerifier.create(names)
                .expectNext("Alexander")
                .verifyComplete();
    }

    @Test
    void namesMono_map_filter() {
        var name = FluxAndMono.namesMono_map_filter(2);
        StepVerifier.create(name)
                .expectNext("ALEX")
                .verifyComplete();
    }

    @Test
    void flatMapExample() {
        var names = FluxAndMono.flatMapExample();
        StepVerifier.create(names)
                .expectNext("K",
                        "e", "v", "i", "n", "C", "l", "o", "e", "A", "n", "d", "y", "M", "a", "r", "c", "u", "s",
                        "A", "m", "a", "n", "d", "a", "J", "o", "e", "A", "l", "e", "x", "a", "n", "d", "e", "r")
                .verifyComplete();
    }

    @Test
    void flatMapAsync() {
        var names = FluxAndMono.flatMapAsync();
        StepVerifier.create(names)
                .expectNextCount(37)
                .verifyComplete();
    }

    @Test
    void concatMapExampleWithDelay() {
        var names = FluxAndMono.concatMapExampleWithDelay();
        StepVerifier.create(names)
                .expectNext("K",
                        "e", "v", "i", "n", "C", "l", "o", "e", "A", "n", "d", "y", "M", "a", "r", "c", "u", "s",
                        "A", "m", "a", "n", "d", "a", "J", "o", "e", "A", "l", "e", "x", "a", "n", "d", "e", "r")
                .verifyComplete();
    }

    @Test
    void namesMono_flatMap() {
        FluxAndMono fluxAndMono = new FluxAndMono();
        int strLen = 3;
        var name = fluxAndMono.namesMono_flatMap(strLen);
        StepVerifier.create(name)
                .expectNext(List.of("A", "L", "E", "X"))
                .verifyComplete();
    }

    @Test
    void exploreConcat() {
        var result = FluxAndMono.exploreConcat();
        StepVerifier.create(result)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();

    }

    @Test
    void exploreConcatWith() {
        var result = FluxAndMono.exploreConcatWith();
        StepVerifier.create(result)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();

    }

    @Test
    void exploreConcatWithMono() {
        var result = FluxAndMono.exploreConcatWithMono();
        StepVerifier.create(result)
                .expectNext("B", "C", "D", "E", "F", "G", "A")
                .verifyComplete();
    }

    @Test
    void exploreMergeWithMono() {
        var result = FluxAndMono.exploreMergeWith();
        StepVerifier.create(result)
                .expectNext("A", "B", "C", "D")
                .verifyComplete();
    }
}
