package com.davee.app.QRCode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

@RestController
public class MainController {
	 private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/img/QRCode.png";

		@RequestMapping("/")
	    public String getQRCode(Model model){
	        String github="DAV_EE";

	        byte[] image = new byte[0];
	        try {

	            // Generate and Return QR Code in Byte Array
	            image = QRCodeGenerator.getQRCodeImage(github, 250,250);

	            // Generate and Save QR Code Image in static/image folder
	     QRCodeGenerator.generateQRCodeImage(github,250,250,QR_CODE_IMAGE_PATH);

	        } catch (WriterException | IOException e) {
	            e.printStackTrace();
	        }
	        // Convert Byte Array into Base64 Encode String
	        String qrcode = Base64.getEncoder().encodeToString(image);

	        model.addAttribute("github",github);
	        model.addAttribute("qrcode",qrcode);

//	        read the image
			BufferedImage bufferedImage;
			try {
				bufferedImage = ImageIO.read(new File(QR_CODE_IMAGE_PATH));
				LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
				BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
				Result result = new MultiFormatReader().decode(binaryBitmap);
			  return  result.getText() + " QR CODE has been generated \n\n" + "/images/" ;
			} catch (IOException | NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return qrcode;

			
			
	    }
		
	
	
		
}
