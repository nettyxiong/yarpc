import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by sxiong on 7/26/17.
 */
public class RPCTest {
    public static void main(String[] args) {
        //on server node
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Server serviceServer = new ServiceCenter(8088);
                    serviceServer.register(HelloService.class,HelloServiceImpl.class);
                    serviceServer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //on client node
        HelloService service = RPClient.getRemoteProxyObj(HelloService.class,new InetSocketAddress("localhost",8088));
        System.out.println(service.sayHi("sxiong"));
        System.out.println(service.print("sxiong"));
    }
}
