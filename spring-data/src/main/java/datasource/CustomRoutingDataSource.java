package datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author Kamill Sokol
 */
public class CustomRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        if(Tenant.getCurrentTenant() == null) {
            return "TENANT1";
        }
        return Tenant.getCurrentTenant().toUpperCase();
    }
}
