package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/imobiliaria";

    private static final String USER = "postgres";

    private static final String PASSWORD = "SQL@12345sql";

    public static Connection getConnection() {

        try {
            Class.forName("org.postgresql.Driver");

            return DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "Driver JDBC do PostgreSQL não encontrado. Coloque o JAR no classpath.",
                    e
            );
        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao conectar com banco.",
                    e
            );
        }
    }
}