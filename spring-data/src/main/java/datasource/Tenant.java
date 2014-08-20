package datasource;

/**
 * @author Kamill Sokol
 */
public class Tenant {

    private static final ThreadLocal<String> TENANT = new ThreadLocal<>();

    public static void setCurrentTenant(String tenant) {
        TENANT.set(tenant);
    }

    public static String getCurrentTenant() {
        return TENANT.get();
    }

    public static void clearCurrentTenant() {
        TENANT.remove();
    }
}
