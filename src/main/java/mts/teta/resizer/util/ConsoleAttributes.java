package mts.teta.resizer.util;

import picocli.CommandLine.*;

import java.io.File;

public class ConsoleAttributes {
    private File inputFile;
    private Integer resizeWidth = null;
    private Integer resizeHeight = null;
    private Integer quality = null;
    private Integer cropWidth = null;
    private Integer cropHeight = null;
    private Integer axisX = null;
    private Integer axisY = null;
    private Integer blurRadius = null;
    private String format = null;
    private File outputFile;

    @Parameters(description = "Source file path",
            paramLabel = "input-file")
    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    @Option(names = "--resize",
            arity = "2",
            split = " ",
            description = "Required parameters: width and height separated by a space",
            hideParamSyntax = true)
    public void setResizeParameters(Integer[] values) {
        setResizeWidth(values[0]);
        setResizeHeight(values[1]);
    }

    @Option(names = "--quality",
            arity = "1",
            description = "Enter the required image quality",
            hideParamSyntax = true)
    public void setQuality(Integer quality){
        this.quality = quality;
    }

    @Option(names = "--crop",
            arity = "4",
            split = " ",
            description = "Parameters for cropping in the format: width, height, starting point on the X axis, " +
                    "starting point on the Y axis",
            hideParamSyntax = true)
    public void setCropParameters(Integer[] values){
        setCropWidth(values[0]);
        setCropHeight(values[1]);
        setAxisX(values[2]);
        setAxisY(values[3]);
    }

    @Option(names = "--blur",
            arity = "1",
            description = "Blur level")
    public void setBlurRadius(Integer blurRadius){
            this.blurRadius = blurRadius;
    }

    @Option(names = "--format",
            arity = "1",
            description = "Converted file format [jpeg, png]")
    public void setFormat(String format){
        this.format = format;
    }

    @Parameters(description = "The path to the converted file",
            paramLabel = "output-file")
    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public File getInputFile() {
        return inputFile;
    }

    public Integer getResizeWidth() {
        return resizeWidth;
    }

    public void setResizeWidth(Integer resizeWidth) {
        this.resizeWidth = resizeWidth;
    }

    public Integer getResizeHeight() {
        return resizeHeight;
    }

    public void setResizeHeight(Integer resizeHeight) {
        this.resizeHeight = resizeHeight;
    }

    public Integer getQuality() {
        return quality;
    }

    public Integer getCropWidth() {
        return cropWidth;
    }

    public void setCropWidth(Integer cropWidth) {
        this.cropWidth = cropWidth;
    }

    public Integer getCropHeight() {
        return cropHeight;
    }

    public void setCropHeight(Integer cropHeight) {
        this.cropHeight = cropHeight;
    }

    public Integer getAxisX() {
        return axisX;
    }

    public void setAxisX(Integer axisX) {
        this.axisX = axisX;
    }

    public Integer getAxisY() {
        return axisY;
    }

    public void setAxisY(Integer axisY) {
        this.axisY = axisY;
    }

    public Integer getBlurRadius() {
        return blurRadius;
    }

    public String getFormat() {
        return format;
    }

    public File getOutputFile() {
        return outputFile;
    }

}
