package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.CorretorDAO;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import model.Coordenador;
import model.Corretor;
import util.JsonUtil;

public class CorretorController implements HttpHandler {

    private final CorretorDAO dao =
            new CorretorDAO();

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

                    if (path.matches("/corretores/\\d+")) {

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

        coordenador.setCodCoordenador(
                Integer.parseInt(
                        JsonUtil.getValue(
                                JsonUtil.getObject(
                                        body,
                                        "coordenador"
                                ),
                                "codCoordenador"
                        )
                )
        );

        Corretor corretor =
                new Corretor();

        corretor.setNomeCorretor(
                JsonUtil.getValue(
                        body,
                        "nomeCorretor"
                )
        );

        corretor.setCreci(
                JsonUtil.getValue(
                        body,
                        "creci"
                )
        );

        corretor.setCoordenador(
                coordenador
        );

        dao.inserir(corretor);

        enviarResposta(
                exchange,
                201,
                """
                {
                    "mensagem":"Corretor cadastrado com sucesso"
                }
                """
        );
    }

    private void listarTodos(HttpExchange exchange)
            throws IOException {

        List<Corretor> lista =
                dao.listar();

        StringBuilder json =
                new StringBuilder();

        json.append("[");

        for (int i = 0; i < lista.size(); i++) {

            Corretor c =
                    lista.get(i);

            json.append("""
            {
                "codCorretor":%d,
                "nomeCorretor":"%s",
                "creci":"%s",
                "coordenador":{
                    "codCoordenador":%d,
                    "nomeCoordenador":"%s"
                }
            }
            """.formatted(
                    c.getCodCorretor(),
                    c.getNomeCorretor(),
                    c.getCreci(),
                    c.getCoordenador()
                            .getCodCoordenador(),
                    c.getCoordenador()
                            .getNomeCoordenador()
            ));

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

        Corretor corretor =
                dao.buscarPorId(id);

        if (corretor == null) {

            enviarResposta(
                    exchange,
                    404,
                    """
                    {
                        "erro":"Corretor não encontrado"
                    }
                    """
            );

            return;
        }

        String json =
                """
                {
                    "codCorretor":%d,
                    "nomeCorretor":"%s",
                    "creci":"%s",
                    "coordenador":{
                        "codCoordenador":%d,
                        "nomeCoordenador":"%s"
                    }
                }
                """.formatted(
                        corretor.getCodCorretor(),
                        corretor.getNomeCorretor(),
                        corretor.getCreci(),
                        corretor.getCoordenador()
                                .getCodCoordenador(),
                        corretor.getCoordenador()
                                .getNomeCoordenador()
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

        coordenador.setCodCoordenador(
                Integer.parseInt(
                        JsonUtil.getValue(
                                JsonUtil.getObject(
                                        body,
                                        "coordenador"
                                ),
                                "codCoordenador"
                        )
                )
        );

        Corretor corretor =
                new Corretor();

        corretor.setCodCorretor(id);

        corretor.setNomeCorretor(
                JsonUtil.getValue(
                        body,
                        "nomeCorretor"
                )
        );

        corretor.setCreci(
                JsonUtil.getValue(
                        body,
                        "creci"
                )
        );

        corretor.setCoordenador(
                coordenador
        );

        dao.atualizar(corretor);

        enviarResposta(
                exchange,
                200,
                """
                {
                    "mensagem":"Corretor atualizado com sucesso"
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
                    "mensagem":"Corretor removido"
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
