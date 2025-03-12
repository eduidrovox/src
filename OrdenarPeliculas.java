import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Comparator;

class Pelicula {
    String titulo;
    int anio;
    String genero;

    public Pelicula(String titulo, int anio, String genero) {
        this.titulo = titulo;
        this.anio = anio;
        this.genero = genero;
    }
}

public class OrdenarPeliculas {
    public static void main(String[] args) {
        List<Pelicula> peliculas = new ArrayList<>();
        
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Ingrese la cantidad de películas: ");
            int numPeliculas = scanner.nextInt();
            scanner.nextLine();
            
            for (int i = 0; i < numPeliculas; i++) {
                System.out.println("Ingrese el título de la película " + (i + 1) + ": ");
                String titulo = scanner.nextLine();
                
                System.out.println("Ingrese el año de lanzamiento: ");
                int anio = scanner.nextInt();
                scanner.nextLine();
                
                System.out.println("Ingrese el género: ");
                String genero = scanner.nextLine();
                
                peliculas.add(new Pelicula(titulo, anio, genero));
            }
        }
        
        peliculas.sort(Comparator.comparingInt(p -> p.anio));
        
        System.out.println("\nLista de películas ordenadas por año de lanzamiento:");
        for (Pelicula pelicula : peliculas) {
            System.out.println(pelicula.anio + " - " + pelicula.titulo + " (" + pelicula.genero + ")");
        }
    }
}
