# 🖥️ Sala Mágica - Backend API

![Banner](banner.png)

> API REST para sistema de reserva de salas e equipamentos escolares desenvolvida em Spring Boot

## 🚀 Sobre o Projeto

O **Sala Mágica Backend** é uma API REST robusta que gerencia todo o sistema de reservas de salas e equipamentos. Desenvolvida com Spring Boot, oferece endpoints seguros e eficientes para autenticação, gerenciamento de usuários, reservas e recursos.

## ✨ Funcionalidades

### 🔐 **Autenticação & Usuários**
- Login com validação de credenciais
- Cadastro de novos usuários
- **Sistema completo de recuperação de senha**
  - Geração de tokens de 6 dígitos
  - Envio de e-mail automático
  - Validação de expiração (15 minutos)
  - Redefinição segura de senha
- Gerenciamento de perfis de usuário

### 📋 **Reservas**
- CRUD completo de reservas
- Reservas de salas e equipamentos
- Validação de disponibilidade
- Histórico de reservas por usuário
- Status de reservas (ativa, cancelada, concluída)

### 🏢 **Recursos**
- Gerenciamento de salas
- Gerenciamento de equipamentos
- Controle de disponibilidade
- Categorização por tipo

### 📧 **Sistema de E-mail**
- Envio automático de códigos de recuperação
- Templates personalizados
- Configuração SMTP flexível
- Suporte a Gmail com senha de app

### 📊 **Mensagens & Suporte**
- Sistema de mensagens de suporte
- Comunicação usuário-administrador

## 🛠️ Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Spring Boot 3.0.5** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Mail** - Envio de e-mails
- **SQL Server** - Banco de dados
- **Maven** - Gerenciamento de dependências
- **Lombok** - Redução de boilerplate

## 📦 Dependências Principais

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
    </dependency>
    <dependency>
        <groupId>com.microsoft.sqlserver</groupId>
        <artifactId>mssql-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+
- SQL Server
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Instalação

1. **Clone o repositório**
```bash
git clone https://github.com/seu-usuario/SalaMagicaSpring.git
cd SalaMagicaSpring
```

2. **Configure o banco de dados**
   - Execute o script `bd_reserva_recurso.sql`
   - Configure as credenciais em `application.properties`

3. **Configure o e-mail (opcional)**
```properties
# application.properties
spring.mail.username=seu-email@gmail.com
spring.mail.password=sua-senha-de-app
```

4. **Execute a aplicação**
```bash
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

## 🏗️ Estrutura do Projeto

```
src/main/java/br/com/itb/miniprojetospring/
├── control/                    # Controllers REST
│   ├── UsuarioController.java
│   ├── ReservaController.java
│   ├── RecursoController.java
│   └── MensagemController.java
├── service/                    # Lógica de negócio
│   ├── UsuarioService.java
│   ├── EmailService.java
│   ├── ReservaService.java
│   └── RecursoService.java
├── model/                      # Entidades JPA
│   ├── Usuario.java
│   ├── Reserva.java
│   ├── Recurso.java
│   ├── TokenRecuperacao.java
│   └── Mensagem.java
├── config/                     # Configurações
│   └── WebConfig.java
└── MiniprojetospringApplication.java
```

## 🔌 Endpoints da API

### 👤 **Usuários**
```http
POST   /usuarios                    # Cadastrar usuário
GET    /usuarios                    # Listar usuários
PUT    /usuarios/{id}               # Atualizar usuário
DELETE /usuarios/{id}               # Deletar usuário
POST   /usuarios/login              # Login
GET    /usuarios/perfil             # Buscar perfil
```

### 🔐 **Recuperação de Senha**
```http
POST   /usuarios/esqueci-senha      # Solicitar código
POST   /usuarios/redefinir-senha    # Redefinir senha
```

### 📋 **Reservas**
```http
POST   /reservas                    # Criar reserva
GET    /reservas                    # Listar reservas
GET    /reservas/user/{id}          # Reservas por usuário
PUT    /reservas/{id}               # Atualizar reserva
DELETE /reservas/{id}               # Cancelar reserva
```

### 🏢 **Recursos**
```http
GET    /recursos                    # Listar recursos
POST   /recursos                    # Criar recurso
PUT    /recursos/{id}               # Atualizar recurso
DELETE /recursos/{id}               # Deletar recurso
```

### 📧 **Mensagens**
```http
POST   /mensagens                   # Enviar mensagem
GET    /mensagens                   # Listar mensagens
```

## 🗄️ Modelo de Dados

### Usuario
```java
@Entity
public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String senha;        // Base64 encoded
    private String nivelAcesso;  // ADMIN, USER, ALUNO
    private String statusUsuario; // ATIVO, INATIVO, TROCAR_SENHA
    private LocalDateTime dataCadastro;
    // ... outros campos
}
```

### TokenRecuperacao
```java
@Entity
public class TokenRecuperacao {
    private Long id;
    private String token;        // 6 dígitos
    private Usuario usuario;
    private LocalDateTime dataExpiracao; // 15 minutos
    private boolean usado;
}
```

## ⚙️ Configuração

### Banco de Dados
```properties
spring.datasource.url=jdbc:sqlserver://localhost;databaseName=bd_reserva_recurso
spring.datasource.username=sa
spring.datasource.password=@ITB123456
```

### E-mail (Gmail)
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu-email@gmail.com
spring.mail.password=sua-senha-de-app
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

## 🔐 Segurança

- **Senhas criptografadas** com Base64
- **Tokens de recuperação** com expiração
- **Validação de entrada** em todos os endpoints
- **CORS configurado** para frontend
- **Controle de acesso** por nível de usuário

## 📧 Sistema de E-mail

### Recuperação de Senha
1. Usuário solicita recuperação via e-mail
2. Sistema gera token de 6 dígitos
3. Token é salvo no banco com expiração de 15 minutos
4. E-mail é enviado automaticamente
5. Usuário usa o token para redefinir senha
6. Token é marcado como usado

### Template de E-mail
```
Assunto: Recuperação de Senha - Sala Mágica

Olá!

Você solicitou a recuperação de senha do sistema Sala Mágica.

Use o código abaixo para redefinir sua senha:

Código: 123456

Este código expira em 15 minutos.

Se você não solicitou esta recuperação, ignore este e-mail.

Atenciosamente,
Equipe Sala Mágica
```

## 🚀 Deploy

### Desenvolvimento
```bash
mvn spring-boot:run
```

### Produção
```bash
mvn clean package
java -jar target/miniprojetospring-0.0.1-SNAPSHOT.jar
```

### Docker (opcional)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

## 📊 Monitoramento

- **Logs detalhados** com Spring Boot Actuator
- **Métricas de performance**
- **Health checks** automáticos

## 🧪 Testes

```bash
mvn test
```

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 👥 Equipe

- **João Pedro Mota Silva** - Desenvolvedor Principal
- **Gabriel Barbosa** - Colaborador

## 📞 Contato

- Email: joaomotasilva10@outlook.com
- GitHub: [@JoaoMotaSilva10](https://github.com/JoaoMotaSilva10)

---

⭐ **Se este projeto te ajudou, deixe uma estrela!**