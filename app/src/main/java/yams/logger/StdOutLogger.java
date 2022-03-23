package yams.logger;

public class StdOutLogger implements Logger {
    @Override
    public void lognln(String str) {
        lognlnSync(str);
    }

    private synchronized static void lognlnSync(String str) {
        System.out.print(str);
    }
}
