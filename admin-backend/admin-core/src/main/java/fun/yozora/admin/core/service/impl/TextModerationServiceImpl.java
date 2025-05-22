package fun.yozora.admin.core.service.impl;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.tms.v20201229.TmsClient;
import com.tencentcloudapi.tms.v20201229.models.TextModerationRequest;
import com.tencentcloudapi.tms.v20201229.models.TextModerationResponse;
import com.tencentcloudapi.tms.v20201229.models.User;
import fun.yozora.admin.core.service.TextModerationService;
import fun.yozora.admin.domain.dto.UserDTO;
import fun.yozora.admin.domain.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class TextModerationServiceImpl implements TextModerationService {
    @Override
    public String commentModeration(UserDTO user, Comment comment) {
        TextModerationResponse response = callTextModerationApi(user, comment);

        if (response != null) {
            return response.getSuggestion();
        }
        return "Error";
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
