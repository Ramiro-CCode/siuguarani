import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class alumnoscarreras {
    private JTable table1;
    private JPanel panel1;
    private JButton materiasButton;
    private JButton carrerasButton;
    private DefaultTableModel dtm;
    JFrame frame;

    public alumnoscarreras(int dnialumno) {

        dtm = new DefaultTableModel();
        dtm.addColumn("ID Carrera");
        table1.setModel(dtm);
        dtm.addRow(new Object[]{"ID Carrera"});


                try {
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/examen",
                            "root",
                            "123"
                    );

                    String query = "SELECT idcarrera FROM alumnoscarreras WHERE dnialumno = ?";
                    PreparedStatement ps = con.prepareStatement(query);

                    ps.setInt(1, dnialumno);

                    ResultSet rs = ps.executeQuery();

                    dtm.setRowCount(1);

                    while (rs.next()) {
                        int idCarrera = rs.getInt("idcarrera");
                        dtm.addRow(new Object[]{idCarrera});
                    }

                    rs.close();
                    ps.close();
                    con.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al cargar las carreras: " + ex.getMessage());
                }

        materiasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaalumnomaterias vista = new vistaalumnomaterias(dnialumno);
                vista.setVisible(true);
                frame.dispose();
            }
        });
    }

    public void setVisible(boolean visible) {
        frame = new JFrame("Alumnos Carreras");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(visible);
    }
}

