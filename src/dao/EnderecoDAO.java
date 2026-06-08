package dao;

import connection.ConnectionFactory;
import model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {

    public void inserir(Endereco endereco) {

        String sql = """
                INSERT INTO endereco
                (
                    logradouro,
                    bairro,
                    cidade,
                    estado,
                    referencia
                )
                VALUES
                (?,?,?,?,?)
                """;

        try (
                Connection conn =
                        ConnectionFactory.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getBairro());
            stmt.setString(3, endereco.getCidade());
            stmt.setString(4, endereco.getEstado());
            stmt.setString(5, endereco.getReferencia());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Endereco> listar() {

        List<Endereco> lista =
                new ArrayList<>();

        String sql =
                "SELECT * FROM endereco";

        try (
                Connection conn =
                        ConnectionFactory.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                Endereco e =
                        new Endereco();

                e.setCodEndereco(
                        rs.getInt("cod_endereco"));

                e.setLogradouro(
                        rs.getString("logradouro"));

                e.setBairro(
                        rs.getString("bairro"));

                e.setCidade(
                        rs.getString("cidade"));

                e.setEstado(
                        rs.getString("estado"));

                e.setReferencia(
                        rs.getString("referencia"));

                lista.add(e);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    public Endereco buscarPorId(int id) {

        String sql =
                "SELECT * FROM endereco WHERE cod_endereco=?";

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

                Endereco e =
                        new Endereco();

                e.setCodEndereco(
                        rs.getInt("cod_endereco"));

                e.setLogradouro(
                        rs.getString("logradouro"));

                e.setBairro(
                        rs.getString("bairro"));

                e.setCidade(
                        rs.getString("cidade"));

                e.setEstado(
                        rs.getString("estado"));

                e.setReferencia(
                        rs.getString("referencia"));

                return e;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void atualizar(Endereco endereco) {

        String sql = """
                UPDATE endereco
                SET
                    logradouro=?,
                    bairro=?,
                    cidade=?,
                    estado=?,
                    referencia=?
                WHERE cod_endereco=?
                """;

        try (
                Connection conn =
                        ConnectionFactory.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getBairro());
            stmt.setString(3, endereco.getCidade());
            stmt.setString(4, endereco.getEstado());
            stmt.setString(5, endereco.getReferencia());
            stmt.setInt(6, endereco.getCodEndereco());

            stmt.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void excluir(int id) {

        String sql =
                "DELETE FROM endereco WHERE cod_endereco=?";

        try (
                Connection conn =
                        ConnectionFactory.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}