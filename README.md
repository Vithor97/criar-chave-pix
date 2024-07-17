# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.vitor.criar-chave-pix' is invalid and this project uses 'com.vitor.criar_chave_pix' instead.


# CRIA-CHAVES-PIX

Aplicação back-end utilizando Java 21 e Spring Boot 3.3.0, focada no cadastro de Chaves PIX .
Utiliza banco de dados MySQL (container), Docker para configuração de ambiente e Jacoco para relatório de cobertura de testes.
A aplicação tenta seguir a arquitetura hexagonal e utiliza os design patterns Chain of Responsibility e Builder.

### :pushpin: Features

- [x] Futuras funcionalidades

### :hammer: Pré-requisitos

Será necessário a utilização de uma IDE de sua preferência e do JDK 21.

### :gear: Tecnologias Utilizadas

- **Spring Boot 3.3.0**
- **Java 21**
- **Banco de dados relacional MySQL** (motivo: relacionamento entre conta e chaves PIX)
- 
- **Jacoco** (para relatório de cobertura de testes)

### 🛠 Detalhes Técnicos

- **Java 21**
- **Spring Boot 3.3.0**
- **Arquitetura baseada em Hexagonal**
- Utilização do design patterns ***Chain of Responsibility*** e ***Builder***
- **Swagger**
- **Docker**
- **Banco MySQL**
- **Testes unitários**

### :whale: Uso do Docker Compose

Para facilitar a configuração do ambiente, você pode usar o Docker Compose. Abaixo está a configuração necessária:

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:latest
    container_name: mysql_container
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: chavespix
      MYSQL_USER: user
      MYSQL_PASSWORD: 1234
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql

volumes:
  mysql_data:
```

### :page_with_curl: Arquivo init.sql

Inclua o arquivo `init.sql` no mesmo diretório do arquivo `docker-compose.yml` para inicializar o banco de dados com a estrutura necessária.

### :hammer_and_wrench: Variáveis de Ambiente

Configure as seguintes variáveis de ambiente na sua IDE:

```
DATASOURCE_URL=jdbc:mysql://localhost:3306/chavespix?useSSL=false&allowPublicKeyRetrieval=true
DATASOURCE_USERNAME=user
DATASOURCE_PASSWORD=1234
```

### 🎲 Iniciando banco de dados pela primeira vez

```bash
#Dentro da pasta `docker` rodar o comando
docker-compose up -d
```

### Documentação Swagger
- http://localhost:8080/swagger-ui/index.html


### :test_tube: Cobertura de Testes

Para gerar o relatório de cobertura de testes utilizando o Jacoco:

```bash
# Execute os testes e gere o relatório
mvn clean test jacoco:report
```

### :sunglasses: Autor

Criado por Vitor Santos de Miranda.

[![Linkedin Badge](https://img.shields.io/badge/-Vitor-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/vitor-santos-de-miranda-3a68ba124/)](https://www.linkedin.com/in/vitor-santos-de-miranda-3a68ba124/)



