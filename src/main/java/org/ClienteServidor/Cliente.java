package org.ClienteServidor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Conectado no servidor.");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.print("Digite sua mensagem: ");
                String message = reader.readLine();

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();
                Element body = doc.createElement("Request");
                body.appendChild(doc.createTextNode(message));
                doc.appendChild(body);

                StringWriter writer = new StringWriter();
                TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc), new StreamResult(writer));
                String reqXML = writer.toString();

                out.writeUTF(reqXML);

                String respXML = in.readUTF();
                System.out.println("Resposta do servidor: " + extrairConteudoXML(respXML));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String extrairConteudoXML(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
        Element root = doc.getDocumentElement();
        return root.getTextContent();
    }
}
