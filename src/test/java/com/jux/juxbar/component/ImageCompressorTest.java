package com.jux.juxbar.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ImageCompressorTest {

    private ImageCompressor imageCompressor;

    @BeforeEach
    void setUp() {
        imageCompressor = new ImageCompressor();
    }

    @Test
    @DisplayName("Should compress image and return compressed byte array")
    void compress_ShouldReturnCompressedImage() throws IOException {
        // Given
        BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        byte[] originalImageBytes = baos.toByteArray();

        // When
        byte[] compressedImageBytes = imageCompressor.compress(originalImageBytes, "jpg", 0.5);

        // Then
        assertThat(compressedImageBytes).isNotNull();
        assertThat(compressedImageBytes.length).isLessThan(originalImageBytes.length);
    }

    @Test
    @DisplayName("Should throw Exception when invalid image data is provided")
    void compress_ShouldThrowIOException_WhenInvalidImageData() {
        // Given
        byte[] invalidImageData = new byte[]{1, 2, 3};

        // When & Then
        assertThrows(Exception.class, () -> imageCompressor.compress(invalidImageData, "jpg", 0.5));
    }
}
