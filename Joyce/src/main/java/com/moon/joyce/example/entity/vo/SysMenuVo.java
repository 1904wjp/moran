package com.moon.joyce.example.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Xing Dao Rong
 * @date 2021/10/11 13:39
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuVo implements Serializable {

    private static final long serialVersionUID = 4395940360155139038L;

    private Long id;

    private String menuName;

    private String menuUrl;

    private Long menuOrder;

    private Long parentId;

    private List<SysMenuVo> children;

}
