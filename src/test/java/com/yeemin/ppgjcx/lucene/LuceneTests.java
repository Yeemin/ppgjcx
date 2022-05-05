package com.yeemin.ppgjcx.lucene;

import java.nio.file.Path;

import com.ossez.analyzer.lucene.IKAnalyzer;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;

public class LuceneTests {

    // https://www.cnblogs.com/lqmblog/p/15192571.html

    @Test
    public void createIndex() throws Exception {
        Directory directory = FSDirectory.open(Path.of("~/lucene/demo"));
        IKAnalyzer ikAnalyzer = new IKAnalyzer();

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(ikAnalyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        Document document = new Document();
        Field field = new TextField("name", "生一鸣", Store.YES);
        document.add(field);

        indexWriter.addDocument(document);
        indexWriter.close();
    }
}
