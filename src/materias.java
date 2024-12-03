import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class materias extends JPanel {
    private JButton agregarButton;
    private JButton eliminarButton;
    private JButton editarButton;
    private JTable table1;
    public JPanel materiasPanel;
    private JButton actualizarButton;
    private JButton alumnosButton;
    private JButton materiasButton;
    private JButton profesoresButton;
    private JButton carrerasButton;
    private JFrame materiasFrame;
    private DefaultTableModel dtm = new DefaultTableModel();

    public void setVisible(boolean b){
        materiasFrame = new JFrame("Materias");
        materiasFrame.setContentPane(materiasPanel);
        materiasFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        materiasFrame.pack();
        materiasFrame.setVisible(b);
    }

    public materias(){
        table1.setModel(dtm);
        dtm.addColumn("Carrera");
        dtm.addColumn("Nombre");
        dtm.addColumn("correlativa");
        dtm.addColumn("anio");
        dtm.addRow(new Object[]{"Carrera","Nombre de la materia","Correlativa","Año"});

        materiasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                materias vista = new materias();
                vista.setVisible(true);
                materiasFrame.dispose();
            }
        });
        alumnosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alumnos vista = new alumnos();
                vista.setVisible(true);
                materiasFrame.dispose();
            }
        });
        carrerasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carreras vista = new carreras();
                vista.setVisible(true);
                materiasFrame.dispose();
            }
        });
        profesoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profesores vista = new profesores();
                vista.setVisible(true);
                materiasFrame.dispose();
            }
        });

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarmateria vista = new agregarmateria();
                vista.setVisible(true);
            }
        });

        actualizarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/examen", "root", "root");
                    PreparedStatement ps = con.prepareStatement("SELECT nombre, correlativa, idcarrera FROM materias WHERE correlativa IS NOT NULL");
                    dtm.setRowCount(0);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String nombre = rs.getString("nombre");
                        int correlativa = rs.getInt("correlativa");
                        int idcarrera = rs.getInt("idcarrera");
                        dtm.addRow(new Object[]{nombre, correlativa, idcarrera});
                    }
                    rs.close();
                    ps.close();
                    con.close();

                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException("Error al actualizar los datos: " + ex.getMessage(), ex);
                }
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, selecciona una materia para eliminar.");
                    return;
                }
                String nombremateria = dtm.getValueAt(selectedRow, 0).toString();

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/examen", "root", "root");

                    String query = "UPDATE materias SET correlativa = NULL WHERE nombre = ?";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, nombremateria);

                    int rowsAffected = ps.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Materia eliminada correctamente.");

                        dtm.removeRow(selectedRow);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró la materia seleccionada.");
                    }
                    ps.close();
                    con.close();

                } catch (ClassNotFoundException | SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar la materia: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

    }
}
