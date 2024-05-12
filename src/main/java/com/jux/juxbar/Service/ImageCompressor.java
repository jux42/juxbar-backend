package com.jux.juxbar.Service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

@Service
public class ImageCompressor {

    public byte[] compress(byte[] image, String format) throws IOException {

            ByteArrayInputStream bais = new ByteArrayInputStream(image);
            BufferedImage bufferedImage = ImageIO.read(bais);

            ByteArrayOutputStream compressed = new ByteArrayOutputStream();
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(format);
            ImageWriter writer = writers.next();

            ImageOutputStream ios = ImageIO.createImageOutputStream(compressed);
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.5f); // 0.5 pour 50% de compression

            writer.write(null, new javax.imageio.IIOImage(bufferedImage, null, null), param);

            // Fermer les flux
            ios.flush();
            writer.dispose();
            ios.close();

            return compressed.toByteArray();

        }

}
