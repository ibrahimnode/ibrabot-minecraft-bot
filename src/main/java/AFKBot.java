import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionPacket;

public class AFKBot {
    public static void main(String[] args) throws InterruptedException {
        String host = System.getenv().getOrDefault("HOST", "ibra2comp.aternos.me");
        int port = 25565;
        String username = System.getenv().getOrDefault("USERNAME", "ibrabot");

        MinecraftProtocol protocol = new MinecraftProtocol(username); // cracked/offline support
        Client client = new Client(host, port, protocol, new TcpSessionFactory());

        client.getSession().connect();

        new Thread(() -> {
            double y = 70.0;
            while (true) {
                try {
                    // Jump simulation
                    client.getSession().send(new ClientPlayerPositionPacket(false, 0.0, y + 1.0, 0.0, false));
                    Thread.sleep(500);
                    client.getSession().send(new ClientPlayerPositionPacket(true, 0.0, y, 0.0, true));

                    // Move slightly
                    client.getSession().send(new ClientPlayerPositionPacket(true, 0.1, y, 0.0, true));

                    Thread.sleep(1500);
                } catch (Exception e) {
                    System.out.println("Loop error: " + e.getMessage());
                }
            }
        }).start();

        client.getSession().addListener(new com.github.steveice10.packetlib.event.session.SessionAdapter() {
            @Override
            public void disconnected(com.github.steveice10.packetlib.event.session.DisconnectedEvent event) {
                System.out.println("Disconnected: " + event.getReason());
                System.exit(1);
            }
        });
    }
}
