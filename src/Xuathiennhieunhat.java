import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Xuathiennhieunhat {
    public static void main(String [] args)throws Exception{
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int sP = 2208;

        String code = ";B23DCCN138;x0cIIJK6";
        DatagramPacket dpGui = new DatagramPacket(code.getBytes(), code.getBytes().length,sA,sP);
        socket.send(dpGui);

        byte [] buffer = new byte [1024];
        DatagramPacket dpNhan = new DatagramPacket(buffer, buffer.length);
        socket.receive(dpNhan);
        String s =new String(dpNhan.getData()).trim();
        System.out.println(s);

        String t[] = s.split(";");
        String h = t[0].trim();
        String k = t[1];

        Map<Character,Integer> mp = new HashMap<>();
        for(int i = 0; i < k.length()-1; i++){
            mp.put(k.charAt(i),mp.getOrDefault(k.charAt(i),0)+1);
        }
        char a = ' ';
        int ab = -1;
        for (Map.Entry<Character,Integer> entry : mp.entrySet()) {
            if(entry.getValue()>ab){
                a =  entry.getKey();
                ab = entry.getValue();
            }
        }
        String c ="";
        for(int i = 0; i < k.length()-1; i++){
            if(k.charAt(i) == a){
                c += (i+1) +",";
            }
        }
        String res = h + ";" + a+":"+c;
        System.out.println(res);
        DatagramPacket dpGui1 = new DatagramPacket(res.getBytes(),res.getBytes().length,sA,sP);
        socket.send(dpGui1);
    }
}
