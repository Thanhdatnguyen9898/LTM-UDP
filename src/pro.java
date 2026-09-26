import UDP.Product;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class pro {
    public static String chuanhoa1(String s){
        String t[] = s.trim().split("\\s+");
        int k = t.length;
        if(k<1) return s;
        String res = t[k-1] + " ";
        for(int i = 1; i < k-1; i++){
            res += t[i] + " ";
        }
        return res + t[0];
    }
    public static int chuanhoa2(int a){
        int b = 0;
        while(a>0){
            b= b*10+ a%10;
            a/=10;
        }
        return b;
    }
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("203.162.10.109");
        int port = 2209;

        String ma = ";B23DCCN138;kZqFKEDL";
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
        Product sv = (Product) ois.readObject();
        sv.setName(chuanhoa1(sv.getName()));
        sv.setQuantity(chuanhoa2(sv.getQuantity()));
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
