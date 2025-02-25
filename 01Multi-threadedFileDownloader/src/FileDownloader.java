import java.io.*;
import java.net.*;
import java.util.concurrent.*;

class FileDownloader implements Callable<String>{
    
    private URL url;
    private String destinationFolder;
    private String fileName;

    FileDownloader(URL url, String destinationFolder){
        this.url = url;
        this.destinationFolder = destinationFolder;
        this.fileName = new File(url.getFile()).getName(); 
    }
    
    @Override
    public String call(){
        try {
            File folder = new File(destinationFolder);
            if (!folder.exists()) {
                folder.mkdirs(); 
            }
            // use try to open streams, in case of exceptions, itll automatically close
            try (InputStream inputStream = url.openStream()) {
                // creates a new File object, outputFile, in the specified destinationFolder
                File outputFile = new File(destinationFolder, fileName);
                try (OutputStream outputStream = new FileOutputStream(outputFile)) {
                    // temporarily hold data read from the input stream
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    // while loop continues reading data from the ip stream into the buffer until no more data is left to read.
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            }
            return "Download successful: " + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "Download failed: " + fileName;
        }
    }
}