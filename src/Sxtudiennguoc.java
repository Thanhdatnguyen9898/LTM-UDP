import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Sxtudiennguoc {
    public static void main(String [] args) throws Exception{
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int sP = 2208;

        String code = ";B23DCCN138;9UfU4Vky";
        DatagramPacket dpGui = new DatagramPacket(code.getBytes(),code.getBytes().length,sA,sP);
        socket.send(dpGui);

        byte[] buffer = new byte [1024];
        DatagramPacket dpNhan = new DatagramPacket(buffer,buffer.length);
        socket.receive(dpNhan);
        String s1 = new String(dpNhan.getData()).trim();
        System.out.println(s1);

        String s[] = s1.split(";");
        String t[] = s[1].trim().split("\\s+");
        Arrays.sort(t, Collections.reverseOrder());
        String res = s[0] + ";" + String.join(",",t);
        System.out.println(res);
        DatagramPacket dpGui1 = new DatagramPacket(res.getBytes(), res.getBytes().length,sA,sP);
        socket.send(dpGui1);
        socket.close();
}
}
