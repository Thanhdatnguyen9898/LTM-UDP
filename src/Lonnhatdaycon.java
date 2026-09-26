import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lonnhatdaycon {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int sP = 2207;

        String code = ";B23DCCN138;iv00Hrq6";
        DatagramPacket dpGui = new DatagramPacket(code.getBytes(), code.getBytes().length, sA, sP);
        socket.send(dpGui);

        byte[] buffer = new byte[1024];
        DatagramPacket dpNhan = new DatagramPacket(buffer, buffer.length);
        socket.receive(dpNhan);
        String s1 = new String(dpNhan.getData()).trim();
        System.out.println(s1);

        String t[] = s1.split(";");
        String h = t[0];

        int n = Integer.parseInt(t[1]);
        int k = Integer.parseInt(t[2]);
        String a[] = t[3].trim().split(",");
        List<Integer> arr = new ArrayList<>();
        for(String x :a){
            arr.add(Integer.parseInt(x));
        }
        int l = 0, r = k;
        String res2 = "";
        while(r<=n){
            List<Integer> sub = new ArrayList<>(arr.subList(l, r));
            res2 += Collections.max(sub) ;
            if(r != n) res2 += ",";
            l++;
            r++;
        }
        String res = h + ";" + res2;
        System.out.println(res);
        DatagramPacket dpGui1 = new DatagramPacket(res.getBytes(),res.getBytes().length,sA,sP);
        socket.send(dpGui1);
    }
}
