import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Maxmin {
    public static void main(String [] args) throws Exception{
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int sP = 2207;

        String code = ";B23DCCN138;WxND5Q0N";
        DatagramPacket dpGui = new DatagramPacket(code.getBytes(),code.getBytes().length,sA,sP);
        socket.send(dpGui);

        byte[] buffer = new byte [1024];
        DatagramPacket dpNhan = new DatagramPacket(buffer,buffer.length);
        socket.receive(dpNhan);
        String s1 = new String(dpNhan.getData()).trim();
        System.out.println(s1);

        String s[] = s1.split(";");
        String a = s[0].trim();
        String b[] = s[1].split(",");
        List<Integer> arr = new ArrayList<>();
        for(String x : b){
            arr.add(Integer.parseInt(x.trim()));
        }
        Collections.sort(arr);
        String res = a + ";" + arr.get(arr.size()-1) +","+  arr.get(0) ;
        System.out.println(res);
        DatagramPacket dpGui1 = new DatagramPacket(res.getBytes(),res.getBytes().length,sA,sP);
        socket.send(dpGui1);
    }
}
