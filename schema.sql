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
