package com.example.cassandra.spring.domain

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table

@Table
data class Person(
        @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 2)
        val firstName: String,
        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 1)
        val lastName: String,
        val age: Int
)
