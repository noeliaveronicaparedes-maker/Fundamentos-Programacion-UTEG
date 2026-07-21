import java.util.Scanner;

class Filme {
    String nombre;
    int estreno;
    String tipo;
}

public class OrdenadorPeliculas {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        System.out.println("===== CATALOGO DE PELICULAS =====");
        System.out.print("Ingrese el numero de peliculas: ");
        int total = entrada.nextInt();
        entrada.nextLine();

        Filme[] catalogo = new Filme[total];

        for (int i = 0; i < total; i++) {

            catalogo[i] = new Filme();

            System.out.println("\nRegistro " + (i + 1));

            System.out.print("Nombre de la pelicula: ");
            catalogo[i].nombre = entrada.nextLine();

            System.out.print("Año de estreno: ");
            catalogo[i].estreno = entrada.nextInt();
            entrada.nextLine();

            System.out.print("Tipo o genero: ");
            catalogo[i].tipo = entrada.nextLine();
        }

        // Ordenamiento Burbuja
        for (int pasada = 0; pasada < total - 1; pasada++) {
            for (int actual = 0; actual < total - pasada - 1; actual++) {

                if (catalogo[actual].estreno > catalogo[actual + 1].estreno) {

                    Filme aux = catalogo[actual];
                    catalogo[actual] = catalogo[actual + 1];
                    catalogo[actual + 1] = aux;
                }
            }
        }

        System.out.println("\n===== PELICULAS ORDENADAS =====");

        for (int i = 0; i < total; i++) {
            System.out.println("--------------------------------");
            System.out.println("Pelicula : " + catalogo[i].nombre);
            System.out.println("Estreno  : " + catalogo[i].estreno);
            System.out.println("Genero   : " + catalogo[i].tipo);
        }

        entrada.close();
    }
}