import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class carreras {
    private JButton alumnosButton;
    private JButton materiasButton;
    private JButton carrerasButton;
    private JButton profesoresButton;
    private JPanel carrerasPanel;
    private JTable table1;
    private JButton agregarButton;
    private JButton eliminarButton;
    private JButton actualizarButton;
    private JFrame carrerasFrame;
    DefaultTableModel dtm = new DefaultTableModel();

    public void setVisible(boolean b){
        carrerasFrame = new JFrame("Materias");
        carrerasFrame.setContentPane(carrerasPanel);
        carrerasFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        carrerasFrame.pack();
        carrerasFrame.setVisible(b);
    }

    carreras(){
        table1.setModel(dtm);
        dtm.addColumn("Carrera");
        dtm.addRow(new Object[]{"Carrera"});

        materiasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                materias vista = new materias();
                vista.setVisible(true);
                carrerasFrame.dispose();
            }
        });
        alumnosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alumnos vista = new alumnos();
                vista.setVisible(true);
                carrerasFrame.dispose();
            }
        });
        carrerasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carreras vista = new carreras();
                vista.setVisible(true);
                carrerasFrame.dispose();
            }
        });
        profesoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profesores vista = new profesores();
                vista.setVisible(true);
                carrerasFrame.dispose();
            }
        });
    }


}
