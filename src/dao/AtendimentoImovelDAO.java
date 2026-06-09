package dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.AtendimentoImovel;
import model.Cliente;
import model.Corretor;
import model.Imovel;

public class AtendimentoImovelDAO {

    public void salvar(AtendimentoImovel atendimento) {

        String sql = """
                INSERT INTO atendimento_imovel
                (
                    cod_imovel,
                    cod_cliente,
                    cod_corretor,
                    data_atendimento,
                    status,
                    valor_venda,
                    observacoes
                )
                VALUES
                (?,?,?,?,?,?,?)
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    atendimento.getImovel().getCodImovel()
            );

            stmt.setInt(
                    2,
                    atendimento.getCliente().getCodCliente()
            );

            stmt.setInt(
                    3,
                    atendimento.getCorretor().getCodCorretor()
            );

            if (atendimento.getDataAtendimento() == null
                    || atendimento.getDataAtendimento().isBlank()) {
                stmt.setNull(4, java.sql.Types.TIMESTAMP);
            } else {
                stmt.setTimestamp(
                        4,
                        Timestamp.valueOf(atendimento.getDataAtendimento())
                );
            }

            stmt.setString(
                    5,
                    atendimento.getStatus()
            );

            stmt.setBigDecimal(
                    6,
                    atendimento.getValorVenda()
            );

            stmt.setString(
                    7,
                    atendimento.getObservacoes()
            );

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar atendimento de imóvel", e);
        }
    }

    public List<AtendimentoImovel> listar() {

        List<AtendimentoImovel> lista = new ArrayList<>();

        String sql = """
                SELECT
                    a.*,
                    i.metragem AS imovel_metragem,
                    i.status AS imovel_status,
                    c.nome AS cliente_nome,
                    r.nome_corretor AS corretor_nome
                FROM atendimento_imovel a
                INNER JOIN imovel i
                    ON i.cod_imovel = a.cod_imovel
                INNER JOIN cliente c
                    ON c.cod_cliente = a.cod_cliente
                INNER JOIN corretor r
                    ON r.cod_corretor = a.cod_corretor
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                AtendimentoImovel atendimento = new AtendimentoImovel();

                atendimento.setCodAtendimento(
                        rs.getInt("cod_atendimento")
                );

                Imovel imovel = new Imovel();
                imovel.setCodImovel(rs.getInt("cod_imovel"));
                imovel.setMetragem(rs.getDouble("imovel_metragem"));
                imovel.setStatus(rs.getString("imovel_status"));
                atendimento.setImovel(imovel);

                Cliente cliente = new Cliente();
                cliente.setCodCliente(rs.getInt("cod_cliente"));
                cliente.setNome(rs.getString("cliente_nome"));
                atendimento.setCliente(cliente);

                Corretor corretor = new Corretor();
                corretor.setCodCorretor(rs.getInt("cod_corretor"));
                corretor.setNomeCorretor(rs.getString("corretor_nome"));
                atendimento.setCorretor(corretor);

                Timestamp data = rs.getTimestamp("data_atendimento");
                atendimento.setDataAtendimento(
                        data != null ? data.toString() : null
                );

                atendimento.setStatus(rs.getString("status"));
                atendimento.setValorVenda(rs.getBigDecimal("valor_venda"));
                atendimento.setObservacoes(rs.getString("observacoes"));

                lista.add(atendimento);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar atendimentos de imóveis", e);
        }

        return lista;
    }

    public AtendimentoImovel buscarPorId(int id) {

        String sql = """
                SELECT
                    a.*,
                    c.nome AS cliente_nome,
                    r.nome_corretor AS corretor_nome
                FROM atendimento_imovel a
                INNER JOIN cliente c
                    ON c.cod_cliente = a.cod_cliente
                INNER JOIN corretor r
                    ON r.cod_corretor = a.cod_corretor
                WHERE a.cod_atendimento = ?
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    AtendimentoImovel atendimento = new AtendimentoImovel();

                    atendimento.setCodAtendimento(
                            rs.getInt("cod_atendimento")
                    );

                    Imovel imovel = new Imovel();
                    imovel.setCodImovel(rs.getInt("cod_imovel"));
                    atendimento.setImovel(imovel);

                    Cliente cliente = new Cliente();
                    cliente.setCodCliente(rs.getInt("cod_cliente"));
                    cliente.setNome(rs.getString("cliente_nome"));
                    atendimento.setCliente(cliente);

                    Corretor corretor = new Corretor();
                    corretor.setCodCorretor(rs.getInt("cod_corretor"));
                    corretor.setNomeCorretor(rs.getString("corretor_nome"));
                    atendimento.setCorretor(corretor);

                    Timestamp data = rs.getTimestamp("data_atendimento");
                    atendimento.setDataAtendimento(
                            data != null ? data.toString() : null
                    );

                    atendimento.setStatus(rs.getString("status"));
                    atendimento.setValorVenda(rs.getBigDecimal("valor_venda"));
                    atendimento.setObservacoes(rs.getString("observacoes"));

                    return atendimento;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar atendimento de imóvel", e);
        }

        return null;
    }

    public void excluir(int id) {

        String sql = "DELETE FROM atendimento_imovel WHERE cod_atendimento = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir atendimento de imóvel", e);
        }
    }
}
