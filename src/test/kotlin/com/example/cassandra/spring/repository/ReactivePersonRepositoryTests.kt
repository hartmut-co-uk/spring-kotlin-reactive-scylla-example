package com.example.cassandra.spring.repository

import com.example.cassandra.spring.domain.Person
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
class ReactivePersonRepositoryTests {
    @Autowired
    private lateinit var repository: ReactivePersonRepository

    @BeforeEach
    fun `'setup' creates table and insert some rows`() {
        val deleteAndInsert = repository
                .deleteAll()
                .thenMany(repository.saveAll(Flux.just(
                        Person("Walter", "White", 50),
                        Person("Skyler", "White", 45),
                        Person("Saul", "Goodman", 42),
                        Person("Jesse", "Pinkman", 27)
                )))
        StepVerifier.create(deleteAndInsert).expectNextCount(4).verifyComplete()
    }

    @Test
    fun `'insert' should performs a count, inserts data and performs a count again using reactive operator chaining`() {
        val saveAndCount = repository
                .count()
                .doOnNext({ cnt -> println(cnt) })
                .thenMany(repository.saveAll(Flux.just(
                        Person("Hank", "Schrader", 43),
                        Person("Mike", "Ehrmantraut", 62)
                )))
                .last()
                .flatMap { _ -> repository.count() }
                .doOnNext({ cnt -> println(cnt) })
        StepVerifier.create(saveAndCount).expectNext(6).verifyComplete()
    }

    @Test
    fun `'performConversion' should performs conversion before result processing`() {
        StepVerifier
                .create(repository.findAll().doOnNext({ value -> println(value) }))
                .expectNextCount(4)
                .verifyComplete()
    }

    @Test
    fun `'query' should fetch data using query derivation`() {
        StepVerifier
                .create(repository.findByLastName("White"))
                .expectNextCount(2)
                .verifyComplete()
    }

    @Test
    fun `'queryWithString' should fetch data with string query`() {
        StepVerifier
                .create(repository.findByFirstNameInAndLastName("Walter", "White")).expectNextCount(1).verifyComplete()
    }

    @Test
    fun `'queryWithDeferred' should fetch data with deferred query derivation`() {
        StepVerifier
                .create(repository.findByLastName(Mono.just("White"))).expectNextCount(2).verifyComplete()
    }

    @Test
    fun `'queryMixedDeferredAndString' should fetch query using derivation and deffered parameter resolution`() {
        StepVerifier
                .create(repository.findByFirstNameAndLastName(Mono.just("Walter"), "White")).expectNextCount(1).verifyComplete()
    }
}
