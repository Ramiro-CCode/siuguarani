import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class alumnos extends JPanel {
    private JButton agregarButton;
    private JButton eliminarButton;
    private JButton editarButton;
    private JTable table1;
    private JPanel alumnosPanel;
    private JButton actualizarButton;
    private JButton alumnosButton;
    private JButton carrerasButton;
    private JButton materiasButton;
    private JButton profesoresButton;
    private JFrame alumnosFrame;
    private DefaultTableModel dtm;

    public void setVisible(boolean b){
        alumnosFrame = new JFrame("Alumnos");
        alumnosFrame.setContentPane(alumnosPanel);
        alumnosFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        alumnosFrame.pack();
        alumnosFrame.setVisible(b);
    }

    public alumnos(){

        materiasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                materias vista = new materias();
                vista.setVisible(true);
                alumnosFrame.dispose();
            }
        });
        alumnosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alumnos vista = new alumnos();
                vista.setVisible(true);
                alumnosFrame.dispose();
            }
        });
        carrerasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carreras vista = new carreras();
                vista.setVisible(true);
                alumnosFrame.dispose();
            }
        });
        profesoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profesores vista = new profesores();
                vista.setVisible(true);
                alumnosFrame.dispose();
            }
        });
        dtm = new DefaultTableModel();
        table1.setModel(dtm);
        dtm.addColumn("Nombre");
        dtm.addColumn("correlativa");
        dtm.addColumn("anio");
        dtm.addRow(new Object[]{"Nombre","DNI","Carrera"});

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
                    PreparedStatement ps = con.prepareStatement("SELECT nombre, dni FROM alumnos WHERE habilitado = 1");
                    dtm.setRowCount(0);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        String nombre = rs.getString("nombre");
                        String carrera = rs.getString("carrera");

                        dtm.addRow(new Object[]{"Nombre", "Carrera"});
                    }
                }
                catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                String nombrealumno = dtm.getValueAt(selectedRow, 0).toString();
                String apellidoalumno = dtm.getValueAt(selectedRow, 1).toString();

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/examen", "root", "root");
                    String query = "UPDATE alumnos SET habilitado = 0 WHERE nombre = ? AND apellido = ?";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, nombrealumno);
                    ps.setString(2, apellidoalumno);

                    con.close();

                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
