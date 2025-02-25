import java.net.*;
import java.util.concurrent.*;
public class Main {
    public static void main(String[] args) {
        String destinationFolder = "C:\\Users\\91984\\OneDrive\\Desktop\\Dev\\Projects\\MultiThreading\\01Multi-threadedFileDownloader\\Files";
        String[] urls = {
            "https://upsc.gov.in/sites/default/files/QP-CGeoScnstP-25-GENERAL-STUDIES-PAPER-I-100225.pdf",
            "https://upsc.gov.in/sites/default/files/QP-CGeoScnstP-25-CHEMISTRY-PAPER-II-100225.pdf",
            "https://upsc.gov.in/sites/default/files/QP-CGeoScnstP-25-GEOPHYSICS-PAPER-II-100225.pdf"
        };

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        try {
            for(String urlStr : urls){
                URL url = new URL(urlStr);
                FileDownloader fileDownloader = new FileDownloader(url, destinationFolder);
                // submit your filedownloader object to the executor service
                // Future represents the result of an asynchronous computation
                Future<String> result = executorService.submit(fileDownloader);
                System.out.println(result.get());
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            executorService.shutdown();
        }
        // The submit() method submits the fileDownloader task to the ExecutorService.
        // The ExecutorService manages a pool of threads and assigns one of the threads to run the fileDownloader task.
        // submit() returns a Future that allows you to interact with the task's result.
        // You can call get() on the Future object to block the current thread until the task completes and the result is available.
        // You can also check if the task is complete or cancelled by calling methods like isDone() or isCancelled() on the Future object.
    }
}
