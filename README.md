# Imobiliaria

Projeto Java simples que expõe uma API HTTP usando `com.sun.net.httpserver.HttpServer`.

## Endpoints disponíveis

- `GET /clientes`
- `GET /clientes/{id}`
- `POST /clientes`
- `DELETE /clientes/{id}`
- `GET /proprietarios`
- `GET /proprietarios/{id}`
- `POST /proprietarios`
- `PUT /proprietarios/{id}`
- `DELETE /proprietarios/{id}`
- `POST /imoveis`

## Requisitos

- Java 17+ instalado
- PostgreSQL rodando em `localhost:5432`
- Banco de dados `imobiliaria`
- Driver JDBC do PostgreSQL no diretório `lib/`

## Configuração do banco

Crie o banco e as tabelas com o SQL abaixo:

```sql
CREATE DATABASE imobiliaria;

\c imobiliaria

CREATE TABLE proprietario (
  cod_proprietario SERIAL PRIMARY KEY,
  nome VARCHAR(255),
  contato VARCHAR(255)
);

CREATE TABLE cliente (
  cod_cliente SERIAL PRIMARY KEY,
  nome VARCHAR(255),
  cpf VARCHAR(20),
  telefone VARCHAR(50),
  email VARCHAR(255),
  endereco TEXT
);
```

Ajuste `src/connection/ConnectionFactory.java` caso as credenciais ou host sejam diferentes.

## Como usar

1. Baixe o driver JDBC do PostgreSQL e coloque em `lib/postgresql.jar`.
2. Compile o projeto:

```bash
mkdir -p out
javac -d out -cp lib/postgresql.jar $(find src -name "*.java")
```

3. Execute o servidor:

```bash
java -cp out:lib/postgresql.jar server.Main
```

O servidor iniciará na porta `8000`.

## Testando com Postman

### Criar proprietário

- Método: `POST`
- URL: `http://localhost:8080/proprietarios`
- Body: `raw` JSON

```json
{
  "nome": "João",
  "contato": "(11) 99999-9999"
}
```

### Listar proprietários

- Método: `GET`
- URL: `http://localhost:8080/proprietarios`

### Buscar proprietário por ID

- Método: `GET`
- URL: `http://localhost:8080/proprietarios/1`

### Atualizar proprietário

- Método: `PUT`
- URL: `http://localhost:8080/proprietarios/1`
- Body JSON com `nome` e `contato`

### Excluir proprietário

- Método: `DELETE`
- URL: `http://localhost:8080/proprietarios/1`

## Observação

O erro `No suitable driver found for jdbc:postgresql://...` significa que o JAR do driver PostgreSQL não foi carregado no classpath de execução. Use o comando acima para executar com `lib/postgresql.jar`.
