import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Sistema de Gestión de Facturas
 * Asignatura: Fundamentos de Programación
 * Unidad 4: Manejo de archivos y librería gráfica
 */
public class Gestionfacturas { 

    // Nombre del archivo de texto para persistencia de datos
    private static final String NOMBRE_ARCHIVO = "facturas.txt";

    // Estructura para representar una Factura
    static class Factura {
        private String numero;
        private String cliente;
        private double monto;

        public Factura(String numero, String cliente, double monto) {
            this.numero = numero;
            this.cliente = cliente;
            this.monto = monto;
        }

        public String getNumero() { return numero; }
        public String getCliente() { return cliente; }
        public double getMonto() { return monto; }

        // Formato estandarizado para guardar en el archivo de texto
        public String aTextoArchivo() {
            return numero + ";" + cliente + ";" + monto;
        }
    }

    public static void main(String[] args) {
        // Asegura la existencia del archivo de texto al iniciar
        inicializarArchivo();

        boolean salir = false;
        String[] opciones = {
            "1. Registro de facturas",
            "2. Consulta específica de una factura",
            "3. Mostrar las facturas en archivo de texto",
            "4. Salir"
        };

        while (!salir) {
            String seleccion = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione una opción del menú:",
                "Sistema de Gestión de Facturas",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
            );

            if (seleccion == null || seleccion.equals("4. Salir")) {
                salir = true;
                JOptionPane.showMessageDialog(null, 
                    "Saliendo del sistema...", 
                    "Finalizado", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else if (seleccion.equals("1. Registro de facturas")) {
                registrarFactura();
            } else if (seleccion.equals("2. Consulta específica de una factura")) {
                consultarFacturaEspecifica();
            } else if (seleccion.equals("3. Mostrar las facturas en archivo de texto")) {
                mostrarFacturasDesdeArchivo();
            }
        }
    }

    // Inicializa el archivo de texto si no existe en el sistema
    private static void inicializarArchivo() {
        try {
            File archivo = new File(NOMBRE_ARCHIVO);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "Error al inicializar el archivo: " + e.getMessage(), 
                "Error de Archivo", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // Opción 1: Registro de Facturas y guardado en archivo
    private static void registrarFactura() {
        String numero = JOptionPane.showInputDialog(null, 
            "Ingrese el Número de Factura:", 
            "Registro de Factura", 
            JOptionPane.PLAIN_MESSAGE);
        if (numero == null || numero.trim().isEmpty()) return;

        String cliente = JOptionPane.showInputDialog(null, 
            "Ingrese el Nombre del Cliente:", 
            "Registro de Factura", 
            JOptionPane.PLAIN_MESSAGE);
        if (cliente == null || cliente.trim().isEmpty()) return;

        String montoStr = JOptionPane.showInputDialog(null, 
            "Ingrese el Monto de la Factura ($):", 
            "Registro de Factura", 
            JOptionPane.PLAIN_MESSAGE);
        if (montoStr == null) return;

        try {
            double monto = Double.parseDouble(montoStr.trim());
            Factura factura = new Factura(numero.trim(), cliente.trim(), monto);

            // Guardado en el archivo de texto en modo append (agregar al final)
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
                writer.write(factura.aTextoArchivo());
                writer.newLine();
            }

            JOptionPane.showMessageDialog(null, 
                "Factura registrada con éxito.", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, 
                "Monto inválido. Ingrese un valor numérico correcto.", 
                "Error de Entrada", 
                JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "Error al guardar en el archivo: " + e.getMessage(), 
                "Error de E/S", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // Opción 2: Consulta específica por número de factura
    private static void consultarFacturaEspecifica() {
        String numBuscado = JOptionPane.showInputDialog(null, 
            "Ingrese el número de factura a consultar:", 
            "Consulta Específica", 
            JOptionPane.QUESTION_MESSAGE);

        if (numBuscado == null || numBuscado.trim().isEmpty()) return;

        boolean encontrada = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length == 3) {
                    String numero = datos[0];
                    String cliente = datos[1];
                    String monto = datos[2];

                    if (numero.equalsIgnoreCase(numBuscado.trim())) {
                        String detalle = "--- DETALLE DE FACTURA ---\n" +
                                         "Número: " + numero + "\n" +
                                         "Cliente: " + cliente + "\n" +
                                         "Monto: $" + monto;
                        
                        JOptionPane.showMessageDialog(null, 
                            detalle, 
                            "Factura Encontrada", 
                            JOptionPane.INFORMATION_MESSAGE);
                        encontrada = true;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "Error al leer el archivo de facturas.", 
                "Error de E/S", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Si no existe, presenta el mensaje exacto solicitado
        if (!encontrada) {
            JOptionPane.showMessageDialog(null, 
                "Factura no se encuentra registrada", 
                "Resultado de Búsqueda", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    // Opción 3: Mostrar todas las facturas leídas del archivo de texto
    private static void mostrarFacturasDesdeArchivo() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE FACTURAS (ARCHIVO DE TEXTO) ===\n\n");
        int contador = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length == 3) {
                    contador++;
                    reporte.append("Factura #").append(datos[0])
                           .append(" | Cliente: ").append(datos[1])
                           .append(" | Monto: $").append(datos[2])
                           .append("\n");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "Error al leer el archivo de datos.", 
                "Error de E/S", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (contador == 0) {
            JOptionPane.showMessageDialog(null, 
                "El archivo de facturas está vacío.", 
                "Sin Registros", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                reporte.toString(), 
                "Facturas Registradas", 
                JOptionPane.PLAIN_MESSAGE);
        }
    }
}