import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wq
 * @date 2021/1/11 22:44
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        Class<?> helloClass = null;
        try {
            helloClass = new HelloClassLoader().findClass("Hello");
            Method method = helloClass.getDeclaredMethod("hello");
            method.invoke(helloClass.newInstance());

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        try (InputStream fis = getClass().getClassLoader().getResourceAsStream("Hello.xlass");
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            if (fis == null) {
                throw new IOException("InputStream null");
            }
            int n;
            while ((n = fis.read()) != -1) {
                output.write(~n);
            }
            byte[] byteArray = output.toByteArray();
            return defineClass(name, byteArray, 0, byteArray.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
