package com.moon.joyce.example.functionality.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author Xing Dao Rong
 * @date 2021/10/13 11:04
 * @desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageComponent {
    private String name;
    private Map<String,String> params;
    private String backgroundUrl;
    private String backgroundType;
    private String backgroundColor;

/*  public PageComponent() {
    }


    public PageComponent(String name, Map<String, String> params, String backgroundUrl, String backgroundType, String backgroundColor) {
        this.name = name;
        this.params = params;
        this.backgroundUrl = backgroundUrl;
        this.backgroundType = backgroundType;
        this.backgroundColor = backgroundColor;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String getBackgroundType() {
        return backgroundType;
    }

    public void setBackgroundType(String backgroundType) {
        this.backgroundType = backgroundType;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "PageComponent{" +
                "name='" + name + '\'' +
                ", params=" + params +
                ", backgroundUrl='" + backgroundUrl + '\'' +
                ", backgroundType='" + backgroundType + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
                '}';
    }
    */

}
