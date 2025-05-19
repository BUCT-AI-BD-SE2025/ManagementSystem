package fun.yozora.admin.web.controller;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.yozora.admin.core.service.ArtifactService;
import fun.yozora.admin.domain.entity.Artifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artifacts")
public class ArtifactController {

    @Autowired
    private ArtifactService artifactService;


    /**
     * 分页查询文物
     * 示例请求：/artifacts?page=1&size=20
     */
    @GetMapping
    public SaResult getArtifacts(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        return SaResult.data(artifactService.page(new Page<>(page, size)));
    }

    /**
     * 根据 ID 查询文物
     */
    @GetMapping("/{id}")
    public Artifact getArtifactById(@PathVariable Integer id) {
        return artifactService.getById(id);
    }

    /**
     * 新增文物
     */
    @PostMapping
    public boolean createArtifact(@RequestBody Artifact artifact) {
        return artifactService.save(artifact);
    }

    /**
     * 更新文物
     */
    @PutMapping("/{id}")
    public boolean updateArtifact(@PathVariable Integer id, @RequestBody Artifact artifact) {
        artifact.setId(id);
        return artifactService.updateById(artifact);
    }

    /**
     * 删除文物
     */
    @DeleteMapping("/{id}")
    public boolean deleteArtifact(@PathVariable Integer id) {
        return artifactService.removeById(id);
    }
}
