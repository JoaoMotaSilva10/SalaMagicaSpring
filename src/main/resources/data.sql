-- Dados iniciais já são inseridos pelo DataInitializer.java
-- Este arquivo pode ser removido ou mantido vazio

-- Inserção de Recursos (Ambientes e Equipamentos)
INSERT INTO Recurso (nome, descricao, tipo, statusRecurso) VALUES ('Auditório', 'Auditório principal do campus', 'AMBIENTE', 'ATIVO');
INSERT INTO Recurso (nome, descricao, tipo, statusRecurso) VALUES ('Sala de Aula', 'Sala padrão para turmas regulares', 'AMBIENTE', 'ATIVO');
INSERT INTO Recurso (nome, descricao, tipo, statusRecurso) VALUES ('Laboratório de Informática', 'Espaço com computadores para aulas práticas', 'AMBIENTE', 'ATIVO');
INSERT INTO Recurso (nome, descricao, tipo, statusRecurso) VALUES ('Laboratório de Eletroeletrônica', 'Laboratório para montagem de circuitos', 'AMBIENTE', 'ATIVO');
INSERT INTO Recurso (nome, descricao, tipo, statusRecurso) VALUES ('Laboratório de Telecomunicações', 'Laboratório com equipamentos de redes e telecom', 'AMBIENTE', 'ATIVO');
INSERT INTO Recurso (nome, descricao, tipo, statusRecurso) VALUES ('Computador', 'Computador desktop padrão', 'EQUIPAMENTO', 'ATIVO');
INSERT INTO Recurso (nome, descricao, tipo, statusRecurso) VALUES ('Chromebook', 'Notebook leve com ChromeOS', 'EQUIPAMENTO', 'ATIVO');
INSERT INTO Recurso (nome, descricao, tipo, statusRecurso) VALUES ('Datashow', 'Projetor multimídia portátil', 'EQUIPAMENTO', 'ATIVO');

