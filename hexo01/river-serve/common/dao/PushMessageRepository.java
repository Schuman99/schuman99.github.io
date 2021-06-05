package com.general.common.dao;

import com.general.common.entity.PushMessage;
import com.general.core.repository.MyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 推送信息Repository
 * @author fengw
 * @create 2017年2月17日
 */
public interface PushMessageRepository extends MyRepository<PushMessage, String> {
    /**
     * 根据消息目标ID的集合查询
     * @param targetIdList
     * @param pageable
     * @return
     * @author fengw
     * @create 2017年2月17日
     */
    Page<PushMessage> findByTargetIdInOrderByCreateTimeDesc(List<String> targetIdList, Pageable pageable);
    /**
     * 根据messagetype 查询
     * @param messageType
     * @param pageable
     * @return
     * @author fengw
     * @create 2017年2月17日
     */
    Page<PushMessage> findByMessageTypeOrderByCreateTimeDesc(String messageType, Pageable pageable);
    /**
     * 根据关联数据ID查询
     * @param relationId
     * @return
     * @author fengw
     * @create 2017年2月18日
     */
    List<PushMessage> findByRelationId(String relationId);

    /**
     * 根据用户 类型 和 是否已读   来查询  所属他的消息
     */
//    @Query(value = "SELECT * FROM push_message WHERE id in ( SELECT push_message_id FROM push_target_user WHERE base_account_id = ?1 AND is_del ='0' AND status IN ?2 ) AND message_type in ?3 AND is_del = '0' ORDER BY create_time DESC",nativeQuery = true)
//    Page<PushMessage> findPushMessage (String userId, List<String> status , List<String> messageType , Pageable pageable);
    @Query(value = "select message from PushMessage message inner join message.pushTargetList pushTarget " +
            "where message.isDel = false " +
            "and pushTarget.isDel = false " +
            "and pushTarget.baseAccount.id = ?1 " +
            "and pushTarget.status in ?2 ")
    Page<PushMessage> findPushMessage (String userId, List<Integer> status, Pageable pageable);
}
