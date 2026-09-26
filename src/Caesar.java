import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Caesar {
    public static String decodeCaesar(String text, int s) {
        StringBuilder sb = new StringBuilder();
        int shift = s % 26;
        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                char decoded = (char) ('A' + (c - 'A' - shift + 26) % 26);
                sb.append(decoded);
            } else if (Character.isLowerCase(c)) {
                char decoded = (char) ('a' + (c - 'a' - shift + 26) % 26);
                sb.append(decoded);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int sP = 2207;

        // a. Gửi mã sinh viên và mã câu hỏi
        String code = ";B23DCCN138;J5SE2YXc";
        DatagramPacket dpGui = new DatagramPacket(code.getBytes(), code.getBytes().length, sA, sP);
        socket.send(dpGui);

        // b. Nhận thông điệp từ server
        byte[] buffer = new byte[2048];
        DatagramPacket dpNhan = new DatagramPacket(buffer, buffer.length);
        socket.receive(dpNhan);
        String s1 = new String(dpNhan.getData(), 0, dpNhan.getLength()).trim();
        System.out.println("Nhan: " + s1);

        // Tách chuỗi: requestId;strEncode;s
        String[] parts = s1.split(";");
        String requestId = parts[0].trim();
        String strEncode = parts[1];
        int s = Integer.parseInt(parts[2].trim());

        // c. Giải mã thông điệp
        String strDecode = decodeCaesar(strEncode, s);
        String res = requestId + ";" + strDecode;
        System.out.println("Gui: " + res);

        DatagramPacket dpGui1 = new DatagramPacket(res.getBytes(), res.getBytes().length, sA, sP);
        socket.send(dpGui1);

        // d. Đóng socket
        socket.close();
    }
}