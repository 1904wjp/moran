package joyce.example.service;

import com.moon.joyce.commons.annotation.RedisValueComponet;
import joyce.example.entity.UserType;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Joyce
 * @since 2021-09-25
 */

public interface UserTypeService extends IService<UserType> {
}
