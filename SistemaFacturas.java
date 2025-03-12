import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class SistemaFacturas extends JFrame {
    private final ArrayList<Factura> facturas = new ArrayList<>();
    private final JTextArea areaTexto;

    // Clase interna para representar una factura
    private class Factura {
        String numeroFactura;
        String cliente;
        double monto;
        String fecha;

        public Factura(String numero, String cliente, double monto, String fecha) {
            this.numeroFactura = numero;
            this.cliente = cliente;
            this.monto = monto;
            this.fecha = fecha;
        }
    }

    public SistemaFacturas() {
        // Configuración de la ventana
        setTitle("Sistema de Facturación");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel del menú
        JPanel panelMenu = new JPanel(new GridLayout(4, 1, 10, 10));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botones del menú
        JButton btnRegistrar = new JButton("1. Registro de facturas");
        JButton btnConsultar = new JButton("2. Consulta específica de una factura");
        JButton btnMostrar = new JButton("3. Mostrar facturas en archivo");
        JButton btnSalir = new JButton("4. Salir");

        // Área de texto para mostrar resultados
        areaTexto = new JTextArea(10, 40);
        areaTexto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);

        // Agregar botones al panel
        panelMenu.add(btnRegistrar);
        panelMenu.add(btnConsultar);
        panelMenu.add(btnMostrar);
        panelMenu.add(btnSalir);

        // Agregar componentes a la ventana
        add(panelMenu, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        // Acciones de los botones
        btnRegistrar.addActionListener(e -> registrarFactura());
        btnConsultar.addActionListener(e -> consultarFactura());
        btnMostrar.addActionListener(e -> mostrarFacturasArchivo());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private void registrarFactura() {
        JTextField numeroField = new JTextField(10);
        JTextField clienteField = new JTextField(20);
        JTextField montoField = new JTextField(10);
        JTextField fechaField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Número de factura:"));
        panel.add(numeroField);
        panel.add(new JLabel("Nombre del cliente:"));
        panel.add(clienteField);
        panel.add(new JLabel("Monto:"));
        panel.add(montoField);
        panel.add(new JLabel("Fecha (dd/mm/aaaa):"));
        panel.add(fechaField);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Registro de Factura", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String numero = numeroField.getText();
                String cliente = clienteField.getText();
                double monto = Double.parseDouble(montoField.getText());
                String fecha = fechaField.getText();

                facturas.add(new Factura(numero, cliente, monto, fecha));
                areaTexto.setText("Factura registrada exitosamente!\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error: El monto debe ser un número válido",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void consultarFactura() {
        String numero = JOptionPane.showInputDialog(this,
                "Ingrese el número de factura a consultar:");

        if (numero != null) {
            boolean encontrada = false;
            for (Factura f : facturas) {
                if (f.numeroFactura.equals(numero)) {
                    areaTexto.setText("""
                            Factura encontrada:
                            Número: %s
                            Cliente: %s
                            Monto: $%.2f
                            Fecha: %s""".formatted(f.numeroFactura, f.cliente, f.monto, f.fecha));
                    encontrada = true;
                    break;
                }
            }
            if (!encontrada) {
                areaTexto.setText("Factura no se encuentra registrada");
            }
        }
    }

    private void mostrarFacturasArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("facturas.txt"))) {
            writer.write("""
                    Listado de Facturas
                    ==================
                    """);

            double totalMontos = 0;
            for (Factura f : facturas) {
                String linea = String.format("Factura: %s | Cliente: %s | Monto: $%.2f | Fecha: %s%n",
                        f.numeroFactura, f.cliente, f.monto, f.fecha);
                writer.write(linea);
                totalMontos += f.monto;
            }
            writer.write("\nTotal de montos: $" + String.format("%.2f", totalMontos));

            areaTexto.setText("""
                    Facturas guardadas en 'facturas.txt' exitosamente!
                    Total de facturas: %d
                    Suma total: $%.2f""".formatted(facturas.size(), totalMontos));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar el archivo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaFacturas sistema = new SistemaFacturas();
            sistema.setVisible(true);
        });
    }
}