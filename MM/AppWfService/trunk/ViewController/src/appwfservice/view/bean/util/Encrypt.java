package appwfservice.view.bean.util;

public class Encrypt {
    public Encrypt() {
        super();
    }
    
    
    
    public static StringBuilder encrypt(String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        while (true) {
            int x = sb.indexOf("=");
            if (x == -1)
                break;
            else
                sb.replace(x, x + 1, "@");
        }
        while (true) {
            int x = sb.indexOf("&");
            if (x == -1)
                break;
            else
                sb.replace(x, x + 1, ":");
        }
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) >= 48 && sb.charAt(i) <= 57) {
                int x = (sb.charAt(i) - 13);
                if (x == 35)
                    sb.replace(i, i + 1, "" + (char) 33);
                else if (x == 38)
                    sb.replace(i, i + 1, "" + (char) 50);
                else if (x == 43)
                    sb.replace(i, i + 1, "" + (char) 45);
                else if (x == 44)
                    sb.replace(i, i + 1, "" + (char) 55);
                else
                    sb.replace(i, i + 1, "" + (char) x);
            }
        }

        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) >= 65 && sb.charAt(i) <= 90) {
                int x = sb.charAt(i) - 65;
                char a = (char) 72;
                int val = 90 - x;
                char b = (char) 83;
                sb.replace(i, i + 1, "" + (char) val);
            } else if (sb.charAt(i) >= 97 && sb.charAt(i) <= 122) {
                int x = sb.charAt(i) - 97;
                int val = 122 - x;
                sb.replace(i, i + 1, "" + (char) val);
            }
        }
        return sb;
    }

}
