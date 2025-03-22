package com.learn;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageDownloader {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\Admin\\Desktop\\fix.txt"));
        String outputDir = "C:\\Users\\Admin\\Desktop\\dw_images\\";

        Path directoryPath = Paths.get(outputDir);
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                System.err.println("Failed to create directory: " + e.getMessage());
                return;
            }
        }

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }

            // url 和 商品名直接是 3个 \t 分隔（手工加下 3个 \t）
            String[] parts = line.split("\\t{3}"); // Split by two or more whitespace characters
            if (parts.length == 2) {
                String imageUrl = parts[0].trim();
                // index > 0的都是商品名
                String productName = parts[1].trim();


                try {
                    downloadImage(imageUrl, productName, outputDir);
                } catch (IOException e) {
                    System.err.println("Error downloading image from " + imageUrl + ": " + e.getMessage());
                }
            } else {
                System.err.println("Skipping invalid line: " + line);
            }
        }

        System.out.println("Image download and renaming process completed.");
    }

    public static void downloadImage(String imageUrl, String productName, String downloadDirectory) throws IOException {
        URL url = new URL(imageUrl);
        String fileExtension = getFileExtension(imageUrl);
        if (fileExtension.isEmpty()) {
            System.err.println("Could not determine file extension for: " + imageUrl);
            return;
        }

        // Sanitize the product name to be a valid filename
        String sanitizedProductName = productName.replaceAll("[^a-zA-Z0-9\\u4E00-\\u9FA5]+", "_");
        String newFileName = sanitizedProductName + fileExtension;
        Path destinationPath = Paths.get(downloadDirectory, newFileName);

        try (InputStream in = url.openStream()) {
            Files.copy(in, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Downloaded and renamed: " + newFileName);
        }
    }

    public static String getFileExtension(String url) {
        String extension = "";
        try {
            URL parsedUrl = new URL(url);
            String path = parsedUrl.getPath();
            int lastDotIndex = path.lastIndexOf('.');
            if (lastDotIndex > 0 && lastDotIndex < path.length() - 1) {
                extension = path.substring(lastDotIndex);
            }
        } catch (Exception e) {
            // Ignore malformed URL exceptions, will be handled in downloadImage
        }
        return extension;
    }
}