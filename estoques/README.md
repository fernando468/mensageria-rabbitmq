## Tecnologias utilizadas

- Java
- Maven
- Spring Boot
- RabbitMQ
- Banco de dados em memória H2

## Ferramentas necessárias

- Java JDK 21
- Maven 3.9.9
- Spring Boot 4.1.0
- Banco de dados em memória H2

## Acessar banco de dados H2

- Login: sa
- Senha: sa
- JDBC URL: jdbc:h2:mem:pedidos

## Rodar aplicação no local pela linha de comando

- `mvn clean -f pom.xml`
- `mvn install -f pom.xml`
- `mvn spring-boot:run`