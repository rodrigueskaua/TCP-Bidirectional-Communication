package org.ClienteServidor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor iniciado");
            System.out.println("Aguardando conexões...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private String clienteID;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.clienteID = gerarClienteID();
    }

    private String gerarClienteID() {
        Random random = new Random();
        int randomNum = random.nextInt(9000) + 1000;

        String ipAddress = "";
        int port = 0;
        try {
            InetSocketAddress address = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
            ipAddress = address.getAddress().getHostAddress();
            port = address.getPort();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddress + ":" + port + "-" + randomNum;
    }

    @Override
    public void run() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            while (true) {
                String receivedMessage = extrairConteudoXML(in.readUTF());
                System.out.println("Mensagem recebida do cliente: " + clienteID + "\n" + "-" + receivedMessage);

                String hourNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
                String responseMessage = "Recebido às " + hourNow  + ": "+ receivedMessage;

                Document doc = dBuilder.newDocument();
                Element body = doc.createElement("Request");
                body.appendChild(doc.createTextNode(responseMessage));
                doc.appendChild(body);

                StringWriter writer = new StringWriter();
                TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc), new StreamResult(writer));
                String reqXML = writer.toString();

                out.writeUTF(reqXML);
            }
        } catch (EOFException eof) {
            System.out.println("Conexão encerrada pelo cliente: " + clienteID);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

     String extrairConteudoXML(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
        Element root = doc.getDocumentElement();
        return root.getTextContent();
    }
}
