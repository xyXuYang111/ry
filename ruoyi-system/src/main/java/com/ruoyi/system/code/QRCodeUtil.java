package com.ruoyi.system.code;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ruoyi.system.domain.SysUser;
import net.glxn.qrgen.javase.QRCode;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.core.vcard.VCard;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * @Auther: xuyang
 * @Date: 2020/1/30 12:48
 * @Description:
 */
public class QRCodeUtil {

    public static String createQrCodePhoto(String content){
        int width = 300;//二维码图片的宽度
        int height = 300;//二维码图片的高度
        String format = "png";//二维码格式

        String filePath = "E:\\photo\\" + System.currentTimeMillis() + ".jpeg";

        //定义二维码内容参数
        HashMap hints = new HashMap();
        //设置字符集编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //设置容错等级，在这里我们使用M级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //设置边框距
        hints.put(EncodeHintType.MARGIN, 2);
        //生成二维码
        try {
            //指定二维码内容
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,hints);
            //指定生成图片的保存路径
            Path file = new File(filePath).toPath();
            //生成二维码
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public static String createQrCodeVCard(SysUser sysUser){

        String filePath = "E:\\photo\\" + System.currentTimeMillis() + ".jpeg";

        VCard vCard = new VCard();
        vCard.setName(sysUser.getUserName());
        vCard.setAddress(sysUser.getAvatar());
        vCard.setCompany("");
        vCard.setPhoneNumber(sysUser.getPhonenumber());
        vCard.setTitle("java开发");
        vCard.setEmail(sysUser.getEmail());
        vCard.setWebsite("http://g29125u915.wicp.vip/");

        ByteArrayOutputStream bout =
                QRCode.from(vCard)
                        .withCharset("utf-8")
                        .withSize(250, 250)
                        .to(ImageType.PNG)
                        .stream();

        try {
            OutputStream out = new FileOutputStream(filePath);
            bout.writeTo(out);
            out.flush();
            out.close();

        } catch (Exception e){
            e.printStackTrace();
        }
        return filePath;
    }
}
