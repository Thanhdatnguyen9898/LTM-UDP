import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

public class LoaiBoKyTu {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int sP = 2208;

        // a. Gửi mã sinh viên và mã câu hỏi
        String code = ";B23DCCN138;JQCO3izC";
        DatagramPacket dpGui = new DatagramPacket(code.getBytes(), code.getBytes().length, sA, sP);
        socket.send(dpGui);

        // b. Nhận thông điệp từ server
        byte[] buffer = new byte[2048];
        DatagramPacket dpNhan = new DatagramPacket(buffer, buffer.length);
        socket.receive(dpNhan);
        String s1 = new String(dpNhan.getData(), 0, dpNhan.getLength()).trim();
        System.out.println("Nhan: " + s1);

        // Tách chuỗi theo định dạng requestId;str1;str2
        String[] parts = s1.split(";");
        String requestId = parts[0].trim();
        String str1 = parts.length > 1 ? parts[1] : "";
        String str2 = parts.length > 2 ? parts[2] : "";

        // c. Lưu các ký tự trong str2 vào Set để tra cứu O(1)
        Set<Character> setChars = new HashSet<>();
        for (char c : str2.toCharArray()) {
            setChars.add(c);
        }

        // Lọc ký tự str1 không xuất hiện trong str2
        StringBuilder strOutput = new StringBuilder();
        for (char c : str1.toCharArray()) {
            if (!setChars.contains(c)) {
                strOutput.append(c);
            }
        }

        String res = requestId + ";" + strOutput.toString();
        System.out.println("Gui: " + res);

        DatagramPacket dpGui1 = new DatagramPacket(res.getBytes(), res.getBytes().length, sA, sP);
        socket.send(dpGui1);

        // d. Đóng socket
        socket.close();
    }
}