# Comunicação Bidirecional TCP

Este projeto é um exemplo de comunicação bidirecional utilizando sockets TCP em Java, desenvolvido como parte da disciplina de Redes de Computadores. O projeto inclui um servidor e um cliente que trocam mensagens no formato XML. 

## Funcionalidade

- **Servidor:** Escuta na porta 12345 e gerencia conexões de múltiplos clientes simultaneamente. Cada cliente é atendido por uma nova thread, que processa e responde às mensagens enviadas.
- **Cliente:** Conecta-se ao servidor na mesma porta, envia mensagens no formato XML e exibe as respostas recebidas do servidor.

## Estrutura do Projeto

- **Servidor (Servidor.java):** 
  - Inicializa o `ServerSocket` e aguarda conexões.
  - Para cada cliente, cria uma nova thread (`ClientHandler`) para gerenciar a comunicação.
  - Gera um identificador único para cada cliente com base no endereço IP e na porta.
  - Processa as mensagens XML recebidas e envia respostas no mesmo formato.

- **Cliente (Cliente.java):**
  - Conecta-se ao servidor usando `Socket`.
  - Permite ao usuário digitar mensagens, que são enviadas para o servidor no formato XML.
  - Recebe e exibe as respostas do servidor.

## Código

### Cliente.java

O cliente estabelece uma conexão com o servidor e permite ao usuário enviar mensagens. As mensagens são encapsuladas em XML e enviadas ao servidor. As respostas do servidor também são recebidas e exibidas no formato XML.

### Servidor.java

O servidor escuta na porta 12345 e gerencia as conexões de clientes. Para cada cliente, uma nova thread é criada para processar mensagens. O servidor responde às mensagens com um timestamp indicando quando a mensagem foi recebida.

## Instruções para Execução

1. **Compilar o Código:**

    ```bash
    javac Servidor.java Cliente.java
    ```

2. **Executar o Servidor:**

    ```bash
    java Servidor
    ```

3. **Executar o Cliente:**

    ```bash
    java Cliente
    ```

Certifique-se de que o servidor esteja em execução antes de iniciar o cliente.

## Dependências

- Java JDK 8 ou superior.
