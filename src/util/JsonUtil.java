package util;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    public static String getValue(
            String json,
            String campo) {

        String chave = "\"" + campo + "\":";

        int inicio = json.indexOf(chave);

        if (inicio == -1) {
            return "";
        }

        inicio += chave.length();

        while (json.charAt(inicio) == ' '
                || json.charAt(inicio) == '"') {

            inicio++;
        }

        int fim = inicio;

        while (fim < json.length()
                && json.charAt(fim) != '"'
                && json.charAt(fim) != ','
                && json.charAt(fim) != '}') {

            fim++;
        }

        return json.substring(
                inicio,
                fim).trim();
    }

    public static String getObject(
            String json,
            String campo) {

        String chave = "\"" + campo + "\":";

        int inicio = json.indexOf(chave);

        if (inicio == -1) {
            return "";
        }

        inicio = json.indexOf("{", inicio);

        int contador = 0;

        int fim = inicio;

        for (; fim < json.length(); fim++) {

            if (json.charAt(fim) == '{') {
                contador++;
            }

            if (json.charAt(fim) == '}') {
                contador--;

                if (contador == 0) {
                    break;
                }
            }
        }

        return json.substring(
                inicio,
                fim + 1);
    }

    public static String getArray(
            String json,
            String campo) {

        String chave = "\"" + campo + "\":";

        int inicio = json.indexOf(chave);

        if (inicio == -1) {
            return "";
        }

        inicio = json.indexOf("[", inicio);

        int contador = 0;

        int fim = inicio;

        for (; fim < json.length(); fim++) {

            if (json.charAt(fim) == '[') {
                contador++;
            }

            if (json.charAt(fim) == ']') {
                contador--;

                if (contador == 0) {
                    break;
                }
            }
        }

        return json.substring(
                inicio,
                fim + 1);
    }

    public static List<String> getObjectsFromArray(
            String arrayJson) {

        List<String> objetos = new ArrayList<>();

        int contador = 0;

        int inicioObjeto = -1;

        for (int i = 0; i < arrayJson.length(); i++) {

            char c = arrayJson.charAt(i);

            if (c == '{') {

                if (contador == 0) {
                    inicioObjeto = i;
                }

                contador++;
            }

            if (c == '}') {

                contador--;

                if (contador == 0) {

                    objetos.add(
                            arrayJson.substring(
                                    inicioObjeto,
                                    i + 1));
                }
            }
        }

        return objetos;
    }

}