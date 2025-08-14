USE master IF EXISTS(select * from sys.databases where name='bd_reserva_recurso')
DROP DATABASE bd_reserva_recurso
GO
-- CRIAR UM BANCO DE DADOS
CREATE DATABASE bd_reserva_recurso
GO
-- ACESSAR O BANCO DE DADOS
USE bd_reserva_recurso
GO

-- Criação da tabela Usuario (sem o campo 'foto')
CREATE TABLE Usuario (
                         id BIGINT IDENTITY(1,1) PRIMARY KEY,
                         nome NVARCHAR(MAX) NOT NULL,
                         email NVARCHAR(255) UNIQUE NOT NULL,
                         senha NVARCHAR(MAX) NOT NULL,
                         nivelAcesso NVARCHAR(50) NULL,  -- ADMIN ou ALUNO
                         dataCadastro DATETIMEOFFSET NOT NULL DEFAULT SYSUTCDATETIME(),
                         statusUsuario NVARCHAR(50) NOT NULL  -- ATIVO, INATIVO ou TROCAR_SENHA
);
GO

-- Inserção de dados na tabela Usuario
INSERT INTO Usuario (nome, email, senha, nivelAcesso, statusUsuario) VALUES
    ('Fulano da Silva', 'fulano@email.com.br', 'MTIzNDU2Nzg=', 'ADMIN', 'ATIVO'),
    ('Beltrana de Sá', 'beltrana@email.com.br', 'MTIzNDU2Nzg=', 'USER', 'ATIVO'),
    ('Sicrana de Oliveira', 'sicrana@email.com.br', 'MTIzNDU2Nzg=', 'USER', 'INATIVO'),
    ('Ordnael Zurc', 'ordnael@email.com.br', 'MTIzNDU2Nzg=', 'USER', 'TROCAR_SENHA');
GO

-- Criação da tabela Recurso
CREATE TABLE Recurso (
                         id BIGINT IDENTITY(1,1) PRIMARY KEY,
                         nome NVARCHAR(MAX) NOT NULL,
                         descricao NVARCHAR(MAX) NOT NULL,
                         tipo NVARCHAR(50) NOT NULL,  -- EQUIPAMENTO ou AMBIENTE
                         statusRecurso NVARCHAR(50) NOT NULL  -- ATIVO ou INATIVO
);
GO

-- Criação da tabela Reserva
CREATE TABLE Reserva (
                         id BIGINT IDENTITY(1,1) PRIMARY KEY,
                         informacao NVARCHAR(MAX) NOT NULL,
                         dataCadastro DATETIMEOFFSET NOT NULL DEFAULT SYSUTCDATETIME(),
                         dataReservada DATETIMEOFFSET NOT NULL,
                         usuario_id BIGINT NOT NULL,
                         recurso_id BIGINT NOT NULL,
                         statusReserva NVARCHAR(50) NOT NULL,  -- EM_ANALISE, ATIVO, CANCELADA, INATIVO
                         CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
                         CONSTRAINT fk_recurso FOREIGN KEY (recurso_id) REFERENCES Recurso(id)
);
GO

-- Criação da tabela Mensagem
CREATE TABLE Mensagem (
                          id BIGINT IDENTITY(1,1) PRIMARY KEY,
                          dataMensagem DATETIMEOFFSET NOT NULL DEFAULT SYSUTCDATETIME(),
                          emissor NVARCHAR(MAX) NOT NULL,
                          email NVARCHAR(255) NOT NULL,
                          telefone NVARCHAR(50) NULL,
                          texto NVARCHAR(MAX) NOT NULL,
                          statusMensagem NVARCHAR(50) NOT NULL  -- ATIVO ou INATIVO
);
GO

-- Inserção de dados na tabela Mensagem
INSERT INTO Mensagem (emissor, email, telefone, texto, statusMensagem) VALUES
    ('Ordnael Zurc', 'ordnael@email.com', '(11) 98765-4123', 'Mensagem de teste', 'ATIVO'),
    ('Maria Onete', 'maria@email.com', NULL, 'Segunda mensagem de teste', 'ATIVO');
GO

-- Inserção de dados na tabela Reserva
INSERT INTO Reserva (informacao, dataReservada, usuario_id, recurso_id, statusReserva)
VALUES
('Reunião com equipe de projeto', '2025-09-10T14:00:00-03:00', 1, 1, 'ATIVO'),  -- Auditório
('Aula prática de programação', '2025-09-11T08:00:00-03:00', 2, 3, 'ATIVO'),      -- Laboratório de Informática
('Reserva para apresentação', '2025-09-12T10:00:00-03:00', 4, 8, 'EM_ANALISE'),  -- Datashow
('Uso do computador para pesquisa', '2025-09-13T09:00:00-03:00', 3, 6, 'CANCELADA'), -- Computador
('Sala para aula de reforço', '2025-09-14T16:00:00-03:00', 1, 2, 'ATIVO');        -- Sala de Aula
GO


-- Ambientes (tipo: AMBIENTE)
INSERT INTO Recurso (nome, descricao, tipo, statusRecurso) VALUES
    ('Auditório', 'Auditório principal do campus', 'AMBIENTE', 'ATIVO'),
    ('Sala de Aula', 'Sala padrão para turmas regulares', 'AMBIENTE', 'ATIVO'),
    ('Laboratório de Informática', 'Espaço com computadores para aulas práticas', 'AMBIENTE', 'ATIVO'),
    ('Laboratório de Eletroeletrônica', 'Laboratório para montagem de circuitos', 'AMBIENTE', 'ATIVO'),
    ('Laboratório de Telecomunicações', 'Laboratório com equipamentos de redes e telecom', 'AMBIENTE', 'ATIVO');
GO

-- Equipamentos (tipo: EQUIPAMENTO)
INSERT INTO Recurso (nome, descricao, tipo, statusRecurso) VALUES
    ('Computador', 'Computador desktop padrão', 'EQUIPAMENTO', 'ATIVO'),
    ('Chromebook', 'Notebook leve com ChromeOS', 'EQUIPAMENTO', 'ATIVO'),
    ('Datashow', 'Projetor multimídia portátil', 'EQUIPAMENTO', 'ATIVO');
GO

-- Consultas para verificar dados
SELECT * FROM Mensagem;
SELECT * FROM Usuario;
SELECT * FROM Recurso;
SELECT * FROM Reserva;
GO