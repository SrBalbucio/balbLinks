package me.balbucio.links.obj;

public class Link {

    private String name;
    private String url;
    private boolean hasPermission;
    private String permission;

    public Link(String name, String url, String permission) {
        this.name = name;
        this.url = url;
        this.permission = permission;
        hasPermission = !permission.equalsIgnoreCase("null");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(boolean hasPermission) {
        this.hasPermission = hasPermission;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
