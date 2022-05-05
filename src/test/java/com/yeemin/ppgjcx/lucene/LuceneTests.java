package com.yeemin.ppgjcx.lucene;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneTests {

    // https://www.cnblogs.com/lqmblog/p/15192571.html

    @Test
    public void createIndex() throws Exception {
        Directory directory = FSDirectory.open(Path.of("lucene/demo"));
        IKAnalyzer ikAnalyzer = new IKAnalyzer();

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(ikAnalyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        Document document = new Document();
        Field field = new TextField("name", "生一鸣", Store.YES);
        document.add(field);

        indexWriter.addDocument(document);
        indexWriter.close();
        directory.close();
    }

    @Test
    public void matchAllDocsQuery() throws IOException {
        Directory directory = FSDirectory.open(Path.of("lucene/demo"));
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        Query query = new MatchAllDocsQuery();
        TopDocs topDocs = searcher.search(query, 10);
        System.out.println("totalHits: " + topDocs.totalHits);

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int doc = scoreDoc.doc;
            Document document = searcher.doc(doc);
            System.out.printf("id: %d, name: %s", doc, document.get("name"));
        }

        reader.close();
        directory.close();
    }

    @Test
    public void termQuery() throws IOException {
        Directory directory = FSDirectory.open(Path.of("lucene/demo"));
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        Query query = new TermQuery(new Term("name", "生"));

        TopDocs topDocs = searcher.search(query, 10);
        System.out.println("totalHits: " + topDocs.totalHits);

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int doc = scoreDoc.doc;
            Document document = searcher.doc(doc);
            System.out.printf("id: %d, name: %s", doc, document.get("name"));
        }

        reader.close();
        directory.close();
    }

    
}
