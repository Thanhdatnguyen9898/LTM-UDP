import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Tongnhiphan {

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int sP = 2208;

        String code = ";B23DCCN138;lIQVug9S";
        DatagramPacket dpGui = new DatagramPacket(code.getBytes(), code.getBytes().length, sA, sP);
        socket.send(dpGui);

        byte[] buffer = new byte[1024];
        DatagramPacket dpNhan = new DatagramPacket(buffer, buffer.length);
        socket.receive(dpNhan);
        String s1 = new String(dpNhan.getData()).trim();
        System.out.println(s1);

        String s[] = s1.split(";");
        String t[] = s[1].trim().split(",");
        BigInteger b1 = new BigInteger(t[0].trim(), 2);
        BigInteger b2 = new BigInteger(t[1].trim(), 2);
        BigInteger sum = b1.add(b2);
        String res = s[0].trim() + ";" + sum.toString();

        DatagramPacket dpGui1 = new DatagramPacket(res.getBytes(), res.getBytes().length, sA, sP);
        socket.send(dpGui1);
        socket.close();
    }
}
