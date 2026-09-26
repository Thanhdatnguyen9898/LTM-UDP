import UDP.Student;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class stu {
    public static String chuanhoaten(String s){
        String h[] = s.split("\\s+");
        String t= "";
        for(String x:h){
            StringBuilder sb = new StringBuilder();
            sb.append(Character.toUpperCase(x.charAt(0))).append(x.substring(1).toLowerCase()).append(" ");
            t+=sb.toString();
        }
        return t.trim();
    }
    public static String taoemail(String s){
        String h[] = s.trim().toLowerCase().split("\\s+");
        int l = h.length;
        String email= h[l-1].trim();
        for(int i = 0; i<l-1; i++){
            email += h[i].charAt(0);
        }
        return email +"@ptit.edu.vn";
    }
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2209;

        String ma = ";B23DCCN138;vRI98k56";
        DatagramPacket guiMa = new DatagramPacket(ma.getBytes(), ma.length(), ip, port);
        socket.send(guiMa);

        byte[] buff = new byte[2048];
        DatagramPacket nhan = new DatagramPacket(buff, buff.length);
        socket.receive(nhan);

        // Bóc tách 8 byte đầu lấy requestId
        String requestId = new String(nhan.getData(), 0, 8);
        // Bóc tách từ byte thứ 8 để đọc Object
        ByteArrayInputStream bais = new ByteArrayInputStream(nhan.getData(), 8, nhan.getLength() - 8);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Student sv = (Student) ois.readObject();

        sv.setName(chuanhoaten(sv.getName()));
        sv.setEmail(taoemail(sv.getName()));
        // Ghép 8 byte requestId + Object đã sửa để gửi lại
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(requestId.getBytes()); // 8 byte requestId
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(sv);
        oos.flush();
        byte[] sendData = baos.toByteArray();
        DatagramPacket guiKQ = new DatagramPacket(sendData, sendData.length, ip, port);
        socket.send(guiKQ);
        // d. Đóng socket
        socket.close();
    }
}
