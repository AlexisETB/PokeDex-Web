package ec.edu.uce.DemoPokedex.Optim;

import javafx.concurrent.Task;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class AsyncTaskRunner {
    private static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public static <T> void runAsync(Task<T> task, Consumer<T> onSuccess, Consumer<Throwable> onError) {
        task.setOnSucceeded(e -> onSuccess.accept(task.getValue()));
        task.setOnFailed(e -> onError.accept(task.getException()));
        executor.submit(task);
    }
}