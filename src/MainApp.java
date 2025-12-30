import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
// src/MainApp.java :.

public class MainApp extends JFrame {

    private JTable tablaClientes;
    private JTable tablaProductos;
    private JTextArea areaCapas;

    public MainApp() {
        setTitle("Oracle 19c - Visualización Tablas + Arquitectura");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        // Panel Tablas :.
        JPanel panelTablas = new JPanel(new BorderLayout());

        tablaClientes = new JTable();
        tablaProductos = new JTable();

        JPanel pTop = new JPanel(new GridLayout(1, 2));
        pTop.add(new JScrollPane(tablaClientes));
        pTop.add(new JScrollPane(tablaProductos));

        JButton btnCargar = new JButton("Cargar Datos de Oracle");
        btnCargar.addActionListener(e -> cargarDatos());

        panelTablas.add(pTop, BorderLayout.CENTER);
        panelTablas.add(btnCargar, BorderLayout.SOUTH);

        // Panel Arquitectura :. . /
        JPanel panelCapas = new JPanel(new BorderLayout());
        areaCapas = new JTextArea();
        areaCapas.setEditable(false);
        areaCapas.setFont(new Font("Arial", Font.PLAIN, 15));
        areaCapas.setText(getCapasOracle());
        panelCapas.add(new JScrollPane(areaCapas), BorderLayout.CENTER);

        tabs.add("Tablas Oracle", panelTablas);
        tabs.add("Capas Oracle 19c", panelCapas);

        add(tabs);
    }

    private void cargarDatos() {
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
        String user = "system"; // cambiar segun su instancia : .
        String pass = "Tapiero123"; // cambiar segun su instancia / . ;
        try (Connection conn = DriverManager.getConnection(url, user, pass))
        {
            // CLIENTES
            DefaultTableModel modelClientes = new DefaultTableModel();
            modelClientes.addColumn("ID_CLIENTE");
            modelClientes.addColumn("NOMBRE");
            modelClientes.addColumn("CIUDAD");

            Statement st1 = conn.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT * FROM CLIENTES_A ORDER BY ID_CLIENTE");

            while (rs1.next()) {
                modelClientes.addRow(new Object[]{
                        rs1.getInt(1),
                        rs1.getString(2),
                        rs1.getString(3)
                });
            }

            tablaClientes.setModel(modelClientes);

            // PRODUCTOS
            DefaultTableModel modelProductos = new DefaultTableModel();
            modelProductos.addColumn("ID_PRODUCTO");
            modelProductos.addColumn("NOMBRE");
            modelProductos.addColumn("PRECIO");

            Statement st2 = conn.createStatement();
            ResultSet rs2 = st2.executeQuery("SELECT * FROM PRODUCTOS_S ORDER BY ID_PRODUCTO");

            while (rs2.next()) {
                modelProductos.addRow(new Object[]{
                        rs2.getInt(1),
                        rs2.getString(2),
                        rs2.getDouble(3)
                });
            }

            tablaProductos.setModel(modelProductos);

                JOptionPane.showMessageDialog(this, "Datos cargados correctamente desde Oracle 19c");

            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private String getCapasOracle() {
        return "ARQUITECTURA GENERAL ORACLE DATABASE 19C\n\n" +
                "1️⃣ Capa de Usuario / Aplicación\n" +
                "- Cliente Java, Formularios, Aplicaciones Empresariales.\n\n" +
                "2️⃣ Capa SQL / Oracle Net Services\n" +
                "- Comunicación JDBC\n" +
                "- Listener\n" +
                "- Sesiones y Conexiones\n\n" +
                "3️⃣ Capa de Instancia (SGA + Background Processes)\n" +
                "- SGA (Shared Pool, Buffer Cache, Redo Log Buffer)\n" +
                "- Procesos: DBWn, LGWR, CKPT, SMON, PMON, ARCn\n\n" +
                "4️⃣ Capa de Base de Datos Física\n" +
                "- Datafiles\n" +
                "- Redo Logs\n" +
                "- Control Files\n\n" +
                "5️⃣ Gestión y Seguridad\n" +
                "- Usuarios y Roles\n" +
                "- Privilegios\n" +
                "- Auditoría\n\n" +
                "Oracle 19c combina alto desempeño, estabilidad y arquitectura robusta.";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp().setVisible(true));
    }
}