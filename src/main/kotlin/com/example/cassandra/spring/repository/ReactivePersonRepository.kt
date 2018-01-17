package com.example.cassandra.spring.repository

import com.example.cassandra.spring.domain.Person
import org.springframework.data.cassandra.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ReactivePersonRepository: ReactiveCrudRepository<Person, String> {

    /**
     * Derived query selecting by {@code lastname}
     *
     * @param lastName
     * @return
     */
    fun findByLastName(lastName: String): Flux<Person>

    /**
     * String query selecting one entity
     *
     * @param firstName
     * @param lastName
     * @return
     */
    @Query("SELECT * FROM person WHERE firstname = ?0 AND lastname = ?1")
    fun findByFirstNameInAndLastName(firstName: String, lastName: String): Mono<Person>

    /**
     * Derived query selecting by {@code lastname}. {@code lastname} uses deferred resolution that does not
     * require blocking to obtain parameter value.
     *
     * @param lastName
     * @return
     */
    fun findByLastName(lastName: Mono<String>): Flux<Person>

    /**
     * Derived query selecting by {@code firstname} and {@code lastname}. {@code firstname} uses deferred
     * resolution that does not require blocking to obtain parameter value.
     *
     * @param firstname
     * @param lastname
     * @return
     */
    fun findByFirstNameAndLastName(firstName: Mono<String>, lastName: String): Mono<Person>
}
