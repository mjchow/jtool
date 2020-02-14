package com.github.mjchow;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.common.BitMatrix;
import javafx.geometry.HorizontalDirection;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Date;

/**
 * Main
 *
 * @author mjchow
 * @since 2020/01/10 11:26
 **/
public class Main {

    public static void main(String[] args) {
        if(args.length < 2) {
            System.out.println("PARAM ERROR");
            return ;
        }
        String method = args[0];
        String arg1 = args[1];
        String arg2 = "";
        if(args.length > 2) {
            arg2 = args[2];
        }

//        String method = "qr";
//        String arg1 = "absfasfas asdf sd c";

        switch (method) {
            case "qr":
                qrCode(arg1);
                break;
            case "ue":
                urlEncode(arg1);
                break;
            case "ud":
                urlDecode(arg1);
                break;
            case "e64":
                encodeBase64(arg1);
                break;
            case "d64":
                decodeBase64(arg1);
                break;
            case "md5":
                md5(arg1);
                break;
//            case "sha1":
//                sha1(arg1);
//                break;
            case "time":
                if(StrUtil.isEmpty(arg2)) {
                    arg2 = "yyyy-MM-dd HH:mm:ss";
                }

                timeFormat(Long.parseLong(arg1), arg2);
                break;

            case "%":
                System.out.println(Integer.valueOf(arg1) % Integer.valueOf(arg2));
                break;
            default:
                System.out.println("NO SUCH METHOD");
        }
    }


    private static void timeFormat(long time, String format) {
        System.out.println(DateUtil.format(new Date(time), format));
    }

    private static void qrCode(String arg) {
        BufferedImage image = QrCodeUtil.generate(arg, 400, 400);
        new ShowImg(image);


//        QrConfig qrConfig = new QrConfig();
//        qrConfig.setHeight(20);
//        qrConfig.setWidth(20);
//        BitMatrix bitMatrix = QrCodeUtil.encode(arg, qrConfig);
//        System.out.println(toAscii(bitMatrix));
    }

    private static void urlEncode(String arg) {
        try {
            String content = URLEncoder.encode(arg, "utf-8");
            System.out.println(content);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private static void urlDecode(String arg) {
        try {
            String content = URLDecoder.decode(arg, "utf-8");
            System.out.println(content);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static void encodeBase64(String arg) {
        String content = Base64.getEncoder().encodeToString(arg.getBytes());
        System.out.println(content);
    }

    private static void decodeBase64(String arg) {
        try {
            String d64Content = new String(Base64.getDecoder().decode(arg), "utf-8");
            System.out.println(d64Content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void md5(String arg) {
        String md5Content = SecureUtil.md5(arg);
        System.out.println(md5Content);
    }

    private static void sha1(String arg) {
        String sha1Content = SecureUtil.sha1(arg);
        System.out.println(sha1Content);
    }

    public static String toAscii(BitMatrix bitMatrix) {
        StringBuilder sb = new StringBuilder();
        System.out.println(bitMatrix.getHeight());
        System.out.println(bitMatrix.getWidth());
        for (int rows = 0; rows < bitMatrix.getHeight(); rows++) {
            for (int cols = 0; cols < bitMatrix.getWidth(); cols++) {
                boolean x = bitMatrix.get(rows, cols);
                if (!x) {
                    // white
                    sb.append("\033[47m  \033[0;39m");
                } else {
                    sb.append("\033[30m  \033[0;39m");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}


class ShowImg extends JFrame {

    private static final long serialVersionUID = 1L;
    private JLabel label;
    private Icon icon;

    ShowImg(BufferedImage image) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
        setLocationRelativeTo(null);
        try {
            setTitle("图像");
            int width = 400;
            int height = 400;
            setSize(width, height);
            label = new JLabel();
            add(label);
            icon = new ImageIcon(image);
        } catch (Exception e) {
            System.out.println("初始化失败" + e.getMessage());
            e.printStackTrace();
        }
        label.setIcon(icon);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = (int) (toolkit.getScreenSize().getWidth() - getWidth()) / 2;
        int y = (int) (toolkit.getScreenSize().getHeight() - getHeight()) / 2;
        setLocation(x, y);
        setVisible(true);
        pack();
    }
}
