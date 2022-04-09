package crypt;

public class BasicCrypt implements Crypt {
    final int key = 5;

    @Override
    public String encrypt(String s) throws Exception {
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();

        for (char aChar : chars) {
            aChar += key;
            sb.append(aChar);
        }

        return sb.toString();
    }

    @Override
    public String decrypt(String s) throws Exception {
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();

        for (char aChar : chars) {
            aChar -= key;
            sb.append(aChar);
        }

        return sb.toString();
    }
}
