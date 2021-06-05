package com.general.common.service.impl;

import com.general.common.service.CommonService;
import com.general.core.repository.MyRepository;
import com.general.core.util.MyBeanUtils;
import com.general.core.util.ReflectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
/**
 * CommonService接口实现
 * 如果需要使用该实现，需要继承该Service
 * @author fengw
 * @create 2016年11月17日
 * @param <E>
 */
public class CommonServiceImpl<E, D> implements CommonService<E, D> {
    @Autowired
    private MyRepository<E, String> myRepository;

    public void setRepository(MyRepository<E, String> repository) {
        this.myRepository = repository;
    }

    @Override
    public boolean exists(String id) {
        return myRepository.existsById(id);
    }
    @Override
    public boolean exist(E entity){
        return myRepository.exists(Example.of(entity));
    }
    @Override
    public E findById(String id) {
        if (!StringUtils.isEmpty(id)) {
            Optional<E> optional = myRepository.findById(id);
            // 增加判空条件 不然会抛出 new NoSuchElementException("No value present");
            if (optional != null) {
                try {
                    return optional.get();
                } catch (NoSuchElementException e) {
                    return null;
                }
            } else {
                return  null;
            }
//            return myRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public D findDTOById(String id) {
        return getDTO(findById(id));
    }

    @Override
    public <T> T findDTOById(String id, Class<T> dtoClass) {
        return getDTO(findById(id), dtoClass);
    }

    @Override
    public List<E> findByIds(List<String> ids) {
        List<E> list = new ArrayList<>();
        if (ids.size()>0 && !ids.isEmpty())  {
            for (String id: ids) {
                list.add(findById(id));
            }
        }
        return list;
    }

    @Override
    public <T> List<T> findDTOByIds(List<String> ids, Class<T> dtoClass) {
        return getDTOList(findByIds(ids), dtoClass);
    }

    @Override
    public List<E> findByEntity(E entity) {
        return myRepository.findByEntity(entity);
    }

    @Override
    public Long countByEntity(E entity) {
        return myRepository.countByEntity(entity);
    }

    @Override
    public List<E> findByEntity(E entity, boolean accurateQuery) {
        if (accurateQuery)
            return myRepository.findByEntityAccurate(entity);
        else
            return myRepository.findByEntity(entity);
    }

    @Override
    public List<D> findDTOByEntity(E entity) {
        return findDTOByEntity(entity, true);
    }

    @Override
    public List<D> findDTOByEntity(E entity, boolean accurateQuery) {
        return getDTOList(findByEntity(entity, accurateQuery));
    }

    @Override
    public List<E> findByEntity(E entity, Sort sort, boolean accurateQuery) {
        if (accurateQuery) {
            return sort != null ? myRepository.findByEntityAccurate(entity, sort) : myRepository.findByEntityAccurate(entity);
        } else {
            return sort != null ? myRepository.findByEntity(entity, sort) : myRepository.findByEntity(entity);
        }
    }

    @Override
    public List<D> findDTOByEntity(E entity, Sort sort) {
        return getDTOList(findByEntity(entity, sort, false));
    }

    @Override
    public <T> List<T> findDTOByEntity(E entity, Sort sort, Class<T> dtoClass) {
        return getDTOList(findByEntity(entity, sort, false), dtoClass);
    }

    @Override
    public <T> List<T> findDTOByEntity(E entity, Sort sort, Class<T> dtoClass, boolean accurateQuery) {
        return getDTOList(findByEntity(entity, sort, accurateQuery), dtoClass);
    }

    @Override
    public Page<E> findPage(E entity, Pageable pageable, boolean accurateQuery, boolean distinct) {
        if (accurateQuery)
            return myRepository.findByEntityAccurate(entity, pageable, distinct);
        else
            return myRepository.findByEntity(entity, pageable, distinct);
    }

    @Override
    public Page<E> findPage(E entity, Pageable pageable) {
        return findPage(entity, pageable, false, false);
    }

    @Override
    public Page<D> findDTOPage(E entity, Pageable pageable, boolean accurateQuery) {
        return getDTOPage(findPage(entity, pageable, accurateQuery, false), pageable);
    }
    @Override
    @Transactional
    public Page<D> findDTOPage(E entity, Pageable pageable) {
        return getDTOPage(findPage(entity, pageable), pageable);
    }

    @Override
    public <T> Page<T> findDTOPage(E entity, Pageable pageable, Class<T> dtoClass) {
        return getDTOPage(findPage(entity, pageable), pageable, dtoClass);
    }

    @Override
    public <T> Page<T> findDTOPage(E entity, Pageable pageable, boolean accurateQuery, Class<T> dtoClass) {
        return getDTOPage(findPage(entity, pageable, accurateQuery, false), pageable, dtoClass);
    }

    @Override
    public <T> Page<T> findDTOPage(E entity, Pageable pageable, boolean accurateQuery, boolean distinct, Class<T> dtoClass) {
        return  getDTOPage(findPage(entity, pageable, accurateQuery, distinct), pageable, dtoClass);
    }

    @Override
    public E save(E entity) {
        if (entity != null) {
            try {
//				MyBeanUtils.dealObjectWithIsNull(entity);
                return myRepository.saveAndFlush(entity);
            } catch (DataIntegrityViolationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    public D saveByDTO(D dto) {
        return getDTO(save(getEntity(dto)));
    }
    @Override
    public <T> T saveByDTO(T dto, Class<T> dtoClass) {
        return getDTO(save(getEntityBySourceDTO(dto)), dtoClass);
    }
    @Override
    public List<E> save(List<E> list) {
        if (list != null && !list.isEmpty())
            return myRepository.saveAll(list);
        return null;
    }
    @Override
    public List<D> saveByDTO(List<D> list) {
        return getDTOList(save(getEntityList(list)));
    }
    @Override
    public <T> List<T> saveByDTO(List<T> list, Class<T> dtoClass) {
        return getDTOList(save(getEntityListBySourceDTO(list)), dtoClass);
    }

    @Override
    public Integer update(E entity) {
        if (entity != null) {
            myRepository.saveAndFlush(entity);
            return 1;
        }
        return 0;
    }

    @Override
    public Integer update(List<E> list) {
        int i = 0;
        for (E e: list) {
            i += update(e);
        }
        return i;
    }

    @Override
    public Integer updateByDTO(D dto) {
        return updateByDTOGeneral(dto);
    }

    @Override
    public <T> Integer updateByDTO(T dto, Class<T> dtoClass) {
        return updateByDTOGeneral(dto);
    }

    private Integer updateByDTOGeneral(Object dto) {
        String id = (String) ReflectionUtils.invokeMethod(dto.getClass(), "getId", dto);
        if (!StringUtils.isEmpty(id)) {
            E entity = this.findById(id);
            if (entity != null) {
                MyBeanUtils.mergeProperties(dto, entity);
                if (myRepository.saveAndFlush(entity) != null) {
                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    public Integer updateByDTO(List<D> list) {
        int i = 0;
        for (D d: list) {
            i += updateByDTO(d);
        }
        return i;
    }
    @Override
    public <T> Integer updateByDTO(List<T> list, Class<T> dtoClass) {
        int i = 0;
        for (T d: list) {
            i += updateByDTO(d, dtoClass);
        }
        return i;
    }
    @Override
    public Integer delete(D dto) {
        if (dto != null) {
            E e = getEntity(dto);
            Method method = ReflectionUtils.findMethod(e.getClass(), "setIsDel", Boolean.class);
            if (method !=null ) {
                ReflectionUtils.invokeMethod(method, e,Boolean.TRUE);
                Integer i = update(e);
                return  i;
            } else {
                if (e !=null) {
                    myRepository.delete(e);
                }
            }
        }
        return 0;
    }

    @Override
    public Integer delete(List<D> list) {
        int i = 0;
        if (list != null && !list.isEmpty()) {
            for (D d: list) {
                i += delete(d);
            }
        }
        return i;
    }

    @Override
    public Integer delete(String id) {
        if (!StringUtils.isEmpty(id)) {
            try {
                E e = findById(id);
                Method method = ReflectionUtils.findMethod(e.getClass(), "setIsDel", Boolean.class);
                if (method !=null) {
                    ReflectionUtils.invokeSetterMethod(e.getClass(),"setIsDel",e, Boolean.class,Boolean.TRUE);
                    return update(e);
                } else {
                    // 方法不存在 真删
                    myRepository.deleteById(id);
                    e = findById(id);
                    if (MyBeanUtils.objectIsNull(e)) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public Integer deleteByIdArray(String[] ids) {
        int i = 0;
        if (ids != null && ids.length > 0) {
            for (String str: ids) {
                i += delete(str);
            }
        }
        return i;
    }

    @Override
    public Integer deleteByIdList(List<String> list) {
        int i = 0;
        if (list != null && !list.isEmpty()) {
            for (String str: list) {
                i += delete(str);
            }
        }
        return i;
    }

    @Override
    public List<E> getEntityList(List<D> dtoList) {
        List<E> entityList = new ArrayList<E>();
        if(dtoList!=null){
            for(D dto : dtoList) {
                entityList.add(getEntity(dto));
            }
        }
        return entityList;
    }
    @Override
    public <T> List<E> getEntityListBySourceDTO(List<T> dtoList) {
        List<E> entityList = new ArrayList<>();
        if(dtoList!=null){
            for(T dto : dtoList) {
                entityList.add(getEntityBySourceDTO(dto));
            }
        }
        return entityList;
    }
    @Override
    public E getEntity(D dto) {
        return getEntityFromSourceDTO(dto);
    }
    @Override
    public <T> E getEntityBySourceDTO(T dto) {
        return getEntityFromSourceDTO(dto);
    }
    @Override
    public List<D> getDTOList(List<E> entityList) {
        List<D> dtoList = new ArrayList<D>();
        if(entityList!=null){
            for(E entity : entityList) {
                dtoList.add(getDTO(entity));
            }
        }
        return dtoList;
    }
    @Override
    public D getDTO(E entity) {
        //1、通过反射获取注解“D”（即模型对象）的类类型
        Type[] types = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments();
        @SuppressWarnings("unchecked")
        Class<D> dtoClass = (Class<D>)types [1];
        if(entity==null){
            return null;
        }else{
            D dto = null;
            try {
                dto = dtoClass.newInstance();
                MyBeanUtils.mergeProperties(entity, dto, false);
                // 设置isDel
                dto = setIsDel(dto);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return dto;
        }
    }
    @Override
    public Page<D> getDTOPage(Page<E> page, Pageable pageable) {
        Page<D> dtoPage = new PageImpl<D>(getDTOList(page.getContent()), pageable, page.getTotalElements());
        return dtoPage;
    }

    @Override
    public <T> T getDTO(E entity, Class<T> dtoClass) {
        if(entity == null){
            return null;
        } else {
            T dto = null;
            try {
                dto = dtoClass.newInstance();
                MyBeanUtils.mergeProperties(entity, dto, false);
                // 设置isDel
                dto = setIsDel(dto);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return dto;
        }
    }

    @Override
    public <T> List<T> getDTOList(List<E> entityList, Class<T> dtoClass) {
        List<T> dtoList = new ArrayList<>();
        if(entityList!=null){
            for(E entity : entityList) {
                dtoList.add(getDTO(entity, dtoClass));
            }
        }
        return dtoList;
    }

    @Override
    public <T> Page<T> getDTOPage(Page<E> page, Pageable pageable, Class<T> dtoClass) {
        Page<T> dtoPage = new PageImpl<T>(getDTOList(page.getContent(), dtoClass), pageable, page.getTotalElements());
        return dtoPage;
    }
    @SuppressWarnings("unchecked")
    private E getEntityFromSourceDTO(Object dto) {
        //1、通过反射获取注解“E”（即模型对象）的类类型
        Class<E> entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        E entity = null;
        try {
            entity = (E) entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if(dto==null){
            return null;
        }else{
            entity = MyBeanUtils.copyProperties(dto, entityClass);
            // 设置isDel
            entity  = setIsDel(entity);
        }
        return entity;
    }

    @Override
    public <T> void exportExcel(E entity, Pageable pageable, String excelName,  Class<T> dtoClass, HttpServletRequest request, HttpServletResponse response) {
        List<T> list = findDTOByEntity(entity, pageable.getSort(), dtoClass, false);
        this.exportExcel(list, excelName, dtoClass, request, response);
    }

    @Override
    public <T> void exportExcel(List<T> dtoList, String excelName, Class<T> dtoClass, HttpServletRequest request,
                                HttpServletResponse response) {
        // TODO Auto-generated method stub
        response.setHeader("content-Type", "application/vnd.ms-excel");
        try {
            response.setHeader("Content-Disposition", "attachment;filename= " + new String(excelName.getBytes("gbk"),"iso-8859-1") + ".xls");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), dtoClass, dtoList);
        try {
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  <T>T setIsDel(T t) {
        Method method = ReflectionUtils.findMethod(t.getClass(), "setIsDel", Boolean.class);
        if (method !=null) {
            ReflectionUtils.invokeSetterMethod(t.getClass(),"setIsDel",t, Boolean.class,Boolean.FALSE);
        }
        return  t;
    }
}
