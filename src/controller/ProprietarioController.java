package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.ProprietarioDAO;
import model.Proprietario;
import util.JsonUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ProprietarioController
        implements HttpHandler {

    private final ProprietarioDAO dao =
            new ProprietarioDAO();

    @Override
    public void handle(HttpExchange exchange)
            throws IOException {

        String metodo =
                exchange.getRequestMethod();

        String path =
                exchange.getRequestURI()
                        .getPath();

        try {

            switch (metodo) {

                case "GET":

                    if(path.matches("/proprietarios/\\d+")) {

                        buscarPorId(exchange);

                    } else {

                        listar(exchange);
                    }

                    break;

                case "POST":

                    inserir(exchange);

                    break;

                case "PUT":

                    atualizar(exchange);

                    break;

                case "DELETE":

                    excluir(exchange);

                    break;

                default:

                    enviarResposta(
                            exchange,
                            405,
                            "{\"erro\":\"Método não permitido\"}"
                    );
            }

        } catch (Exception e) {

            enviarResposta(
                    exchange,
                    500,
                    "{\"erro\":\"" + e.getMessage() + "\"}"
            );
        }
    }

    private void inserir(HttpExchange exchange)
            throws IOException {

        String body =
                new String(
                        exchange.getRequestBody()
                                .readAllBytes()
                );

        Proprietario p =
                new Proprietario();

        p.setNome(
                JsonUtil.getValue(body,"nome"));

        p.setContato(
                JsonUtil.getValue(body,"contato"));

        dao.inserir(p);

        enviarResposta(
                exchange,
                201,
                "{\"mensagem\":\"Proprietário criado\"}"
        );
    }

    private void listar(HttpExchange exchange)
            throws IOException {

        List<Proprietario> lista =
                dao.listar();

        StringBuilder json =
                new StringBuilder("[");

        for(int i = 0; i < lista.size(); i++) {

            Proprietario p =
                    lista.get(i);

            json.append("{")
                    .append("\"codProprietario\":")
                    .append(p.getCodProprietario())
                    .append(",")

                    .append("\"nome\":\"")
                    .append(p.getNome())
                    .append("\",")

                    .append("\"contato\":\"")
                    .append(p.getContato())
                    .append("\"")
                    .append("}");

            if(i < lista.size()-1) {
                json.append(",");
            }
        }

        json.append("]");

        enviarResposta(
                exchange,
                200,
                json.toString()
        );
    }

    private void buscarPorId(HttpExchange exchange)
            throws IOException {

        String path =
                exchange.getRequestURI()
                        .getPath();

        int id =
                Integer.parseInt(
                        path.substring(
                                path.lastIndexOf("/") + 1
                        )
                );

        Proprietario p =
                dao.buscarPorId(id);

        if(p == null) {

            enviarResposta(
                    exchange,
                    404,
                    "{\"erro\":\"Proprietário não encontrado\"}"
            );

            return;
        }

        String json =
                "{"
                        + "\"codProprietario\":"
                        + p.getCodProprietario()
                        + ","

                        + "\"nome\":\""
                        + p.getNome()
                        + "\","

                        + "\"contato\":\""
                        + p.getContato()
                        + "\""

                        + "}";

        enviarResposta(
                exchange,
                200,
                json
        );
    }

    private void atualizar(HttpExchange exchange)
            throws IOException {

        String path =
                exchange.getRequestURI()
                        .getPath();

        int id =
                Integer.parseInt(
                        path.substring(
                                path.lastIndexOf("/") + 1
                        )
                );

        String body =
                new String(
                        exchange.getRequestBody()
                                .readAllBytes()
                );

        Proprietario p =
                new Proprietario();

        p.setCodProprietario(id);

        p.setNome(
                JsonUtil.getValue(body,"nome"));

        p.setContato(
                JsonUtil.getValue(body,"contato"));

        dao.atualizar(p);

        enviarResposta(
                exchange,
                200,
                "{\"mensagem\":\"Proprietário atualizado\"}"
        );
    }

    private void excluir(HttpExchange exchange)
            throws IOException {

        String path =
                exchange.getRequestURI()
                        .getPath();

        int id =
                Integer.parseInt(
                        path.substring(
                                path.lastIndexOf("/") + 1
                        )
                );

        dao.excluir(id);

        enviarResposta(
                exchange,
                200,
                "{\"mensagem\":\"Proprietário removido\"}"
        );
    }

    private void enviarResposta(
            HttpExchange exchange,
            int status,
            String json)
            throws IOException {

        exchange.getResponseHeaders()
                .set(
                        "Content-Type",
                        "application/json"
                );

        byte[] bytes =
                json.getBytes();

        exchange.sendResponseHeaders(
                status,
                bytes.length
        );

        OutputStream os =
                exchange.getResponseBody();

        os.write(bytes);

        os.close();
    }
}