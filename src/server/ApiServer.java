package server;

import com.sun.net.httpserver.HttpServer;
import controller.ClienteController;
import controller.CoordenadorController;
import controller.CorretorController;
import controller.ImovelController;
import controller.ProprietarioController;
import controller.AtendimentoImovelController;
import java.net.InetSocketAddress;

public class ApiServer {

    public static void main(String[] args) {

        try {

            start();

        } catch (Exception e) {

            System.err.println(
                    "Erro ao iniciar o servidor: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }
    }

    public static void start()
            throws Exception {

        HttpServer server =
                HttpServer.create(
                        new InetSocketAddress(8000),
                        0
                );

        server.createContext(
                "/cliente",
                new ClienteController()
        );

        server.createContext(
                "/proprietario",
                new ProprietarioController()
        );

        server.createContext(
                "/imovel",
                new ImovelController()
        );

        server.createContext(
                "/coordenador",
                new CoordenadorController()
        );

        server.createContext(
                "/corretor",
                new CorretorController()
        );

        server.createContext(
                "/atendimento-imovel",
                new controller.AtendimentoImovelController()
        );

        server.setExecutor(null);

        server.start();

        System.out.println(
                "Servidor iniciado na porta 8000"
        );
    }
}