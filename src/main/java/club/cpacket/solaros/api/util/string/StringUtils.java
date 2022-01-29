package club.cpacket.solaros.api.util.string;

/**
 * @author zzurio
 */

public class StringUtils {

    public static boolean contains(String name, String... items) {
        boolean flag = false;

        for (String i : items) {
            if (i.equalsIgnoreCase(name)) {
                flag = true;

                break;
            }
        }

        return flag;
    }
}
