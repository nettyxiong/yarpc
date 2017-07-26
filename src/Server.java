import java.io.IOException;

/**
 * Created by sxiong on 7/26/17.
 */
public interface Server {
    public void stop();

    public void start() throws IOException;

    public void register(Class serviceInterface,Class impl);

    public boolean isRunning();

    public int getPort();
}
