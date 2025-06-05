USE master IF EXISTS (SELECT * FROM sys.databases WHERE name='miniprojeto') 
DROP DATABASE bd_salamagica
GO

CREATE DATABASE bd_salamagica
GO

USE bd_salamagica
GO

CREATE TABLE Funcionario
(
    id BIGINT IDENTITY PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    nome VARCHAR(100) NULL,
    senha VARCHAR(100) NOT NULL
)
GO

CREATE TABLE FuncionarioLogin
(
    id BIGINT IDENTITY PRIMARY KEY,
    funcionario_id BIGINT NOT NULL, -- Chave estrangeira
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    nome VARCHAR(100) NULL,
    FOREIGN KEY (funcionario_id) REFERENCES Funcionario(id)
)
GO

-- Inserindo um funcionário
INSERT INTO Funcionario (nome, email, senha) 
VALUES ('gabriel', 'gabriel@gmail.com', 'leleu100')
GO

-- Declara a variável e pega o último ID inserido
DECLARE @FuncionarioId BIGINT = SCOPE_IDENTITY();

-- Inserindo um login para o funcionário
INSERT INTO FuncionarioLogin (funcionario_id, nome, email, senha) 
VALUES (@FuncionarioId, 'gabriel', 'gabriel@gmail.com', 'leleu100')
GO

SELECT 
    f.id AS FuncionarioId,
    f.nome AS FuncionarioNome,
    fl.email AS LoginEmail,
    fl.senha AS LoginSenha
FROM 
    Funcionario f
JOIN 
    FuncionarioLogin fl ON f.id = fl.funcionario_id;
