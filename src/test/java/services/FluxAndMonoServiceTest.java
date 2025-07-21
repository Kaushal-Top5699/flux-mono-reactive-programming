package services;

import org.junit.jupiter.api.Test;
import reactive.programming.FluxAndMono;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

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
}
