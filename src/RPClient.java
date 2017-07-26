import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;


/**
 * Created by sxiong on 7/26/17.
 */
public class RPClient<T> {
    public static <T> T getRemoteProxyObj(final Class<?> serviceInterface, final InetSocketAddress addr){
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[]{serviceInterface}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = null;
                ObjectInputStream inputStream = null;
                ObjectOutputStream outputStream = null;

                try{
                    socket = new Socket();
                    socket.connect(addr);

                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeUTF(serviceInterface.getName());
                    outputStream.writeUTF(method.getName());
                    outputStream.writeObject(method.getParameterTypes());
                    outputStream.writeObject(args);

                    inputStream = new ObjectInputStream(socket.getInputStream());
                    return inputStream.readObject();
                }finally {
                    if (socket!=null){
                        socket.close();
                    }
                    if (inputStream!=null){
                        inputStream.close();
                    }
                    if (outputStream!=null){
                        outputStream.close();
                    }
                }
            }
        });
    }
}
