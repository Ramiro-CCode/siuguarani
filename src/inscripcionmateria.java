import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class inscripcionmateria {
    private JTable table1;
    private JPanel panel1;
    private JButton inscribirseButton;
    private JFrame inscripcionmateriaframe;
    private DefaultTableModel dtm = new DefaultTableModel();

    public void setVisible(boolean b) {
        inscripcionmateriaframe = new JFrame("Materias");
        inscripcionmateriaframe.setContentPane(panel1);
        inscripcionmateriaframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inscripcionmateriaframe.pack();
        inscripcionmateriaframe.setVisible(b);
    }

    public inscripcionmateria(int dnialumno) {
        dtm.addColumn("Nombre");
        dtm.addColumn("Correlativa");
        dtm.addColumn("ID Carrera");
        table1.setModel(dtm);
        dtm.addRow(new Object[]{"Materia", "Correlativa", "ID Carrera"});

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/examen", "root", "123");

            PreparedStatement psCarrera = con.prepareStatement(
                    "SELECT idcarrera FROM alumnoscarreras WHERE dnialumno = ?");
            psCarrera.setInt(1, dnialumno);
            ResultSet rsCarrera = psCarrera.executeQuery();

            if (rsCarrera.next()) {
                int idCarrera = rsCarrera.getInt("idcarrera");

                PreparedStatement psMaterias = con.prepareStatement(
                        "SELECT id, nombre, idcarrera, idcorrelativa FROM materias WHERE idcarrera = ?");
                psMaterias.setInt(1, idCarrera);
                ResultSet rsMaterias = psMaterias.executeQuery();

                while (rsMaterias.next()) {
                    String nombre = rsMaterias.getString("nombre");
                    int correlativa = rsMaterias.getInt("idcorrelativa");
                    int idcarrera = rsMaterias.getInt("idcarrera");
                    dtm.addRow(new Object[]{nombre, correlativa, idcarrera});
                }

                rsMaterias.close();
                psMaterias.close();
            }

            rsCarrera.close();
            psCarrera.close();
            con.close();

        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Error al cargar las materias: " + ex.getMessage(), ex);
        }

        inscribirseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Selecciona una materia para inscribirte.");
                    return;
                }

                String nombreMateria = dtm.getValueAt(selectedRow, 0).toString();
                int idCorrelativa = (int) dtm.getValueAt(selectedRow, 1);

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/examen", "root", "123");

                    PreparedStatement psCorrelativa = con.prepareStatement(
                            "SELECT estado FROM alumnosmaterias WHERE dnialumno = ? AND idcarrera = ? AND estado = 1");
                    psCorrelativa.setInt(1, dnialumno);
                    psCorrelativa.setInt(2, idCorrelativa);
                    ResultSet rsCorrelativa = psCorrelativa.executeQuery();

                    if (idCorrelativa != 0 && !rsCorrelativa.next()) {
                        JOptionPane.showMessageDialog(null,
                                "No puedes inscribirte porque no tienes aprobada la correlativa.");
                    } else {
                        PreparedStatement psInscribir = con.prepareStatement(
                                "INSERT INTO alumnosmaterias (dnialumno, idcarrera, estado) VALUES (?, ?, 2)");
                        psInscribir.setInt(1, dnialumno);
                        psInscribir.setInt(2, idCorrelativa);
                        psInscribir.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Inscripción realizada con éxito.");
                        psInscribir.close();
                    }

                    rsCorrelativa.close();
                    psCorrelativa.close();
                    con.close();

                } catch (ClassNotFoundException | SQLException ex) {
                    throw new RuntimeException("Error al inscribirse: " + ex.getMessage(), ex);
                }
            }
        });
    }
}
