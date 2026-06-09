package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.ImovelDAO;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import model.Endereco;
import model.FotoImovel;
import model.Imovel;
import model.Proprietario;
import model.TipoImovel;
import util.JsonUtil;

public class ImovelController implements HttpHandler {

        private final ImovelDAO dao = new ImovelDAO();

        @Override
        public void handle(HttpExchange exchange)
                        throws IOException {

                String metodo = exchange.getRequestMethod();

                try {

                        switch (metodo) {

                                case "POST":

                                        inserir(exchange);

                                        break;

                                case "GET":

                                        listar(exchange);

                                        break;

                                default:

                                        enviarResposta(
                                                        exchange,
                                                        405,
                                                        """
                                                                        {
                                                                            "erro":"Método não permitido"
                                                                        }
                                                                        """);
                        }

                } catch (Exception e) {

                        enviarResposta(
                                        exchange,
                                        500,
                                        """
                                                        {
                                                            "erro":"%s"
                                                        }
                                                        """.formatted(
                                                        e.getMessage()));
                }
        }

        private void inserir(HttpExchange exchange)
                        throws IOException {

                String body = new String(
                                exchange.getRequestBody()
                                                .readAllBytes());

                /*
                 * PROPRIETARIO
                 */

                String proprietarioJson = JsonUtil.getObject(
                                body,
                                "proprietario");

                Proprietario proprietario = new Proprietario();

                proprietario.setCodProprietario(
                                Integer.parseInt(
                                                JsonUtil.getValue(
                                                                proprietarioJson,
                                                                "codProprietario")));

                String tipoJson = JsonUtil.getObject(
                                body,
                                "tipoImovel");

                TipoImovel tipoImovel = new TipoImovel();

                tipoImovel.setCodTipoImovel(
                                Integer.parseInt(
                                                JsonUtil.getValue(
                                                                tipoJson,
                                                                "codTipoImovel")));

                String enderecoJson = JsonUtil.getObject(
                                body,
                                "endereco");

                Endereco endereco = new Endereco();

                endereco.setLogradouro(
                                JsonUtil.getValue(
                                                enderecoJson,
                                                "logradouro"));

                endereco.setBairro(
                                JsonUtil.getValue(
                                                enderecoJson,
                                                "bairro"));

                endereco.setCidade(
                                JsonUtil.getValue(
                                                enderecoJson,
                                                "cidade"));

                endereco.setEstado(
                                JsonUtil.getValue(
                                                enderecoJson,
                                                "estado"));

                endereco.setReferencia(
                                JsonUtil.getValue(
                                                enderecoJson,
                                                "referencia"));

                String fotosJson = JsonUtil.getArray(
                                body,
                                "fotos");

                List<String> fotosArray = JsonUtil.getObjectsFromArray(
                                fotosJson);

                List<FotoImovel> fotos = new ArrayList<>();

                for (String fotoJson : fotosArray) {

                        FotoImovel foto = new FotoImovel();

                        foto.setArqFoto(
                                        JsonUtil.getValue(
                                                        fotoJson,
                                                        "arqFoto"));

                        fotos.add(foto);
                }

                Imovel imovel = new Imovel();

                imovel.setMetragem(
                                Double.parseDouble(
                                                JsonUtil.getValue(
                                                                body,
                                                                "metragem")));

                imovel.setStatus(
                                JsonUtil.getValue(
                                                body,
                                                "status"));

                imovel.setValorVenda(
                                new BigDecimal(
                                                JsonUtil.getValue(
                                                                body,
                                                                "valorVenda")));

                imovel.setValorLocacao(
                                new BigDecimal(
                                                JsonUtil.getValue(
                                                                body,
                                                                "valorLocacao")));

                imovel.setQtdQuartos(
                                Integer.parseInt(
                                                JsonUtil.getValue(
                                                                body,
                                                                "qtdQuartos")));

                imovel.setQtdSuites(
                                Integer.parseInt(
                                                JsonUtil.getValue(
                                                                body,
                                                                "qtdSuites")));

                imovel.setQtdGaragens(
                                Integer.parseInt(
                                                JsonUtil.getValue(
                                                                body,
                                                                "qtdGaragens")));

                imovel.setProprietario(
                                proprietario);

                imovel.setTipoImovel(
                                tipoImovel);

                imovel.setEndereco(
                                endereco);

                imovel.setFotos(
                                fotos);

                dao.salvar(imovel);

                enviarResposta(
                                exchange,
                                201,
                                """
                                                {
                                                    "mensagem":"Imovel cadastrado com sucesso"
                                                }
                                                """);
        }

        private void enviarResposta(
                        HttpExchange exchange,
                        int status,
                        String json)
                        throws IOException {

                exchange.getResponseHeaders().add(
                                "Content-Type",
                                "application/json");

                byte[] bytes = json.getBytes();

                exchange.sendResponseHeaders(
                                status,
                                bytes.length);

                OutputStream os = exchange.getResponseBody();

                os.write(bytes);

                os.close();
        }

        private void listar(HttpExchange exchange)
                        throws IOException {

                List<Imovel> imoveis = dao.listar();

                StringBuilder json = new StringBuilder();

                json.append("[");

                for (int i = 0; i < imoveis.size(); i++) {

                        Imovel imovel = imoveis.get(i);

                        json.append(
                                        """
                                                        {
                                                            "codImovel":%d,
                                                            "metragem":%s,
                                                            "status":"%s"
                                                        }
                                                        """.formatted(
                                                        imovel.getCodImovel(),
                                                        imovel.getMetragem(),
                                                        imovel.getStatus()));

                        if (i < imoveis.size() - 1) {
                                json.append(",");
                        }
                }

                json.append("]");

                enviarResposta(
                                exchange,
                                200,
                                json.toString());
        }
}
