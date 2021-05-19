package mts.teta.resizer;

import mts.teta.resizer.imageprocessor.BadAttributesException;
import mts.teta.resizer.imageprocessor.ImageProcessor;
import mts.teta.resizer.util.ConsoleAttributes;
import picocli.CommandLine;
import picocli.CommandLine.*;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.concurrent.Callable;

@Command(name = "convert",
        mixinStandardHelpOptions = true,
        version = "convert 0.0.1",
        description = "...",
        sortOptions = false,
        headerHeading = "Usage:%n%n",
        synopsisHeading = "%n",
        descriptionHeading = "%nDescription:%n%n",
        parameterListHeading = "%nParameters:%n",
        optionListHeading = "%nOptions:%n")
public class ResizerApp extends ConsoleAttributes implements Callable<Integer> {

    public static void main(String... args) {
        int exitCode = runConsole(args);
        System.exit(exitCode);
    }

    protected static int runConsole(String[] args) {
        return new CommandLine(new ResizerApp()).execute(args);
    }

    @Override
    public Integer call() throws IOException, BadAttributesException {
        ImageProcessor imageProcessor = new ImageProcessor();
        try {
            imageProcessor.processImage(ImageIO.read(getInputFile()), this);
        } catch (IIOException e){
            throw new IIOException("Can't read input file!");
        }
        return 0;
    }
}
