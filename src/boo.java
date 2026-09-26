import UDP.Book;
import UDP.Employee;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class boo {
    public static String chuanhoa1(String s) {
        String t[] = s.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for(String x:t){
            sb.append(Character.toUpperCase(x.charAt(0)))
                    .append(x.substring(1).toLowerCase()).append(" ");
        }
        return sb.toString().trim();
    }

    public static String chuanhoa2(String s) {
        String t[] = s.trim().split("\\s+");
        String res = t[0].trim().toUpperCase() + ", ";
        int l = t.length;
        for (int i = 1; i < l; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(Character.toUpperCase(t[i].charAt(0)))
                    .append(t[i].substring(1).toLowerCase());
            res += sb.toString() + " ";
        }
        return res.trim();
    }

    public static String chuanhoa3(String s) {
        return s.substring(0, 3) + "-" + s.substring(3, 4) + "-" + s.substring(4, 6) + "-" + s.substring(6, 12) + "-" + s.substring(12);
    }

    public static String chuanhoa4(String s) {
        String t[] = s.trim().split("-");
        return t[1] + "/" + t[0];
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
        Book sv = (Book) new ObjectInputStream(
                new ByteArrayInputStream(nhan.getData(), 8, nhan.getLength() - 8)
        ).readObject();

        sv.setTitle(chuanhoa1(sv.getTitle()));
        sv.setAuthor(chuanhoa2(sv.getAuthor()));
        sv.setIsbn(chuanhoa3(sv.getIsbn()));
        sv.setPublishDate(chuanhoa4(sv.getPublishDate()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(reqId);
        new ObjectOutputStream(baos).writeObject(sv);

        socket.send(new DatagramPacket(baos.toByteArray(), baos.size(), ip, port));
        socket.close();
        System.out.println("Đã gửi kết quả thành công!");
    }
}
