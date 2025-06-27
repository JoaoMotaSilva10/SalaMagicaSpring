USE master IF EXISTS(select * from sys.databases where name='bd_reserva_recurso')
DROP DATABASE bd_reserva_recurso
GO
-- CRIAR UM BANCO DE DADOS
CREATE DATABASE bd_reserva_recurso
GO
-- ACESSAR O BANCO DE DADOS
USE bd_reserva_recurso
GO

CREATE TABLE Usuario
(
    id            INT			IDENTITY,
    nome          VARCHAR(100)	NOT NULL,
    email         VARCHAR(100)	UNIQUE NOT NULL,
    senha         VARCHAR(100)	NOT NULL,
    nivelAcesso   VARCHAR(10)    NULL, -- ADMIN ou ALUNO
    foto			 VARBINARY(MAX) NULL,
    dataCadastro	 SMALLDATETIME	NOT NULL,
    statusUsuario VARCHAR(20)    NOT NULL, -- ATIVO ou INATIVO ou TROCAR_SENHA

    PRIMARY KEY (id)
)
    GO
INSERT Usuario (nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario)
VALUES ('Fulano da Silva', 'fulano@email.com.br', 'MTIzNDU2Nzg=', 'ADMIN', NULL, GETDATE(), 'ATIVO')
INSERT Usuario (nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario)
VALUES ('Beltrana de Sá', 'beltrana@email.com.br', 'MTIzNDU2Nzg=', 'USER', NULL, GETDATE(), 'ATIVO')
INSERT Usuario (nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario)
VALUES ('Sicrana de Oliveira', 'sicrana@email.com.br', 'MTIzNDU2Nzg=', 'USER', NULL, GETDATE(), 'INATIVO')
INSERT Usuario (nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario)
VALUES ('Ordnael Zurc', 'ordnael@email.com.br', 'MTIzNDU2Nzg=', 'USER', NULL, GETDATE(), 'TROCAR_SENHA')
GO


CREATE TABLE Recurso
(
    id				INT		     IDENTITY,
    nome			VARCHAR(100) NOT NULL,
    descricao		VARCHAR(400) NOT NULL,
    tipo			VARCHAR(100) NOT NULL, -- EQUIPAMENTO ou AMBIENTE
    statusRecurso	VARCHAR(10)  NOT NULL, -- ATIVO ou INATIVO

    PRIMARY KEY (id)
)
    GO

CREATE TABLE Reserva
(
    id	            INT			   IDENTITY,
    informacao		VARCHAR(100)   NOT NULL,
    dataCadastro	SMALLDATETIME  NOT NULL,
    dataReservada	SMALLDATETIME  NOT NULL,
    usuario_id		INT			   NOT NULL,
    recurso_id		INT			   NOT NULL,
    statusReserva	VARCHAR(10)	   NOT NULL, -- EM_ANALISE ou ATIVO ou CANCELADA ou INATIVO

    PRIMARY KEY (id),
    FOREIGN KEY (usuario_id) REFERENCES Usuario (id),
    FOREIGN KEY (recurso_id) REFERENCES Recurso (id)
)
    GO

CREATE TABLE Mensagem
(
    id	            INT			  IDENTITY,
    dataMensagem    SMALLDATETIME NOT NULL,
    emissor			VARCHAR(100)  NOT NULL,
    email 	        VARCHAR(100)  NOT NULL,
    telefone	    VARCHAR(20)       NULL,
    texto 	        VARCHAR(400)  NOT NULL,
    statusMensagem  VARCHAR(10)   NOT NULL, -- ATIVO ou INATIVO

    PRIMARY KEY (id)
)
    GO
INSERT Mensagem (dataMensagem, emissor, email, telefone, texto, statusMensagem)
VALUES (GETDATE(), 'Ordnael Zurc', 'ordnael@email.com', '(11) 98765-4123', 'Mensagem de teste', 'ATIVO')
INSERT Mensagem (dataMensagem, emissor, email, telefone, texto, statusMensagem)
VALUES (GETDATE(), 'Maria Onete', 'maria@email.com', null, 'Segunda mensagem de teste', 'ATIVO')
GO

SELECT * FROM Mensagem
SELECT * FROM Usuario

/* VERIFICAR CONEXÕES EXISTENTES */
/*
SELECT * FROM sys.dm_exec_sessions
WHERE database_id = DB_ID('bd_pizzaria_3d')
AND host_name IS NOT NULL
AND program_name LIKE 'Microsoft SQL Server Management Studio%'
*/




