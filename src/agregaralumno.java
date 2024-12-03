import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class agregaralumno {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton agregarButton;
    private JButton cancelarButton;
    private JPanel agregaralumnopanel;
    private JFrame agregaralumnosFrame;

    public void setVisible(boolean b){
        agregaralumnosFrame = new JFrame("Agregar alumno");
        agregaralumnosFrame.setContentPane(agregaralumnopanel);
        agregaralumnosFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        agregaralumnosFrame.pack();
        agregaralumnosFrame.setVisible(b);
    }

    agregaralumno(){


        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField1.getText();
                String apellido = textField2.getText();
                String carrera = textField3.getText();

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "root", "root");
                    String query = "INSERT INTO alumnos (nombre, apellido, carrera, habilitado) VALUES (?, ?, ?, ?)";

                    PreparedStatement ps = con.prepareStatement(query) ;

                    ps.setString(1, nombre);
                    ps.setString(2, apellido);
                    ps.setString(3, carrera);
                    ps.setBoolean(4, true);
                    ps.executeUpdate();

                } catch (ClassNotFoundException | SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregaralumnosFrame.dispose();
            }
        });
    }
}
