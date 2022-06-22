package joyce.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.Ids;
import com.moon.joyce.commons.annotation.Table;
import com.moon.joyce.example.entity.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 
 * </p>
 *
 * @author Joyce
 * @since 2021-09-25
 */
/*@Entity
@Table(name = "user_type")
@org.hibernate.annotations.Table(appliesTo = "user_type",comment = "角色表")*/
@TableName("user_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_type",content = "用户类型")
public class UserType extends Page {
    private static final long serialVersionUID = 1573373319724501081L;
    /**
     * 身份
     */
    @Ids
    @TableField("id_code")
    /*@Column(name = "id_code",unique = true,columnDefinition = "varchar(64) COMMENT '角色名字'")*/
    private String idCode;

}
