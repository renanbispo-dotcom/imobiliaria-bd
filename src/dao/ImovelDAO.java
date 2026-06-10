package dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Endereco;
import model.FotoImovel;
import model.Imovel;
import model.Proprietario;
import model.TipoImovel;

public class ImovelDAO {

        public void salvar(Imovel imovel) {

                Connection conn = null;

                try {

                        conn = ConnectionFactory.getConnection();

                        conn.setAutoCommit(false);

                        String sqlEndereco = "INSERT INTO endereco "
                                        + "(logradouro, bairro, cidade, estado, referencia) "
                                        + "VALUES (?, ?, ?, ?, ?)";

                        Integer codEndereco;

                        try (PreparedStatement stmtEndereco = conn.prepareStatement(
                                        sqlEndereco,
                                        PreparedStatement.RETURN_GENERATED_KEYS)) {

                                stmtEndereco.setString(
                                                1,
                                                imovel.getEndereco().getLogradouro());

                                stmtEndereco.setString(
                                                2,
                                                imovel.getEndereco().getBairro());

                                stmtEndereco.setString(
                                                3,
                                                imovel.getEndereco().getCidade());

                                stmtEndereco.setString(
                                                4,
                                                imovel.getEndereco().getEstado());

                                stmtEndereco.setString(
                                                5,
                                                imovel.getEndereco().getReferencia());

                                stmtEndereco.executeUpdate();

                                try (ResultSet rs = stmtEndereco.getGeneratedKeys()) {

                                        if (!rs.next()) {
                                                throw new RuntimeException(
                                                                "Nao foi possivel obter o codigo do endereco.");
                                        }

                                        codEndereco = rs.getInt(1);
                                }
                        }

                        String sqlImovel = "INSERT INTO imovel ("
                                        + "metragem, "
                                        + "status, "
                                        + "valor_venda, "
                                        + "valor_locacao, "
                                        + "qtd_quartos, "
                                        + "qtd_suites, "
                                        + "qtd_garagens, "
                                        + "cod_proprietario, "
                                        + "cod_tipo_imovel, "
                                        + "cod_endereco"
                                        + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                        Integer codImovel;

                        try (PreparedStatement stmtImovel = conn.prepareStatement(
                                        sqlImovel,
                                        PreparedStatement.RETURN_GENERATED_KEYS)) {

                                stmtImovel.setDouble(
                                                1,
                                                imovel.getMetragem());

                                stmtImovel.setString(
                                                2,
                                                imovel.getStatus());

                                stmtImovel.setBigDecimal(
                                                3,
                                                imovel.getValorVenda());

                                stmtImovel.setBigDecimal(
                                                4,
                                                imovel.getValorLocacao());

                                stmtImovel.setInt(
                                                5,
                                                imovel.getQtdQuartos());

                                stmtImovel.setInt(
                                                6,
                                                imovel.getQtdSuites());

                                stmtImovel.setInt(
                                                7,
                                                imovel.getQtdGaragens());

                                stmtImovel.setInt(
                                                8,
                                                imovel.getProprietario()
                                                                .getCodProprietario());

                                stmtImovel.setInt(
                                                9,
                                                imovel.getTipoImovel()
                                                                .getCodTipoImovel());

                                stmtImovel.setInt(
                                                10,
                                                codEndereco);

                                stmtImovel.executeUpdate();

                                try (ResultSet rs = stmtImovel.getGeneratedKeys()) {

                                        if (!rs.next()) {
                                                throw new RuntimeException(
                                                                "Nao foi possivel obter o codigo do imovel.");
                                        }

                                        codImovel = rs.getInt(1);
                                }
                        }

                        if (imovel.getFotos() != null
                                        && !imovel.getFotos().isEmpty()) {

                                String sqlFoto = "INSERT INTO foto_imovel "
                                                + "(arq_foto, cod_imovel) "
                                                + "VALUES (?, ?)";

                                try (PreparedStatement stmtFoto = conn.prepareStatement(sqlFoto)) {

                                        for (FotoImovel foto : imovel.getFotos()) {

                                                stmtFoto.setString(
                                                                1,
                                                                foto.getArqFoto());

                                                stmtFoto.setInt(
                                                                2,
                                                                codImovel);

                                                stmtFoto.executeUpdate();
                                        }
                                }
                        }

                        conn.commit();

                } catch (Exception e) {

                        if (conn != null) {

                                try {
                                        conn.rollback();
                                } catch (SQLException ex) {
                                        ex.printStackTrace();
                                }
                        }

                        throw new RuntimeException(
                                        "Erro ao salvar imovel.",
                                        e);

                } finally {

                        if (conn != null) {

                                try {
                                        conn.close();
                                } catch (SQLException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }

        public List<Imovel> listar() {

        List<Imovel> lista = new ArrayList<>();
        
        String sql = """
                SELECT
                    i.cod_imovel,
                    i.metragem,
                    i.status,
                    i.valor_venda,
                    i.valor_locacao,
                    i.qtd_quartos,
                    i.qtd_suites,
                    i.qtd_garagens,

                    p.cod_proprietario,
                    p.nome AS nome_proprietario,

                    t.cod_tipo_imovel,
                    t.tipo AS tipo_imovel,

                    e.cod_endereco,
                    e.logradouro,
                    e.bairro,
                    e.cidade,
                    e.estado,
                    e.referencia,

                    f.cod_foto_imovel,
                    f.arq_foto

                FROM imovel i
                INNER JOIN proprietario p ON p.cod_proprietario = i.cod_proprietario
                INNER JOIN tipo_imovel t  ON t.cod_tipo_imovel = i.cod_tipo_imovel
                INNER JOIN endereco e     ON e.cod_endereco = i.cod_endereco
                LEFT JOIN foto_imovel f   ON f.cod_imovel = i.cod_imovel
                ORDER BY i.cod_imovel;
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            Imovel imovelAtual = null;

            while (rs.next()) {
                int codImovel = rs.getInt("cod_imovel");

                // Como a query repete linhas para imóveis com mais de uma foto,
                // agrupamos as fotos no mesmo objeto de imóvel.
                if (imovelAtual == null || imovelAtual.getCodImovel() != codImovel) {
                    
                    imovelAtual = new Imovel();
                    imovelAtual.setCodImovel(codImovel);
                    imovelAtual.setMetragem(rs.getDouble("metragem"));
                    imovelAtual.setStatus(rs.getString("status"));
                    imovelAtual.setValorVenda(rs.getBigDecimal("valor_venda"));
                    imovelAtual.setValorLocacao(rs.getBigDecimal("valor_locacao"));
                    imovelAtual.setQtdQuartos(rs.getInt("qtd_quartos"));
                    imovelAtual.setQtdSuites(rs.getInt("qtd_suites"));
                    imovelAtual.setQtdGaragens(rs.getInt("qtd_garagens"));

                    // Mapeamento Corrigido do Proprietário
                    Proprietario proprietario = new Proprietario();
                    proprietario.setCodProprietario(rs.getInt("cod_proprietario"));
                    proprietario.setNome(rs.getString("nome_proprietario")); // <--- CORRIGIDO AQUI
                    imovelAtual.setProprietario(proprietario);

                    // Mapeamento Corrigido do Tipo
                    TipoImovel tipo = new TipoImovel();
                    tipo.setCodTipoImovel(rs.getInt("cod_tipo_imovel"));
                    tipo.setTipo(rs.getString("tipo_imovel")); // <--- CORRIGIDO AQUI
                    imovelAtual.setTipoImovel(tipo);

                    // Mapeamento do Endereço
                    Endereco endereco = new Endereco();
                    endereco.setCodEndereco(rs.getInt("cod_endereco"));
                    endereco.setLogradouro(rs.getString("logradouro"));
                    endereco.setBairro(rs.getString("bairro"));
                    endereco.setCidade(rs.getString("cidade"));
                    endereco.setEstado(rs.getString("estado"));
                    endereco.setReferencia(rs.getString("referencia"));
                    imovelAtual.setEndereco(endereco);

                    // Inicializa a lista de fotos vazia para o imóvel
                    imovelAtual.setFotos(new ArrayList<>());

                    lista.add(imovelAtual);
                }

                // Se houver uma foto associada nesta linha, adiciona na lista do imóvel atual
                String nomeFoto = rs.getString("arq_foto");
                if (nomeFoto != null) {
                    FotoImovel foto = new FotoImovel();
                    foto.setCodFotoImovel(rs.getInt("cod_foto_imovel"));
                    foto.setArqFoto(nomeFoto);
                    imovelAtual.getFotos().add(foto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar imoveis.", e);
        }

        return lista;
    }
}
