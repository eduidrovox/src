import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Ingrediente {
    String nombre;
    double cantidadGramos;
    String grupoNutricional;
    double valorNutricional;

    public Ingrediente(String nombre, double cantidadGramos, String grupoNutricional, double valorNutricional) {
        this.nombre = nombre;
        this.cantidadGramos = cantidadGramos;
        this.grupoNutricional = grupoNutricional;
        this.valorNutricional = valorNutricional;
    }
}

public class CalculadoraNutrientes {
    public static void main(String[] args) {
        List<Ingrediente> ingredientes = new ArrayList<>();
        
        try (Scanner scanner = new Scanner(System.in)) {
            int numIngredientes;
            do {
                System.out.println("Ingrese la cantidad de ingredientes (máximo 5): ");
                numIngredientes = scanner.nextInt();
                scanner.nextLine();
            } while (numIngredientes < 1 || numIngredientes > 5);
            
            for (int i = 0; i < numIngredientes; i++) {
                System.out.println("Ingrese el nombre del ingrediente " + (i + 1) + ": ");
                String nombre = scanner.nextLine();
                
                System.out.println("Ingrese la cantidad en gramos: ");
                double cantidad = scanner.nextDouble();
                
                scanner.nextLine(); // Limpiar buffer
                System.out.println("Ingrese el grupo nutricional (caloría, proteína, grasa, carbohidrato): ");
                String grupo = scanner.nextLine().toLowerCase();
                
                System.out.println("Ingrese el valor nutricional por 100g: ");
                double valorNutricional = scanner.nextDouble();
                scanner.nextLine(); // Limpiar buffer
                
                ingredientes.add(new Ingrediente(nombre, cantidad, grupo, (valorNutricional * cantidad / 100)));
            }
        }
        
        double totalCalorias = 0, totalProteinas = 0, totalGrasas = 0, totalCarbohidratos = 0;
        
        for (Ingrediente ing : ingredientes) {
            System.out.println("Ingrediente: " + ing.nombre + ", Cantidad: " + ing.cantidadGramos + " g");
            switch (ing.grupoNutricional) {
                case "caloría" -> totalCalorias += ing.valorNutricional;
                case "proteína" -> totalProteinas += ing.valorNutricional;
                case "grasa" -> totalGrasas += ing.valorNutricional;
                case "carbohidrato" -> totalCarbohidratos += ing.valorNutricional;
                default -> System.out.println("Grupo nutricional no válido para " + ing.nombre);
            }
        }
        
        System.out.println("Resumen nutricional:");
        System.out.println("Calorías: " + totalCalorias + " kcal");
        System.out.println("Proteínas: " + totalProteinas + " g");
        System.out.println("Grasas: " + totalGrasas + " g");
        System.out.println("Carbohidratos: " + totalCarbohidratos + " g");
        
        if (totalCalorias > 2000 || totalGrasas > 70 || totalCarbohidratos > 300 || totalProteinas < 50) {
            System.out.println("El valor nutricional de la comida NO es aceptable.");
        } else {
            System.out.println("El valor nutricional de la comida es aceptable.");
        }
    }
}
