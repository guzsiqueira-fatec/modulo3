## Consumo de mensagens em um tópico Kafka com Spring Cloud Function

Aplicação Java que consome mensagens de um tópico Kafka utilizando Spring Cloud Stream e Spring Cloud Function.

### Tecnologias

- Java 21
- Spring Boot 3.5.7
- Spring Cloud 2025.0.0
- Spring Cloud Stream Kafka
- Apache Kafka 4.1.1
- Docker & Docker Compose
- Maven

## Estrutura

```
├── docker-compose.yaml     # Orquestração dos containers
├── Dockerfile              # Build multi-stage da aplicação
├── pom.xml                 # Dependências Maven
└── src/main/java/br/gov/sp/fatec/
    ├── LambdaKafkaApplication.java    # Classe principal
    ├── Message.java                   # Record que representa a mensagem
    └── MessageConsumerFunction.java   # Consumer do Kafka
```

### Modelo de Mensagem

```java
public record Message(
    String uniqueId,
    String content,
    long timestamp  // epoch milliseconds
) {}
```

## Como executar

1. **Subir os containers:**
   ```bash
   docker compose up -d
   ```

2. **Verificar logs da aplicação:**
   ```bash
   docker compose logs lambda -f
   ```

3. **Parar os containers:**
   ```bash
   docker compose down
   ```

### Serviços disponíveis

| Serviço   | Descrição                    | Porta        |
|-----------|------------------------------|--------------|
| kafka     | Broker Kafka (KRaft mode)    | 9092 (local) |
| kafka-ui  | Interface web para Kafka     | 4000         |
| lambda    | Aplicação consumidora        | 8080         |

### Variáveis de Ambiente

| Variável             | Descrição               | Valor padrão   |
|----------------------|-------------------------|----------------|
| KAFKA_BROKERS        | Endereço do broker      | kafka:9094     |
| KAFKA_TOPIC          | Tópico a ser consumido  | messages       |
| KAFKA_CONSUMER_GROUP | Grupo do consumidor     | lambda-group   |

## Como testar

### Enviando mensagens via CLI

1. Acesse o container do Kafka:
   ```bash
   docker exec -it kafka bash
   ```

2. Crie o tópico (se não existir):
   ```bash
   /opt/kafka/bin/kafka-topics.sh --create --topic messages --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
   ```

3. Envie uma mensagem:
   ```bash
   /opt/kafka/bin/kafka-console-producer.sh --topic messages --bootstrap-server localhost:9092
   ```
   
   Digite a mensagem em JSON:
   ```json
   {"uniqueId":"123","content":"Hello World","timestamp":1733155200000}
   ```

4. Verifique os logs da aplicação para confirmar o consumo:
   ```bash
   docker compose logs lambda -f
   ```

### Kafka UI

Acesse a interface web do Kafka UI em: **http://localhost:4000**

Funcionalidades:
- Visualizar tópicos e partições
- Produzir mensagens diretamente pela interface
- Monitorar grupos de consumidores
- Visualizar mensagens nos tópicos

## Análises de Qualidade de Código

O projeto está configurado com o plugin do SonarQube para análise de código:
https://sonarcloud.io/summary/overall?id=mod3-lambda&branch=master


## Imagem da aplicação no Docker Hub

O pipeline do projeto publica a imagem Docker da aplicação no Docker Hub:
https://hub.docker.com/repository/docker/guzsiqueira/mod3-lambda
