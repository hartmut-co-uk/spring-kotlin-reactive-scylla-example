# Spring Boot with Kotlin and Reactive Cassandra example
A Kotlin project using Spring Data Cassandra with Reactor.
I also use [Scylla](https://github.com/scylladb/scylla) 
in place of Apache Cassandra. Scylla is an awesome implementation 
of Cassandra in C++, you definitely should check it out 
:school_satchel:.

## Quick start
The following step is used to setup ScyllaDB, Apache Cassandra is similar.
Pull ScyllaDB Docker image and create a dump container (for 
testing purpose only, for production please consult [ScyllaDB Docker Hub](https://hub.docker.com/r/scylladb/scylla/)
and ScyllaDB documentation)

~~~
    docker pull scylladb/scylla
    docker run --named scylla-test -p 127.0.0.1:9042:9042 -d scylladb/scylla
    docker start scylla-test
~~~

You can also install CQL shell to your host machine to interact 
with Scylla. Remember CQL shell only work with Python 2.

~~~
    pip install cqlsh
~~~

To connect with Scylla:

~~~
    cqlsh 127.0.0.1 9042
~~~

Test if Scylla worked:

~~~
    cqlsh> select release_version from system.local;
    
     release_version
    -----------------
               3.0.8
    
    (1 rows)
~~~

Congratulation! Now you have a fully working Scylla DB for your own :tada: :tada:

In order to run this example, you have to create a new keyspace named `mykeyspace`:

~~~
    create keyspace mykeyspace with replication = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
~~~

After that, clone this repository and edit `application.properties` according 
to your settings. Domain code is contained in the `main` module. You can find how to
use the repository in the `test` module. Good luck!!

## References
1. The code is based on [spring-projects/spring-data-examples](https://github.com/spring-projects/spring-data-examples/tree/master/cassandra/reactive) 
and [spring-projects/spring-boot](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples/spring-boot-sample-data-cassandra)
2. Find out more about Reactor: [reactor/reactor-core](https://github.com/reactor/reactor-core)
3. ScyllaDB: [scylladb/scylla](https://github.com/scylladb/scylla)
4. ScyllaDB Docker Hub: [scylladb/scylla](https://hub.docker.com/r/scylladb/scylla/)
