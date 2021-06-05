package com.general.common.controller;

import com.general.common.dto.BaseDTO;
import com.general.common.entity.BaseEntity;
import com.general.common.service.CommonService;
import com.general.core.msg.AjaxResult;
import com.general.core.msg.MessageCode;
import com.general.core.page.PageParam;
import com.general.core.query.QueryParam;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Controller通用方法
 */
public abstract class BaseController<E extends BaseEntity> {
    /**
     * 查询符合条件的所有数据
     * @param service
     * @param queryParam
     * @param <S>
     * @return
     */
    public <S extends CommonService> AjaxResult findAll(S service, QueryParam<E> queryParam) {
        return findAll(service, queryParam, null);
    }

    /**
     * 查询符合条件的所有数据,并转换为指定DTO
     * @param service
     * @param queryParam
     * @param <S>
     * @return
     */
    public <S extends CommonService> AjaxResult findAll(S service, QueryParam<E> queryParam, Class dtoClass) {
        try {
            if (dtoClass == null) {
                return AjaxResult.createSuccessResult(service.findDTOByEntity(queryParam.getParam(), queryParam.getSort()));
            }
            return AjaxResult.createSuccessResult(service.findDTOByEntity(queryParam.getParam(), queryParam.getSort(), dtoClass));
        } catch (Exception e) {
            return AjaxResult.createErrorResult(MessageCode.QUERY_FAILED, e);
        }
    }

    /**
     * 查询符合条件的所有数据
     * @param service
     * @param queryParam
     * @param <S>
     * @return
     */
    public <S extends CommonService> AjaxResult findPage(S service, PageParam<E> queryParam) {
        return findPage(service, queryParam, null);
    }

    /**
     * 查询符合条件的数据数量
     * @param service
     * @param entity
     * @param <S>
     * @return
     */
    public <S extends CommonService> AjaxResult countByEntity(S service, E entity) {
        return AjaxResult.createSuccessResult(service.countByEntity(entity));
    }

    /**
     * 查询符合条件的所有数据,并转换为指定DTO
     * @param service
     * @param queryParam
     * @param <S>
     * @return
     */
    public <S extends CommonService> AjaxResult findPage(S service, PageParam<E> queryParam, Class dtoClass) {
        try {
            if (dtoClass == null) {
                return AjaxResult.createSuccessResult(service.findDTOPage(queryParam.getParam(), queryParam.getPageable()));
            }
            return AjaxResult.createSuccessResult(service.findDTOPage(queryParam.getParam(), queryParam.getPageable(), dtoClass));
        } catch (Exception e) {
            return AjaxResult.createErrorResult(MessageCode.QUERY_FAILED, e);
        }
    }

    /**
     * 根据ID查询数据
     * @param service
     * @param id
     * @param <S>
     * @return
     */
    public <S extends CommonService> AjaxResult findOne(S service, String id) {
        return findOne(service, id, null);
    }

    /**
     * 根据ID查询数据,并转换为指定DTO
     * @param service
     * @param id
     * @param <S>
     * @return
     */
    public <S extends CommonService> AjaxResult findOne(S service, String id, Class dtoClass) {
        try {
            if (StringUtils.isEmpty(id)) {
                return AjaxResult.createErrorResult(MessageCode.QUERY_FAILED, "数据ID不能为空");
            }
            if (dtoClass == null) {
                return AjaxResult.createSuccessResult(service.findDTOById(id));
            }
            return AjaxResult.createSuccessResult(service.findDTOById(id, dtoClass));
        } catch (Exception e) {
            return AjaxResult.createErrorResult(MessageCode.QUERY_FAILED, e);
        }
    }

    /**
     * 保存数据
     * @param service
     * @param dto
     * @return
     */
    public <S extends CommonService, D extends BaseDTO> AjaxResult save(S service, D dto) {
        try {
            if (dto == null) {
                return AjaxResult.createErrorResult(MessageCode.SAVE_FAILED, "数据内容不能为空");
            }
            return AjaxResult.createSuccessResult(service.saveByDTO(dto, dto.getClass()));
        } catch (Exception e) {
            return AjaxResult.createErrorResult(MessageCode.SAVE_FAILED, e);
        }
    }

    /**
     * 保存数据
     * @param service
     * @param entity
     * @return
     */
    public <S extends CommonService, E extends BaseEntity> AjaxResult save(S service, E entity) {
        try {
            if (entity == null) {
                return AjaxResult.createErrorResult(MessageCode.SAVE_FAILED, "数据内容不能为空");
            }
            return AjaxResult.createSuccessResult(service.getDTO(service.save(entity)));
        } catch (Exception e) {
            return AjaxResult.createErrorResult(MessageCode.SAVE_FAILED, e);
        }
    }
    /**
     * 更新数据
     * @param service
     * @param dto
     * @return
     */
    public <S extends CommonService, D extends BaseDTO> AjaxResult update(S service, D dto) {
        try {
            if (dto == null || StringUtils.isEmpty(dto.getId())) {
                return AjaxResult.createErrorResult(MessageCode.UPDATE_FAILED, "数据内容不能为空");
            }
            int i = service.updateByDTO(dto, dto.getClass());
            if (i > 0) {
                return AjaxResult.createSuccessResult(service.findDTOById(dto.getId(), dto.getClass()));
            } else {
                return AjaxResult.createErrorResult(MessageCode.UPDATE_FAILED);
            }
        } catch (Exception e) {
            return AjaxResult.createErrorResult(MessageCode.UPDATE_FAILED, e);
        }
    }

    /**
     * 删除数据
     * @param service
     * @param ids 待删除数据ID数组
     * @return
     */
    public <S extends CommonService> AjaxResult delete(S service, List<String> ids) {
        try {
            if (ids == null || ids.size() == 0) {
                return AjaxResult.createErrorResult(MessageCode.DELETE_FAILED, "数据内容不能为空");
            }
            int i = service.deleteByIdList(ids);
            if (i > 0) {
                return AjaxResult.createSuccessResult(null);
            } else {
                return AjaxResult.createErrorResult(MessageCode.DELETE_FAILED);
            }
        } catch (Exception e) {
            return AjaxResult.createErrorResult(MessageCode.DELETE_FAILED, e);
        }
    }
}
