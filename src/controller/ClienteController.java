package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.ClienteDAO;
import model.Cliente;
import util.JsonUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ClienteController implements HttpHandler {

    private final ClienteDAO dao = new ClienteDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String metodo = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        try {

            switch (metodo) {

                case "GET":

                    if (path.matches("/clientes/\\d+")) {

                        buscarPorId(exchange);

                    } else {

                        listarTodos(exchange);
                    }

                    break;

                case "POST":

                    inserir(exchange);

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

        String body = new String(
                exchange.getRequestBody().readAllBytes()
        );

        Cliente cliente = new Cliente();

        cliente.setNome(
                JsonUtil.getValue(body, "nome")
        );

        cliente.setCpf(
                JsonUtil.getValue(body, "cpf")
        );

        cliente.setTelefone(
                JsonUtil.getValue(body, "telefone")
        );

        cliente.setEmail(
                JsonUtil.getValue(body, "email")
        );

        cliente.setEndereco(
                JsonUtil.getValue(body, "endereco")
        );

        dao.inserir(cliente);

        enviarResposta(
                exchange,
                201,
                "{\"mensagem\":\"Cliente cadastrado com sucesso\"}"
        );
    }

    private void listarTodos(HttpExchange exchange)
            throws IOException {

        List<Cliente> clientes =
                dao.listar();

        StringBuilder json =
                new StringBuilder();

        json.append("[");

        for (int i = 0; i < clientes.size(); i++) {

            Cliente c =
                    clientes.get(i);

            json.append("{")
                    .append("\"codCliente\":")
                    .append(c.getCodCliente())
                    .append(",")

                    .append("\"nome\":\"")
                    .append(c.getNome())
                    .append("\",")

                    .append("\"cpf\":\"")
                    .append(c.getCpf())
                    .append("\",")

                    .append("\"telefone\":\"")
                    .append(c.getTelefone())
                    .append("\",")

                    .append("\"email\":\"")
                    .append(c.getEmail())
                    .append("\",")

                    .append("\"endereco\":\"")
                    .append(c.getEndereco())
                    .append("\"")
                    .append("}");

            if (i < clientes.size() - 1) {

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
                exchange.getRequestURI().getPath();

        int id =
                Integer.parseInt(
                        path.substring(
                                path.lastIndexOf("/") + 1
                        )
                );

        Cliente cliente =
                dao.buscarPorId(id);

        if (cliente == null) {

            enviarResposta(
                    exchange,
                    404,
                    "{\"erro\":\"Cliente não encontrado\"}"
            );

            return;
        }

        String json =
                "{"
                        + "\"codCliente\":"
                        + cliente.getCodCliente()
                        + ","

                        + "\"nome\":\""
                        + cliente.getNome()
                        + "\","

                        + "\"cpf\":\""
                        + cliente.getCpf()
                        + "\","

                        + "\"telefone\":\""
                        + cliente.getTelefone()
                        + "\","

                        + "\"email\":\""
                        + cliente.getEmail()
                        + "\","

                        + "\"endereco\":\""
                        + cliente.getEndereco()
                        + "\""

                        + "}";

        enviarResposta(
                exchange,
                200,
                json
        );
    }

    private void excluir(HttpExchange exchange)
            throws IOException {

        String path =
                exchange.getRequestURI().getPath();

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
                "{\"mensagem\":\"Cliente removido\"}"
        );
    }

    private void enviarResposta(
            HttpExchange exchange,
            int status,
            String json)
            throws IOException {

        exchange.getResponseHeaders().add(
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

    Cliente cliente =
            new Cliente();

    cliente.setCodCliente(id);

    cliente.setNome(
            JsonUtil.getValue(
                    body,
                    "nome"
            )
    );

    cliente.setCpf(
            JsonUtil.getValue(
                    body,
                    "cpf"
            )
    );

    cliente.setTelefone(
            JsonUtil.getValue(
                    body,
                    "telefone"
            )
    );

    cliente.setEmail(
            JsonUtil.getValue(
                    body,
                    "email"
            )
    );

    cliente.setEndereco(
            JsonUtil.getValue(
                    body,
                    "endereco"
            )
    );

    dao.atualizar(cliente);

    enviarResposta(
            exchange,
            200,
            """
            {
                "mensagem":"Cliente atualizado com sucesso"
            }
            """
    );
}
}