package com.general.common.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

/**
 * 通用Service
 * 定义了通用的接口方法定义
 *
 * @author fengw
 * @create 2016年11月17日
 */
public interface CommonService<E, D> {
    /**
     * 根据ID判断该数据是否存在
     */
    boolean exists(String id);

    /**
     * 根据Entity 判断该数据是否存在
     */
    boolean exist(E entity);

    /**
     * 根据ID查找对象
     */
    E findById(String id);

    /**
     * 根据ID查找DTO对象
     */
    D findDTOById(String id);

    /**
     * 根据ID查找指定类型DTO对象
     */
    <T> T findDTOById(String id, Class<T> dtoClass);

    /**
     * 根据List ID查找对象
     */
    List<E> findByIds(List<String> ids);

    /**
     * 根据List ID查找对象
     */
    <T> List<T> findDTOByIds(List<String> ids, Class<T> dtoClass);

    /**
     * 根据entity中值自动查询
     */
    List<E> findByEntity(E entity);

    /**
     * 查询结果数量
     */
    Long countByEntity(E entity);

    /**
     * 根据entity中值自动查询
     */
    List<E> findByEntity(E entity, boolean accurateQuery);

    /**
     * 根据entity中值自动查询，并将结果转换为DTO
     */
    List<D> findDTOByEntity(E entity);

    /**
     * 根据DTO中有值的自动查询，并将结果转换为DTO
     * @param accurateQuery 是否精确查找
     */
    List<D> findDTOByEntity(E entity, boolean accurateQuery);

    /**
     * 根据entity中有值的字段进行查询并排序
     * @param accurateQuery 是否精确查找
     */
    List<E> findByEntity(E entity, Sort sort, boolean accurateQuery);

    /**
     * 根据entity中有值的字段进行查询并排序
     */
    List<D> findDTOByEntity(E entity, Sort sort);

    /**
     * 根据entity中有值的字段查询并排序，并将结果转换为指定类型DTO
     */
    <T> List<T> findDTOByEntity(E entity, Sort sort, Class<T> dtoClass);

    /**
     * 根据entity中有值的字段查询并排序，并将结果转换为指定类型DTO
     * @param accurateQuery 是否精确查找
     */
    <T> List<T> findDTOByEntity(E entity, Sort sort, Class<T> dtoClass, boolean accurateQuery);

    /**
     * 根据entity中有值的字段分页查询
     * @param accurateQuery 是否精确查找
     * @param distinct 是否去重
     */
    Page<E> findPage(E entity, Pageable pageable, boolean accurateQuery, boolean distinct);

    /**
     * 根据entity中有值的字段分页查询
     */
    Page<E> findPage(E entity, Pageable pageable);

    /**
     * 根据entity中有值的字段分页查询
     */
    Page<D> findDTOPage(E entity, Pageable pageable, boolean accurateQuery);

    /**
     * 根据entity中有值的字段查询，并将结果转换为指定类型DTO
     */
    <T> Page<T> findDTOPage(E entity, Pageable pageable, boolean accurateQuery, boolean distinct, Class<T> dtoClass);

    /**
     * 根据entity中有值的字段查询，并将结果转换为指定类型DTO
     * @param accurateQuery 是否精确查找
     */
    <T> Page<T> findDTOPage(E entity, Pageable pageable, boolean accurateQuery, Class<T> dtoClass);

    /**
     * 根据entity中有值的字段查询，并将结果转换为指定类型DTO
     */
    <T> Page<T> findDTOPage(E entity, Pageable pageable, Class<T> dtoClass);

    /**
     * 根据entity中有值的字段查询，并将结果转换为指定类型DTO
     */
    Page<D> findDTOPage(E entity, Pageable pageable);

    /**
     * 保存实体类至数据库
     */
    @Transactional
    E save(E entity);

    /**
     * 根据DTO保存至数据库
     */
    @Transactional
    D saveByDTO(D dto);

    /**
     * 根据指定DTO保存至数据库
     */
    <T> T saveByDTO(T dto, Class<T> dtoClass);

    /**
     * 批量保存实体类至数据库
     */
    @Transactional
    List<E> save(List<E> list);

    /**
     * 批量保存DTO至数据库
     */
    @Transactional
    List<D> saveByDTO(List<D> list);

    /**
     * 批量保存指定DTO至数据库
     */
    <T> List<T> saveByDTO(List<T> list, Class<T> dtoClass);

    /**
     * 更新实体至数据库
     */
    Integer update(E entity);
    /**
     * 批量更新实体至数据库
     */
    Integer update(List<E> list);
    /**
     * 由DTO更新实体至数据库
     */
    @Transactional
    Integer updateByDTO(D dto);

    /**
     * 由指定DTO更新实体至数据库
     */
    <T> Integer updateByDTO(T dto, Class<T> dtoClass);

    /**
     * 由DTO List批量更新实体至数据库
     */
    @Transactional
    Integer updateByDTO(List<D> list);

    /**
     * 由指定DTO List批量更新实体至数据库
     */
    <T> Integer updateByDTO(List<T> list, Class<T> dtoClass);

    /**
     * 根据实体删除相应数据
     */
    @Transactional
    Integer delete(D dto);

    /**
     * 根据实体批量删除相应数据
     */
    @Transactional
    Integer delete(List<D> list);

    /**
     * 根据数据ID删除相应数据
     */
    @Transactional
    Integer delete(String id);

    /**
     * 根据数据ID批量删除相应数据
     */
    @Transactional
    Integer deleteByIdArray(String[] ids);

    /**
     * 根据数据ID批量删除相应数据
     */
    @Transactional
    Integer deleteByIdList(List<String> list);

    /**
     * DTO转Entity
     */
    E getEntity(D dto);

    /**
     * 指定类型DTO转Entity
     */
    <T> E getEntityBySourceDTO(T dto);

    /**
     * DTO list转Entity list
     */
    List<E> getEntityList(List<D> dtoList);

    /**
     * 指定类型DTO list转Entity list
     */
    <T> List<E> getEntityListBySourceDTO(List<T> dtoList);

    /**
     * Entity转DTO
     */
    D getDTO(E entity);

    /**
     * Entity转指定类型DTO
     */
    <T> T getDTO(E entity, Class<T> dtoClass);

    /**
     * Entity List 转 DTO list
     */
    List<D> getDTOList(List<E> entityList);

    /**
     * Entity List 转指定类型 DTO List
     */
    <T> List<T> getDTOList(List<E> entityList, Class<T> dtoClass);

    /**
     * Entity Page转DTO Page
     */
    Page<D> getDTOPage(Page<E> page, Pageable pageable);

    /**
     * 获取指定DTO类型的Page
     */
    <T> Page<T> getDTOPage(Page<E> page, Pageable pageable, Class<T> dtoClass);

    /**
     * 导出Excel
     */
    <T> void exportExcel(E entity, Pageable pageable, String excelName, Class<T> dtoClass, HttpServletRequest request, HttpServletResponse response);

    /**
     * 导出Excel
     */
    <T> void exportExcel(List<T> dtoList, String excelName, Class<T> dtoClass, HttpServletRequest request, HttpServletResponse response);
}
