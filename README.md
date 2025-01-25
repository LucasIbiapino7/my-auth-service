# 🔐 Sistema de Autenticação com Spring Boot & JWT

Este projeto implementa um sistema de autenticação robusto utilizando **Spring Boot**, **Spring Security** e **JWT**. Ele fornece funcionalidades para **login, registro de usuários, recuperação de senha e redefinição de senha**.

## Tecnologias Utilizadas

- **Spring Boot 3+**
- **Spring Security**
- **JWT (JSON Web Token)**
- **Spring Data JPA**
- **Banco de Dados H2**
- **Open Feign**
- **Envio de emails (para envio de e-mails o sistema utiliza um outro serviço feito por mim para o envio de emails por meio do Open Feign)**
---

## 🔧 Como Executar o Projeto

### 1️⃣ **Clone o Repositório**
```bash
git clone https://github.com/seu-usuario/nome-do-repositorio.git
```

### 3️⃣ **Execute a Aplicação na sua IDE ou com o comando abaixo**
```bash
mvn spring-boot:run
```
A API estará rodando em `http://localhost:8080`

---

## 🔗 Endpoints da API

### 🔑 **Autenticação**
| Método  | Endpoint     | Descrição |
|---------|-------------|-----------|
| `POST`  | `/login`    | Autentica um usuário e retorna um token JWT |
| `POST`  | `/register` | Registra um novo usuário |

### 🔄 **Recuperação de Senha**
| Método  | Endpoint               | Descrição |
|---------|-------------------------|-----------|
| `POST`  | `/recover-token`        | Envia um e-mail com o link de recuperação |
| `PUT`   | `/new-password`         | Redefine a senha usando um token de recuperação |

 **Observação:** O envio de email é feito por um outro serviço desenvolvido por mim, então não está nesse projeto, ele apenas envia a requisição!
---

## 📜 Licença
Este projeto está sob a licença MIT.   

---

💡 **Feedbacks são bem-vindos!** 

