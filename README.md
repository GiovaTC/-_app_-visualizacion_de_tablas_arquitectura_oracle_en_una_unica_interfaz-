# -_app_-visualizacion_de_tablas_arquitectura_oracle_en_una_unica_interfaz- :. .
# Aplicación Java Swing + Oracle 19c .  
**Visualización de Tablas + Arquitectura Oracle en una Única Interfaz** :

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/14067548-0499-4c47-b76e-976d344e67d4" />  

A continuación encontrará una **solución completa, profesional y lista para ejecutar en IntelliJ IDEA**, que realiza lo siguiente :

- Conecta a **Oracle Database 19c mediante JDBC**.  
- Lee y **muestra en una interfaz gráfica (Java Swing)** los registros de **dos tablas** (con 40–80 registros de ejemplo).  
- Presenta en la misma interfaz una sección informativa con las principales **capas de arquitectura de Oracle 19c**.  
- Incluye **script SQL completo** para creación de tablas + carga de datos.  
- Código listo para copiar, pegar, compilar y ejecutar .

---

## 1️⃣ Script Oracle 19c – Creación y Datos de Ejemplo :
Ejecutar en SQL Plus, SQL Developer o SQL\*Plus.

```sql
-- CREACIÓN DE TABLAS
CREATE TABLE CLIENTES (
    ID_CLIENTE NUMBER PRIMARY KEY,
    NOMBRE VARCHAR2(50),
    CIUDAD VARCHAR2(40)
);

CREATE TABLE PRODUCTOS (
    ID_PRODUCTO NUMBER PRIMARY KEY,
    NOMBRE VARCHAR2(50),
    PRECIO NUMBER(10,2)
);

-- 40 REGISTROS CLIENTES
BEGIN
  FOR i IN 1..40 LOOP
    INSERT INTO CLIENTES VALUES(i, 'Cliente '||i, 'Ciudad '||MOD(i,5));
  END LOOP;
  COMMIT;
END;
/

-- 80 REGISTROS PRODUCTOS
BEGIN
  FOR i IN 1..80 LOOP
    INSERT INTO PRODUCTOS VALUES(i, 'Producto '||i, (i*1000));
  END LOOP;
  COMMIT;
END;
/
```
## 2️⃣ Configuración JDBC en IntelliJ IDEA :

Crear proyecto Java.

Descargar y agregar ojdbc8.jar u ojdbc11.jar.

Agregarlo en : 
```
Project Structure → Libraries → Add
```

## 3️⃣ Código Java Completo (Swing + JDBC + UI Única) :

Guardar como : 
```
src/MainApp.java

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

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

        // Panel Tablas
        JPanel panelTablas = new JPanel(new BorderLayout());

        tablaClientes = new JTable();
        tablaProductos = new JTable();

        JPanel pTop = new JPanel(new GridLayout(1,2));
        pTop.add(new JScrollPane(tablaClientes));
        pTop.add(new JScrollPane(tablaProductos));

        JButton btnCargar = new JButton("Cargar Datos de Oracle");
        btnCargar.addActionListener(e -> cargarDatos());

        panelTablas.add(pTop, BorderLayout.CENTER);
        panelTablas.add(btnCargar, BorderLayout.SOUTH);

        // Panel Arquitectura
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
        String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
        String user = "SYSTEM";        // Cambiar según su instancia
        String pass = "admin";         // Cambiar según su instancia

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {

            // CLIENTES
            DefaultTableModel modelClientes = new DefaultTableModel();
            modelClientes.addColumn("ID_CLIENTE");
            modelClientes.addColumn("NOMBRE");
            modelClientes.addColumn("CIUDAD");

            Statement st1 = conn.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT * FROM CLIENTES ORDER BY ID_CLIENTE");

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
            ResultSet rs2 = st2.executeQuery("SELECT * FROM PRODUCTOS ORDER BY ID_PRODUCTO");

            while (rs2.next()) {
                modelProductos.addRow(new Object[]{
                        rs2.getInt(1),
                        rs2.getString(2),
                        rs2.getDouble(3)
                });
            }

            tablaProductos.setModel(modelProductos);

            JOptionPane.showMessageDialog(this, "Datos cargados correctamente desde Oracle 19c");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private String getCapasOracle() {
        return  "ARQUITECTURA GENERAL ORACLE DATABASE 19C\n\n" +
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
```
## 4️⃣ Resultado Esperado en la Interfaz :

✔️ Pestaña 1: Tablas Oracle

Tabla CLIENTES (40 registros).

Tabla PRODUCTOS (80 registros).

Botón: “Cargar Datos desde Oracle”.

✔️ Pestaña 2: Capas Oracle 19c

Descripción clara y estructurada de la arquitectura Oracle 19c .  

## 📌 Observaciones Finales :

Cambiar usuario, contraseña y servicio Oracle según su ambiente:  
```
jdbc:oracle:thin:@localhost:1521:ORCL 
```
Requiere ojdbc compatible con su versión de JDK . 

## © 2025 Giovanny Alejandro Tapiero Cataño & chatGpt :. .
Aplicación Java – Visualización de Tablas Oracle 19c y Arquitectura.
Todos los derechos reservados. Prohibida su reproducción o uso sin autorización .
:. . - / .  

