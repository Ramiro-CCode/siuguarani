import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class profesormaterias {
    private JTable table1;
    private JPanel panel1;
    private JButton cargarNotasButton;
    private DefaultTableModel dtm;

    public profesormaterias(int dniprofesor) {
        dtm = new DefaultTableModel();
        dtm.addColumn("Nombre de la materia");
        dtm.addColumn("Carrera");
        table1.setModel(dtm);
        dtm.addRow(new Object[]{"Nombre de la materia", "Carrera"});

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/examen",
                    "root",
                    "root"
            );

            String query = "SELECT nombre, idcarrera FROM materias WHERE dniprofesor = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, dniprofesor);
            ResultSet rs = ps.executeQuery();

            dtm.setRowCount(1);

            while (rs.next()) {
                String nombreMateria = rs.getString("nombre");
                int idCarrera = rs.getInt("idcarrera");
                dtm.addRow(new Object[]{nombreMateria, idCarrera});
            }

            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar las materias: " + ex.getMessage());
        }


        cargarNotasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarnotas vista = new cargarnotas();
                vista.setVisible(true);
            }
        });
    }

    public void setVisible(boolean visible) {
        JFrame frame = new JFrame("Profesor Materias");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(visible);
    }
}
