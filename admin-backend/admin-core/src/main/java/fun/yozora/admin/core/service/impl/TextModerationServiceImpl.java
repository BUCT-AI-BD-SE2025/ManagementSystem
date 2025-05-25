package fun.yozora.admin.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.tms.v20201229.TmsClient;
import com.tencentcloudapi.tms.v20201229.models.TextModerationRequest;
import com.tencentcloudapi.tms.v20201229.models.TextModerationResponse;
import com.tencentcloudapi.tms.v20201229.models.User;
import fun.yozora.admin.core.service.TextModerationResultService;
import fun.yozora.admin.core.service.TextModerationService;
import fun.yozora.admin.domain.dto.UserDTO;
import fun.yozora.admin.domain.entity.Comment;
import fun.yozora.admin.domain.entity.TextModerationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class TextModerationServiceImpl implements TextModerationService {

    @Autowired
    private TextModerationResultService textModerationResultService;

    @Override
    public String commentModeration(UserDTO user, Comment comment) {
        TextModerationResponse response = callTextModerationApi(user, comment);

        if (response != null) {
            saveModerationResult(response, comment.getContent());
            return response.getSuggestion();
        }
        return "Error";
    }
    private void saveModerationResult(TextModerationResponse response, String rawContent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TextModerationResult result = new TextModerationResult();
            result.setBizType(response.getBizType());
            result.setLabel(response.getLabel());
            result.setSuggestion(response.getSuggestion());

            // 关键词转为 JSON 数组字符串
            if (response.getKeywords() != null && response.getKeywords().length > 0) {
                result.setKeywords(objectMapper.writeValueAsString(response.getKeywords()));
            }

            result.setScore(response.getScore());
            result.setDataId(response.getDataId());
            result.setRequestId(response.getRequestId());
            result.setContent(rawContent); // 原始评论内容
            result.setCreateTime(LocalDateTime.now());

            // 其他字段可选填充
            result.setSubLabel(response.getSubLabel());
            result.setContextText(response.getContextText());

            // 复杂对象也保存为 JSON 字符串
            if (response.getDetailResults() != null) {
                result.setDetailResults(objectMapper.writeValueAsString(response.getDetailResults()));
            }
            if (response.getRiskDetails() != null) {
                result.setRiskDetails(objectMapper.writeValueAsString(response.getRiskDetails()));
            }
            if (response.getSentimentAnalysis() != null) {
                result.setSentimentAnalysis(objectMapper.writeValueAsString(response.getSentimentAnalysis()));
            }

            // 插入数据库
            textModerationResultService.save(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private TextModerationResponse callTextModerationApi(UserDTO userDTO, Comment comment) {
        try {
            // 1. 初始化凭证和客户端
            Credential cred = new Credential(
);

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("tms.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            TmsClient client = new TmsClient(cred, "ap-beijing", clientProfile);

            TextModerationRequest req = new TextModerationRequest();

            User user = new User();

            user.setUserId(userDTO.getUid());
            user.setNickname(userDTO.getNickname());
            req.setUser(user);
            req.setDataId(comment.getCommentId());

            String encodedContent = Base64.getEncoder().encodeToString(comment.getContent().getBytes("UTF-8"));
            req.setContent(encodedContent);

            return client.TextModeration(req);
        } catch (TencentCloudSDKException e) {
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Error Message: " + e.getMessage());
            System.err.println("Request ID: " + e.getRequestId());
            return null;
        } catch (Exception e) {
            System.err.println("Error Message: " + e.getMessage());
            return null;
        }
    }

}
