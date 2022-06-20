package com.example.jd_demo.entity;


import org.springframework.util.StringUtils;

public class QL {

    private String value;
    private String _id;
    private String created;
    private String status;
    private String timestamp;
    private String position;
    private String name;
    private String remarks;

    public QL() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public String add() {
        StringBuilder s = new StringBuilder();
        s.append("[{\n");
        if (!StringUtils.isEmpty(value)){
            s.append("  \"value\": \"").append(value).append("\",\n");
        }
        if (!StringUtils.isEmpty(remarks)){
            s.append("  \"remarks\": \"").append(remarks).append("\",\n");
        }
        if (!StringUtils.isEmpty(_id)){
            s.append("  \"_id\": \"").append(_id).append("\",\n");
        }

        s.append("  \"name\": \"").append(name).append("\"\n}]");
        return s.toString();
    }

    @Override
    public String toString() {
        return "{" +
                "value='" + value + '\'' +
                ", _id='" + _id + '\'' +
                ", created='" + created + '\'' +
                ", status='" + status + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", position='" + position + '\'' +
                ", name='" + name + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
