import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class agregarmateria {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton agregarButton;
    private JButton cancelarButton;
    private JPanel agregarmateriasPanel;
    private JFrame agregarmateriasFrame;

    public void setVisible(boolean b){
        agregarmateriasFrame = new JFrame("Ventana principal");
        agregarmateriasFrame.setContentPane(agregarmateriasPanel);
        agregarmateriasFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        agregarmateriasFrame.pack();
        agregarmateriasFrame.setVisible(b);
    }

    agregarmateria(){


        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField1.getText();
                int correlativa = Integer.parseInt(textField2.getText());
                int anio = Integer.parseInt(textField3.getText());

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "root", "root");
                    String query = "INSERT INTO materias (nombre, anio, correlativa, habilitado) VALUES (?, ?, ?, ?)";

                    PreparedStatement ps = con.prepareStatement(query) ;

                    ps.setString(1, nombre);
                    ps.setInt(2, anio);
                    ps.setInt(3, correlativa);
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
                agregarmateriasFrame.dispose();
            }
        });
    }
}
