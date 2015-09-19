package home.boot.blocking;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by alex on 9/11/2015.
 */
@RestController
@RequestMapping("/blocking")
public class FileController {
    private static final  String responseString = "{name: 'Max, lastname: 'Mustermann', occupation: 'developer'}";

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    public String getSimpleJson(){
        return responseString;
    }

    @Cacheable("file")
    @RequestMapping(value = "/{file_name}", method = RequestMethod.GET)
    public Resource getFile(@PathVariable("file_name") String fileName) {
        System.out.println("file access on disk");
        return new ClassPathResource(fileName+".html");
    }

    /**
     * Useless as the above does the same much shorter code.
     * @param fileName
     * @return
     * @throws FileNotFoundException
     * @throws URISyntaxException
     */
    @ResponseBody
    @RequestMapping(value = "/nio/{file_name}", method = RequestMethod.GET)
    public String getNioFile(@PathVariable("file_name") String fileName) throws FileNotFoundException, URISyntaxException {
        URI uri = ClassLoader.getSystemResource(fileName + ".html").toURI();
        StringBuffer buffer = new StringBuffer();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(uri))){
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return buffer.toString();
    }
}
