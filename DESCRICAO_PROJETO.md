**Título**: Sistema de Gestão para Imobiliária

**Resumo**
- Este projeto é uma aplicação backend em Java para gestão operacional de uma imobiliária. Ele centraliza o cadastro e gerenciamento de imóveis, pessoas (clientes, proprietários, corretores, coordenadores), endereços, fotos e registros de atendimentos, expondo uma API para consumo por interfaces externas.

**Área de Atuação**
- Setor imobiliário: corretagem, administração de imóveis, locação e intermediação de vendas.
- Usuários-alvo: corretores, coordenadores, proprietários, equipe administrativa e sistemas integrados (front-end web/mobile).

**Objetivo do Sistema**
- Fornecer uma plataforma organizada para armazenar, consultar e atualizar informações relacionadas a imóveis e relacionamentos com clientes e proprietários.
- Automatizar fluxos operacionais rotineiros (cadastro, busca, associação de fotos, registro de atendimentos) para reduzir retrabalho manual e acelerar tomada de decisão.

**Problema que Resolve**
- Fragmentação de dados: substitui planilhas, arquivos e registros dispersos por um repositório único e consistente.
- Falta de histórico: possibilita registrar e consultar o histórico de atendimentos e interações por imóvel e cliente.
- Falta de rastreabilidade de responsabilidades: organiza papéis (corretor, coordenador, proprietário) e facilita o acompanhamento de quem realizou cada ação.

**Principais Funcionalidades**
- Gestão de Imóveis: CRUD de imóveis, associação de fotos e tipo de imóvel.
- Gestão de Pessoas: CRUD para `Cliente`, `Proprietario`, `Corretor`, `Coordenador`.
- Atendimentos: registro e histórico de atendimentos vinculados a imóveis e clientes.
- Endereços: armazenamento e normalização de endereços de imóveis e pessoas.
- API REST: endpoints para integração com front-ends e serviços externos.
- Utilitários: serialização JSON para respostas e comunicação entre camadas.

**Arquitetura e Organização**
- Camadas separadas: conexão com banco (`connection`), modelos de domínio (`model`), acesso a dados (`dao`), controladores de aplicação (`controller`) e servidor/API (`server`).
- Padrões adotados: uso de DAOs para persistência, controllers para orquestração de regras e um utilitário para JSON.
- Persistência: modelo relacional (arquivo `schema.sql` presente no repositório).

**Tecnologias e Requisitos**
- Linguagem: Java (backend).
- Banco de dados: relacional (SQL) — configurar conforme `schema.sql`.
- Estrutura do projeto: pacotes `model`, `dao`, `controller`, `server`, `connection`, `util`.

**Valor de Negócio**
- Eficiência operacional: reduz tempo gasto em rotinas administrativas.
- Melhoria no atendimento: histórico centralizado melhora a qualidade das interações com clientes.
- Escalabilidade: separação de camadas e API permitem integrar interfaces e expandir funcionalidades.

**Próximos passos sugeridos**
- Implementar testes automatizados para DAOs e controllers.
- Adicionar autenticação/autorização para controle de acesso por função.
- Construir um frontend web/mobile que consuma a API.

---
Documento gerado automaticamente e substituído conforme solicitação do repositório.
