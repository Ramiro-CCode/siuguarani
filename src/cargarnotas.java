import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class cargarnotas {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton cargarButton;
    private JPanel panel1;
    private JFrame frame;

    public void setVisible(boolean visible) {
        frame = new JFrame("Cargar Notas");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(visible);
    }

    public cargarnotas() {
        cargarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dnialumno;
                int idcarrera;
                int estado;

                try {
                    dnialumno = Integer.parseInt(textField1.getText());
                    idcarrera = Integer.parseInt(textField2.getText());
                    estado = Integer.parseInt(textField3.getText());

                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/examen",
                            "root",
                            "root"
                    );

                    String query = "UPDATE materias SET estado = ? WHERE dnialumno = ? AND idcarrera = ?";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setInt(1, estado);
                    ps.setInt(2, dnialumno);
                    ps.setInt(3, idcarrera);

                    int rowsUpdated = ps.executeUpdate();

                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(null, "Estado actualizado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró un registro con los datos ingresados.");
                    }

                    ps.close();
                    con.close();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese valores numéricos válidos.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el estado: " + ex.getMessage());
                }
            }
        });
    }
}
