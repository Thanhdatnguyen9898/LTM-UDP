import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class hoadau {
        public static void main(String[] args) throws Exception {
            DatagramSocket socket = new DatagramSocket();
            InetAddress sA = InetAddress.getByName("36.50.135.242");
            int sP = 2208;

            String code = ";B23DCCN138;cdRr9xSG";
            DatagramPacket dpGui = new DatagramPacket(code.getBytes(), code.getBytes().length, sA, sP);
            socket.send(dpGui);

            byte[] buffer = new byte[1024];
            DatagramPacket dpNhan = new DatagramPacket(buffer, buffer.length);
            socket.receive(dpNhan);
            String s = new String(dpNhan.getData()).trim();
            System.out.println(s);

            String t[] = s.split(";");
            String h = t[0].trim();
            String k = t[1].trim();
            String a[] = k.split("\\s+");
            String b = "";
            for(String x: a){
                StringBuilder sb = new StringBuilder();
                sb.append(Character.toUpperCase(x.charAt(0))).append(x.substring(1).toLowerCase());
                b+=  sb.toString() + " ";
            }
            String res = h+ ";" +b.trim();

            System.out.println(res);
            DatagramPacket dpGui1 = new DatagramPacket(res.getBytes(),res.getBytes().length,sA,sP);
            socket.send(dpGui1);
        }
    }

