/**
 * Created by sxiong on 7/26/17.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHi(String name) {
        return "Hi," + name;
    }

    @Override
    public String print(String str) {
        return str;
    }
}
