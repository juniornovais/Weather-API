# Weather API

API REST para consultar a previsão do tempo de uma cidade usando a API da Visual Crossing.

## Tecnologias

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- PostgreSQL
- Redis
- Resilience4j
- Gradle Wrapper

## Funcionalidades

- Consulta de clima por cidade.
- Controle de requisições com Resilience4j.
- Integração com Redis para cache e armazenamento de sessão.
- Serialização JSON tipada para os valores armazenados no Redis.
- Endpoint de saúde do Spring Actuator.

## Pré-requisitos

- JDK 21 ou superior.
- PostgreSQL em execução na porta `5432`.
- Redis em execução na porta `6379`.
- Uma chave da API da [Visual Crossing](https://www.visualcrossing.com/weather-api).

O banco esperado pela configuração padrão é `cacheweather`:

```sql
CREATE DATABASE cacheweather;
```

O Redis pode ser executado localmente pelo WSL ou por um serviço compatível com Redis no Windows. Valide a instalação com:

```powershell
redis-cli ping
```

O resultado esperado é `PONG`.

## Configuração

Não versione chaves ou senhas. Configure as variáveis de ambiente antes de iniciar a aplicação:

```powershell
$env:WEATHER_API_KEY="sua chave da Visual Crossing"
$env:SPRING_DATASOURCE_PASSWORD="sua senha do PostgreSQL"
```

O arquivo `.env` não é carregado automaticamente pelo Spring Boot. No IntelliJ IDEA, configure essas variáveis em **Run/Debug Configurations → Environment variables**.

As propriedades padrão estão em `src/main/resources/application.properties`:

```properties
weather.api.url=https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.datasource.url=jdbc:postgresql://localhost:5432/cacheweather
spring.datasource.username=postgres
```

Para alterar Redis, PostgreSQL ou outras configurações sem editar o arquivo, use as propriedades equivalentes como variáveis de ambiente, por exemplo `SPRING_DATA_REDIS_HOST` e `SPRING_DATASOURCE_URL`.

## Executando

No Windows:

```powershell
.\gradlew.bat bootRun
```

No Linux/macOS:

```bash
./gradlew bootRun
```

A aplicação será iniciada em `http://localhost:8080`.

## Endpoint principal

Consulta o clima de uma cidade:

```http
GET /api/weather/{city}
```

Exemplo:

```powershell
curl.exe "http://localhost:8080/api/weather/Sao%20Paulo"
```

Quando o limite de requisições é excedido, a API retorna HTTP `429 Too Many Requests`.

## Cache Redis

O projeto habilita cache com `@EnableCaching` e configura um serializer JSON tipado em `RedisConfig`. O TTL padrão dessa configuração é de um minuto.

Para aplicar cache a um método, use `@Cacheable`:

```java
@Cacheable(value = "weather", key = "#city")
public VisualCrossingResponse checkCity(String city) {
    // consulta à Visual Crossing
}
```

## Testes e build

```powershell
.\gradlew.bat test
.\gradlew.bat build
```

## Estrutura principal

```text
src/main/java/boot/spring/weather_api
├── config       # Configurações do Redis
├── controller   # Endpoints REST
├── dto          # Objetos de resposta
├── infra        # Exceções da aplicação
└── service      # Integração com a API de clima
```

idea project: https://roadmap.sh/projects/weather-api-wrapper-service
