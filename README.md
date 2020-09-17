? **Which \*type\* of application would you like to create?** Microservice
application

? **[Alpha] Do you want to make it reactive with Spring WebFlux?** No

? **What is the base name of your application?** Transaction

? **As you are running in a microservice architecture, on which port would like
yo**

**ur server to run? It should be unique to avoid port conflicts.** 8081

? **What is your default Java package name?** com.azrul.ebanking.transaction

? **Which service discovery server do you want to use?** JHipster Registry (uses
Eur

eka, provides Spring Cloud Config support and monitoring dashboards)

? **Which \*type\* of authentication would you like to use?** JWT authentication
(stat

eless, with a token)

? **Which \*type\* of database would you like to use?** SQL (H2, MySQL, MariaDB,
Postg

reSQL, Oracle, MSSQL)

? **Which \*production\* database would you like to use?** PostgreSQL

? **Which \*development\* database would you like to use?** H2 with in-memory
persiste

nce

? **Do you want to use the Spring cache abstraction?** No - Warning, when using
an S

QL database, this will disable the Hibernate 2nd level cache!

? **Would you like to use Maven or Gradle for building the backend?** Maven

? **Which other technologies would you like to use?** Asynchronous messages
using Ap

ache Kafka

? **Would you like to enable internationalization support?** No

? **Besides JUnit and Jest, which testing frameworks would you like to use?**
(Press

 **\<space\>** to select, **\<a\>** to toggle all, **\<i\>** to invert
selection)

? **Would you like to install other generators from the JHipster Marketplace?**
No

 

 

 

 

 

 

 

? **Which \*type\* of application would you like to create?** Microservice
application

? **[Alpha] Do you want to make it reactive with Spring WebFlux?** No

? **What is the base name of your application?** UnitTrust

? **As you are running in a microservice architecture, on which port would like
yo**

**ur server to run? It should be unique to avoid port conflicts.** 8082

? **What is your default Java package name?** com.azrul.ebanking.unittrust

? **Which service discovery server do you want to use?** JHipster Registry (uses
Eur

eka, provides Spring Cloud Config support and monitoring dashboards)

? **Which \*type\* of authentication would you like to use?** JWT authentication
(stat

eless, with a token)

? **Which \*type\* of database would you like to use?** SQL (H2, MySQL, MariaDB,
Postg

reSQL, Oracle, MSSQL)

? **Which \*production\* database would you like to use?** PostgreSQL

? **Which \*development\* database would you like to use?** H2 with in-memory
persiste

nce

? **Do you want to use the Spring cache abstraction?** No - Warning, when using
an S

QL database, this will disable the Hibernate 2nd level cache!

? **Would you like to use Maven or Gradle for building the backend?** Maven

? **Which other technologies would you like to use?** Asynchronous messages
using Ap

ache Kafka

? **Would you like to enable internationalization support?** No

? **Besides JUnit and Jest, which testing frameworks would you like to use?**
(Press

 **\<space\>** to select, **\<a\>** to toggle all, **\<i\>** to invert
selection)

? **Would you like to install other generators from the JHipster Marketplace?**
(y/N

) No

 

 

Install Kafka
-------------

Azruls-MacBook-Pro:\~ azrul\$ helm repo add bitnami
https://charts.bitnami.com/bitnami

"bitnami" has been added to your repositories

Azruls-MacBook-Pro:\~ azrul\$ helm install my-kafka bitnami/kafka

NAME: my-kafka

LAST DEPLOYED: Mon Sep 14 09:08:13 2020

NAMESPACE: default

STATUS: deployed

REVISION: 1

TEST SUITE: None

NOTES:

\*\* Please be patient while the chart is being deployed \*\*

Kafka can be accessed by consumers via port 9092 on the following DNS name from
within your cluster:

    my-kafka.default.svc.cluster.local

Each Kafka broker can be accessed by producers via port 9092 on the following
DNS name(s) from within your cluster:

    my-kafka-0.my-kafka-headless.default.svc.cluster.local:9092

To create a pod that you can use as a Kafka client run the following commands:

    kubectl run my-kafka-client --restart='Never' --image
docker.io/bitnami/kafka:2.6.0-debian-10-r18 --namespace default --command --
sleep infinity

    kubectl exec --tty -i my-kafka-client --namespace default -- bash

    PRODUCER:

        kafka-console-producer.sh \\

            --broker-list
my-kafka-0.my-kafka-headless.default.svc.cluster.local:9092 \\

            --topic test

    CONSUMER:

        kafka-console-consumer.sh \\

            --bootstrap-server my-kafka.default.svc.cluster.local:9092 \\

            --topic test \\

            --from-beginning

 

 

 

Create topics
-------------

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
bin/kafka-topics.sh --create --topic deposit-debit-request --bootstrap-server localhost:9092
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
bin/kafka-topics.sh --create --topic deposit-debit-response --bootstrap-server localhost:9092
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
