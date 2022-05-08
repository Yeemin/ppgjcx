package com.yeemin.ppgjcx.core;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import com.alibaba.fastjson2.JSONObject;

import org.apache.lucene.document.Document;

public interface LuceneBaseDao {

    /**
     * 索引数据
     * 
     * @param consumer 数据映射
     */
    public void index(Consumer<Document> consumer);

    /**
     * 校验id是否存在
     * 
     * @param idFieldName  id字段名
     * @param idFieldValue id字段值
     * @return boolean true表示已存在
     */
    boolean checkIdExist(String idFieldName, String idFieldValue);

    /**
     * 根据id查询
     * 
     * @param idFieldName  id字段名
     * @param idFieldValue id字段值
     * @param function     映射
     * @return T 单个数据
     */
    <T> T queryById(String idFieldName, String idFieldValue, Function<Document, T> function);

    /**
     * 根据id查询
     * 
     * @param idFieldName  id字段名
     * @param idFieldValue id字段值
     * @param type         返参类型
     * @return T 单个数据
     */
    default <T> T queryById(String idFieldName, String idFieldValue, Class<T> type) {
        return queryById(idFieldName, idFieldValue, doc -> {
            JSONObject jsonObject = new JSONObject();
            doc.getFields().forEach(field -> jsonObject.fluentPut(field.name(), field.stringValue()));
            return jsonObject.toJavaObject(type);
        });
    }

    /**
     * 根据单个查询条件查询列表
     * 
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @param maxSize    最大数量
     * @param function   映射
     * @return List<T> 数据集合
     */
    <T> List<T> queryListBySingleField(String fieldName, String fieldValue, int maxSize,
            Function<Document, T> function);

    /**
     * 根据单个查询条件查询列表
     * 
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @param maxSize    最大数量
     * @param type       数据类型
     * @return List<T> 数据集合
     */
    default <T> List<T> queryListBySingleField(String fieldName, String fieldValue, int maxSize,
            Class<T> type) {
        return queryListBySingleField(fieldName, fieldValue, doc -> {
            JSONObject jsonObject = new JSONObject();
            doc.getFields().forEach(field -> jsonObject.fluentPut(field.name(), field.stringValue()));
            return jsonObject.toJavaObject(type);
        });
    }

    /**
     * 根据单个查询条件查询列表，最大1000条
     * 
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @param function   映射
     * @return List<T> 数据集合
     */
    default <T> List<T> queryListBySingleField(String fieldName, String fieldValue, Function<Document, T> function) {
        return queryListBySingleField(fieldName, fieldValue, 1000, function);
    }

    /**
     * 根据单个查询条件查询列表，最大1000条
     * 
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @param type       数据类型
     * @return List<T> 数据集合
     */
    default <T> List<T> queryListBySingleField(String fieldName, String fieldValue, Class<T> type) {
        return queryListBySingleField(fieldName, fieldValue, 1000, type);
    }

    /**
     * 查询列表
     * 
     * @param criteria 查询条件
     * @param maxSize  最大查询条数
     * @param function 字段映射
     * @return List<T> 数据集合
     */
    <T> List<T> queryList(Map<String, String> criteria, int maxSize, Function<Document, T> function);

    /**
     * 查询列表
     * 
     * @param criteria 查询条件
     * @param maxSize  最大查询条数
     * @param type     数据类型
     * @return List<T> 数据集合
     */
    default <T> List<T> queryList(Map<String, String> criteria, int maxSize, Class<T> type) {
        return queryList(criteria, maxSize, doc -> {
            JSONObject jsonObject = new JSONObject();
            doc.getFields().forEach(field -> jsonObject.fluentPut(field.name(), field.stringValue()));
            return jsonObject.toJavaObject(type);
        });
    }

    /**
     * 查询列表，最大1000条
     * 
     * @param criteria 查询条件
     * @param function 字段映射
     * @return List<T> 数据集合
     */
    default <T> List<T> queryList(Map<String, String> criteria, Function<Document, T> function) {
        return queryList(criteria, 1000, function);
    }

    /**
     * 查询列表，最大1000条
     * 
     * @param criteria 查询条件
     * @param type     数据类型
     * @return List<T> 数据集合
     */
    default <T> List<T> queryList(Map<String, String> criteria, Class<T> type) {
        return queryList(criteria, 1000, type);
    }

    /**
     * 根据id更新数据
     * 
     * @param idFieldName  id字段名
     * @param idFieldValue id字段值
     * @param consumer     数据内容新增
     */
    void updateById(String idFieldName, String idFieldValue, Consumer<Document> consumer);

    /**
     * 根据id主键删除数据
     * 
     * @param idFieldName  主键字段名
     * @param idFieldValue 主键字段值
     */
    void deleteById(String idFieldName, String idFieldValue);

    /**
     * 删除索引全部数据
     */
    void deleteAll();

}
