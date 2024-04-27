# TCP-Bidirectional-Communication
Este repositório contém um exemplo de comunicação bidirecional utilizando sockets TCP em Java, com um servidor e clientes capazes de trocar mensagens em formato XML. O código demonstra como implementar a comunicação entre cliente e servidor, incluindo a geração de identificadores para os clientes, tratamento de desconexões e mensagens em XML

Como funciona:
1. O servidor é iniciado e entra em um loop de espera por conexões de clientes.
2. Quando um cliente se conecta, é criada uma nova thread para lidar com a comunicação com esse cliente.
3. O cliente pode enviar mensagens em formato XML para o servidor.
4. O servidor recebe as mensagens XML, processa-as conforme necessário e responde ao cliente também em formato XML.
