package com.moon.joyce.example.entity.vo;

/**
 * @Author: XingDaoRong
 * @Date: 2022/1/27
 */

import com.moon.joyce.example.entity.Source;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainSource implements Serializable {
    private static final long serialVersionUID = 7527743720707672741L;
    private Long id;
    private Source source;
    private List<Source> sources;
}
