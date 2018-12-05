package com.ppdai.monitor.service.entity;

import java.util.List;
import java.util.Map;

/**
 * @Author haijiang
 * @Description 标签entity类
 * @Date 2018/12/03
 **/
public class TagEntity {

    private Map<String, List<String>> tags;

    public Map<String, List<String>> getTags() {
        return tags;
    }

    public void setTags(Map<String, List<String>> tags) {
        this.tags = tags;
    }
}
