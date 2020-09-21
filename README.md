 

 

 

? **Which \*type\* of application would you like to create?** Microservice
application

? **[Beta] Do you want to make it reactive with Spring WebFlux?** No

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

? **Which \*development\* database would you like to use?** H2 with disk-based
persist

ence

? **Do you want to use the Spring cache abstraction?** No - Warning, when using
an S

QL database, this will disable the Hibernate 2nd level cache!

? **Would you like to use Maven or Gradle for building the backend?** Maven

? **Which other technologies would you like to use?** 

? **Would you like to enable internationalization support?** No

? **Besides JUnit and Jest, which testing frameworks would you like to use?** 

? **Would you like to install other generators from the JHipster Marketplace?**
No

\--------------

? **Which \*type\* of application would you like to create?** Microservice
application

? **[Beta] Do you want to make it reactive with Spring WebFlux?** No

? **What is the base name of your application?** DepositAccount

? **As you are running in a microservice architecture, on which port would like
yo**

**ur server to run? It should be unique to avoid port conflicts.** 8082

? **What is your default Java package name?** com.azrul.ebanking.depositaccount

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

? **Which \*development\* database would you like to use?** H2 with disk-based
persist

ence

? **Do you want to use the Spring cache abstraction?** No - Warning, when using
an S

QL database, this will disable the Hibernate 2nd level cache!

? **Would you like to use Maven or Gradle for building the backend?** Maven

? **Which other technologies would you like to use?** 

? **Would you like to enable internationalization support?** No

? **Besides JUnit and Jest, which testing frameworks would you like to use?** 

? **Would you like to install other generators from the JHipster Marketplace?**
(y/N

) No

\----------------

 

? **Which \*type\* of application would you like to create?** Microservice
gateway

? **[Beta] Do you want to make it reactive with Spring WebFlux?** No

? **What is the base name of your application?** GatewayKafka

? **As you are running in a microservice architecture, on which port would like
yo**

**ur server to run? It should be unique to avoid port conflicts.** 8080

? **What is your default Java package name?** com.azrul.ebanking.gatewaykafka

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

? **Which \*development\* database would you like to use?** H2 with disk-based
persist

ence

? **Do you want to use the Spring cache abstraction?** No - Warning, when using
an S

QL database, this will disable the Hibernate 2nd level cache!

? **Do you want to use Hibernate 2nd level cache?** No

? **Would you like to use Maven or Gradle for building the backend?** Maven

? **Which other technologies would you like to use?** 

? **Which \*Framework\* would you like to use for the client?** Angular

? **Would you like to use a Bootswatch theme (https://bootswatch.com/)?**
Default JH

ipster

? **Would you like to enable internationalization support?** No

? **Besides JUnit and Jest, which testing frameworks would you like to use?** 

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

 

 

Build Docker Image
------------------

 

To generate the missing Docker image(s), please run:

  ./mvnw -ntp -Pprod -Dmaven.test.skip=true verify jib:dockerBuild in
/Users/azrul/Documents/GitHub/Ebanking-JHipster-Kafka-Vault/DepositAccount

  ./mvnw -ntp -Pprod -Dmaven.test.skip=true verify jib:dockerBuild in
/Users/azrul/Documents/GitHub/Ebanking-JHipster-Kafka-Vault/Gateway

  ./mvnw -ntp -Pprod -Dmaven.test.skip=true verify jib:dockerBuild in
/Users/azrul/Documents/GitHub/Ebanking-JHipster-Kafka-Vault/Transaction

 

 

**INFO!** Alternatively, you can use Jib to build and push image directly to a
remote registry:

  ./mvnw -ntp -Pprod -Dmaven.test.skip=true verify jib:build
-Djib.to.image=azrulhasni/depositaccount in
/Users/azrul/Documents/GitHub/Ebanking-JHipster-Kafka-Vault/DepositAccount

  ./mvnw -ntp -Pprod -Dmaven.test.skip=true verify jib:build
-Djib.to.image=azrulhasni/gateway_kafka in
/Users/azrul/Documents/GitHub/Ebanking-JHipster-Kafka-Vault/gateway_kafka

  ./mvnw -ntp -Pprod -Dmaven.test.skip=true verify jib:build
-Djib.to.image=azrulhasni/transaction in
/Users/azrul/Documents/GitHub/Ebanking-JHipster-Kafka-Vault/Transaction

You can deploy all your apps by running the following kubectl command:

  bash kubectl-apply.sh -f
