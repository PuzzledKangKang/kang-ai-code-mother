package net.pkk.kangaicodemother.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import net.pkk.kangaicodemother.model.dto.chathistory.ChatHistoryQueryRequest;
import net.pkk.kangaicodemother.model.entity.ChatHistory;
import net.pkk.kangaicodemother.model.entity.User;

import java.time.LocalDateTime;

/**
 * 对话历史 服务层。
 *
 * @author 林子康
 * @since 2025-08-14
 */
public interface ChatHistoryService extends IService<ChatHistory> {

    /**
     * 添加对话历史
     *
     * @param appId       应用 ID
     * @param message     消息内容
     * @param messageType 消息类型
     * @param userId      用户 ID
     * @return 是否成功
     */
    boolean addChatMessage(Long appId, String message, String messageType, Long userId);

    /**
     * 根据应用 id 删除对话记录
     *
     * @param appId 应用 id
     * @return 是否成功
     */
    boolean deleteByAppId(Long appId);

    /**
     * 分页查询某 APP 的历史对话记录
     *
     * @param appId          应用 ID
     * @param pageSize       页面条数
     * @param lastCreateTime 上一页的最后一个创建时间
     * @param loginUser      当前登录用户
     * @return 对话历史
     */
    Page<ChatHistory> listAppChatHistoryByPage(Long appId, int pageSize,
                                               LocalDateTime lastCreateTime,
                                               User loginUser);

    /**
     * 加载对话历史到内存
     *
     * @param appId      应用 ID
     * @param chatMemory 对话记忆
     * @param maxCount   最多加载多少条
     * @return 加载成功的条数
     */
    int loadChatHistoryToMemory(Long appId, MessageWindowChatMemory chatMemory, int maxCount);

    /**
     * 构造查询条件
     *
     * @param chatHistoryQueryRequest 对话历史查询请求
     * @return 查询条件
     */
    QueryWrapper getQueryWrapper(ChatHistoryQueryRequest chatHistoryQueryRequest);
}
