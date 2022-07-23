package dev.leanhe.minecraft.shareinfo;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.ServerMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;

public class Shareinfo implements ModInitializer {

    public static final String MOD_ID = "shareinfo";
    public static int LISTEN_PORT = 25134;

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Shareinfo instance;


    static class PointHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            var player = MinecraftClient.getInstance().player;
            String response;
            if (player == null) {
                response = "{\"vaild\": false, \"x\": 0.0, \"y\": 0.0, \"z\": 0.0}";
            } else {
                var postion = player.getPos();
                response = "{\"vaild\": true, \"x\": %f, \"y\": %f, \"z\": %f}".formatted(postion.getX(), postion.getY(), postion.getZ());
            }
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Listening on localhost: %d".formatted(LISTEN_PORT));
        instance = this;
        try {
            var server = HttpServer.create(new InetSocketAddress(LISTEN_PORT), 0);
            server.createContext("/", new PointHandler());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            LOGGER.error("Got error while create http server", e);
        }
    }
}
