import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class UnirArchivos {

    public static void main(String[] args) {
        try {
            Map<String, Persona> personas = new HashMap<>();

            // 1. Leer txt (NAN, adina)
            BufferedReader br = new BufferedReader(new FileReader("nan.txt"));
            String line;
            br.readLine(); // saltar cabecera
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String nan = parts[0].trim();
                    String adina = parts[1].trim();
                    personas.putIfAbsent(nan, new Persona(nan));
                    personas.get(nan).adina = adina;
                }
            }
            br.close();

            // 2. Leer json simple (sin librería externa)
            br = new BufferedReader(new FileReader("nan.json"));
            StringBuilder jsonBuilder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                jsonBuilder.append(line.trim());
            }
            br.close();

            String jsonText = jsonBuilder.toString();
            jsonText = jsonText.substring(1, jsonText.length() - 1); // quitar [ ]
            String[] objetos = jsonText.split("\\},\\{");
            for (String obj : objetos) {
                obj = obj.replace("{", "").replace("}", "").replace("\"", "");
                String[] campos = obj.split(",");
                String nan = "", izena = "", abizena = "";
                for (String campo : campos) {
                    String[] kv = campo.split(":");
                    if (kv.length < 2) continue;
                    String key = kv[0].trim();
                    String value = kv[1].trim();
                    switch (key) {
                        case "NAN" -> nan = value;
                        case "IZENA" -> izena = value;
                        case "ABIZENA" -> abizena = value;
                    }
                }
                if (!nan.isEmpty()) {
                    personas.putIfAbsent(nan, new Persona(nan));
                    personas.get(nan).izena = izena;
                    personas.get(nan).abizena = abizena;
                }
            }

            // 3. Leer xml (dni = NAN, helbidea)
            File xmlFile = new File("nan.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("persona");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    String nan = elem.getElementsByTagName("dni").item(0).getTextContent();
                    String helbidea = elem.getElementsByTagName("helbidea").item(0).getTextContent();
                    personas.putIfAbsent(nan, new Persona(nan));
                    personas.get(nan).helbidea = helbidea;
                }
            }

            // 4. Crear CSV final
            PrintWriter pw = new PrintWriter(new File("nan.csv"));
            pw.println("NAN,IZENA,ABIZENA,adina,helbidea");
            for (Persona p : personas.values()) {
                pw.printf("%s,%s,%s,%s,%s\n",
                        p.nan,
                        p.izena != null ? p.izena : "",
                        p.abizena != null ? p.abizena : "",
                        p.adina != null ? p.adina : "",
                        p.helbidea != null ? p.helbidea : ""
                );
            }
            pw.close();
            System.out.println("CSV generado correctamente: nan.csv");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Persona {
        String nan;
        String izena;
        String abizena;
        String adina;
        String helbidea;

        Persona(String nan) {
            this.nan = nan;
        }
    }
}
