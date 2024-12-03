import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/examen";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    public String buscarTablaPorDNI(String dni) {
        String[] tablas = {"alumnos", "profesores", "administradores"};
        try (Connection con = conectar()) {
            for (String tabla : tablas) {
                String query = "SELECT dni FROM " + tabla + " WHERE dni = ?";
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, dni);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return tabla;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error durante la consulta: " + e.getMessage());
        }
        return null;
    }
}
