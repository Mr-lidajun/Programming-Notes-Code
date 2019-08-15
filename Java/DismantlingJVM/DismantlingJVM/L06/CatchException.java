package l06;

public class CatchException {

    private int tyyBlock;
    private int catchBlock;
    private int finallyBlock;
    private int methodExit;

    public void test() {
        try {
            tyyBlock = 0;
            byte[] bytes = new byte[]{10};
            System.out.println(bytes[8]);
        } catch (IndexOutOfBoundsException e) {
            catchBlock = 1;
            System.out.println(10086 / 0);
        } finally {
            Object obj = null;
            obj.hashCode();
            finallyBlock = 2;
            System.out.println(
                    "block01 catchBlock=" + catchBlock + "，methodExit=" + methodExit + "，finallyBlock="
                            + finallyBlock);
        }
        methodExit = 3;
        System.out.println(
                "block02 catchBlock=" + catchBlock + "，methodExit=" + methodExit + "，finallyBlock="
                        + finallyBlock);
    }

    public static final void main(String[] args) {
        new CatchException().test();
    }
}
