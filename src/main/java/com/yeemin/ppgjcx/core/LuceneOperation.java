package com.yeemin.ppgjcx.core;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

import com.yeemin.ppgjcx.domain.exception.LuceneException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
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

    public void index(Map<String, String> objectMap) {
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

    @Override
    public void start() {
        try {
            directory = FSDirectory.open(Path.of("lucene/" + getClass().getName()));
            ikAnalyzer = new IKAnalyzer();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
