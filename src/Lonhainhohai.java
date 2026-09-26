import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class Lonhainhohai {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int sP = 2207;

        String code = ";B23DCCN138;oQifsr90";
        DatagramPacket dpGui = new DatagramPacket(code.getBytes(), code.getBytes().length, sA, sP);
        socket.send(dpGui);

        byte[] buffer = new byte[1024];
        DatagramPacket dpNhan = new DatagramPacket(buffer, buffer.length);
        socket.receive(dpNhan);
        String s1 = new String(dpNhan.getData()).trim();
        System.out.println(s1);

        String t[] = s1.split(";");
        String h = t[0];
        String k = t[1];
        TreeSet<Integer> set = new TreeSet<>();
        for (String x : k.split(",")) {
            set.add(Integer.parseInt(x.trim()));
        }

        List<Integer> list = new ArrayList<>(set);
        int secondMin = list.get(1);
        int secondMax = list.get(list.size() - 2);

        String res = h + ";" + secondMax + "," + secondMin;
        System.out.println(res);
        DatagramPacket dpGui1 = new DatagramPacket(res.getBytes(),res.getBytes().length,sA,sP);
        socket.send(dpGui1);

    }
}
