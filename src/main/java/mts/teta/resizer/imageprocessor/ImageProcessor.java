package mts.teta.resizer.imageprocessor;

import marvin.image.MarvinImage;
import mts.teta.resizer.ResizerApp;
import net.coobird.thumbnailator.Thumbnails;
import org.marvinproject.image.blur.gaussianBlur.GaussianBlur;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {

    public void processImage(BufferedImage bufferedImage, ResizerApp app) throws BadAttributesException, IOException {
        BufferedImage tempBufferedImg = createTempBufferedImage(bufferedImage);

        if (app.getFormat() == null) {
            String format = getFileExtension(app.getInputFile());
            app.setFormat(format);

        } else {
            String format = app.getFormat().toLowerCase();
            if(!format.equals("jpeg") && !format.equals("png") && !format.equals("jpg")) {
                throw new BadAttributesException("Please check params!");
            }
        }

        if (app.getAxisY() != null && app.getAxisX() != null && app.getCropWidth() != null && app.getCropHeight() != null) {
            tempBufferedImg = cropFile(tempBufferedImg, app);
        }

        if (app.getResizeHeight() != null && app.getResizeWidth() != null) {
            tempBufferedImg = resize(tempBufferedImg, app.getResizeWidth(), app.getResizeHeight());
        }

        if (app.getQuality() != null) {
            tempBufferedImg = quality(tempBufferedImg, app.getQuality());
        }

        if (app.getBlurRadius() != null) {
            tempBufferedImg = blur(tempBufferedImg, app.getBlurRadius());
        }

        saveImage(tempBufferedImg, app.getFormat(), app.getOutputFile());

    }

    private BufferedImage cropFile(BufferedImage bufferedImage, ResizerApp resizerApp) throws BadAttributesException {
        BufferedImage copyOfImage;
        try {
            BufferedImage temp = bufferedImage.getSubimage(
                    resizerApp.getAxisX(),
                    resizerApp.getAxisY(),
                    resizerApp.getCropWidth(),
                    resizerApp.getCropHeight());

            copyOfImage = createTempBufferedImage(temp);
        } catch (RasterFormatException e){
            throw new BadAttributesException("Please check params!");
        }
        return copyOfImage;
    }

    private BufferedImage resize(BufferedImage bufferedImage, Integer width, Integer height) throws IOException, BadAttributesException {
        if (width >= 0 && height >= 0) {
            return Thumbnails.of(bufferedImage)
                    .size(width, height)
                    .keepAspectRatio(false)
                    .asBufferedImage();
        } else {
            throw new BadAttributesException("Please check params!");
        }
    }

    private BufferedImage blur(BufferedImage bufferedImage, Integer blurRadius) throws BadAttributesException {
        if (blurRadius >= 0) {
            MarvinImage image = new MarvinImage();
            image.setBufferedImage(bufferedImage);

            GaussianBlur gaussianBlur = new GaussianBlur();
            gaussianBlur.load();
            gaussianBlur.setAttributes("radius", blurRadius);

            gaussianBlur.process(image.clone(), image);
            image.update();

            return image.getBufferedImage();
        } else {
            throw new BadAttributesException("Please check params!");
        }
    }

    private BufferedImage quality(BufferedImage bufferedImage, Integer quality) throws IOException, BadAttributesException {
        if(quality > 0 && quality <= 100) {
            return Thumbnails.of(bufferedImage)
                    .scale(1f)
                    .outputQuality((float) quality / 100)
                    .asBufferedImage();
        } else {
            throw new BadAttributesException("Please check params!");
        }
    }

    private BufferedImage createTempBufferedImage(BufferedImage bufferedImage) throws BadAttributesException {
        try {
            BufferedImage copyOfImage = new BufferedImage(
                    bufferedImage.getWidth(),
                    bufferedImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);

            Graphics g = copyOfImage.createGraphics();
            g.drawImage(bufferedImage, 0, 0, null);
            return copyOfImage;
        } catch (NullPointerException e){
            throw new BadAttributesException("Input file is not image");
        }
    }

    private void saveImage(BufferedImage bufferedImage, String format, File outputFile) throws IOException {
        Thumbnails
                .of(bufferedImage)
                .scale(1f)
                .toFile(new File(getFilePath(outputFile)+"."+format));
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return null;
        }
        return name.substring(lastIndexOf+1);
    }

    private String getFilePath(File file) {
        String name = file.toString();
        int index = name.lastIndexOf(".");
        if (index == -1) {
            return name;
        } else {
            return name.substring(0, index);
        }
    }
}
