import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Menu {
    static final String TXT = "nan.txt";
    static final String JSON = "nan.json";
    static final String XML = "nan.xml";
    static final String CSV = "nan.csv";
    // Colores ANSI para consola
    static final String RESET = "\u001B[0m";
    static final String RED = "\u001B[31m";
    static final String GREEN = "\u001B[32m";
    static final String BLUE = "\u001B[34m";
    static final String YELLOW = "\u001B[33m";
    static final String CYAN = "\u001B[36m";
    static final String PURPLE = "\u001B[35m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 5) {
            System.out.println(BLUE + "\n--- MENU PRINCIPAL ---" + RESET);
            System.out.println("1. Ver archivos");
            System.out.println("2. Buscar por NAN");
            System.out.println("3. Editar archivos");
            System.out.println("4. Borrar archivos");
            System.out.println("5. Salir");
            System.out.print(YELLOW + "Elige una opcion: " + RESET);

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(RED + "Entrada no valida. Por favor, introduce un numero." + RESET);
                continue;
            }

            switch (opcion) {
                // menu ver archivos
                case 1:
                    System.out.println(GREEN + "\n--- VER ARCHIVOS ---" + RESET);
                    System.out.println("1. Ver TXT");
                    System.out.println("2. Ver JSON");
                    System.out.println("3. Ver XML");
                    System.out.println("4. Ver CSV");
                    System.out.print(YELLOW + "Elige una opcion: " + RESET);

                    int subOpcionVer = 0;
                    try {
                        subOpcionVer = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(RED + "Entrada no valida. Por favor, introduce un numero." + RESET);
                        continue;
                    }

                    switch (subOpcionVer) {
                        case 1:
                            verTXT();
                            break;
                        case 2:
                            verJSON();
                            break;
                        case 3:
                            verXML();
                            break;
                        case 4:
                            verCSV();
                            break;
                        default:
                            System.out.println(RED + "Opcion no valida." + RESET);
                    }
                    break;
                // menu buscar por nan
                case 2:
                    System.out.println(GREEN + "\n--- BUSCAR POR NAN ---" + RESET);
                    System.out.print(YELLOW + "Introduce el NAN a buscar: " + RESET);
                    String nanBuscar = sc.nextLine();

                    System.out.println("1. Buscar en TXT");
                    System.out.println("2. Buscar en JSON");
                    System.out.println("3. Buscar en XML");
                    System.out.println("4. Buscar en CSV");
                    System.out.print(YELLOW + "Elige una opcion: " + RESET);

                    int subOpcionBuscar = 0;
                    try {
                        subOpcionBuscar = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(RED + "Entrada no valida. Por favor, introduce un numero." + RESET);
                        continue;
                    }

                    switch (subOpcionBuscar) {
                        case 1:
                            buscarTXT(nanBuscar);
                            break;
                        case 2:
                            buscarJSON(nanBuscar);
                            break;
                        case 3:
                            buscarXML(nanBuscar);
                            break;
                        case 4:
                            buscarCSV(nanBuscar);
                            break;
                        default:
                            System.out.println(RED + "Opcion no valida." + RESET);
                    }
                    break;
                // menu editar archivos
                case 3:
                    editarMenu(sc);
                    break;
                // borrar archivos
                case 4:
                    System.out.println(GREEN + "\n--- BORRAR ARCHIVOS ---" + RESET);
                    System.out.println("1. Borrar TXT");
                    System.out.println("2. Borrar JSON");
                    System.out.println("3. Borrar XML");
                    System.out.println("4. Borrar CSV");
                    System.out.print(YELLOW + "Elige una opcion: " + RESET);

                    int subOpcionBorrar = 0;
                    try {
                        subOpcionBorrar = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(RED + "Entrada no valida. Por favor, introduce un numero." + RESET);
                        continue;
                    }

                    String fileToDelete = null;
                    switch (subOpcionBorrar) {
                        case 1:
                            fileToDelete = TXT;
                            break;
                        case 2:
                            fileToDelete = JSON;
                            break;
                        case 3:
                            fileToDelete = XML;
                            break;
                        case 4:
                            fileToDelete = CSV;
                            break;
                        default:
                            System.out.println(RED + "Opcion no valida." + RESET);
                            continue;
                    }

                    if (fileToDelete != null) {
                        File file = new File(fileToDelete);
                        if (file.exists()) {
                            if (file.delete()) {
                                System.out.println(GREEN + fileToDelete + " borrado exitosamente." + RESET);
                            } else {
                                System.out.println(RED + "No se pudo borrar " + fileToDelete + "." + RESET);
                            }
                        } else {
                            System.out.println(RED + fileToDelete + " no existe." + RESET);
                        }
                    }
                    break;
                case 5:
                    System.out.println(GREEN + "Saliendo del programa. Adios!" + RESET);
                    break;
                default:
                    System.out.println(RED + "Opcion no valida." + RESET);
            }
        }
        sc.close();

    }

    // -------------------- VER ARCHIVOS --------------------
    static void verTXT() {
        System.out.println(CYAN + "\n--- CONTENIDO TXT ---" + RESET);
        try (BufferedReader br = new BufferedReader(new FileReader(TXT))) {
            String line;
            System.out.printf("%-15s %-5s\n", "NAN", "EDAD");
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    System.out.printf("%-15s %-5s\n", parts[0], parts[1]);
                }
            }
        } catch (Exception e) {
            System.out.println(RED + "Error leyendo TXT: " + e.getMessage() + RESET);
        }
    }

    static void verJSON() {
        System.out.println(CYAN + "\n--- CONTENIDO JSON ---" + RESET);
        try (BufferedReader br = new BufferedReader(new FileReader(JSON))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println(RED + "Error leyendo JSON: " + e.getMessage() + RESET);
        }
    }

    static void verXML() {
        System.out.println(CYAN + "\n--- CONTENIDO XML ---" + RESET);
        try {
            File xmlFile = new File(XML);
            if (!xmlFile.exists()) {
                System.out.println(RED + "Archivo XML no encontrado." + RESET);
                return;
            }
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("persona");
            System.out.printf("%-15s %-30s\n", "NAN", "DIRECCION");
            for (int i = 0; i < nList.getLength(); i++) {
                Element elem = (Element) nList.item(i);
                String nan = elem.getElementsByTagName("dni").item(0).getTextContent();
                String helbidea = elem.getElementsByTagName("helbidea").item(0).getTextContent();
                System.out.printf("%-15s %-30s\n", nan, helbidea);
            }
        } catch (Exception e) {
            System.out.println(RED + "Error leyendo XML: " + e.getMessage() + RESET);
        }
    }

    static void verCSV() {
        System.out.println(CYAN + "\n--- CONTENIDO CSV ---" + RESET);
        try (BufferedReader br = new BufferedReader(new FileReader(CSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println(RED + "Error leyendo CSV: " + e.getMessage() + RESET);
        }
    }

    // -------------------- BUSCADOR --------------------
    static void buscarTXT(String nan) {
        System.out.println(PURPLE + "\n--- BUSQUEDA EN TXT ---" + RESET);
        try (BufferedReader br = new BufferedReader(new FileReader(TXT))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(nan)) {
                    System.out.printf("NAN: %s, EDAD: %s\n", parts[0], parts[1]);
                    found = true;
                    break;
                }
            }
            if (!found)
                System.out.println(RED + "NAN no encontrado en TXT." + RESET);
        } catch (Exception e) {
            System.out.println(RED + "Error buscando en TXT: " + e.getMessage() + RESET);
        }
    }

    static void buscarJSON(String nan) {
        System.out.println(PURPLE + "\n--- BUSQUEDA EN JSON ---" + RESET);
        try (BufferedReader br = new BufferedReader(new FileReader(JSON))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                if (line.contains("\"NAN\": \"" + nan + "\"")) {
                    String nombre = "";
                    String apellido = "";
                    while ((line = br.readLine()) != null && !line.contains("}")) {
                        if (line.contains("\"IZENA\":")) {
                            nombre = line.split(":")[1].trim().replace("\"", "").replace(",", "");
                        } else if (line.contains("\"ABIZENA\":")) {
                            apellido = line.split(":")[1].trim().replace("\"", "").replace(",", "");

                        }
                    }
                    System.out.printf("NAN: %s, NOMBRE: %s, APELLIDO: %s\n", nan, nombre, apellido);
                    found = true;
                    break;
                }
                if (!found)
                    System.out.println(RED + "NAN no encontrado en JSON." + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "Error buscando en JSON: " + e.getMessage() + RESET);
        }
    }

    static void buscarXML(String nan) {
        System.out.println(PURPLE + "\n--- BUSQUEDA EN XML ---" + RESET);
        try {
            File xmlFile = new File(XML);
            if (!xmlFile.exists()) {
                System.out.println(RED + "Archivo XML no encontrado." + RESET);
                return;
            }
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("persona");
            boolean found = false;
            for (int i = 0; i < nList.getLength(); i++) {
                Element elem = (Element) nList.item(i);
                String dni = elem.getElementsByTagName("dni").item(0).getTextContent();
                if (dni.equals(nan)) {
                    String helbidea = elem.getElementsByTagName("helbidea").item(0).getTextContent();
                    System.out.printf("NAN: %s, DIRECCION: %s\n", dni, helbidea);
                    found = true;
                    break;
                }
            }
            if (!found)
                System.out.println(RED + "NAN no encontrado en XML." + RESET);
        } catch (Exception e) {
            System.out.println(RED + "Error buscando en XML: " + e.getMessage() + RESET);
        }
    }

    static void buscarCSV(String nan) {
        System.out.println(PURPLE + "\n--- BUSQUEDA EN CSV ---" + RESET);
        try (BufferedReader br = new BufferedReader(new FileReader(CSV))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[0].equals(nan)) {
                    System.out.printf("NAN: %s, NOMBRE: %s, APELLIDO: %s, EDAD: %s, DIRECCION: %s\n",
                            parts[0], parts[1], parts[2], parts[3], parts[4]);
                    found = true;
                    break;
                }
            }
            if (!found)
                System.out.println(RED + "NAN no encontrado en CSV." + RESET);
        } catch (Exception e) {
            System.out.println(RED + "Error buscando en CSV: " + e.getMessage() + RESET);
        }
    }

    // -------------------- BORRAR ARCHIVOS --------------------
    // vacia el contenido de un archivo
    static void borrarArchivo(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            // vacia el archivo
        } catch (Exception e) {
            System.out.println(RED + "Error borrando " + filename + ": " + e.getMessage() + RESET);
        }
    }

    // -------------------- EDITAR ARCHIVOS --------------------
    static void editarMenu(Scanner sc) {
        System.out.println("\n--- EDITAR ---");
        System.out.println("1. Añadir al TXT");
        System.out.println("2. Añadir al JSON");
        System.out.println("3. Añadir al XML");
        System.out.println("4. Editar CSV (distribuye en los demas)");
        System.out.print("Elige una opcion: ");
        int opcion;
        try {
            opcion = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            opcion = 0;
        }

        switch (opcion) {
            case 1 -> editarTXT(sc);
            case 2 -> editarJSON(sc);
            case 3 -> editarXML(sc);
            case 4 -> editarCSV(sc);
            default -> System.out.println(RED + "Opcion no valida" + RESET);
        }
    }

    static void editarTXT(Scanner sc) {
        try (FileWriter fw = new FileWriter(TXT, true)) {
            System.out.print("Introduce NAN: ");
            String nan = sc.nextLine().trim();
            System.out.print("Introduce edad: ");
            String edad = sc.nextLine().trim();
            if (nan.isEmpty() || edad.isEmpty() || !edad.matches("\\d+")) {
                System.out.println(RED + "Error: NAN obligatorio y edad debe ser numérica." + RESET);
                return;
            }
            fw.write(nan + "," + edad + "\n");
            System.out.println(GREEN + "Añadido al TXT." + RESET);
        } catch (Exception e) {
            System.out.println(RED + "Error editando TXT: " + e.getMessage() + RESET);
        }
    }

    static void editarJSON(Scanner sc) {
        try {
            List<String> lineas = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(JSON))) {
                String line;
                while ((line = br.readLine()) != null)
                    lineas.add(line);
            }
            if (!lineas.isEmpty())
                lineas.remove(lineas.size() - 1);

            System.out.print("Introduce NAN: ");
            String nan = sc.nextLine().trim();
            System.out.print("Introduce nombre: ");
            String nombre = sc.nextLine().trim();
            System.out.print("Introduce apellido: ");
            String apellido = sc.nextLine().trim();

            if (nan.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
                System.out.println(RED + "Error: campos vacíos." + RESET);
                return;
            }

            String nuevaPersona = "    {\n" +
                    "        \"NAN\": \"" + nan + "\",\n" +
                    "        \"IZENA\": \"" + nombre + "\",\n" +
                    "        \"ABIZENA\": \"" + apellido + "\"\n" +
                    "    }\n]";

            if (lineas.size() > 1)
                lineas.set(lineas.size() - 1, lineas.get(lineas.size() - 1) + ",");

            try (PrintWriter pw = new PrintWriter(new FileWriter(JSON))) {
                for (String l : lineas)
                    pw.println(l);
                pw.println(nuevaPersona);
            }

            System.out.println(GREEN + "Añadido al JSON." + RESET);
        } catch (Exception e) {
            System.out.println(RED + "Error editando JSON: " + e.getMessage() + RESET);
        }
    }

    static void editarXML(Scanner sc) {
        try {
            File xmlFile = new File(XML);
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc;
            if (xmlFile.exists()) {
                doc = dBuilder.parse(xmlFile);
            } else {
                doc = dBuilder.newDocument();
                Element root = doc.createElement("personas");
                doc.appendChild(root);
            }

            System.out.print("Introduce NAN: ");
            String nan = sc.nextLine().trim();
            System.out.print("Introduce direccion: ");
            String helbidea = sc.nextLine().trim();

            if (nan.isEmpty() || helbidea.isEmpty()) {
                System.out.println(RED + "Error: campos vacíos." + RESET);
                return;
            }

            Element nuevaPersona = doc.createElement("persona");

            Element dniElem = doc.createElement("dni");
            dniElem.setTextContent(nan);
            nuevaPersona.appendChild(dniElem);

            Element helbElem = doc.createElement("helbidea");
            helbElem.setTextContent(helbidea);
            nuevaPersona.appendChild(helbElem);

            doc.getDocumentElement().appendChild(nuevaPersona);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(xmlFile));

            System.out.println(GREEN + "Añadido al XML." + RESET);
        } catch (Exception e) {
            System.out.println(RED + "Error editando XML: " + e.getMessage() + RESET);
        }
    }

    static void editarCSV(Scanner sc) {
        try (FileWriter fw = new FileWriter(CSV, true)) {
            System.out.print("Introduce NAN: ");
            String nan = sc.nextLine().trim();
            System.out.print("Introduce nombre: ");
            String nombre = sc.nextLine().trim();
            System.out.print("Introduce apellido: ");
            String apellido = sc.nextLine().trim();
            System.out.print("Introduce edad: ");
            String edad = sc.nextLine().trim();
            System.out.print("Introduce direccion: ");
            String helbidea = sc.nextLine().trim();

            if (nan.isEmpty() || !edad.matches("\\d+")) {
                System.out.println(RED + "Error: NAN obligatorio y edad debe ser numérica." + RESET);
                return;
            }

            String linea = nan + "," + nombre + "," + apellido + "," + edad + "," + helbidea;
            fw.write(linea + "\n");
            System.out.println(GREEN + "Añadido al CSV." + RESET);

            actualizarArchivosDesdeCSV(nan, nombre, apellido, edad, helbidea);
        } catch (Exception e) {
            System.out.println(RED + "Error editando CSV: " + e.getMessage() + RESET);
        }
    }

    static void actualizarArchivosDesdeCSV(String nan, String nombre, String apellido, String edad, String helbidea) {
        // actualizar TXT
        try (FileWriter fw = new FileWriter(TXT, true)) {
            fw.write(nan + "," + edad + "\n");
        } catch (Exception ignored) {
        }

        // actualizar JSON
        try {
            List<String> lineas = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(JSON))) {
                String line;
                while ((line = br.readLine()) != null)
                    lineas.add(line);
            }
            if (!lineas.isEmpty())
                lineas.remove(lineas.size() - 1);
            String nuevaPersona = "    {\n" +
                    "        \"NAN\": \"" + nan + "\",\n" +
                    "        \"IZENA\": \"" + nombre + "\",\n" +
                    "        \"ABIZENA\": \"" + apellido + "\"\n" +
                    "    }\n]";
            if (lineas.size() > 1)
                lineas.set(lineas.size() - 1, lineas.get(lineas.size() - 1) + ",");
            try (PrintWriter pw = new PrintWriter(new FileWriter(JSON))) {
                for (String l : lineas)
                    pw.println(l);
                pw.println(nuevaPersona);
            }
        } catch (Exception ignored) {
        }

        // actualizar XML
        try {
            File xmlFile = new File(XML);
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc;
            if (xmlFile.exists())
                doc = dBuilder.parse(xmlFile);
            else {
                doc = dBuilder.newDocument();
                Element root = doc.createElement("personas");
                doc.appendChild(root);
            }

            Element nuevaPersona = doc.createElement("persona");
            Element dniElem = doc.createElement("dni");
            dniElem.setTextContent(nan);
            nuevaPersona.appendChild(dniElem);
            Element helbElem = doc.createElement("helbidea");
            helbElem.setTextContent(helbidea);
            nuevaPersona.appendChild(helbElem);
            doc.getDocumentElement().appendChild(nuevaPersona);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(xmlFile));
        } catch (Exception ignored) {
        }
    }
}
