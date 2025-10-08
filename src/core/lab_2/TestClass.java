package core.lab_2;

public class TestClass {
    public void publicMethod1(String str) {
        System.out.println("Public method 1: " + str);
    }

    @InvokeTimes(1)
    public void publicMethod2(int a, int b) {
        System.out.println("Public method 2: " + a + ", " + b);
    }

    public void publicMethod3(boolean flag) {
        System.out.println("Public method 3: " + flag);
    }

    protected void protectedMethod1(double value) {
        System.out.println("Protected method 1: " + value);
    }

    @InvokeTimes(2)
    protected void protectedMethod2(String first, String second) {
        System.out.println("Protected method 2: " + first + ", " + second);
    }

    @InvokeTimes(3)
    protected void protectedMethod3(int x, boolean flag, char c) {
        System.out.println("Protected method 3: " + x + ", " + flag + ", " + c);
    }

    private void privateMethod1() {
        System.out.println("Private method 1: no parameters");
    }

    @InvokeTimes(4)
    private void privateMethod2(int value, String str) {
        System.out.println("Private method 2: " + value + ", " + str);
    }

    @InvokeTimes(2)
    private void privateMethod3(long l, float f, double d, boolean b) {
        System.out.println("Private method 3: " + l + ", " + f + ", " + d + ", " + b);
    }
}
