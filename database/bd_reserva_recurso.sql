-- Script de criação do banco de dados Sala Mágica
-- SQL Server - Baseado no modelo do backend Spring Boot

-- Criar banco de dados
CREATE DATABASE bd_reserva_recurso;
GO

USE bd_reserva_recurso;
GO

-- Tabela base Pessoa (herança JOINED)
CREATE TABLE Pessoa (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) NOT NULL UNIQUE,
    senha NVARCHAR(255) NOT NULL,
    dataCadastro DATETIME2 DEFAULT GETDATE(),
    statusUsuario NVARCHAR(20) DEFAULT 'ATIVO'
);

-- Tabela Aluno (herda de Pessoa)
CREATE TABLE Aluno (
    id BIGINT PRIMARY KEY,
    rm NVARCHAR(20),
    turma NVARCHAR(50),
    serie NVARCHAR(20),
    periodo NVARCHAR(20),
    cpf NVARCHAR(14),
    FOREIGN KEY (id) REFERENCES Pessoa(id)
);

-- Tabela Gerenciador (herda de Pessoa)
CREATE TABLE Gerenciador (
    id BIGINT PRIMARY KEY,
    unidade NVARCHAR(100),
    departamento NVARCHAR(100),
    cargo NVARCHAR(100),
    FOREIGN KEY (id) REFERENCES Pessoa(id)
);

-- Tabela Administrador (herda de Pessoa)
CREATE TABLE Administrador (
    id BIGINT PRIMARY KEY,
    nivelPermissao NVARCHAR(20),
    setor NVARCHAR(100),
    FOREIGN KEY (id) REFERENCES Pessoa(id)
);

-- Tabela TokenRecuperacao
CREATE TABLE TokenRecuperacao (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    token NVARCHAR(6) NOT NULL,
    pessoa_id BIGINT NOT NULL,
    dataExpiracao DATETIME2 NOT NULL,
    usado BIT DEFAULT 0,
    FOREIGN KEY (pessoa_id) REFERENCES Pessoa(id)
);

-- Tabela Recurso
CREATE TABLE Recurso (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nome NVARCHAR(100) NOT NULL,
    descricao NVARCHAR(255),
    tipo NVARCHAR(50) NOT NULL,
    statusRecurso NVARCHAR(20) DEFAULT 'ATIVO'
);

-- Tabela Reserva
CREATE TABLE Reserva (
    id INT IDENTITY(1,1) PRIMARY KEY,
    informacao NVARCHAR(255),
    dataCadastro DATETIME2 DEFAULT GETDATE(),
    dataReservada DATETIME2 NOT NULL,
    pessoa_id BIGINT NOT NULL,
    recurso_id INT NOT NULL,
    statusReserva NVARCHAR(20) DEFAULT 'ATIVA',
    FOREIGN KEY (pessoa_id) REFERENCES Pessoa(id),
    FOREIGN KEY (recurso_id) REFERENCES Recurso(id)
);

-- Tabela Mensagem
CREATE TABLE Mensagem (
    id INT IDENTITY(1,1) PRIMARY KEY,
    dataMensagem DATETIME2 DEFAULT GETDATE(),
    emissor NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) NOT NULL,
    rm NVARCHAR(20),
    assunto NVARCHAR(100),
    texto NVARCHAR(MAX) NOT NULL,
    statusMensagem NVARCHAR(20) NOT NULL
);

-- Inserir dados iniciais

-- Administrador padrão
INSERT INTO Pessoa (nome, email, senha, statusUsuario) 
VALUES ('Admin Sistema', 'admin@salamagica.com', 'YWRtaW4xMjM=', 'ATIVO');

INSERT INTO Administrador (id, nivelPermissao, setor) 
VALUES (1, 'SUPER_ADMIN', 'TI');

-- Gerenciador exemplo
INSERT INTO Pessoa (nome, email, senha, statusUsuario) 
VALUES ('João Gerenciador', 'joao.ger@escola.com', 'MTIzNDU2', 'ATIVO');

INSERT INTO Gerenciador (id, unidade, departamento, cargo) 
VALUES (2, 'Campus Central', 'Coordenação', 'Coordenador');

-- Aluno exemplo
INSERT INTO Pessoa (nome, email, senha, statusUsuario) 
VALUES ('Maria Aluna', 'maria.aluna@escola.com', 'MTIzNDU2', 'ATIVO');

INSERT INTO Aluno (id, rm, turma, serie, periodo, cpf) 
VALUES (3, '12345', '3A', '3º Ano', 'Manhã', '123.456.789-00');

-- Recursos (Ambientes e Equipamentos)
INSERT INTO Recurso (nome, descricao, tipo, statusRecurso) VALUES
('Auditório', 'Auditório principal do campus', 'SALA', 'ATIVO'),
('Sala de Aula 101', 'Sala padrão para turmas regulares', 'SALA', 'ATIVO'),
('Laboratório de Informática', 'Espaço com computadores para aulas práticas', 'LABORATORIO', 'ATIVO'),
('Laboratório de Eletroeletrônica', 'Laboratório para montagem de circuitos', 'LABORATORIO', 'ATIVO'),
('Laboratório de Telecomunicações', 'Laboratório com equipamentos de redes e telecom', 'LABORATORIO', 'ATIVO'),
('Computador Desktop', 'Computador desktop padrão', 'EQUIPAMENTO', 'ATIVO'),
('Chromebook', 'Notebook leve com ChromeOS', 'EQUIPAMENTO', 'ATIVO'),
('Datashow', 'Projetor multimídia portátil', 'EQUIPAMENTO', 'ATIVO');

-- Reserva exemplo
INSERT INTO Reserva (informacao, dataReservada, pessoa_id, recurso_id, statusReserva) 
VALUES ('Aula prática de programação', '2024-01-15 14:00:00', 3, 3, 'EM_ANALISE');

-- Criar índices para performance
CREATE INDEX IX_Reserva_DataReservada ON Reserva(dataReservada);
CREATE INDEX IX_Reserva_Pessoa ON Reserva(pessoa_id);
CREATE INDEX IX_Reserva_Recurso ON Reserva(recurso_id);
CREATE INDEX IX_TokenRecuperacao_Pessoa ON TokenRecuperacao(pessoa_id);
CREATE INDEX IX_Pessoa_Email ON Pessoa(email);

GO

SELECT * FROM Aluno;
SELECT * FROM Pessoa;
SELECT * FROM Gerenciador;
SELECT * FROM Administrador;
SELECT * FROM TokenRecuperacao;
SELECT * FROM Recurso;
SELECT * FROM Reserva;
SELECT * FROM Mensagem