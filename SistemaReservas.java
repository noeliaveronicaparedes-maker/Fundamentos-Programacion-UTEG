import java.util.Scanner;

public class SistemaReservas {

    // Constantes de configuración del sistema (Restricciones del taller)
    private static final int TOTAL_ASIENTOS = 150; // Número de asientos disponibles
    private static final int TIEMPO_LIMITE_DIAS = 30; // Tiempo fijado para realizar reservas
    private static final double PESO_MAX_EQUIPAJE = 23.0; // Restricción de peso en kg
    private static final int CANTIDAD_MAX_EQUIPAJE = 2; // Restricción de cantidad de maletas

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        
        // Variables de control de simulación
        int asientosOcupados = 45; // Simulación de asientos ya reservados previamente
        
        System.out.println("=================================================");
        System.out.println("    SISTEMA DE RESERVA DE VUELOS - UTEG ONLINE   ");
        System.out.println("=================================================");
        
        // 1. CONTROL DE TIEMPO DE LA RESERVA
        System.out.print("Ingrese con cuántos días de anticipación realiza su reserva: ");
        int diasAnticipacion = teclado.nextInt();
        teclado.nextLine(); // Limpiar el búfer de entrada
        
        if (diasAnticipacion > TIEMPO_LIMITE_DIAS) {
            System.out.println("\n[ERROR] No se pueden realizar reservas con más de " + TIEMPO_LIMITE_DIAS + " días de anticipación.");
            System.out.println("Proceso cancelado. Intente más cerca de la fecha de su vuelo.");
            return;
        }
        
        // 2. DISPONIBILIDAD DE ASIENTOS
        int asientosDisponibles = TOTAL_ASIENTOS - asientosOcupados;
        System.out.println("\n--- VERIFICACIÓN DE DISPONIBILIDAD ---");
        System.out.println("Asientos totales del avión: " + TOTAL_ASIENTOS);
        System.out.println("Asientos disponibles actualmente: " + asientosDisponibles);
        
        if (asientosDisponibles <= 0) {
            System.out.println("\n[LO SENTIMOS] El vuelo se encuentra completamente lleno. No hay asientos disponibles.");
            return;
        }
        
        // 3. CAPTURA DE DATOS PRINCIPALES DEL PASAJERO
        System.out.println("\n--- DATOS PRINCIPALES DEL PASAJERO ---");
        System.out.print("Ingrese el nombre completo del pasajero: ");
        String nombrePasajero = teclado.nextLine();
        
        System.out.print("Ingrese el número de pasaporte o identificación: ");
        String documentoIdentidad = teclado.nextLine();
        
        System.out.print("Ingrese la edad del pasajero: ");
        int edadPasajero = teclado.nextInt();
        teclado.nextLine(); // Limpiar búfer
        
        // 4. DETERMINACIÓN DE RUTA SEGÚN TIPO DE BOLETO
        System.out.println("\n--- SELECCIÓN DE TIPO DE BOLETO Y RUTA ---");
        System.out.println("1. Boleto Nacional (Ruta: Guayaquil - Quito)");
        System.out.println("2. Boleto Internacional Económico (Ruta: Guayaquil - Miami)");
        System.out.println("3. Boleto Internacional Business Class (Ruta: Guayaquil - Madrid)");
        System.out.print("Seleccione una opción (1-3): ");
        int tipoBoleto = teclado.nextInt();
        teclado.nextLine(); // Limpiar búfer
        
        String rutaSeleccionada = "";
        String categoriaBoleto = "";
        
        switch (tipoBoleto) {
            case 1:
                categoriaBoleto = "Nacional";
                rutaSeleccionada = "Guayaquil (GYE) -> Quito (UIO)";
                break;
            case 2:
                categoriaBoleto = "Internacional Económico";
                rutaSeleccionada = "Guayaquil (GYE) -> Miami (MIA)";
                break;
            case 3:
                categoriaBoleto = "Internacional Business Class";
                rutaSeleccionada = "Guayaquil (GYE) -> Madrid (MAD)";
                break;
            default:
                System.out.println("\n[ERROR] Opción de boleto inválida. Se asignará Ruta Nacional por defecto.");
                categoriaBoleto = "Nacional (Por defecto)";
                rutaSeleccionada = "Guayaquil (GYE) -> Quito (UIO)";
                break;
        }
        
        // 5. RESTRICCIÓN DE CANTIDAD Y PESO DE EQUIPAJE
        System.out.println("\n--- CONTROL DE EQUIPAJE permitido ---");
        System.out.println("Máximo permitido: " + CANTIDAD_MAX_EQUIPAJE + " maletas de hasta " + PESO_MAX_EQUIPAJE + " kg cada una.");
        
        System.out.print("¿Cuántas piezas de equipaje va a registrar?: ");
        int cantidadMaletas = teclado.nextInt();
        
        if (cantidadMaletas > CANTIDAD_MAX_EQUIPAJE) {
            System.out.println("\n[RESTRICCIÓN] Ha excedido el límite de piezas permitidas (" + CANTIDAD_MAX_EQUIPAJE + ").");
            System.out.println("Deberá pagar un recargo por exceso de equipaje en el aeropuerto.");
        }
        
        boolean pesoExcedido = false;
        for (int i = 1; i <= cantidadMaletas; i++) {
            System.out.print("Ingrese el peso de la maleta #" + i + " (en kg): ");
            double pesoMaleta = teclado.nextDouble();
            if (pesoMaleta > PESO_MAX_EQUIPAJE) {
                pesoExcedido = true;
            }
        }
        
        if (pesoExcedido) {
            System.out.println("\n[ALERTA] Al menos una de sus maletas supera los " + PESO_MAX_EQUIPAJE + " kg permitidos.");
            System.out.println("Aplica cargos adicionales por sobrepeso en el counter.");
        }
        
        // 6. CONFIRMACIÓN Y DESPLIEGUE FINAL DE LA RESERVA
        asientosOcupados++; // Se procesa el asiento del pasajero actual
        System.out.println("\n=================================================");
        System.out.println("        RESERVA PROCESADA CON ÉXITO             ");
        System.out.println("=================================================");
        System.out.println("Pasajero: " + nombrePasajero.toUpperCase());
        System.out.println("Identificación: " + documentoIdentidad);
        System.out.println("Edad: " + edadPasajero + " años");
        System.out.println("Categoría de Viaje: " + categoriaBoleto);
        System.out.println("Ruta Asignada: " + rutaSeleccionada);
        System.out.println("Días de anticipación de reserva: " + diasAnticipacion + " días");
        System.out.println("Equipaje registrado: " + cantidadMaletas + " pieza(s)");
        System.out.println("Estado del Asiento: ASIGNADO (Asientos libres restantes: " + (TOTAL_ASIENTOS - asientosOcupados) + ")");
        System.out.println("=================================================");
        System.out.println("¡Gracias por elegir nuestra aerolínea!");
        
        teclado.close();
    }
}