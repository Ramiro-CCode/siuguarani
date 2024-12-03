import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login {
    private JPanel vistalogin;
    private JButton ingresarButton;
    private JTextField usuarioField;
    private JPasswordField contraField;
    private JFrame loginframe;
    private int usuario;

    public void setVisible(boolean b) {
        loginframe = new JFrame("Ingresar");
        loginframe.setContentPane(vistalogin);
        loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginframe.pack();
        loginframe.setLocationRelativeTo(null);
        loginframe.setVisible(b);
    }

    public login() {
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usuario = Integer.parseInt(usuarioField.getText());
                String usuario2 = usuarioField.getText();
                String contrasena = new String(contraField.getPassword());

                if (usuario2.equals("123") && contrasena.equals("123")) {
                    materias frame = new materias();
                    frame.setVisible(true);
                    loginframe.dispose();
                } else {
                    ConexionBD conexion = new ConexionBD();
                    String tabla = conexion.buscarTablaPorDNI(usuario2);

                    if (tabla == null) {
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                    } else {
                        abrirVista(tabla);
                        loginframe.dispose();
                    }
                }
            }
        });
    }

    private void abrirVista(String tabla) {
        switch (tabla) {
            case "alumnos":
                vistaalumnomaterias vista1 = new vistaalumnomaterias(usuario);
                vista1.setVisible(true);
                break;
            case "profesores":
                profesormaterias vista2 = new profesormaterias(usuario);
                vista2.setVisible(true);
                break;
            case "administradores":
                materias vista3 = new materias();
                vista3.setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Error desconocido.");
        }
    }

    public static void main(String[] args) {
        login loginFrame = new login();
        loginFrame.setVisible(true);
    }
}
