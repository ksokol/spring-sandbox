package entity;

import java.util.List;

/**
 * @author Kamill Sokol dev@sokol-web.de
 */
public class Country {

    private Long id;
    private String name;
    private List<String> locales;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getLocales() {
        return locales;
    }

    public void setLocales(List<String> locales) {
        this.locales = locales;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
