package net.pkk.kangaicodemother.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import net.pkk.kangaicodemother.model.entity.App;
import net.pkk.kangaicodemother.mapper.AppMapper;
import net.pkk.kangaicodemother.service.AppService;
import org.springframework.stereotype.Service;

/**
 * 应用 服务层实现。
 *
 * @author 林子康
 * @since 2025-08-11
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App>  implements AppService{

}
