package dao;

import connection.ConnectionFactory;
import model.Proprietario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioDAO {

    public void inserir(Proprietario proprietario) {

        String sql = """
            INSERT INTO proprietario
            (
                nome,
                contato
            )
            VALUES
            (?,?)
            """;

        try (
                Connection conn =
                        ConnectionFactory.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(1,
                    proprietario.getNome());

            stmt.setString(2,
                    proprietario.getContato());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Proprietario> listar() {

        List<Proprietario> lista =
                new ArrayList<>();

        String sql =
                "SELECT * FROM proprietario";

        try (
                Connection conn =
                        ConnectionFactory.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                Proprietario p =
                        new Proprietario();

                p.setCodProprietario(
                        rs.getInt("cod_proprietario"));

                p.setNome(
                        rs.getString("nome"));

                p.setContato(
                        rs.getString("contato"));

                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Proprietario buscarPorId(int id) {

        String sql =
                "SELECT * FROM proprietario WHERE cod_proprietario=?";

        try (
                Connection conn =
                        ConnectionFactory.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            ResultSet rs =
                    stmt.executeQuery();

            if (rs.next()) {

                Proprietario p =
                        new Proprietario();

                p.setCodProprietario(
                        rs.getInt("cod_proprietario"));

                p.setNome(
                        rs.getString("nome"));

                p.setContato(
                        rs.getString("contato"));

                return p;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void atualizar(Proprietario proprietario) {

        String sql = """
            UPDATE proprietario
            SET
                nome=?,
                contato=?
            WHERE cod_proprietario=?
            """;

        try (
                Connection conn =
                        ConnectionFactory.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(
                    1,
                    proprietario.getNome());

            stmt.setString(
                    2,
                    proprietario.getContato());

            stmt.setInt(
                    3,
                    proprietario.getCodProprietario());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {

        String sql =
                "DELETE FROM proprietario WHERE cod_proprietario=?";

        try (
                Connection conn =
                        ConnectionFactory.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}