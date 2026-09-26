import UDP.Customer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class cus {
    public static String chuanhoa1(String s){
        String t[] = s.trim().split("\\s+");
        int l = t.length;
        String res = t[l-1].toUpperCase() +", ";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < l-1; i++){
            sb.append(Character.toUpperCase(t[i].charAt(0)))
                    .append(t[i].substring(1).toLowerCase())
                    .append(" ");
        }
        return (res + sb.toString()).trim();
    }
    public static String chuanhoa2(String s) {
        String t[] = s.trim().split("-");
        String res = t[1] + "/" + t[0] + "/" + t[2];
        return res ;
    }
    public static String chuanhoa3(String s) {
        s = s.toLowerCase();
        String t[] = s.trim().split("\\s+");
        int l = t.length;
        String res = "";
        for(int i = 0; i< l-1; i++){
            res += t[i].charAt(0);
        }
        return (res+ t[l-1]).trim();
    }

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2209;

        byte[] gui = (";B23DCCN138;EIZCo9C3").getBytes();
        socket.send(new DatagramPacket(gui, gui.length, ip, port));

        DatagramPacket nhan = new DatagramPacket(new byte[2048], 2048);
        socket.receive(nhan);

        byte[] reqId = Arrays.copyOfRange(nhan.getData(), 0, 8);
        Customer sv = (Customer) new ObjectInputStream(
                new ByteArrayInputStream(nhan.getData(), 8, nhan.getLength() - 8)
        ).readObject();

        sv.setUserName(chuanhoa3(sv.getName()));
        sv.setName(chuanhoa1(sv.getName()));
        sv.setDayOfBirth(chuanhoa2(sv.getDayOfBirth()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(reqId);
        new ObjectOutputStream(baos).writeObject(sv);

        socket.send(new DatagramPacket(baos.toByteArray(), baos.size(), ip, port));
        socket.close();
        System.out.println("Đã gửi kết quả thành công!");
    }
}
