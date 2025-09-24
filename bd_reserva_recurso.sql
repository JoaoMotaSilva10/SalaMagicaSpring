USE master;
IF EXISTS(SELECT * FROM sys.databases WHERE name='bd_reserva_recurso')
    DROP DATABASE bd_reserva_recurso;
GO

-- CRIAR UM BANCO DE DADOS
CREATE DATABASE bd_reserva_recurso;
GO

-- ACESSAR O BANCO DE DADOS
USE bd_reserva_recurso;
GO

-- Criação da tabela Usuario
CREATE TABLE Usuario (
                         id BIGINT IDENTITY(1,1) PRIMARY KEY,
                         nome NVARCHAR(MAX) NOT NULL,
                         email NVARCHAR(255) UNIQUE NOT NULL,
                         senha NVARCHAR(MAX) NOT NULL,
                         nivelAcesso NVARCHAR(50) NULL,  -- ADMIN ou USER
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

INSERT INTO Usuario (nome, email, senha, nivelAcesso, statusUsuario)
VALUES ('João Mota', 'joaopedromotasilva200@gmail.com', 'MTIzNDU2Nzg=', 'ADMIN', 'ATIVO');



-- Criação da tabela Recurso
CREATE TABLE Recurso (
                         id BIGINT IDENTITY(1,1) PRIMARY KEY,
                         nome NVARCHAR(MAX) NOT NULL,
                         descricao NVARCHAR(MAX) NOT NULL,
                         tipo NVARCHAR(50) NOT NULL,  -- EQUIPAMENTO ou AMBIENTE
                         statusRecurso NVARCHAR(50) NOT NULL  -- ATIVO ou INATIVO
);
GO

-- Inserção de Recursos (Ambientes e Equipamentos)
INSERT INTO Recurso (nome, descricao, tipo, statusRecurso) VALUES
('Auditório', 'Auditório principal do campus', 'AMBIENTE', 'ATIVO'),
('Sala de Aula', 'Sala padrão para turmas regulares', 'AMBIENTE', 'ATIVO'),
('Laboratório de Informática', 'Espaço com computadores para aulas práticas', 'AMBIENTE', 'ATIVO'),
('Laboratório de Eletroeletrônica', 'Laboratório para montagem de circuitos', 'AMBIENTE', 'ATIVO'),
('Laboratório de Telecomunicações', 'Laboratório com equipamentos de redes e telecom', 'AMBIENTE', 'ATIVO'),
('Computador', 'Computador desktop padrão', 'EQUIPAMENTO', 'ATIVO'),
('Chromebook', 'Notebook leve com ChromeOS', 'EQUIPAMENTO', 'ATIVO'),
('Datashow', 'Projetor multimídia portátil', 'EQUIPAMENTO', 'ATIVO');
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

-- Inserção de dados na tabela Reserva (agora os recursos já existem!)
INSERT INTO Reserva (informacao, dataReservada, usuario_id, recurso_id, statusReserva) VALUES
('Reunião com equipe de projeto', '2025-09-10T14:00:00-03:00', 1, 1, 'ATIVO'),
('Aula prática de programação', '2025-09-11T08:00:00-03:00', 2, 3, 'ATIVO'),
('Reserva para apresentação', '2025-09-12T10:00:00-03:00', 4, 8, 'EM_ANALISE'),
('Uso do computador para pesquisa', '2025-09-13T09:00:00-03:00', 3, 6, 'CANCELADA'),
('Sala para aula de reforço', '2025-09-14T16:00:00-03:00', 1, 2, 'ATIVO');
GO


-- Criação da tabela TokenRecuperacao
CREATE TABLE TokenRecuperacao (
                                  id BIGINT IDENTITY(1,1) PRIMARY KEY,
                                  token NVARCHAR(10) NOT NULL,
                                  usuario_id BIGINT NOT NULL,
                                  dataExpiracao DATETIME2 NOT NULL,
                                  usado BIT NOT NULL DEFAULT 0,
                                  CONSTRAINT fk_token_usuario FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
);
GO


-- Criação da tabela Perfil
CREATE TABLE Perfil (
                        id BIGINT IDENTITY(1,1) PRIMARY KEY,
                        usuario_id BIGINT NOT NULL UNIQUE,
                        rm NVARCHAR(50) NULL,
                        unidade NVARCHAR(MAX) NULL,
                        turma NVARCHAR(50) NULL,
                        serie NVARCHAR(50) NULL,
                        periodo NVARCHAR(50) NULL,
                        cpf NVARCHAR(14) NULL,
                        CONSTRAINT fk_perfil_usuario FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
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


-- Inserção de perfis para usuários existentes (opcional)
INSERT INTO Perfil (usuario_id, rm, unidade, turma, serie, periodo, cpf) VALUES
(1, '12345', 'Campus São Paulo', 'A1', '3º Ano', 'Manhã', '123.456.789-00'),
(2, '67890', 'Campus São Paulo', 'B2', '2º Ano', 'Tarde', '987.654.321-00');
GO

-- Consultas para verificar dados
SELECT * FROM Usuario;
SELECT * FROM Perfil;
SELECT * FROM Recurso;
SELECT * FROM Reserva;
SELECT * FROM Mensagem;
GO
