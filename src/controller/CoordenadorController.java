
package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.CoordenadorDAO;
import model.Coordenador;
import util.JsonUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class CoordenadorController implements HttpHandler {

    private final CoordenadorDAO dao =
            new CoordenadorDAO();

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

                    if (path.matches("/coordenadores/\\d+")) {

                        buscarPorId(exchange);

                    } else {

                        listarTodos(exchange);
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
                            """
                            {
                                "erro":"Método não permitido"
                            }
                            """
                    );
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
                            e.getMessage()
                    )
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

        Coordenador coordenador =
                new Coordenador();

        coordenador.setNomeCoordenador(
                JsonUtil.getValue(
                        body,
                        "nomeCoordenador"
                )
        );

        dao.inserir(coordenador);

        enviarResposta(
                exchange,
                201,
                """
                {
                    "mensagem":"Coordenador cadastrado com sucesso"
                }
                """
        );
    }

    private void listarTodos(HttpExchange exchange)
            throws IOException {

        List<Coordenador> lista =
                dao.listar();

        StringBuilder json =
                new StringBuilder();

        json.append("[");

        for (int i = 0; i < lista.size(); i++) {

            Coordenador c =
                    lista.get(i);

            json.append("{")
                    .append("\"codCoordenador\":")
                    .append(c.getCodCoordenador())
                    .append(",")

                    .append("\"nomeCoordenador\":\"")
                    .append(c.getNomeCoordenador())
                    .append("\"")

                    .append("}");

            if (i < lista.size() - 1) {

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

        Coordenador coordenador =
                dao.buscarPorId(id);

        if (coordenador == null) {

            enviarResposta(
                    exchange,
                    404,
                    """
                    {
                        "erro":"Coordenador não encontrado"
                    }
                    """
            );

            return;
        }

        String json =
                """
                {
                    "codCoordenador":%d,
                    "nomeCoordenador":"%s"
                }
                """.formatted(
                        coordenador.getCodCoordenador(),
                        coordenador.getNomeCoordenador()
                );

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

        Coordenador coordenador =
                new Coordenador();

        coordenador.setCodCoordenador(id);

        coordenador.setNomeCoordenador(
                JsonUtil.getValue(
                        body,
                        "nomeCoordenador"
                )
        );

        dao.atualizar(coordenador);

        enviarResposta(
                exchange,
                200,
                """
                {
                    "mensagem":"Coordenador atualizado com sucesso"
                }
                """
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
                """
                {
                    "mensagem":"Coordenador removido"
                }
                """
        );
    }

    private void enviarResposta(
            HttpExchange exchange,
            int status,
            String json)
            throws IOException {

        exchange.getResponseHeaders()
                .add(
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