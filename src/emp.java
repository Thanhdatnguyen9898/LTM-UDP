
import UDP.Employee;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class emp {
    public static String chuanhoa1(String s) {
        String t[] = s.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for(String x: t){
            sb.append(Character.toUpperCase(x.charAt(0))).append(x.substring(1).toLowerCase()).append(" ");
        }
        return sb.toString().trim();
    }
    public static double tangluong(double l, String ns) {
        String ns1[] = ns.trim().split("-");
        int x =0;
        for(int i = 0; i <4; i++){
            x+= ns1[0].charAt(i) - '0';
        }
        return l * (1+x*1.0/100);
    }
    public static String chuanhoa2(String s) {
        String t[] = s.trim().split("-");
        return t[2] +"/" + t[1] + "/" + t[0];
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
        Employee sv = (Employee) new ObjectInputStream(
                new ByteArrayInputStream(nhan.getData(), 8, nhan.getLength() - 8)
        ).readObject();

        sv.setName(chuanhoa1(sv.getName()));
        sv.setSalary(tangluong(sv.getSalary(),sv.getHireDate()));
        sv.setHireDate(chuanhoa2(sv.getHireDate()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(reqId);
        new ObjectOutputStream(baos).writeObject(sv);

        socket.send(new DatagramPacket(baos.toByteArray(), baos.size(), ip, port));
        socket.close();
    }
}