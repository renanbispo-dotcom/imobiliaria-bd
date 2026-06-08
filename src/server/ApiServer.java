package server;

import com.sun.net.httpserver.HttpServer;
import controller.ClienteController;
import controller.ImovelController; // <-- ADICIONADO: Import do controlador de imóveis
import controller.ProprietarioController;

import java.net.InetSocketAddress;

public class ApiServer {

    // Método main adicionado para permitir a execução do servidor
    public static void main(String[] args) {
        try {
            start();
        } catch (Exception e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void start() throws Exception {

        HttpServer server =
                HttpServer.create(
                        new InetSocketAddress(8000),
                        0
                );

        server.createContext(
                "/clientes",
                new ClienteController()
        );

        server.createContext(
                "/proprietarios",
                new ProprietarioController()
        );

        server.createContext(
                "/imoveis",
                new ImovelController()
        );

        server.setExecutor(null);

        server.start();

        System.out.println(
                "Servidor iniciado na porta 8000"
        );
    }
}