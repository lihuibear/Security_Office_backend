package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lihui.security_office_backend.model.entity.Score;
import generator.service.ScoreService;
import com.lihui.security_office_backend.mapper.ScoreMapper;
import org.springframework.stereotype.Service;

/**
* @author lihui
* @description 针对表【score(学生分数)】的数据库操作Service实现
* @createDate 2025-03-29 14:24:11
*/
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score>
    implements ScoreService{

}




