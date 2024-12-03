import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class profesores extends JPanel {
    private JButton agregarButton;
    private JButton eliminarButton;
    private JButton editarButton;
    private JTable table1;
    private JPanel profesoresPanel;
    private JButton actualizarButton;
    private JButton alumnosButton;
    private JButton profesoresButton;
    private JButton carrerasButton;
    private JButton materiasButton;
    private JFrame profesoresFrame;
    private DefaultTableModel dtm;

    public void setVisible(boolean b){
        profesoresFrame = new JFrame("Materias");
        profesoresFrame.setContentPane(profesoresPanel);
        profesoresFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        profesoresFrame.pack();
        profesoresFrame.setVisible(b);
    }

    public profesores(){
        alumnosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alumnos vista = new alumnos();
                vista.setVisible(true);
                profesoresFrame.dispose();
            }
        });
        materiasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                materias vista = new materias();
                vista.setVisible(true);
                profesoresFrame.dispose();
            }
        });
        carrerasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carreras vista = new carreras();
                vista.setVisible(true);
                profesoresFrame.dispose();
            }
        });
        profesoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profesores vista = new profesores();
                vista.setVisible(true);
                profesoresFrame.dispose();
            }
        });

        dtm = new DefaultTableModel();
        table1.setModel(dtm);
        dtm.addColumn("Nombre");
        dtm.addColumn("Apellido");
        dtm.addRow(new Object[]{"Nombre","DNI"});

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarprofesores vista = new agregarprofesores();
                vista.setVisible(true);
            }
        });

        actualizarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/examen", "root", "root");
                    PreparedStatement ps = con.prepareStatement("SELECT nombre, dni FROM profesores WHERE habilitado = 1");
                    dtm.setRowCount(0);
                    dtm.addRow(new Object[]{"Nombre","DNI"});
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        String nombre = rs.getString("nombre");
                        int dni = rs.getInt("dni");

                        dtm.addRow(new Object[]{nombre, dni});
                    }
                    rs.close();
                    ps.close();
                    con.close();
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }


    });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                int dni = (int) dtm.getValueAt(selectedRow, 1);

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "root", "root");
                    String query = "UPDATE profesores SET habilitado = 0 WHERE dni = ?";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setInt(1, dni);

                    con.close();

                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
