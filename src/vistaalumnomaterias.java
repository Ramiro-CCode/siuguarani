import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class vistaalumnomaterias {
    private JTable table1;
    private JPanel vistaalumnosmateriaspanel;
    private JButton inscribirseAUnaMateriaButton;
    private JButton materiasButton;
    private JButton carrerasButton;
    private JFrame vistaalumnomateriasframe;
    private DefaultTableModel dtm = new DefaultTableModel();

    public void setVisible(boolean b){
        vistaalumnomateriasframe = new JFrame("Alumnos");
        vistaalumnomateriasframe.setContentPane(vistaalumnosmateriaspanel);
        vistaalumnomateriasframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vistaalumnomateriasframe.pack();
        vistaalumnomateriasframe.setVisible(b);
    }

    public vistaalumnomaterias(int dnialumno) {
        table1.setModel(dtm);
        dtm.addColumn("Materia");
        dtm.addColumn("Carrera");
        dtm.addColumn("Estado");
        dtm.addRow(new Object[]{"Materia", "Carrera", "Estado"});

        inscribirseAUnaMateriaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inscripcionmateria vista = new inscripcionmateria(dnialumno);
                vista.setVisible(true);
            }
        });


        carrerasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alumnoscarreras vista = new alumnoscarreras(dnialumno);
                vista.setVisible(true);
                vistaalumnomateriasframe.dispose();
            }
        });
    }
}
