package ru.strobo.gps.data.ent;

public class Config {

    private String devId = "";
    private String host = "";
    private String port = "";

    public Config() {
    }

    public Config(String devId, String host, String port) {
        this.devId = devId;
        this.host = host;
        this.port = port;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
