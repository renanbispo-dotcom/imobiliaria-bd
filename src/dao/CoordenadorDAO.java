package dao;

import connection.ConnectionFactory;
import model.Coordenador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CoordenadorDAO {

    public void inserir(Coordenador coordenador) {

        String sql =
                """
                INSERT INTO coordenador
                (
                    nome_coordenador
                )
                VALUES
                (?)
                """;

        try (
                Connection conn =
                        ConnectionFactory.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(
                    1,
                    coordenador.getNomeCoordenador()
            );

            stmt.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public List<Coordenador> listar() {

        List<Coordenador> lista =
                new ArrayList<>();

        String sql =
                "SELECT * FROM coordenador";

        try (
                Connection conn =
                        ConnectionFactory.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                Coordenador coordenador =
                        new Coordenador();

                coordenador.setCodCoordenador(
                        rs.getInt("cod_coordenador")
                );

                coordenador.setNomeCoordenador(
                        rs.getString("nome_coordenador")
                );

                lista.add(coordenador);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }

    public Coordenador buscarPorId(int id) {

        String sql =
                "SELECT * FROM coordenador WHERE cod_coordenador=?";

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

                Coordenador coordenador =
                        new Coordenador();

                coordenador.setCodCoordenador(
                        rs.getInt("cod_coordenador")
                );

                coordenador.setNomeCoordenador(
                        rs.getString("nome_coordenador")
                );

                return coordenador;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public void atualizar(Coordenador coordenador) {

        String sql =
                """
                UPDATE coordenador
                SET
                    nome_coordenador=?
                WHERE
                    cod_coordenador=?
                """;

        try (
                Connection conn =
                        ConnectionFactory.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(
                    1,
                    coordenador.getNomeCoordenador()
            );

            stmt.setInt(
                    2,
                    coordenador.getCodCoordenador()
            );

            stmt.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void excluir(int id) {

        String sql =
                "DELETE FROM coordenador WHERE cod_coordenador=?";

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