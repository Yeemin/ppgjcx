package com.yeemin.ppgjcx.core;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import com.yeemin.ppgjcx.domain.exception.LuceneException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.wltea.analyzer.lucene.IKAnalyzer;

public abstract class AbstractLuceneBaseDao implements LuceneBaseDao, SmartLifecycle {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Directory directory;

    private IKAnalyzer ikAnalyzer;

    /**
     * 索引数据
     * @param consumer 数据映射
     */
    @Override
    public void index(Consumer<Document> consumer) {
        try (IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(ikAnalyzer))) {
            Document document = new Document();
            consumer.accept(document);
            indexWriter.addDocument(document);
        } catch (IOException e) {
            logger.error("index error", e);
            throw new LuceneException(e);
        }
    }

    /**
     * 校验id是否存在
     * 
     * @param idFieldName  id字段名
     * @param idFieldValue id字段值
     * @return boolean true表示已存在
     */
    @Override
    public boolean checkIdExist(String idFieldName, String idFieldValue) {
        try (DirectoryReader reader = DirectoryReader.open(directory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            Query query = new TermQuery(new Term(idFieldName, idFieldValue));
            TopDocs topDocs = searcher.search(query, 1);
            return topDocs.totalHits.value > 0;
        } catch (IOException e) {
            logger.error("checkIdExist error", e);
            throw new LuceneException(e);
        }
    }

    /**
     * 根据id查询
     * 
     * @param idFieldName  id字段名
     * @param idFieldValue id字段值
     * @param function     映射
     * @return T 单个数据
     */
    @Override
    public <T> T queryById(String idFieldName, String idFieldValue, Function<Document, T> function) {
        try (DirectoryReader reader = DirectoryReader.open(directory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            Query query = new TermQuery(new Term(idFieldName, idFieldValue));
            TopDocs topDocs = searcher.search(query, 2);
            if (topDocs.totalHits.value > 1) {
                throw new LuceneException("too many result by queryById");
            }
            if (topDocs.totalHits.value <= 0) {
                return null;
            }
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            return function.apply(searcher.doc(scoreDocs[0].doc));
        } catch (IOException e) {
            logger.error("queryById error", e);
            throw new LuceneException(e);
        }
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
    @Override
    public <T> List<T> queryListBySingleField(String fieldName, String fieldValue, int maxSize,
            Function<Document, T> function) {
        try (DirectoryReader reader = DirectoryReader.open(directory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            QueryParser queryParser = new QueryParser(fieldName, ikAnalyzer);
            Query query = queryParser.parse(fieldValue);
            TopDocs topDocs = searcher.search(query, maxSize > 1000 ? 1000 : maxSize);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            List<T> list = new ArrayList<>();
            for (ScoreDoc scoreDoc : scoreDocs) {
                int doc = scoreDoc.doc;
                Document document = searcher.doc(doc);
                list.add(function.apply(document));
            }
            return list;
        } catch (IOException | ParseException e) {
            logger.error("queryListBySingleField error", e);
            throw new LuceneException(e);
        }
    }

    /**
     * 根据id更新数据
     * 
     * @param idFieldName id字段名
     * @param idFieldValue id字段值
     * @param consumer 数据内容新增
     */
    @Override
    public void updateById(String idFieldName, String idFieldValue, Consumer<Document> consumer) {
        try (IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(ikAnalyzer));) {
            Document document = new Document();
            consumer.accept(document);
            indexWriter.updateDocument(new Term(idFieldName, idFieldValue), document);
        } catch (Exception e) {
            logger.error("updateById error", e);
            throw new LuceneException(e);
        }
    }

    /**
     * 根据id主键删除数据
     * 
     * @param idFieldName 主键字段名
     * @param idFieldValue 主键字段值
     */
    @Override
    public void deleteById(String idFieldName, String idFieldValue) {
        try (IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(ikAnalyzer));) {
            indexWriter.deleteDocuments(new Term(idFieldName, idFieldValue));
        } catch (Exception e) {
            logger.error("deleteById error", e);
            throw new LuceneException(e);
        }
    }

    /**
     * 删除索引全部数据
     */
    @Override
    public void deleteAll() {
        try (IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(ikAnalyzer));) {
            indexWriter.deleteAll();
        } catch (Exception e) {
            logger.error("deleteAll error", e);
            throw new LuceneException(e);
        }
    }

    @Override
    public void start() {
        if (directory == null || ikAnalyzer == null) {
            try {
                directory = FSDirectory.open(Path.of("lucene/", getDic()));
                ikAnalyzer = new IKAnalyzer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void stop() {
        Optional.ofNullable(directory).ifPresent(directory -> {
            try {
                directory.close();
            } catch (IOException e) {
                logger.error("", e);
            }
        });
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    protected abstract String getDic();

}
