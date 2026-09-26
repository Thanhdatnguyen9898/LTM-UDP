import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SXvitribandau {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int sP = 2207;

        String code = ";B23DCCN138;aKZwZxWk";
        DatagramPacket dpGui = new DatagramPacket(code.getBytes(), code.getBytes().length, sA, sP);
        socket.send(dpGui);

        byte[] buffer = new byte[1024];
        DatagramPacket dpNhan = new DatagramPacket(buffer, buffer.length);
        socket.receive(dpNhan);
        String s1 = new String(dpNhan.getData()).trim();
        System.out.println(s1);

        String s[] = s1.trim().split(";");
        String t1 = s[0].trim();
        String t2[] = s[1].trim().split(",");
        Map<Integer,String> mp = new TreeMap<>();
        for(String x : t2){
            String tmp[] = x.trim().split(":");
            mp.put(Integer.parseInt(tmp[1]),tmp[0].trim());
        }
        List<String> list = new ArrayList<>(mp.values());

        String res = t1 + ";" + String.join(",",list);

        System.out.println(res);
        DatagramPacket dpGui1 = new DatagramPacket(res.getBytes(), res.getBytes().length,sA,sP);
        socket.send(dpGui1);
        socket.close();
    }
}