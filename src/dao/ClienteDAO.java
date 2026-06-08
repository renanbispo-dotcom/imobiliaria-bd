package dao;

import connection.ConnectionFactory;
import model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void inserir(Cliente cliente) {

        String sql = """
            INSERT INTO cliente
            (
                nome,
                cpf,
                telefone,
                email,
                endereco
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

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getEndereco());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> listar() {

        List<Cliente> lista =
                new ArrayList<>();

        String sql =
                "SELECT * FROM cliente";

        try (
                Connection conn =
                        ConnectionFactory.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                Cliente cliente =
                        new Cliente();

                cliente.setCodCliente(
                        rs.getInt("cod_cliente"));

                cliente.setNome(
                        rs.getString("nome"));

                cliente.setCpf(
                        rs.getString("cpf"));

                cliente.setTelefone(
                        rs.getString("telefone"));

                cliente.setEmail(
                        rs.getString("email"));

                cliente.setEndereco(
                        rs.getString("endereco"));

                lista.add(cliente);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Cliente buscarPorId(int id) {

        String sql =
                "SELECT * FROM cliente WHERE cod_cliente=?";

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

                Cliente cliente =
                        new Cliente();

                cliente.setCodCliente(
                        rs.getInt("cod_cliente"));

                cliente.setNome(
                        rs.getString("nome"));

                cliente.setCpf(
                        rs.getString("cpf"));

                cliente.setTelefone(
                        rs.getString("telefone"));

                cliente.setEmail(
                        rs.getString("email"));

                cliente.setEndereco(
                        rs.getString("endereco"));

                return cliente;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void excluir(int id) {

        String sql =
                "DELETE FROM cliente WHERE cod_cliente=?";

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

    public void atualizar(Cliente cliente) {

    String sql = """
        UPDATE cliente
        SET
            nome = ?,
            cpf = ?,
            telefone = ?,
            email = ?,
            endereco = ?
        WHERE cod_cliente = ?
        """;

    try (
            Connection conn =
                    ConnectionFactory.getConnection();

            PreparedStatement stmt =
                    conn.prepareStatement(sql)
    ) {

        stmt.setString(1, cliente.getNome());
        stmt.setString(2, cliente.getCpf());
        stmt.setString(3, cliente.getTelefone());
        stmt.setString(4, cliente.getEmail());
        stmt.setString(5, cliente.getEndereco());

        stmt.setInt(
                6,
                cliente.getCodCliente()
        );

        stmt.executeUpdate();

    } catch (Exception e) {

        e.printStackTrace();
    }
}
}
