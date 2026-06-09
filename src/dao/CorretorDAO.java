package dao;

import connection.ConnectionFactory;
import model.Corretor;
import model.Coordenador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CorretorDAO {

    public void inserir(Corretor corretor) {

        

        String sql =
                """
                INSERT INTO corretor
                (
                    nome_corretor,
                    creci,
                    cod_coordenador
                )
                VALUES
                (?,?,?)
                """;

        try (
                Connection conn =
                        ConnectionFactory.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(
                    1,
                    corretor.getNomeCorretor()
            );

            stmt.setString(
                    2,
                    corretor.getCreci()
            );

            stmt.setInt(
                    3,
                    corretor.getCoordenador()
                            .getCodCoordenador()
            );

            stmt.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public List<Corretor> listar() {

        List<Corretor> lista =
                new ArrayList<>();

        String sql =
                """
                SELECT
                    c.*,
                    co.nome_coordenador
                FROM corretor c
                INNER JOIN coordenador co
                    ON co.cod_coordenador =
                       c.cod_coordenador
                """;

        try (
                Connection conn =
                        ConnectionFactory.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                Corretor corretor =
                        new Corretor();

                corretor.setCodCorretor(
                        rs.getInt("cod_corretor")
                );

                corretor.setNomeCorretor(
                        rs.getString("nome_corretor")
                );

                corretor.setCreci(
                        rs.getString("creci")
                );

                Coordenador coordenador =
                        new Coordenador();

                coordenador.setCodCoordenador(
                        rs.getInt("cod_coordenador")
                );

                coordenador.setNomeCoordenador(
                        rs.getString("nome_coordenador")
                );

                corretor.setCoordenador(
                        coordenador
                );

                lista.add(corretor);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }

    public Corretor buscarPorId(int id) {

        String sql =
                """
                SELECT
                    c.*,
                    co.nome_coordenador
                FROM corretor c
                INNER JOIN coordenador co
                    ON co.cod_coordenador =
                       c.cod_coordenador
                WHERE c.cod_corretor=?
                """;

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

                Corretor corretor =
                        new Corretor();

                corretor.setCodCorretor(
                        rs.getInt("cod_corretor")
                );

                corretor.setNomeCorretor(
                        rs.getString("nome_corretor")
                );

                corretor.setCreci(
                        rs.getString("creci")
                );

                Coordenador coordenador =
                        new Coordenador();

                coordenador.setCodCoordenador(
                        rs.getInt("cod_coordenador")
                );

                coordenador.setNomeCoordenador(
                        rs.getString("nome_coordenador")
                );

                corretor.setCoordenador(
                        coordenador
                );

                return corretor;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public void atualizar(Corretor corretor) {

        String sql =
                """
                UPDATE corretor
                SET
                    nome_corretor=?,
                    creci=?,
                    cod_coordenador=?
                WHERE
                    cod_corretor=?
                """;

        try (
                Connection conn =
                        ConnectionFactory.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(
                    1,
                    corretor.getNomeCorretor()
            );

            stmt.setString(
                    2,
                    corretor.getCreci()
            );

            stmt.setInt(
                    3,
                    corretor.getCoordenador()
                            .getCodCoordenador()
            );

            stmt.setInt(
                    4,
                    corretor.getCodCorretor()
            );

            stmt.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void excluir(int id) {

        String sql =
                "DELETE FROM corretor WHERE cod_corretor=?";

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