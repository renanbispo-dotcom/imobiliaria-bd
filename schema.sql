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

CREATE TABLE endereco (
  cod_endereco SERIAL PRIMARY KEY,
  logradouro VARCHAR(255),
  bairro VARCHAR(255),
  cidade VARCHAR(255),
  estado VARCHAR(255),
  referencia VARCHAR(255)
);

CREATE TABLE tipo_imovel (
  cod_tipo_imovel SERIAL PRIMARY KEY,
  tipo VARCHAR(100)
);

CREATE TABLE imovel (
  cod_imovel SERIAL PRIMARY KEY,
  metragem DOUBLE PRECISION,
  status VARCHAR(100),
  valor_venda NUMERIC(15,2),
  valor_locacao NUMERIC(15,2),
  qtd_quartos INTEGER,
  qtd_suites INTEGER,
  qtd_garagens INTEGER,
  cod_proprietario INTEGER REFERENCES proprietario(cod_proprietario),
  cod_tipo_imovel INTEGER REFERENCES tipo_imovel(cod_tipo_imovel),
  cod_endereco INTEGER REFERENCES endereco(cod_endereco)
);

CREATE TABLE foto_imovel (
  cod_foto_imovel SERIAL PRIMARY KEY,
  arq_foto TEXT,
  cod_imovel INTEGER REFERENCES imovel(cod_imovel)
);

CREATE TABLE coordenador (
  cod_coordenador SERIAL PRIMARY KEY,
  nome_coordenador VARCHAR(255)
);

CREATE TABLE corretor (
  cod_corretor SERIAL PRIMARY KEY,
  nome_corretor VARCHAR(255),
  creci VARCHAR(100),
  cod_coordenador INTEGER REFERENCES coordenador(cod_coordenador)
);

CREATE TABLE atendimento_imovel (
  cod_atendimento SERIAL PRIMARY KEY,
  cod_imovel INTEGER NOT NULL REFERENCES imovel(cod_imovel),
  cod_cliente INTEGER NOT NULL REFERENCES cliente(cod_cliente),
  cod_corretor INTEGER NOT NULL REFERENCES corretor(cod_corretor),
  data_atendimento TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
  status VARCHAR(100),
  valor_venda NUMERIC(15,2),
  observacoes TEXT
);
