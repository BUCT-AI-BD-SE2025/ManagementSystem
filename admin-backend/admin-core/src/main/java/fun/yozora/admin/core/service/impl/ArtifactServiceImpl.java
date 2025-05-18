package fun.yozora.admin.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.yozora.admin.domain.entity.Artifact;
import fun.yozora.admin.core.service.ArtifactService;
import fun.yozora.admin.repository.mapper.ArtifactMapper;
import org.springframework.stereotype.Service;

/**
* @author Yozor
* @description 针对表【artifact】的数据库操作Service实现
* @createDate 2025-05-19 04:37:44
*/
@Service
public class ArtifactServiceImpl extends ServiceImpl<ArtifactMapper, Artifact>
    implements ArtifactService{

}




