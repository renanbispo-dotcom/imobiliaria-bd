package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.AtendimentoImovelDAO;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;
import model.AtendimentoImovel;
import model.Cliente;
import model.Corretor;
import model.Imovel;
import util.JsonUtil;

public class AtendimentoImovelController implements HttpHandler {

    private final AtendimentoImovelDAO dao = new AtendimentoImovelDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String metodo = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        try {
            switch (metodo) {
                case "GET":
                    if (path.matches("/atendimento-imovel/\\d+")) {
                        buscarPorId(exchange);
                    } else {
                        listar(exchange);
                    }
                    break;
                case "POST":
                    inserir(exchange);
                    break;
                case "DELETE":
                    excluir(exchange);
                    break;
                default:
                    enviarResposta(exchange, 405, "{\"erro\":\"Método não permitido\"}");
            }
        } catch (Exception e) {
            enviarResposta(exchange, 500, "{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    private void inserir(HttpExchange exchange) throws IOException {

        String body = new String(exchange.getRequestBody().readAllBytes());

        String imovelJson = JsonUtil.getObject(body, "imovel");
        String clienteJson = JsonUtil.getObject(body, "cliente");
        String corretorJson = JsonUtil.getObject(body, "corretor");

        Imovel imovel = new Imovel();
        imovel.setCodImovel(Integer.parseInt(JsonUtil.getValue(imovelJson, "codImovel")));

        Cliente cliente = new Cliente();
        cliente.setCodCliente(Integer.parseInt(JsonUtil.getValue(clienteJson, "codCliente")));

        Corretor corretor = new Corretor();
        corretor.setCodCorretor(Integer.parseInt(JsonUtil.getValue(corretorJson, "codCorretor")));

        AtendimentoImovel atendimento = new AtendimentoImovel();
        atendimento.setImovel(imovel);
        atendimento.setCliente(cliente);
        atendimento.setCorretor(corretor);
        atendimento.setStatus(JsonUtil.getValue(body, "status"));
        atendimento.setValorVenda(new BigDecimal(JsonUtil.getValue(body, "valorVenda")));
        atendimento.setObservacoes(JsonUtil.getValue(body, "observacoes"));

        dao.salvar(atendimento);

        enviarResposta(exchange, 201, "{\"mensagem\":\"Atendimento de imóvel cadastrado com sucesso\"}");
    }

    private void listar(HttpExchange exchange) throws IOException {

    List<AtendimentoImovel> atendimentos = dao.listar();

    StringBuilder json = new StringBuilder();
    json.append("[");

    for (int i = 0; i < atendimentos.size(); i++) {
        AtendimentoImovel atendimento = atendimentos.get(i);
        json.append("{")
                .append("\"codAtendimento\":")
                .append(atendimento.getCodAtendimento()).append(",")
                
                // --- BLOCO DO IMÓVEL COM TIPO ANINHADO ---
                .append("\"imovel\":{")
                .append("\"codImovel\":").append(atendimento.getImovel().getCodImovel()).append(",")
                .append("\"metragem\":").append(atendimento.getImovel().getMetragem()).append(",")
                .append("\"status\":\"").append(atendimento.getImovel().getStatus()).append("\",")
                .append("\"tipoImovel\":{")
                    .append("\"codTipoImovel\":").append(atendimento.getImovel().getTipoImovel().getCodTipoImovel()).append(",")
                    .append("\"tipo\":\"").append(atendimento.getImovel().getTipoImovel().getTipo()).append("\"")
                .append("}")
                .append("}")
                // -----------------------------------------
                
                .append(",")
                .append("\"cliente\":{")
                .append("\"codCliente\":")
                .append(atendimento.getCliente().getCodCliente()).append(",")
                .append("\"nome\":\"")
                .append(atendimento.getCliente().getNome()).append("\"}")
                .append(",")
                .append("\"corretor\":{")
                .append("\"codCorretor\":")
                .append(atendimento.getCorretor().getCodCorretor()).append(",")
                .append("\"nomeCorretor\":\"")
                .append(atendimento.getCorretor().getNomeCorretor()).append("\"}")
                .append(",")
                .append("\"dataAtendimento\":\"")
                .append(atendimento.getDataAtendimento() == null ? "" : atendimento.getDataAtendimento()).append("\"")
                .append(",")
                .append("\"status\":\"")
                .append(atendimento.getStatus()).append("\"")
                .append(",")
                .append("\"valorVenda\":")
                .append(atendimento.getValorVenda()).append(",")
                .append("\"observacoes\":\"")
                .append(atendimento.getObservacoes() == null ? "" : atendimento.getObservacoes()).append("\"")
                .append("}");

        if (i < atendimentos.size() - 1) {
            json.append(",");
        }
    }

    json.append("]");

    enviarResposta(exchange, 200, json.toString());
}

    private void buscarPorId(HttpExchange exchange) throws IOException {

        String path = exchange.getRequestURI().getPath();
        int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));

        AtendimentoImovel atendimento = dao.buscarPorId(id);

        if (atendimento == null) {
            enviarResposta(exchange, 404, "{\"erro\":\"Atendimento não encontrado\"}");
            return;
        }

        String json = "{"
                + "\"codAtendimento\":" + atendimento.getCodAtendimento() + ","
                + "\"imovel\":{\"codImovel\":" + atendimento.getImovel().getCodImovel() + "},"
                + "\"cliente\":{\"codCliente\":" + atendimento.getCliente().getCodCliente() + ",\"nome\":\"" + atendimento.getCliente().getNome() + "\"},"
                + "\"corretor\":{\"codCorretor\":" + atendimento.getCorretor().getCodCorretor() + ",\"nomeCorretor\":\"" + atendimento.getCorretor().getNomeCorretor() + "\"},"
                + "\"dataAtendimento\":\"" + (atendimento.getDataAtendimento() == null ? "" : atendimento.getDataAtendimento()) + "\","
                + "\"status\":\"" + atendimento.getStatus() + "\","
                + "\"valorVenda\":" + atendimento.getValorVenda() + ","
                + "\"observacoes\":\"" + (atendimento.getObservacoes() == null ? "" : atendimento.getObservacoes()) + "\""
                + "}";

        enviarResposta(exchange, 200, json);
    }

    private void excluir(HttpExchange exchange) throws IOException {

        String path = exchange.getRequestURI().getPath();
        int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));

        dao.excluir(id);

        enviarResposta(exchange, 200, "{\"mensagem\":\"Atendimento removido com sucesso\"}");
    }

    private void enviarResposta(HttpExchange exchange, int status, String json) throws IOException {

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        byte[] bytes = json.getBytes();
        exchange.sendResponseHeaders(status, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
