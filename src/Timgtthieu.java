import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Timgtthieu {
    public static void main(String [] args) throws Exception{
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int sP = 2207;

        String code = ";B23DCCN138;kmrT8ybw";
        DatagramPacket dpGui = new DatagramPacket(code.getBytes(), code.getBytes().length,sA,sP);
        socket.send(dpGui);

        byte[] buffer = new byte[1024];
        DatagramPacket dpNhan = new DatagramPacket(buffer,buffer.length);
        socket.receive(dpNhan);
        String s1 = new String(dpNhan.getData()).trim();
        System.out.println(s1);

        String tmp[] = s1.split(";");
        String t1 = tmp[0];
        int n = Integer.parseInt(tmp[1]);
        String t3[] = tmp[2].split(",");

        HashSet<Integer> daCo = new HashSet<>();
        for(String x: t3){
            daCo.add(Integer.parseInt(x.trim()));
        }
        List<String> missing = new ArrayList<>();

        for(int i = 1; i <=n; i++){
            if(!daCo.contains(i)){
                missing.add(String.valueOf(i));
            }
        }
        String res = t1 + ";" + String.join(",",missing);
        System.out.println(res);
        DatagramPacket dpGui1 = new DatagramPacket(res.getBytes(), res.getBytes().length,sA,sP);
        socket.send(dpGui1);
        socket.close();

    }
}
