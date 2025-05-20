package fun.yozora.admin.web.controller;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.yozora.admin.core.service.ArtifactService;
import fun.yozora.admin.domain.entity.Artifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String originId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String period,
            @RequestParam(required = false) String material,
            @RequestParam(required = false) String measurement,
            @RequestParam(required = false) String artist,
            @RequestParam(required = false) String creditLine,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String illusion
    )
    {
        QueryWrapper<Artifact> queryWrapper = new QueryWrapper<>();
        if (id !=null&&!id.isEmpty())
            queryWrapper.eq("id", id);
        if(originId !=null&&!originId.isEmpty())
            queryWrapper.eq("origin_id", originId);
        if(title !=null&&!title.isEmpty())
           queryWrapper.like("title", title);
        if(location !=null&&!location.isEmpty())
            queryWrapper.like("location", location);
        if(period !=null&&!period.isEmpty())
            queryWrapper.like("period", period);
        if(material !=null&&!material.isEmpty())
            queryWrapper.like("material", material);
        if (measurement !=null&&!measurement.isEmpty())
            queryWrapper.like("measurement", measurement);
        if(artist !=null&&!artist.isEmpty())
            queryWrapper.like("artist", artist);
        if(creditLine !=null&&!creditLine.isEmpty())
            queryWrapper.like("credit_line", creditLine);
        if(type !=null&&!type.isEmpty())
            queryWrapper.like("type", type);
        if(description !=null&&!description.isEmpty())
            queryWrapper.like("description", description);
        if(illusion !=null&&!illusion.isEmpty())
            queryWrapper.like("illusion", illusion);
        Page<Artifact> artifactPage = artifactService.page(new Page<>(page, pageSize), queryWrapper);
        return SaResult.data(artifactPage);
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
