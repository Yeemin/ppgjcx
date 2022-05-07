package com.yeemin.ppgjcx.core;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import com.yeemin.ppgjcx.domain.exception.LuceneException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
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

public abstract class LuceneOperation implements SmartLifecycle {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Directory directory;

    private IKAnalyzer ikAnalyzer;

    protected void index(Map<String, String> objectMap) {
        try (IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(ikAnalyzer))) {
            Document document = new Document();
            objectMap.forEach((k, v) -> {
                document.add(new TextField(k, v, Store.YES));
            });
            indexWriter.addDocument(document);
        } catch (IOException e) {
            logger.error("index data error", e);
            throw new LuceneException(e);
        }
    }

    protected void index(Consumer<Document> consumer) {
        try (IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(ikAnalyzer))) {
            Document document = new Document();
            consumer.accept(document);
            indexWriter.addDocument(document);
        } catch (IOException e) {
            logger.error("index data error", e);
            throw new LuceneException(e);
        }
    }

    protected <T> List<T> queryParser(String key, String value, Function<Document, T> function) {
        try (DirectoryReader reader = DirectoryReader.open(directory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            QueryParser queryParser = new QueryParser(key, new IKAnalyzer());
            Query query = queryParser.parse(value);
            TopDocs topDocs = searcher.search(query, 1000);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            List<T> list = new ArrayList<>();
            for (ScoreDoc scoreDoc : scoreDocs) {
                int doc = scoreDoc.doc;
                Document document = searcher.doc(doc);
                list.add(function.apply(document));
            }
            return list;
        } catch (IOException | ParseException e) {
            logger.error("queryParser error", e);
            throw new LuceneException(e);
        }
    }

    protected <T> List<T> termQuery(String key, String value, Function<Document, T> function) {
        try (DirectoryReader reader = DirectoryReader.open(directory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            Query query = new TermQuery(new Term(key, value));
            TopDocs topDocs = searcher.search(query, 1000);
            System.out.println("totalHits: " + topDocs.totalHits);
            List<T> list = new ArrayList<>();
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : scoreDocs) {
                int doc = scoreDoc.doc;
                Document document = searcher.doc(doc);
                list.add(function.apply(document));
            }
            return list;
        } catch (IOException e) {
            logger.error("queryTerm error", e);
            throw new LuceneException(e);
        }
    }

    protected long countByTermQuery(String key, String value) {
        try (DirectoryReader reader = DirectoryReader.open(directory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            Query query = new TermQuery(new Term(key, value));
            TopDocs topDocs = searcher.search(query, 1000);
            System.out.println("totalHits: " + topDocs.totalHits);
            return topDocs.totalHits.value;
        } catch (IOException e) {
            logger.error("queryTerm error", e);
            throw new LuceneException(e);
        }
    }

    protected void update(String key, String value, Consumer<Document> consumer) {
        try (IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(ikAnalyzer));) {
            Document document = new Document();
            consumer.accept(document);
            indexWriter.updateDocument(new Term(key, value), document);
        } catch (Exception e) {
            logger.error("update error", e);
            throw new LuceneException(e);
        }
    }

    protected void deleteByQuery(String key, String value) {
        try (IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(ikAnalyzer));) {
            indexWriter.deleteDocuments(new Term(key, value));
        } catch (Exception e) {
            logger.error("delete error", e);
            throw new LuceneException(e);
        }
    }

    @Override
    public void start() {
        if (directory == null || ikAnalyzer == null) {
            try {
                directory = FSDirectory.open(Path.of("lucene/" + getClass().getName()));
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
                e.printStackTrace();
            }
        });
    }

    @Override
    public boolean isRunning() {
        return false;
    }

}
