import java.util.Scanner;

public class CalculadoraNutricional {

    // Método principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("   SISTEMA DE EVALUACIÓN NUTRICIONAL      ");
        System.out.println("==========================================");

        System.out.print("¿Cuántos ingredientes contiene su comida? ");
        int numIngredientes = sc.nextInt();

        // Acumuladores para cada grupo nutricional (en gramos)
        double totalCalorias = 0;
        double totalProteinas = 0;
        double totalGrasas = 0;
        double totalCarbohidratos = 0;

        for (int i = 1; i <= numIngredientes; i++) {
            System.out.println("\n--- Ingrediente " + i + " ---");
            System.out.print("Ingrese la cantidad en gramos: ");
            double gramos = sc.nextDouble();

            mostrarMenuGrupos();
            System.out.print("Seleccione el grupo nutricional (1-4): ");
            int opcionGrupo = sc.nextInt();

            switch (opcionGrupo) {
                case 1:
                    totalCalorias += gramos;
                    break;
                case 2:
                    totalProteinas += gramos;
                    break;
                case 3:
                    totalGrasas += gramos;
                    break;
                case 4:
                    totalCarbohidratos += gramos;
                    break;
                default:
                    System.out.println("Opción inválida. Registro no sumado.");
                    break;
            }
        }

        // Mostrar el desglose de nutrientes
        mostrarResumen(totalCalorias, totalProteinas, totalGrasas, totalCarbohidratos);

        // Evaluar si es aceptable
        boolean esAceptable = evaluarNutricion(totalProteinas, totalGrasas, totalCarbohidratos);
        
        System.out.println("\n==========================================");
        if (esAceptable) {
            System.out.println("RESULTADO: El valor nutricional de la comida es ACEPTABLE.");
        } else {
            System.out.println("RESULTADO: El valor nutricional de la comida NO ES ACEPTABLE.");
            System.out.println("Sugerencia: Asegúrese de incluir proteínas/carbohidratos y evitar exceso de grasas.");
        }
        System.out.println("==========================================");

        sc.close();
    }

    // Subprograma para mostrar opciones
    public static void mostrarMenuGrupos() {
        System.out.println("Grupo Nutricional:");
        System.out.println("1. Caloría (fuente energética pura / azúcares)");
        System.out.println("2. Proteína");
        System.out.println("3. Grasa");
        System.out.println("4. Carbohidrato");
    }

    // Subprograma para imprimir el resumen
    public static void mostrarResumen(double cal, double prot, double gras, double carb) {
        double totalGramos = cal + prot + gras + carb;
        System.out.println("\n==========================================");
        System.out.println("         RESUMEN NUTRICIONAL              ");
        System.out.println("==========================================");
        System.out.printf("Gramos de Calorías puras : %.2f g\n", cal);
        System.out.printf("Gramos de Proteínas      : %.2f g\n", prot);
        System.out.printf("Gramos de Grasas         : %.2f g\n", gras);
        System.out.printf("Gramos de Carbohidratos  : %.2f g\n", carb);
        System.out.printf("Total consumido          : %.2f g\n", totalGramos);
    }

    // Subprograma con la lógica para determinar si la comida es aceptable
    public static boolean evaluarNutricion(double prot, double gras, double carb) {
        double totalGramos = prot + gras + carb;
        if (totalGramos == 0) return false;

        // Criterio nutricional:
        // 1. Debe incluir aporte de proteína o carbohidratos.
        // 2. El porcentaje de grasas no debe superar el 35% del total de macronutrientes.
        double porcentajeGrasa = (gras / totalGramos) * 100;
        
        return (prot > 0 || carb > 0) && (porcentajeGrasa <= 35.0);
    }
}